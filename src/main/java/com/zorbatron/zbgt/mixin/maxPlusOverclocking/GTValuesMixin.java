package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import gregtech.api.GTValues;

@Mixin(value = GTValues.class, remap = false)
public class GTValuesMixin {

    @Shadow
    @Final
    @Mutable
    public static long[] V = new long[] { 8, 32, 128, 512, 2048, 8192, 32768, 131072, 524288, 2097152, 8388608,
            33554432, 134217728, 536870912, 2147483648L };
}
