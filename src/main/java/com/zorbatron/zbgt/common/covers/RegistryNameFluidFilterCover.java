package com.zorbatron.zbgt.common.covers;

import java.util.function.Consumer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.gui.Widget;
import gregtech.common.covers.filter.FluidFilter;

public class RegistryNameFluidFilterCover extends FluidFilter {

    public RegistryNameFluidFilterCover() {}

    @Override
    public boolean testFluid(FluidStack fluidStack) {
        return false;
    }

    @Override
    public int getFluidTransferLimit(FluidStack fluidStack) {
        return 0;
    }

    @Override
    public int getMaxOccupiedHeight() {
        return 0;
    }

    @Override
    public void initUI(Consumer<Widget> widgetGroup) {}

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {}

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {}

    @Override
    public void configureFilterTanks(int amount) {}

    @Override
    public void setMaxConfigurableFluidSize(int maxStackSize) {}
}
