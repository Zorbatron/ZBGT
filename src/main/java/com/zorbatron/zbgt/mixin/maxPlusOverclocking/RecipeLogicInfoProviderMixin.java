package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import net.minecraft.util.text.TextFormatting;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.llamalad7.mixinextras.sugar.Local;
import com.zorbatron.zbgt.api.ZBGTValues;

import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.integration.theoneprobe.provider.RecipeLogicInfoProvider;
import mcjty.theoneprobe.api.TextStyleClass;

@Mixin(value = RecipeLogicInfoProvider.class)
public class RecipeLogicInfoProviderMixin {

    @ModifyVariable(method = "addProbeInfo(Lgregtech/api/capability/impl/AbstractRecipeLogic;Lmcjty/theoneprobe/api/IProbeInfo;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/tileentity/TileEntity;Lmcjty/theoneprobe/api/IProbeHitData;)V",
                    at = @At(value = "INVOKE_ASSIGN",
                             target = "Lgregtech/api/util/TextFormattingUtil;formatNumbers(J)Ljava/lang/String;"),
                    index = 1,
                    argsOnly = true)
    private String text(String original, @Local(ordinal = 1) int absEUt) {
        return TextFormatting.RED.toString() + TextFormattingUtil.formatNumbers(absEUt) + TextStyleClass.INFO +
                " EU/t" + TextFormatting.GREEN +
                " (" + ZBGTValues.VOCNF[GTUtility.getTierByVoltage(absEUt)] + TextFormatting.GREEN + ")";
    }
}
