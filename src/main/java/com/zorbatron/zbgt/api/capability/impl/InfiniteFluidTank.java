package com.zorbatron.zbgt.api.capability.impl;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.metatileentity.MetaTileEntity;

public class InfiniteFluidTank extends NotifiableFluidTank {

    // NotifiableFluidTank's isExport is private and there is not getter method :\
    private final boolean isExport;
    private final int supplyAmount = Integer.MAX_VALUE;

    public InfiniteFluidTank(MetaTileEntity entityToNotify, boolean isExport) {
        super(0, entityToNotify, isExport);
        this.isExport = isExport;
    }

    @Nullable
    @Override
    public FluidStack getFluid() {
        if (this.fluid == null) {
            return null;
        } else {
            return new FluidStack(this.fluid, supplyAmount);
        }
    }

    @Override
    public void setFluid(@Nullable FluidStack fluid) {
        if (fluid == null) {
            this.fluid = null;
            return;
        }

        this.fluid = new FluidStack(fluid.getFluid(), supplyAmount);
        onContentsChanged();
    }

    @Override
    public int getFluidAmount() {
        if (this.fluid != null) {
            return supplyAmount;
        } else {
            return 0;
        }
    }

    @Override
    public int getCapacity() {
        return supplyAmount;
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
        return supplyAmount;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (this.fluid == null) {
            return null;
        } else {
            return new FluidStack(this.fluid, maxDrain);
        }
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return new FluidStack(resource.getFluid(), supplyAmount);
    }
}
