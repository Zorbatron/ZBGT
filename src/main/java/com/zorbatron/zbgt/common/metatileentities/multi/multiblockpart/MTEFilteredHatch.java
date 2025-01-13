package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import com.zorbatron.zbgt.api.render.ZBGTTextures;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.impl.FilteredItemHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

public class MTEFilteredHatch extends MetaTileEntityMultiblockPart
                              implements IMultiblockAbilityPart<IFluidTank>, IControllable {

    private final MultiblockAbility<IFluidTank> multiblockAbility;
    private final Supplier<FluidStack> filter;
    private final int tankCapacity;

    private final FluidTank tank;

    private boolean workingEnabled = true;

    public MTEFilteredHatch(ResourceLocation metaTileEntityId, int tier,
                            MultiblockAbility<IFluidTank> multiblockAbility, Supplier<FluidStack> filter,
                            int tankCapacity) {
        super(metaTileEntityId, tier);
        this.multiblockAbility = multiblockAbility;
        this.filter = filter;
        this.tankCapacity = tankCapacity;

        this.tank = new FluidTank(tankCapacity) {

            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return fluid.isFluidEqual(filter.get());
            }
        };

        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTEFilteredHatch(metaTileEntityId, getTier(), multiblockAbility, filter, tankCapacity);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, tank);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new FilteredItemHandler(this, 1).setFillPredicate(
                FilteredItemHandler.getCapabilityFilter(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new GTItemStackHandler(this, 1);
    }

    @Override
    public void update() {
        super.update();

        if (!getWorld().isRemote && workingEnabled) {
            fillInternalTankFromFluidContainer(tank);
            pullFluidsFromNearbyHandlers(getFrontFacing());
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        // Create base builder/widget references
        ModularUI.Builder builder = ModularUI.defaultBuilder();
        TankWidget tankWidget = new TankWidget(tank, 69, 52, 18, 18)
                .setAlwaysShowFull(true).setDrawHoveringText(false).setContainerClicking(true, true);

        builder.image(7, 16, 81, 55, GuiTextures.DISPLAY)
                .widget(new ImageWidget(91, 36, 14, 15, GuiTextures.TANK_ICON))
                .widget(new SlotWidget(exportItems, 0, 90, 53, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY));

        // Add general widgets
        return builder.label(6, 6, getMetaFullName())
                .label(11, 20, "gregtech.gui.fluid_amount", 0xFFFFFF)
                .widget(new AdvancedTextWidget(11, 30, list -> {
                    TextComponentTranslation translation = tankWidget.getFluidTextComponent();
                    if (translation != null) list.add(translation);
                }, 0xFFFFFF))
                .widget(new AdvancedTextWidget(11, 40, list -> {
                    String amount = tankWidget.getFormattedFluidAmount();
                    if (!amount.equals("0")) {
                        list.add(new TextComponentString(amount));
                    }
                }, 0xFFFFFF))
                .widget(tankWidget)
                .widget(new FluidContainerSlotWidget(importItems, 0, 90, 16, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY))
                .bindPlayerInventory(entityPlayer.inventory)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public void setWorkingEnabled(boolean workingEnabled) {
        this.workingEnabled = workingEnabled;
        World world = getWorld();
        if (world != null && !world.isRemote) {
            writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(workingEnabled));
        }
    }

    @Override
    public boolean isWorkingEnabled() {
        return workingEnabled;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }

        return super.getCapability(capability, side);
    }

    @Override
    public MultiblockAbility<IFluidTank> getAbility() {
        return multiblockAbility;
    }

    @Override
    public void registerAbilities(List<IFluidTank> abilityList) {
        abilityList.add(tank);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        ZBGTTextures.FLUID_INPUT_BLACK.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(workingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.workingEnabled = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);

        if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.workingEnabled = buf.readBoolean();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        data.setBoolean("WorkingEnabled", workingEnabled);

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        workingEnabled = data.getBoolean("WorkingEnabled");
    }
}
