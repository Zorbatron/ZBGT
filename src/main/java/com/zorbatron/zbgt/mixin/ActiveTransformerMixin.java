package com.zorbatron.zbgt.mixin;

import java.util.List;

import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.zorbatron.zbgt.api.metatileentity.ZBGTMultiblockAbilities;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityActiveTransformer;

@Mixin(value = MetaTileEntityActiveTransformer.class, remap = false)
public abstract class ActiveTransformerMixin extends MultiblockControllerBase {

    private ActiveTransformerMixin(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @ModifyReturnValue(method = "getHatchPredicates", at = @At(value = "RETURN"))
    private TraceabilityPredicate addRFHatchesToPredicate(TraceabilityPredicate original) {
        return original.or(abilities(ZBGTMultiblockAbilities.RF_INPUT_HATCH).setPreviewCount(1))
                .or(abilities(ZBGTMultiblockAbilities.RF_OUTPUT_HATCH).setPreviewCount(1));
    }

    @Inject(method = "formStructure",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z",
                     ordinal = 2,
                     shift = At.Shift.AFTER))
    private void collectRFInputHatchesOnForm(PatternMatchContext context, CallbackInfo ci,
                                             @Local(ordinal = 0) List<IEnergyContainer> inputs) {
        inputs.addAll(getAbilities(ZBGTMultiblockAbilities.RF_INPUT_HATCH));
    }

    @Inject(method = "formStructure",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z",
                     ordinal = 3,
                     shift = At.Shift.AFTER))
    private void collectRFOutputHatchesOnForm(PatternMatchContext context, CallbackInfo ci,
                                              @Local(ordinal = 1) List<IEnergyContainer> outputs) {
        outputs.addAll(getAbilities(ZBGTMultiblockAbilities.RF_OUTPUT_HATCH));
    }
}
