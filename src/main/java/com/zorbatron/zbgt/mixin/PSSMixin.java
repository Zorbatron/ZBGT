package com.zorbatron.zbgt.mixin;

import java.util.List;

import net.minecraft.client.resources.I18n;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.zorbatron.zbgt.common.ZBGTConfig;

import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityPowerSubstation;

@Mixin(value = MetaTileEntityPowerSubstation.class, remap = false)
public class PSSMixin {

    @Redirect(method = "createStructurePattern",
              at = @At(value = "INVOKE",
                       target = "Lgregtech/api/pattern/FactoryBlockPattern;setRepeatable(II)Lgregtech/api/pattern/FactoryBlockPattern;"))
    private FactoryBlockPattern setRepeatable(FactoryBlockPattern instance, int minRepeat, int maxRepeat) {
        return instance.setRepeatable(minRepeat, ZBGTConfig.multiblockSettings.overridePSSHeight ?
                ZBGTConfig.multiblockSettings.overriddenPSSHeight : maxRepeat);
    }

    @WrapOperation(method = "addInformation",
                   at = @At(
                            value = "INVOKE",
                            target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                            ordinal = 2))
    private boolean add(List<Object> instance, Object e, Operation<Boolean> original) {
        if (ZBGTConfig.multiblockSettings.overridePSSHeight) {
            return instance.add(I18n.format("gregtech.machine.power_substation.tooltip3",
                    TextFormattingUtil.formatNumbers(ZBGTConfig.multiblockSettings.overriddenPSSHeight)));
        } else {
            return original.call(instance, e);
        }
    }
}
