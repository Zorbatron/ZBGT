package com.zorbatron.zbgt.api.capability.impl;

import java.util.function.Supplier;

import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class InfiniteEnergyContainer extends EnergyContainerHandler {

    private final boolean isExportHatch;
    private final Supplier<Boolean> powerMultiplierSupplier;
    private final Supplier<Long> voltageSupplier;
    private final Supplier<Long> amperageSupplier;

    public InfiniteEnergyContainer(MetaTileEntity tileEntity, boolean isExportHatch,
                                   Supplier<Boolean> powerMultiplierSupplier, Supplier<Long> voltageSupplier,
                                   Supplier<Long> amperageSupplier) {
        super(tileEntity, 0, 0, 0, 0, 0);

        this.isExportHatch = isExportHatch;
        this.powerMultiplierSupplier = powerMultiplierSupplier;
        this.voltageSupplier = voltageSupplier;
        this.amperageSupplier = amperageSupplier;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        return isExportHatch ? getOutputVoltage() * getOutputAmperage() : energyToAdd;
    }

    @Override
    public long getEnergyStored() {
        if (isExportHatch) return 0L;

        long power = getInputVoltage() * getInputAmperage();

        // Check if the attached multiblock is a PSS or AT and if not, 16x the maximum capacity.
        if (powerMultiplierSupplier.get()) {
            return power;
        } else {
            return power * 16;
        }
    }

    @Override
    public long getEnergyCapacity() {
        if (isExportHatch) return getOutputVoltage() * getOutputAmperage();

        long power = getInputVoltage() * getInputAmperage();

        // Check if the attached multiblock is a PSS or AT and if not, 16x the maximum capacity.
        if (powerMultiplierSupplier.get()) {
            return power;
        } else {
            return power * 16;
        }
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
