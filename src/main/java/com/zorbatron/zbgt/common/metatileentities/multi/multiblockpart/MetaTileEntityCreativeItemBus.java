package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;

import com.zorbatron.zbgt.api.capability.impl.InfiniteItemStackHandler;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.PhantomSlotWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.Position;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntityCreativeItemBus extends MetaTileEntityMultiblockNotifiablePart implements
                                           IMultiblockAbilityPart<IItemHandlerModifiable> {

    private InfiniteItemStackHandler infiniteItemStackHandler;

    public MetaTileEntityCreativeItemBus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.MAX, false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCreativeItemBus(metaTileEntityId);
    }

    @Override
    protected void initializeInventory() {
        this.infiniteItemStackHandler = new InfiniteItemStackHandler(this, false);

        super.initializeInventory();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        WidgetGroup slots = new WidgetGroup(new Position(7, 7 + 18));

        slots.addWidget(new PhantomSlotWidget(infiniteItemStackHandler, 0, 0, 0)
                .setClearSlotOnRightClick(true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setChangeListener(this.infiniteItemStackHandler::onContentsChanged));

        return ModularUI.defaultBuilder()
                .label(7, 7, getMetaFullName())
                .widget(slots)
                .bindPlayerInventory(entityPlayer.inventory)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        Textures.ITEM_HATCH_INPUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return this.infiniteItemStackHandler;
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.add(this.importItems);
    }
}
