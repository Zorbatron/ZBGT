package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.zorbatron.zbgt.api.util.ZBGTUtility;

import gregtech.api.capability.impl.AbstractRecipeLogic;

@Mixin(value = AbstractRecipeLogic.class, remap = false)
public class AbstractRecipeLogicMixin {

    @Redirect(method = "getNumberOfOCs",
              at = @At(value = "INVOKE", target = "Lgregtech/api/util/GTUtility;getTierByVoltage(J)B"))
    private byte getNumberOfOCs(long voltage) {
        return ZBGTUtility.getOCTierByVoltage(voltage);
    }

    @ModifyReturnValue(method = "getOverclockForTier", at = @At("RETURN"))
    private int getOverClockForTier(int original) {
        return ZBGTUtility.getOCTierByVoltage(original);
    }
}
