package com.zorbatron.zbgt.client.widgets;

import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.impl.ModularUIGui;
import gregtech.api.gui.resources.IGuiTexture;
import gregtech.api.gui.widgets.PhantomSlotWidget;
import gregtech.api.util.Position;
import gregtech.api.util.Size;

public class PhantomSlotNoAmountTextWidget extends PhantomSlotWidget {

    public PhantomSlotNoAmountTextWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition,
                                         int yPosition) {
        super(itemHandler, slotIndex, xPosition, yPosition);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int mouseX, int mouseY, float partialTicks, IRenderContext context) {
        Position pos = getPosition();
        Size size = getSize();
        if (backgroundTexture != null) {
            for (IGuiTexture backgroundTexture : this.backgroundTexture) {
                backgroundTexture.draw(pos.x, pos.y, size.width, size.height);
            }
        }
        ItemStack itemStack = slotReference.getStack();
        ModularUIGui modularUIGui = gui == null ? null : gui.getModularUIGui();
        if (itemStack.isEmpty() && modularUIGui != null && modularUIGui.getDragSplitting() &&
                modularUIGui.getDragSplittingSlots().contains(slotReference)) { // draw split
            int splitSize = modularUIGui.getDragSplittingSlots().size();
            itemStack = gui.entityPlayer.inventory.getItemStack();
            if (!itemStack.isEmpty() && splitSize > 1 && Container.canAddItemToSlot(slotReference, itemStack, true)) {
                itemStack = itemStack.copy();
                Container.computeStackSize(modularUIGui.getDragSplittingSlots(), modularUIGui.dragSplittingLimit,
                        itemStack, slotReference.getStack().isEmpty() ? 0 : slotReference.getStack().getCount());
                int k = Math.min(itemStack.getMaxStackSize(), slotReference.getItemStackLimit(itemStack));
                if (itemStack.getCount() > k) {
                    itemStack.setCount(k);
                }
            }
        }
        if (!itemStack.isEmpty()) {
            // Set the stack count to 1 which doesn't show the amount text
            itemStack.setCount(1);
            drawItemStack(itemStack, pos.x + 1, pos.y + 1, null);
        }

        if (isMouseOverElement(mouseX, mouseY)) {
            drawSelectionOverlay(pos.x + 1, pos.y + 1, 16, 16);
        }
    }
}
