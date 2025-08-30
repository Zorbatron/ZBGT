package com.zorbatron.zbgt.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.zorbatron.zbgt.api.util.ZBGTLog;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.WireProperties;
import gregtech.common.pipelike.cable.BlockCable;

@Mixin(value = BlockCable.class, remap = false)
public class BlockCableGetFallbackTypeLogMixin {

    @Shadow
    @Final
    private Map<Material, WireProperties> enabledMaterials;

    @Inject(method = "getFallbackType()Lgregtech/api/unification/material/properties/WireProperties;",
            at = @At(value = "HEAD"))
    public void logEnabledMaterials(CallbackInfoReturnable<WireProperties> cir) {
        ZBGTLog.logger.info("getFallBackType called on BlockCable, printing enabled materials:");
        ZBGTLog.logger.info("enabledMaterials has {} entries", enabledMaterials.size());
        for (Material key : enabledMaterials.keySet()) {
            ZBGTLog.logger.info("Entry material: {}", key.getName());
        }
    }
}
