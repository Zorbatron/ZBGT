package com.zorbatron.zbgt.api.recipes.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;

import gregtech.api.recipes.recipeproperties.RecipeProperty;

public class PreciseAssemblerProperty extends RecipeProperty<Integer> {

    public static final String KEY = "precise_casing_tier";

    public static PreciseAssemblerProperty INSTANCE;

    protected PreciseAssemblerProperty() {
        super(KEY, Integer.class);
    }

    public static PreciseAssemblerProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreciseAssemblerProperty();
        }

        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("zbgt.machine.precise_assembler.precise_casing.tier",
                I18n.format(PreciseCasing.CasingType.getUntranslatedShortNameByTier(castValue(value)))), x, y, color);
    }
}
