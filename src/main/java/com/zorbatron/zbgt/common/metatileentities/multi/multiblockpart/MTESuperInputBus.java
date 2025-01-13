package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.capability.impl.LargeSlotItemStackHandler;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.api.util.ZBGTUtility;
import com.zorbatron.zbgt.client.widgets.ItemSlotTinyAmountTextWidget;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
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
import gregtech.api.util.Position;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MTESuperInputBus extends MetaTileEntityMultiblockNotifiablePart
                              implements IMultiblockAbilityPart<IItemHandlerModifiable>, IControllable {

    private boolean workingEnabled = false;
    private boolean shouldReturnItems = false;
    private int slotLimit = Integer.MAX_VALUE;
    private GhostCircuitItemStackHandler ghostCircuitItemStackHandler;
    private LargeSlotItemStackHandler largeSlotItemStackHandler;
    private ItemHandlerList actualImportItems;

    public MTESuperInputBus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.HV, false);
    }

    @Override
    protected void initializeInventory() {
        this.largeSlotItemStackHandler = new LargeSlotItemStackHandler(this, 16, null, false, () -> this.slotLimit);
        this.ghostCircuitItemStackHandler = new GhostCircuitItemStackHandler(this);

        this.actualImportItems = new ItemHandlerList(
                Arrays.asList(this.largeSlotItemStackHandler, this.ghostCircuitItemStackHandler));

        super.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTESuperInputBus(metaTileEntityId);
    }

    @Override
    public IItemHandlerModifiable getImportItems() {
        return this.actualImportItems;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 18 + 18 * 5 + 94)
                .label(6, 6, getMetaFullName());

        WidgetGroup slots = new WidgetGroup(new Position(7 + (int) (18 * 2.5), 18));

        // Item slots
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int index = y * 4 + x;

                slots.addWidget(new ItemSlotTinyAmountTextWidget(this.largeSlotItemStackHandler, index,
                        x * 18, y * 18, false, false)
                                .setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        WidgetGroup extraWidgets = new WidgetGroup(new Position(7, 18 * 5 + 9));

        // Slot limit
        extraWidgets.addWidget(new ImageWidget(0, 0, 18 * 4, 20, GuiTextures.DISPLAY)
                .setTooltip("zbgt.machine.super_input_bus.slot_limit"));
        extraWidgets.addWidget(new TextFieldWidget2(2, 5, 18 * 4, 16,
                () -> String.valueOf(this.slotLimit),
                (string) -> {
                    if (!string.isEmpty()) {
                        this.slotLimit = Integer.parseInt(string);
                    }
                }).setMaxLength(10).setNumbersOnly(1, Integer.MAX_VALUE));

        // Circuit slot
        extraWidgets.addWidget(new GhostCircuitSlotWidget(ghostCircuitItemStackHandler, 0, 18 * 6, 0)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.INT_CIRCUIT_OVERLAY)
                .setConsumer(
                        slotWidget -> ZBGTUtility.getCircuitSlotTooltip(slotWidget, ghostCircuitItemStackHandler)));

        // Auto pull
        extraWidgets.addWidget(new ToggleButtonWidget(18 * 7, 0, 18, 18, ZBGTTextures.AUTO_PULL,
                this::isWorkingEnabled, this::setWorkingEnabled)
                        .shouldUseBaseBackground()
                        .setTooltipText("zbgt.machine.super_input_bus.auto_pull"));

        // Return items
        extraWidgets.addWidget(new ToggleButtonWidget(18 * 8, 0, 18, 18, GuiTextures.BUTTON_ITEM_OUTPUT,
                this::shouldReturnItems, this::setReturningItems)
                        .setTooltipText("zbgt.machine.super_input_bus.return_items.button")
                        .shouldUseBaseBackground());

        return builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * 5 + 12)
                .widget(slots)
                .widget(extraWidgets)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public void update() {
        super.update();

        if (!getWorld().isRemote) {
            if (getOffsetTimer() % 20 == 0 && workingEnabled) {
                pullItemsFromNearbyHandlers(getFrontFacing());
            }

            if (shouldReturnItems) {
                pushItemsIntoNearbyHandlers(getFrontFacing());

                if (ZBGTUtility.isInventoryEmpty(this.largeSlotItemStackHandler)) {
                    setReturningItems(false);
                }
            }
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.largeSlotItemStackHandler);
        } else if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }

        return super.getCapability(capability, side);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        if (shouldRenderOverlay()) {
            Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
            Textures.ITEM_HATCH_INPUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
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
    public boolean isWorkingEnabled() {
        return this.workingEnabled;
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
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);

        if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.workingEnabled = buf.readBoolean();
        } else if (dataId == GregtechDataCodes.UPDATE_AUTO_OUTPUT) {
            this.shouldReturnItems = buf.readBoolean();
        }
    }

    private boolean shouldReturnItems() {
        return this.shouldReturnItems;
    }

    private void setReturningItems(boolean shouldReturnItems) {
        this.shouldReturnItems = shouldReturnItems;

        World world = getWorld();
        if (world != null && !world.isRemote) {
            writeCustomData(GregtechDataCodes.UPDATE_AUTO_OUTPUT, buf -> buf.writeBoolean(workingEnabled));
        }
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                      CuboidRayTraceResult hitResult) {
        if (shouldReturnItems()) {
            setReturningItems(false);
            playerIn.sendStatusMessage(
                    new TextComponentTranslation("zbgt.machine.super_input_bus.return_items.screwdriver.disabled"),
                    true);
        } else {
            setReturningItems(true);
            playerIn.sendStatusMessage(
                    new TextComponentTranslation("zbgt.machine.super_input_bus.return_items.screwdriver.enabled"),
                    true);
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("zbgt.machine.super_input_bus.screwdriver"));
        tooltip.add(I18n.format("zbgt.machine.super_input_bus.soft_mallet"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("AutoPull", this.workingEnabled);
        data.setInteger("SlotLimit", this.slotLimit);
        this.ghostCircuitItemStackHandler.write(data);
        data.setTag("Slots", this.largeSlotItemStackHandler.serializeNBT());

        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        this.workingEnabled = data.getBoolean("AutoPull");
        this.slotLimit = data.getInteger("SlotLimit");
        this.ghostCircuitItemStackHandler.read(data);
        this.largeSlotItemStackHandler.deserializeNBT(data.getCompoundTag("Slots"));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);

        buf.writeBoolean(this.workingEnabled);
        buf.writeInt(this.slotLimit);
        buf.writeBoolean(this.shouldReturnItems);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);

        this.workingEnabled = buf.readBoolean();
        this.slotLimit = buf.readInt();
        this.shouldReturnItems = buf.readBoolean();
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
