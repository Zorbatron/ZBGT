package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import gregtech.api.GTValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GTValues.class, remap = false)
public class GTValuesMixin {

    ///TODO implement this when I grow a brain
    @Inject(method = "<clinit>", at = @At(value = ""))
    private static void modifyV(CallbackInfo ci) {
        long[] newValues = new long[] { 8, 32, 128, 512, 2048, 8192, 32768, 131072, 524288, 2097152, 8388608, 33554432,
                134217728, 536870912, 2147483648L };
    }
}
