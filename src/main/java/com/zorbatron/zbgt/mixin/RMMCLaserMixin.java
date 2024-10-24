package com.zorbatron.zbgt.mixin;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.abilities;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;

@Mixin(value = RecipeMapMultiblockController.class, remap = false)
public abstract class RMMCLaserMixin {

    @Shadow
    protected IEnergyContainer energyContainer;

    @Inject(method = "initializeAbilities", at = @At(value = "RETURN"))
    private void allowExtraAbilities(CallbackInfo ci) {
        RecipeMapMultiblockController controller = (RecipeMapMultiblockController) (Object) this;
        List<IEnergyContainer> energyInputs = new ArrayList<>();

        energyInputs.addAll(controller.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyInputs.addAll(controller.getAbilities(MultiblockAbility.SUBSTATION_INPUT_ENERGY));
        energyInputs.addAll(controller.getAbilities(MultiblockAbility.INPUT_LASER));

        energyContainer = new EnergyContainerList(energyInputs);
    }

    @WrapOperation(method = "autoAbilities(ZZZZZZZ)Lgregtech/api/pattern/TraceabilityPredicate;",
                   at = @At(value = "INVOKE",
                            target = "Lgregtech/api/metatileentity/multiblock/RecipeMapMultiblockController;abilities([Lgregtech/api/metatileentity/multiblock/MultiblockAbility;)Lgregtech/api/pattern/TraceabilityPredicate;",
                            ordinal = 0))
    private TraceabilityPredicate allowExtraHatches(MultiblockAbility<?>[] multiblockAbilities,
                                                    Operation<TraceabilityPredicate> original) {
        return abilities(MultiblockAbility.INPUT_ENERGY, MultiblockAbility.SUBSTATION_INPUT_ENERGY,
                MultiblockAbility.INPUT_LASER);
    }
}
