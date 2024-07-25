package com.zorbatron.zbgt.api.capability.impl;

import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class InfiniteEnergyContainerHandler extends EnergyContainerHandler {

    public InfiniteEnergyContainerHandler(MetaTileEntity tileEntity, long maxCapacity, long maxInputVoltage,
                                          long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage,
                                          boolean isSource) {
        super(tileEntity, maxCapacity, maxInputVoltage, maxInputAmperage, maxOutputVoltage, maxOutputAmperage);
        if (isSource) {
            this.energyStored = maxCapacity;
        }
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        return energyToAdd;
    }
}
