package com.zorbatron.zbgt.api.capability.impl;

import java.util.function.Supplier;

import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class InfiniteEnergyContainerHandler extends EnergyContainerHandler {

    private final boolean isExportHatch;
    private final Supplier<Boolean> powerMultiplierSupplier;

    public InfiniteEnergyContainerHandler(MetaTileEntity tileEntity, long voltage, long amperage, boolean isExportHatch,
                                          Supplier<Boolean> powerMultiplierSupplier) {
        super(tileEntity, 0,
                isExportHatch ? 0L : voltage,
                isExportHatch ? 0L : amperage,
                isExportHatch ? voltage : 0L,
                isExportHatch ? amperage : 0L);

        this.isExportHatch = isExportHatch;
        this.powerMultiplierSupplier = powerMultiplierSupplier;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        return isExportHatch ? getOutputVoltage() * getOutputAmperage() : energyToAdd;
    }

    @Override
    public long getEnergyStored() {
        if (isExportHatch) return 0L;

        long power = getInputVoltage() * getInputAmperage();
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
        if (powerMultiplierSupplier.get()) {
            return power;
        } else {
            return power * 16;
        }
    }
}
