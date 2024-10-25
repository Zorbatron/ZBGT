package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.zorbatron.zbgt.api.ZBGTValues;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.util.GTUtility;

// Overwrites for the same reason as MultiblockDisplayTextMixin
@Mixin(value = MultiblockRecipeLogic.class, remap = false)
public abstract class MultiblockRecipeLogicMixin {

    @Shadow
    public abstract IEnergyContainer getEnergyContainer();

    /**
     * @author Zorbatron
     * @reason I have to >:(
     */
    @Overwrite
    public long getMaximumOverclockVoltage() {
        IEnergyContainer energyContainer = getEnergyContainer();
        if (energyContainer instanceof EnergyContainerList) {
            long voltage;
            long amperage;
            if (energyContainer.getInputVoltage() > energyContainer.getOutputVoltage()) {
                voltage = energyContainer.getInputVoltage();
                amperage = energyContainer.getInputAmperage();
            } else {
                voltage = energyContainer.getOutputVoltage();
                amperage = energyContainer.getOutputAmperage();
            }

            if (amperage == 1) {
                // amperage is 1 when the energy is not exactly on a tier

                // the voltage for recipe search is always on tier, so take the closest lower tier
                return ZBGTValues.VOC[GTUtility.getFloorTierByVoltage(voltage)];
            } else {
                // amperage != 1 means the voltage is exactly on a tier
                // ignore amperage, since only the voltage is relevant for recipe search
                // amps are never > 3 in an EnergyContainerList
                return voltage;
            }
        }
        return Math.max(energyContainer.getInputVoltage(), energyContainer.getOutputVoltage());
    }

    /**
     * @author Zorbatron
     * @reason I have to >:(
     */
    @Overwrite
    public long getMaxVoltage() {
        IEnergyContainer energyContainer = getEnergyContainer();
        if (!((MultiblockRecipeLogic) (Object) this).consumesEnergy()) {
            // Generators
            long voltage = energyContainer.getOutputVoltage();
            long amperage = energyContainer.getOutputAmperage();
            if (energyContainer instanceof EnergyContainerList && amperage == 1) {
                // Amperage is 1 when the energy is not exactly on a tier.
                // The voltage for recipe search is always on tier, so take the closest lower tier.
                // List check is done because single hatches will always be a "clean voltage," no need
                // for any additional checks.
                return ZBGTValues.VOC[GTUtility.getFloorTierByVoltage(voltage)];
            }
            return voltage;
        } else {
            // Machines
            if (energyContainer instanceof EnergyContainerList energyList) {
                long highestVoltage = energyList.getHighestInputVoltage();
                if (energyList.getNumHighestInputContainers() > 1) {
                    // allow tier + 1 if there are multiple hatches present at the highest tier
                    int tier = GTUtility.getTierByVoltage(highestVoltage);
                    return GTValues.V[Math.min(tier + 1, GTValues.MAX)];
                } else {
                    return highestVoltage;
                }
            } else {
                return energyContainer.getInputVoltage();
            }
        }
    }
}
