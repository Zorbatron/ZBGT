package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.capability.impl.InfiniteItemStackHandler;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.api.util.ZBGTUtility;
import com.zorbatron.zbgt.client.widgets.PhantomSlotNoTextWidget;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IDataStickIntractable;
import gregtech.api.capability.IGhostSlotConfigurable;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.util.GTUtility;
import gregtech.api.util.Position;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MTECreativeItemBus extends MetaTileEntityMultiblockNotifiablePart implements
                                IMultiblockAbilityPart<IItemHandlerModifiable>, IGhostSlotConfigurable,
                                IDataStickIntractable {

    private InfiniteItemStackHandler infiniteItemStackHandler;
    private GhostCircuitItemStackHandler circuitItemStackHandler;
    private ItemHandlerList actualImportItems;
    private int slotLimit = Integer.MAX_VALUE;

    public MTECreativeItemBus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.MAX, false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTECreativeItemBus(metaTileEntityId);
    }

    @Override
    protected void initializeInventory() {
        this.infiniteItemStackHandler = new InfiniteItemStackHandler(this, 16, () -> this.slotLimit);
        this.circuitItemStackHandler = new GhostCircuitItemStackHandler(this);
        this.actualImportItems = new ItemHandlerList(
                Arrays.asList(this.infiniteItemStackHandler, this.circuitItemStackHandler));

        super.initializeInventory();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 18 + 18 * 4 + 94 + 20)
                .label(6, 6, getMetaFullName());

        WidgetGroup slots = new WidgetGroup(new Position(52, 18));

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int index = y * 4 + x;

                slots.addWidget(new PhantomSlotNoTextWidget(infiniteItemStackHandler, index, x * 18, y * 18)
                        .setClearSlotOnRightClick(true).setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        SlotWidget circuitSlot = new GhostCircuitSlotWidget(circuitItemStackHandler, 0, 151, 72)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.INT_CIRCUIT_OVERLAY)
                .setConsumer(slotWidget -> ZBGTUtility.getCircuitSlotTooltip(slotWidget, this.circuitItemStackHandler));

        final int textYPos = 95;
        builder.widget(new ImageWidget(7, textYPos, 18 * 4, 20, GuiTextures.DISPLAY)
                .setTooltip("zbgt.machine.super_input_bus.slot_limit"));
        builder.widget(new TextFieldWidget2(9, textYPos + 5, 18 * 4, 16,
                () -> String.valueOf(this.slotLimit),
                (string) -> {
                    if (!string.isEmpty()) {
                        this.slotLimit = Integer.parseInt(string);
                    }
                    this.infiniteItemStackHandler.onContentsChanged(0);
                }).setMaxLength(10).setNumbersOnly(1, Integer.MAX_VALUE));

        return builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 122)
                .widget(slots)
                .widget(circuitSlot)
                .build(getHolder(), entityPlayer);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        if (shouldRenderOverlay()) {
            Textures.ITEM_HATCH_INPUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
            ZBGTTextures.SWIRLY_INFINITY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    public IItemHandlerModifiable getImportItems() {
        return this.actualImportItems;
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.add(this.actualImportItems);
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);

        ZBGTUtility.addNotifiableToMTE(this.actualImportItems, controllerBase, this, false);
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        super.removeFromMultiBlock(controllerBase);

        ZBGTUtility.removeNotifiableFromMTE(this.actualImportItems, controllerBase);
    }

    @Override
    public boolean hasGhostCircuitInventory() {
        return true;
    }

    @Override
    public void setGhostCircuitConfig(int config) {
        if (this.circuitItemStackHandler.getCircuitValue() == config) {
            return;
        }
        this.circuitItemStackHandler.setCircuitValue(config);
        if (!getWorld().isRemote) {
            markDirty();
        }
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false;
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
        super.writeToNBT(data);

        GTUtility.writeItems(infiniteItemStackHandler, "InfiniteInventory", data);
        this.circuitItemStackHandler.write(data);
        data.setInteger("SlotLimit", this.slotLimit);

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        GTUtility.readItems(infiniteItemStackHandler, "InfiniteInventory", data);
        this.circuitItemStackHandler.read(data);
        if (data.hasKey("SlotLimit")) {
            this.slotLimit = data.getInteger("SlotLimit");
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.creative_tooltip.1") + TooltipHelper.RAINBOW +
                I18n.format("gregtech.creative_tooltip.2") + I18n.format("gregtech.creative_tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.me.copy_paste.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    @Override
    public void onDataStickLeftClick(EntityPlayer player, ItemStack dataStick) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("CreativeItemBus", writeConfigToTag());
        dataStick.setTagCompound(tag);
        dataStick.setTranslatableName("zbgt.machine.creative_item_bus.data_stick.name");
        player.sendStatusMessage(new TextComponentTranslation("gregtech.machine.me.import_copy_settings"), true);
    }

    private NBTTagCompound writeConfigToTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("Inventory", infiniteItemStackHandler.serializeNBT());
        this.circuitItemStackHandler.write(tag);
        tag.setInteger("SlotLimit", this.slotLimit);
        return tag;
    }

    @Override
    public boolean onDataStickRightClick(EntityPlayer player, ItemStack dataStick) {
        NBTTagCompound tag = dataStick.getTagCompound();
        if (tag == null || !tag.hasKey("CreativeItemBus")) return false;

        readConfigFromTag(tag.getCompoundTag("CreativeItemBus"));

        return true;
    }

    private void readConfigFromTag(NBTTagCompound tag) {
        this.infiniteItemStackHandler.deserializeNBT(tag.getCompoundTag("Inventory"));
        this.circuitItemStackHandler.read(tag);
        this.slotLimit = tag.getInteger("SlotLimit");
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
