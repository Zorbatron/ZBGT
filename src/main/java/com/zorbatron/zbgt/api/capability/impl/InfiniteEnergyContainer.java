package com.zorbatron.zbgt.api.capability.impl;

import java.util.function.Supplier;

import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class InfiniteEnergyContainer extends EnergyContainerHandler {

    private final boolean isExportHatch;
    private final Supplier<Boolean> powerMultiplierSupplier;
    private final Supplier<Long> voltageSupplier;
    private final Supplier<Long> amperageSupplier;
    private final Supplier<Boolean> isWorkingEnabledSupplier;

    public InfiniteEnergyContainer(MetaTileEntity tileEntity, boolean isExportHatch,
                                   Supplier<Boolean> powerMultiplierSupplier, Supplier<Long> voltageSupplier,
                                   Supplier<Long> amperageSupplier, Supplier<Boolean> isWorkingEnabledSupplier) {
        super(tileEntity, 0, 0, 0, 0, 0);

        this.isExportHatch = isExportHatch;
        this.powerMultiplierSupplier = powerMultiplierSupplier;
        this.voltageSupplier = voltageSupplier;
        this.amperageSupplier = amperageSupplier;
        this.isWorkingEnabledSupplier = isWorkingEnabledSupplier;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        return isExportHatch ? getOutputVoltage() * getOutputAmperage() : energyToAdd;
    }

    // Check if the attached multiblock is a PSS or AT and if not, 16x the maximum capacity.
    private long multiplier() {
        long power;

        if (isExportHatch) {
            power = getOutputVoltage() * getOutputAmperage();
        } else {
            power = getInputVoltage() * getInputAmperage();
        }

        return powerMultiplierSupplier.get() ? power : power * 16;
    }

    @Override
    public long getEnergyStored() {
        if (isWorkingEnabledSupplier.get() == isExportHatch) {
            return 0L;
        } else {
            return multiplier();
        }
    }

    @Override
    public long getEnergyCapacity() {
        return multiplier();
    }

    @Override
    public long getInputVoltage() {
        return isExportHatch ? 0L : voltageSupplier.get();
    }

    @Override
    public long getInputAmperage() {
        return isExportHatch ? 0L : amperageSupplier.get();
    }

    @Override
    public long getOutputVoltage() {
        return isExportHatch ? voltageSupplier.get() : 0L;
    }

    @Override
    public long getOutputAmperage() {
        return isExportHatch ? amperageSupplier.get() : 0L;
    }
}
