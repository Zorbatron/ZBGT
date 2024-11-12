package com.zorbatron.zbgt.api.recipes.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

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
        int tier = castValue(value);
        minecraft.fontRenderer.drawString(I18n.format("recipemap.chem_plant.tier", tier,
                I18n.format(String.format("zbgt.machine.chem_plant.casing.%d", tier - 1))), x, y, color);
    }
}
