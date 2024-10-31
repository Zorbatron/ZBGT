package com.zorbatron.zbgt.api.recipes.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import gregtech.api.recipes.recipeproperties.RecipeProperty;

public class NanoForgeProperty extends RecipeProperty<Integer> {

    public static final String KEY = "nano_forge_tier";

    public static NanoForgeProperty INSTANCE;

    protected NanoForgeProperty() {
        super(KEY, Integer.class);
    }

    public static NanoForgeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NanoForgeProperty();
        }

        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("zbgt.machine.nano_forge.tier", castValue(value)), x, y, color);
    }
}
