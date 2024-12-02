package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import static gregtech.api.capability.FeCompat.*;
import static gregtech.api.capability.GregtechDataCodes.SYNC_TILE_MODE;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.FeCompat;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

public class MetaTileEntityRFEnergyHatch extends MetaTileEntityMultiblockPart
                                         implements IMultiblockAbilityPart<IEnergyContainer> {

    private final boolean isExportHatch;
    private boolean allSideAccess;

    private final EUContainer euContainer;
    private final FEContainer feContainer;

    private long storedEU = 0;
    private final long maxStoredEU;

    public MetaTileEntityRFEnergyHatch(ResourceLocation metaTileEntityId, int tier, boolean isExportHatch) {
        super(metaTileEntityId, tier);

        this.isExportHatch = isExportHatch;
        this.allSideAccess = true;
        this.maxStoredEU = GTValues.V[tier];

        euContainer = new EUContainer();
        feContainer = new FEContainer();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityRFEnergyHatch(metaTileEntityId, getTier(), isExportHatch);
    }

    @Override
    public void update() {
        super.update();

        if (isExportHatch) {
            if (allSideAccess) {
                for (EnumFacing side : EnumFacing.values()) {
                    pushEnergy(side);
                }
            } else {
                pushEnergy(getFrontFacing());
            }
        }
    }

    private void pushEnergy(EnumFacing direction) {
        TileEntity te = getNeighbor(direction);
        if (te == null) return;
        IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite());
        if (energyStorage == null) return;
        euContainer.removeEnergy(insertEuBounded(energyStorage, storedEU, Integer.MAX_VALUE));
    }

    /**
     * Copied from {@link FeCompat#insertEu(IEnergyStorage, long)} but with {@link FeCompat#toFeBounded(long, int, int)}
     * instead of {@link FeCompat#toFe(long, int)}.
     */
    private long insertEuBounded(IEnergyStorage storage, long amountEU, int max) {
        int euToFeRatio = ratio(false);
        int feSent = storage.receiveEnergy(toFeBounded(amountEU, euToFeRatio, max), true);
        return toEu(storage.receiveEnergy(feSent - (feSent % euToFeRatio), false), euToFeRatio);
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
            for (EnumFacing facing : EnumFacing.values()) {
                if (!allSideAccess && facing != getFrontFacing()) continue;
                (isExportHatch ? Textures.CONVERTER_FE_OUT : Textures.CONVERTER_FE_IN)
                        .renderSided(facing, renderState, translation, pipeline);
            }
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
            if (allSideAccess || side == null || side == getFrontFacing()) {
                return CapabilityEnergy.ENERGY.cast(feContainer);
            }
        }

        return super.getCapability(capability, side);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);

        tooltip.add(I18n.format("zbgt.machine.rf_hatch.all_side_access"));

        if (isExportHatch) {
            tooltip.add(I18n.format("zbgt.machine.rf_hatch.fe_out", feContainer.getMaxEnergyStored()));
        } else {
            tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", GTValues.V[getTier()],
                    GTValues.VNF[getTier()]));
            tooltip.add(I18n.format("gregtech.universal.tooltip.amperage_in", 1));
        }

        tooltip.add(I18n.format("zbgt.machine.rf_hatch.energy_storage_capacity", feContainer.getMaxEnergyStored()));
        tooltip.add(I18n.format("gregtech.universal.disabled"));
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                      CuboidRayTraceResult hitResult) {
        if (getWorld().isRemote) {
            scheduleRenderUpdate();
            return true;
        }

        if (allSideAccess) {
            setSideAccess(false);
            playerIn.sendMessage(new TextComponentTranslation("zbgt.machine.rf_hatch.all_side_access.off"));
        } else {
            setSideAccess(true);
            playerIn.sendMessage(new TextComponentTranslation("zbgt.machine.rf_hatch.all_side_access.on"));
        }

        return true;
    }

    private void setSideAccess(boolean allSideAccess) {
        this.allSideAccess = allSideAccess;
        if (!getWorld().isRemote) {
            writeCustomData(GregtechDataCodes.SYNC_TILE_MODE, b -> b.writeBoolean(allSideAccess));
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == SYNC_TILE_MODE) {
            this.allSideAccess = buf.readBoolean();
            scheduleRenderUpdate();
        }
        super.receiveCustomData(dataId, buf);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        buf.writeBoolean(allSideAccess);
        super.writeInitialSyncData(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        this.allSideAccess = buf.readBoolean();
        super.receiveInitialSyncData(buf);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        storedEU = data.getLong("StoredEU");
        allSideAccess = data.getBoolean("AllSideAccess");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        data.setLong("StoredEU", storedEU);
        data.setBoolean("AllSideAccess", allSideAccess);

        return data;
    }

    @Override
    public boolean canPartShare() {
        return false;
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
            return 1;
        }

        @Override
        public long getInputVoltage() {
            return GTValues.V[getTier()];
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
