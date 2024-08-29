package com.zorbatron.zbgt.api.capability.impl;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTUtility;

public class InfiniteItemStackHandler extends NotifiableItemStackHandler {

    public InfiniteItemStackHandler(MetaTileEntity dirtyNotifier, int slots, boolean isExport) {
        super(dirtyNotifier, slots, null, isExport);
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return GTUtility.copy(Integer.MAX_VALUE, super.getStackInSlot(slot));
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return GTUtility.copy(amount, getStackInSlot(slot));
    }
}
