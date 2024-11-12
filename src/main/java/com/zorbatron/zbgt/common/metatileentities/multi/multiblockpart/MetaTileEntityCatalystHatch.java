package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;

import com.zorbatron.zbgt.api.render.ZBGTGuiTextures;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.api.util.ZBGTLog;
import com.zorbatron.zbgt.api.util.ZBGTUtility;
import com.zorbatron.zbgt.common.items.ZBGTCatalystItem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.INotifiableHandler;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.util.GTUtility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntityCatalystHatch extends MetaTileEntityMultiblockNotifiablePart implements
                                         IMultiblockAbilityPart<IItemHandlerModifiable> {

    public MetaTileEntityCatalystHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.ULV, false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCatalystHatch(metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();

        if (!getWorld().isRemote && getOffsetTimer() % 5 == 0) {
            ZBGTUtility.collapseInventorySlotContents(importItems);
            moveItemsFromStockToUsage(importItems, exportItems);
        }
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new GTItemStackHandler(this, 16);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(this, 16, null, false);
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        ZBGTUtility.addNotifiableToMTE((INotifiableHandler) importItems, controllerBase, this, false);
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        ZBGTUtility.removeNotifiableFromMTE((INotifiableHandler) importItems, controllerBase);
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.add(importItems);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 18 + 18 * 4 + 94)
                .label(5, 5, getMetaFullName());

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                int index = y * 4 + x;

                builder.widget(new SlotWidget(importItems, index,
                        7 + x * 18, 18 + y * 18, true, true)
                                .setBackgroundTexture(GuiTextures.SLOT));

                builder.widget(new SlotWidget(exportItems, index,
                        7 + x * 18 + 18 * 5, 18 + y * 18, true, false)
                                .setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        builder.image(7 + 18 * 4 + 1, (int) (18 * 2.5), 16, 16, ZBGTGuiTextures.ZBGT_LOGO);

        return builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 18)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        ZBGTTextures.CATALYST_HATCH_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    private void moveItemsFromStockToUsage(IItemHandlerModifiable src, IItemHandlerModifiable dst) {
        // This should never happen, but just in case
        if (src.getSlots() != dst.getSlots()) {
            ZBGTLog.logger.error("Handlers {} and {} don't have the same slot count in Catalyst hatch {}", src, dst,
                    this);
            return;
        }

        int slotCount = src.getSlots();

        for (int slot = 0; slot < slotCount; slot++) {
            ItemStack srcStack = src.getStackInSlot(slot);
            if (!ZBGTCatalystItem.isItemCatalyst(srcStack)) {
                continue;
            }

            ItemStack srcStackCopy = GTUtility.copy(1, srcStack);

            boolean matchFound = false;
            boolean didSet = false;

            for (int dstSlot = 0; dstSlot < slotCount; dstSlot++) {
                if (srcStack.isItemEqual(dst.getStackInSlot(dstSlot))) {
                    matchFound = true;
                    break;
                }
            }

            for (int dstSlot = 0; dstSlot < slotCount; dstSlot++) {
                if (dst.getStackInSlot(dstSlot).isEmpty() && !matchFound) {
                    dst.setStackInSlot(dstSlot, srcStackCopy);
                    didSet = true;
                    break;
                }
            }

            if (didSet) {
                srcStack.setCount(srcStack.getCount() - 1);
            }
        }
    }
}
