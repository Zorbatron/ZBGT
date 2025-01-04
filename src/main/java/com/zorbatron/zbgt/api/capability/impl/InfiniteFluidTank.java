package com.zorbatron.zbgt.api.capability.impl;

import java.util.function.Supplier;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.metatileentity.MetaTileEntity;

public class InfiniteFluidTank extends NotifiableFluidTank {

    // NotifiableFluidTank's isExport is private and there is no getter method :\
    private final boolean isExport;
    private final Supplier<Integer> supplyAmount;

    public InfiniteFluidTank(MetaTileEntity entityToNotify, boolean isExport, Supplier<Integer> supplyAmount) {
        super(0, entityToNotify, isExport);
        this.isExport = isExport;
        this.supplyAmount = supplyAmount;
    }

    public InfiniteFluidTank(MetaTileEntity entityToNotify, boolean isExport) {
        this(entityToNotify, isExport, () -> Integer.MAX_VALUE);
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        if (this.fluid == null) {
            return null;
        } else {
            FluidStack fluidStack = this.fluid.copy();
            fluidStack.amount = supplyAmount.get();
            return fluidStack;
        }
    }

    @Override
    public void setFluid(@Nullable FluidStack fluid) {
        if (fluid == null) {
            this.fluid = null;
            return;
        }

        this.fluid = fluid.copy();
        onContentsChanged();
    }

    @Override
    public int getFluidAmount() {
        if (this.fluid != null) {
            return supplyAmount.get();
        } else {
            return 0;
        }
    }

    @Override
    public int getCapacity() {
        return supplyAmount.get();
    }

    @Override
    public boolean canFill() {
        return isExport;
    }

    @Override
    public boolean canDrain() {
        return !isExport;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return isExport ? supplyAmount.get() : 0;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (this.fluid == null) {
            return null;
        } else {
            FluidStack fluidStack = this.fluid.copy();
            fluidStack.amount = maxDrain;
            return fluidStack;
        }
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (this.fluid == null || !this.fluid.isFluidEqual(resource)) return null;
        return resource.copy();
    }

    public void onContentsChangedButPublic() {
        onContentsChanged();
    }
}
