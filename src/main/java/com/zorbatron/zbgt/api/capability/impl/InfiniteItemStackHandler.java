package com.zorbatron.zbgt.api.capability.impl;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTUtility;

public class InfiniteItemStackHandler extends NotifiableItemStackHandler {

    private ItemStack setItem;

    public InfiniteItemStackHandler(MetaTileEntity dirtyNotifier, boolean isExport) {
        super(dirtyNotifier, 1, null, isExport);
        this.setItem = ItemStack.EMPTY;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        stack.setCount(1);
        this.setItem = stack;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    protected int getStackLimit(int slot, @NotNull ItemStack stack) {
        return 1;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return setItem;
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return GTUtility.copy(amount, setItem);
    }

    public void onContentsChanged() {
        super.onContentsChanged(0);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setTag("setItem", this.setItem.serializeNBT());
        return itemTag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.setItem = new ItemStack(nbt.getCompoundTag("setItem"));
    }
}
