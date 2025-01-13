package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.capability.impl.InfiniteFluidTank;
import com.zorbatron.zbgt.api.render.ZBGTTextures;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IDataStickIntractable;
import gregtech.api.capability.impl.FilteredItemHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MTECreativeFluidHatch extends MetaTileEntityMultiblockNotifiablePart implements
                                   IMultiblockAbilityPart<IFluidTank>, IDataStickIntractable {

    private final InfiniteFluidTank fluidTank;
    private int slotLimit = Integer.MAX_VALUE;

    public MTECreativeFluidHatch(ResourceLocation metaTileEntityId, boolean isExportHatch) {
        super(metaTileEntityId, GTValues.MAX, isExportHatch);
        this.fluidTank = new InfiniteFluidTank(this, isExportHatch, () -> slotLimit);
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTECreativeFluidHatch(metaTileEntityId, isExportHatch);
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return !isExportHatch;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166 + 20);

        PhantomFluidWidget tankWidget = new PhantomFluidWidget(69, 52, 18, 18, this.fluidTank::getFluid,
                this.fluidTank::setFluid).showTip(false).setBackgroundTexture(null);

        builder.image(7, 16, 81, 55, GuiTextures.DISPLAY)
                .widget(new ImageWidget(91, 36, 14, 15, GuiTextures.TANK_ICON))
                .widget(new SlotWidget(exportItems, 0, 90, 53, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY));

        final int textYPos = 80;
        builder.widget(new ImageWidget(7, textYPos, 18 * 4, 20, GuiTextures.DISPLAY)
                .setTooltip("zbgt.machine.super_input_bus.slot_limit"));
        builder.widget(new TextFieldWidget2(9, textYPos + 5, 18 * 4, 16,
                () -> String.valueOf(this.slotLimit),
                (string) -> {
                    if (!string.isEmpty()) {
                        this.slotLimit = Integer.parseInt(string);
                    }
                    this.fluidTank.onContentsChangedButPublic();
                }).setMaxLength(10).setNumbersOnly(1, Integer.MAX_VALUE));

        return builder.label(6, 6, getMetaFullName())
                .widget(new SimpleTextWidget(11, 20, "", 0xFFFFFF, getFluidNameText(this.fluidTank)).setCenter(false))
                .widget(tankWidget)
                .widget(new FluidContainerSlotWidget(importItems, 0, 90, 16, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 20)
                .build(getHolder(), entityPlayer);
    }

    private Supplier<String> getFluidNameText(InfiniteFluidTank fluidTank) {
        return () -> fluidTank.getFluid() != null ? fluidTank.getFluid().getLocalizedName() : "";
    }

    @Override
    public MultiblockAbility<IFluidTank> getAbility() {
        return MultiblockAbility.IMPORT_FLUIDS;
    }

    @Override
    public void registerAbilities(List<IFluidTank> abilityList) {
        abilityList.add(fluidTank);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && !isExportHatch) {
            fillContainerFromInternalTank(fluidTank);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, @NotNull Matrix4 translation,
                                     IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            Textures.FLUID_HATCH_INPUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
            ZBGTTextures.SWIRLY_INFINITY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            // allow both importing and exporting from the tank
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank);
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, fluidTank);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new FilteredItemHandler(this).setFillPredicate(
                FilteredItemHandler.getCapabilityFilter(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gregtech.creative_tooltip.1") + TooltipHelper.RAINBOW +
                I18n.format("gregtech.creative_tooltip.2") + I18n.format("gregtech.creative_tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.me.copy_paste.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.slotLimit);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.slotLimit = buf.readInt();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        this.fluidTank.writeToNBT(data);
        data.setInteger("SlotLimit", this.slotLimit);

        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        this.fluidTank.readFromNBT(data);
        if (data.hasKey("SlotLimit")) {
            this.slotLimit = data.getInteger("SlotLimit");
        }

        super.readFromNBT(data);
    }

    @Override
    public void onDataStickLeftClick(EntityPlayer player, ItemStack dataStick) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("CreativeFluidHatch", writeConfigToTag());
        dataStick.setTagCompound(tag);
        dataStick.setTranslatableName("zbgt.machine.creative_reservoir_hatch.data_stick.name");
        player.sendStatusMessage(new TextComponentTranslation("gregtech.machine.me.import_copy_settings"), true);
    }

    private NBTTagCompound writeConfigToTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("SlotLimit", this.slotLimit);

        return fluidTank.writeToNBT(tag);
    }

    @Override
    public boolean onDataStickRightClick(EntityPlayer player, ItemStack dataStick) {
        NBTTagCompound tag = dataStick.getTagCompound();

        if (tag == null || !tag.hasKey("CreativeFluidHatch")) return false;

        readConfigFromTag(tag.getCompoundTag("CreativeFluidHatch"));
        player.sendStatusMessage(new TextComponentTranslation("gregtech.machine.me.import_paste_settings"), true);

        return true;
    }

    private void readConfigFromTag(NBTTagCompound tag) {
        fluidTank.setFluid(FluidStack.loadFluidStackFromNBT(tag));
        this.slotLimit = tag.getInteger("SlotLimit");
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
