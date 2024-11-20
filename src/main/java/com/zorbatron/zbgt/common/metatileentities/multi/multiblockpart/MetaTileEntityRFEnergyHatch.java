package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import static gregtech.api.capability.FeCompat.*;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.FeCompat;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.PipelineUtil;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

public class MetaTileEntityRFEnergyHatch extends MetaTileEntityMultiblockPart
                                         implements IMultiblockAbilityPart<IEnergyContainer> {

    private final boolean isExportHatch;

    private final EUContainer euContainer;
    private final FEContainer feContainer;

    private long storedEU = 0;
    private final long maxStoredEU = 10_000_000;

    public MetaTileEntityRFEnergyHatch(ResourceLocation metaTileEntityId, boolean isExportHatch) {
        super(metaTileEntityId, GTValues.LV);
        this.isExportHatch = isExportHatch;

        euContainer = new EUContainer();
        feContainer = new FEContainer();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityRFEnergyHatch(metaTileEntityId, isExportHatch);
    }

    @Override
    public void update() {
        super.update();

        if (isExportHatch) {
            TileEntity te = getNeighbor(getFrontFacing());
            if (te == null) return;
            IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, getFrontFacing().getOpposite());
            if (energyStorage == null) return;
            euContainer.removeEnergy(FeCompat.insertEu(energyStorage, storedEU));
        }
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            (isExportHatch ? Textures.CONVERTER_FE_OUT : Textures.CONVERTER_FE_IN).renderSided(getFrontFacing(),
                    renderState, translation, PipelineUtil.color(pipeline, GTValues.VC[getTier()]));
        }
    }

    @Override
    public MultiblockAbility<IEnergyContainer> getAbility() {
        return isExportHatch ? MultiblockAbility.SUBSTATION_OUTPUT_ENERGY : MultiblockAbility.SUBSTATION_INPUT_ENERGY;
    }

    @Override
    public void registerAbilities(List<IEnergyContainer> abilityList) {
        abilityList.add(euContainer);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(feContainer);
        }

        return super.getCapability(capability, side);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        storedEU = data.getLong("StoredEU");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        data.setLong("StoredEU", storedEU);

        return data;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class EUContainer implements IEnergyContainer {

        @Override
        public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
            return 0;
        }

        @Override
        public boolean inputsEnergy(EnumFacing side) {
            return false;
        }

        @Override
        public long changeEnergy(long differenceAmount) {
            if (differenceAmount == 0) return 0;
            return differenceAmount > 0 ? addEnergy(differenceAmount) : removeEnergy(-differenceAmount);
        }

        @Override
        public long addEnergy(long energyToAdd) {
            if (energyToAdd <= 0) return 0;
            long original = energyToAdd;

            long change = Math.min(getEnergyCapacity() - storedEU, energyToAdd);
            storedEU += change;
            energyToAdd -= change;

            return original - energyToAdd;
        }

        @Override
        public long removeEnergy(long energyToRemove) {
            if (energyToRemove <= 0) return 0;
            long change = Math.min(storedEU, energyToRemove);
            storedEU -= change;
            return change;
        }

        @Override
        public long getEnergyStored() {
            return storedEU;
        }

        @Override
        public long getEnergyCapacity() {
            return maxStoredEU;
        }

        @Override
        public long getInputAmperage() {
            return 0;
        }

        @Override
        public long getInputVoltage() {
            return 0;
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class FEContainer implements IEnergyStorage {

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            if (isExportHatch || maxReceive <= 0) return 0;
            int received = Math.min(getMaxEnergyStored() - getEnergyStored(), maxReceive);
            received -= received % ratio(true); // avoid rounding issues
            if (!simulate) storedEU += toEu(received, ratio(true));
            return received;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            if (!isExportHatch || maxExtract <= 0) return 0;
            int extracted = Math.min(getEnergyStored(), maxExtract);
            extracted -= extracted % ratio(false);
            if (!simulate) storedEU -= toEu(extracted, ratio(true));
            return extracted;
        }

        @Override
        public int getEnergyStored() {
            return toFeBounded(storedEU, ratio(false), Integer.MAX_VALUE);
        }

        @Override
        public int getMaxEnergyStored() {
            return toFeBounded(maxStoredEU, ratio(false), Integer.MAX_VALUE);
        }

        @Override
        public boolean canExtract() {
            return isExportHatch;
        }

        @Override
        public boolean canReceive() {
            return !isExportHatch;
        }
    }
}
