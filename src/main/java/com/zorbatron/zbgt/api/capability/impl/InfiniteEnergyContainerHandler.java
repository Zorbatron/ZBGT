package com.zorbatron.zbgt.api.capability.impl;

import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class InfiniteEnergyContainerHandler extends EnergyContainerHandler {

    private final boolean isExportHatch;

    public InfiniteEnergyContainerHandler(MetaTileEntity tileEntity, long maxCapacity, long maxInputVoltage,
                                          long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage,
                                          boolean isExportHatch) {
        super(tileEntity, maxCapacity, maxInputVoltage, maxInputAmperage, maxOutputVoltage, maxOutputAmperage);

        this.isExportHatch = isExportHatch;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        if (energyToAdd > 0) {
            return getInputVoltage() * getInputAmperage();
        }

        return energyToAdd;
    }

    @Override
    public long getEnergyStored() {
        return isExportHatch ? 0L : getInputVoltage() * getInputAmperage() * 16L;
    }

    @Override
    public long getEnergyCapacity() {
        return isExportHatch ? getOutputVoltage() * getOutputAmperage() : getInputVoltage() * getInputAmperage() * 16L;
    }
}
