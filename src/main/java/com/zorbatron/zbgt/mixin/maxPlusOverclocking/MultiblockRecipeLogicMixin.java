package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.zorbatron.zbgt.api.ZBGTValues;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.util.GTUtility;

@Mixin(value = MultiblockRecipeLogic.class, remap = false)
public class MultiblockRecipeLogicMixin {

    @ModifyReturnValue(method = "getMaximumOverclockVoltage", at = @At(value = "RETURN", ordinal = 0))
    private long getMaximumOverclockVoltage(long original, @Local(ordinal = 0) long voltage) {
        return ZBGTValues.VOC[GTUtility.getFloorTierByVoltage(voltage)];
    }

    @ModifyReturnValue(method = "getMaxVoltage", at = @At(value = "RETURN", ordinal = 0))
    private long getMaxVoltage(long original, @Local(ordinal = 0) long voltage) {
        return ZBGTValues.VOC[GTUtility.getFloorTierByVoltage(voltage)];
    }
}
