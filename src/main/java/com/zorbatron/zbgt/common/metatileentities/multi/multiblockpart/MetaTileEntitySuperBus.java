package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import com.zorbatron.zbgt.api.capability.impl.LargeSlotItemStackHandler;
import com.zorbatron.zbgt.client.ClientHandler;
import com.zorbatron.zbgt.client.widgets.ItemSlotTinyAmountTextWidget;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.Position;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntitySuperBus extends MetaTileEntityMultiblockNotifiablePart
                                    implements IMultiblockAbilityPart<IItemHandlerModifiable>, IControllable {

    private boolean workingEnabled = false;

    public MetaTileEntitySuperBus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.HV, false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySuperBus(metaTileEntityId);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new LargeSlotItemStackHandler(this, 16, null, false);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 18 + 18 * 4 + 94)
                .label(6, 6, getMetaFullName());

        WidgetGroup slots = new WidgetGroup(new Position(7 + (int) (18 * 2.5), 18));

        // Item slots
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int index = y * 4 + x;

                slots.addWidget(new ItemSlotTinyAmountTextWidget(this.importItems, index,
                        x * 18, y * 18, false, false)
                                .setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        // Auto pull
        builder.widget(new ToggleButtonWidget(7 + 18 * 7, 18 + 18 * 3, 18, 18, ClientHandler.AUTO_PULL,
                this::isWorkingEnabled, this::setWorkingEnabled)
                        .shouldUseBaseBackground()
                        .setTooltipText("zbgt.machine.super_bus.auto_pull"));

        // Return items
        builder.widget(new ImageWidget(7 + 18 * 8, 18 + 18 * 3, 18, 18, GuiTextures.BUTTON_OVERCLOCK));
        builder.widget(new ClickButtonWidget(7 + 18 * 8, 18 + 18 * 3, 18, 18, "", (clickData -> returnItems()))
                .setTooltipText("zbgt.machine.super_bus.return_items")
                .setButtonTexture(GuiTextures.BUTTON_ITEM_OUTPUT));

        return builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * 4 + 12)
                .widget(slots)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public void update() {
        super.update();

        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0 && workingEnabled) {
            pullItemsFromNearbyHandlers(getFrontFacing());
        }
    }

    private void returnItems() {
        pushItemsIntoNearbyHandlers(getFrontFacing());
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.importItems);
        } else if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }

        return super.getCapability(capability, side);
    }

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
        abilityList.add(this.importItems);
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
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setBoolean("AutoPull", this.workingEnabled);

        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        this.workingEnabled = data.getBoolean("AutoPull");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);

        buf.writeBoolean(this.workingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);

        this.workingEnabled = buf.readBoolean();
    }
}
