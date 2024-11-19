package com.zorbatron.zbgt.integration.jei.utils.render;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mezz.jei.plugins.vanilla.ingredients.item.ItemStackRenderer;

public class SpecializedItemStackTextRenderer extends ItemStackRenderer {

    private final String customText;
    private final Predicate<ItemStack> matcher;

    public SpecializedItemStackTextRenderer(String customText, Predicate<ItemStack> matcher) {
        this.customText = customText;
        this.matcher = matcher;
    }

    @Override
    public void render(@NotNull Minecraft minecraft, int xPosition, int yPosition, @Nullable ItemStack ingredient) {
        super.render(minecraft, xPosition, yPosition, ingredient);

        if (ingredient != null && matcher.test(ingredient)) {
            GlStateManager.disableBlend();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5, 0.5, 1);
            // z hackery to render the text above the item
            GlStateManager.translate(0, 0, 160);

            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            fontRenderer.drawStringWithShadow(customText,
                    (xPosition + 6) * 2 - fontRenderer.getStringWidth(customText) + 19,
                    (yPosition + 1) * 2, 0xFFFF00);

            GlStateManager.popMatrix();
            GlStateManager.enableBlend();
        }
    }
}
