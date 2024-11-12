package com.zorbatron.zbgt.api.recipes.properties;

import net.minecraft.client.Minecraft;

import gregtech.api.recipes.recipeproperties.RecipeProperty;

public class ChemPlantProperty extends RecipeProperty<Integer> {

    public static final String KEY = "chem_plant_casing_tier";

    public static ChemPlantProperty INSTANCE;

    protected ChemPlantProperty() {
        super(KEY, Integer.class);
    }

    public static ChemPlantProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChemPlantProperty();
        }

        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(String.format("Do this later smh %s", castValue(value)), x, y, color);
    }
}
