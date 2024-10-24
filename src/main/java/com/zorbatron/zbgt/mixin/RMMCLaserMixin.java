package com.zorbatron.zbgt.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;

@Mixin(value = RecipeMapMultiblockController.class, remap = false)
public abstract class RMMCLaserMixin {

    @Shadow
    protected IEnergyContainer energyContainer;

    @Inject(method = "initializeAbilities", at = @At(value = "RETURN"))
    protected void initializeAbilities(CallbackInfo ci) {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) (Object) this;
        List<IEnergyContainer> energyInputs = new ArrayList<>();

        energyInputs.addAll(controller.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyInputs.addAll(controller.getAbilities(MultiblockAbility.SUBSTATION_INPUT_ENERGY));
        energyInputs.addAll(controller.getAbilities(MultiblockAbility.INPUT_LASER));

        energyContainer = new EnergyContainerList(energyInputs);
    }
}
