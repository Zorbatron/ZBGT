package com.zorbatron.zbgt.api.capability.impl;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class LargeSlotItemStackHandler extends NotifiableItemStackHandler {

    Supplier<Integer> slotCapacity;

    public LargeSlotItemStackHandler(MetaTileEntity metaTileEntity, int slots, MetaTileEntity entityToNotify,
                                     boolean isExport) {
        this(metaTileEntity, slots, entityToNotify, isExport, () -> Integer.MAX_VALUE);
    }

    public LargeSlotItemStackHandler(MetaTileEntity metaTileEntity, int slots, MetaTileEntity entityToNotify,
                                     boolean isExport, Supplier<Integer> slotCapacity) {
        super(metaTileEntity, slots, entityToNotify, isExport);

        this.slotCapacity = slotCapacity;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slotCapacity.get();
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
