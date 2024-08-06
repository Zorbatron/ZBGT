package com.zorbatron.zbgt.api.capability.impl;

import java.util.function.Supplier;

import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityActiveTransformer;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityPowerSubstation;

public class InfiniteEnergyContainerHandler extends EnergyContainerHandler {

    private final boolean isExportHatch;
    private final Supplier<MultiblockControllerBase> controllerTileSupplier;

    public InfiniteEnergyContainerHandler(MetaTileEntity tileEntity, long maxInputVoltage, long maxInputAmperage,
                                          long maxOutputVoltage, long maxOutputAmperage, boolean isExportHatch,
                                          Supplier<MultiblockControllerBase> controllerTileSupplier) {
        super(tileEntity, 0, maxInputVoltage, maxInputAmperage, maxOutputVoltage, maxOutputAmperage);

        this.isExportHatch = isExportHatch;
        this.controllerTileSupplier = controllerTileSupplier;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        return isExportHatch ? getOutputVoltage() * getOutputAmperage() : energyToAdd;
    }

    @Override
    public long getEnergyStored() {
        if (isExportHatch) return 0L;

        long power = getInputVoltage() * getInputAmperage();
        if (isPSSOrAT()) {
            return power;
        } else {
            return power * 16;
        }
    }

    @Override
    public long getEnergyCapacity() {
        if (isExportHatch) return getOutputVoltage() * getOutputAmperage();

        long power = getInputVoltage() * getInputAmperage();
        if (isPSSOrAT()) {
            return power;
        } else {
            return power * 16;
        }
    }

    private boolean isPSSOrAT() {
        return controllerTileSupplier.get() instanceof MetaTileEntityPowerSubstation ||
                controllerTileSupplier.get() instanceof MetaTileEntityActiveTransformer;
    }
}
