package com.zorbatron.zbgt.mixin.chem_plant;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import com.llamalad7.mixinextras.sugar.Local;

import gregtech.integration.jei.utils.render.ItemStackTextRenderer;

@Mixin(value = ItemStackTextRenderer.class, remap = false)
public class ItemStackTextRendererMixin {

    @ModifyArgs(method = "render(Lnet/minecraft/client/Minecraft;IILnet/minecraft/item/ItemStack;)V",
                at = @At(value = "INVOKE",
                         target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I",
                         ordinal = 1))
    private void drawString(Args args, @Local(argsOnly = true, ordinal = 0) int xPosition) {
        String text = "test";
        int newXPos = (xPosition + 6) * 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) + 19;

        args.set(0, text);
        args.set(1, (float) newXPos);
    }
}
