package com.zorbatron.zbgt.api.capability.impl;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTUtility;

public class InfiniteItemStackHandler extends NotifiableItemStackHandler {

    private final Supplier<Integer> supplyAmount;

    public InfiniteItemStackHandler(MetaTileEntity dirtyNotifier, int slots, Supplier<Integer> supplyAmount) {
        super(dirtyNotifier, slots, null, false);
        this.supplyAmount = supplyAmount;
    }

    public InfiniteItemStackHandler(MetaTileEntity dirtyNotifier, int slots) {
        this(dirtyNotifier, slots, () -> Integer.MAX_VALUE);
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return GTUtility.copy(supplyAmount.get(), super.getStackInSlot(slot));
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return GTUtility.copy(amount, getStackInSlot(slot));
    }
}
