package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import gregtech.api.GTValues;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityCleanroom;

@Mixin(value = MetaTileEntityCleanroom.class, remap = false)
public class MetaTileEntityCleanroomMixin {

    @ModifyReturnValue(method = "getEnergyTier", at = @At("RETURN"))
    private int getEnergyTier(int original) {
        return Math.max(GTValues.MAX, original);
    }
}
