package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.zorbatron.zbgt.api.util.ZBGTUtility;

import gregtech.api.capability.impl.AbstractRecipeLogic;

@Mixin(value = AbstractRecipeLogic.class, remap = false)
public class AbstractRecipeLogicMixin {

    @Redirect(method = "getNumberOfOCs",
              at = @At(value = "INVOKE", target = "Lgregtech/api/util/GTUtility;getTierByVoltage(J)B"))
    private byte getNumberOfOCs(long voltage) {
        return ZBGTUtility.getOCTierByVoltage(voltage);
    }

    @Redirect(method = "getNumberOfOCs",
              at = @At(value = "INVOKE",
                       target = "Lgregtech/api/capability/impl/AbstractRecipeLogic;getOverclockForTier(J)I"))
    private int getOverClockForTier(AbstractRecipeLogic instance, long voltage) {
        return ZBGTUtility.getOCTierByVoltage(voltage);
    }
}
