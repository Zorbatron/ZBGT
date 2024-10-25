package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.llamalad7.mixinextras.sugar.Local;
import com.zorbatron.zbgt.api.ZBGTValues;

import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.util.GTUtility;

@Mixin(value = MultiblockDisplayText.Builder.class, remap = false)
public class MultiblockDisplayTextMixin {

    @ModifyArg(method = "addEnergyUsageLine",
               at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/util/text/TextComponentString;"))
    private String addEnergyUsageLine(@Local long maxVoltage) {
        return ZBGTValues.VOCNF[GTUtility.getFloorTierByVoltage(maxVoltage)];
    }

    @ModifyArg(method = "addEnergyUsageExactLine",
               at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/util/text/TextComponentString;"))
    private String addEnergyUsageExactLine(long energyUsage) {
        return ZBGTValues.VOCNF[GTUtility.getFloorTierByVoltage(energyUsage)];
    }

    @ModifyArg(method = "addEnergyProductionLine",
               at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/util/text/TextComponentString;"))
    private String addEnergyProductionLine(long maxVoltage) {
        return ZBGTValues.VOCNF[GTUtility.getFloorTierByVoltage(maxVoltage)];
    }

    @ModifyArg(method = "addEnergyProductionAmpsLine",
               at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/util/text/TextComponentString;"))
    private String addEnergyProductionAmpsLine(long maxVoltage) {
        return ZBGTValues.VOCNF[GTUtility.getFloorTierByVoltage(maxVoltage)];
    }
}
