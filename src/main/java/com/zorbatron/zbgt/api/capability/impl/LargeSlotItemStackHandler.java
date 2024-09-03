package com.zorbatron.zbgt.api.capability.impl;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class LargeSlotItemStackHandler extends NotifiableItemStackHandler {

    public LargeSlotItemStackHandler(MetaTileEntity metaTileEntity, int slots, MetaTileEntity entityToNotify,
                                     boolean isExport) {
        super(metaTileEntity, slots, entityToNotify, isExport);
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    protected int getStackLimit(int slot, @NotNull ItemStack stack) {
        return getSlotLimit(slot);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) return ItemStack.EMPTY;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        if (existing.isEmpty()) return ItemStack.EMPTY;

        if (existing.getCount() <= amount) {
            if (!simulate) {
                this.stacks.set(slot, ItemStack.EMPTY);
                onContentsChanged(slot);
            }

            return existing;
        } else {
            if (!simulate) {
                this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(
                        existing, existing.getCount() - amount));
                onContentsChanged(slot);
            }

            return ItemHandlerHelper.copyStackWithSize(existing, amount);
        }
    }
}
