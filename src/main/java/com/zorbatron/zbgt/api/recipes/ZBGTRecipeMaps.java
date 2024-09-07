package com.zorbatron.zbgt.api.recipes;

import static gregtech.api.recipes.RecipeMaps.POLARIZER_RECIPES;

import com.zorbatron.zbgt.api.recipes.builders.CoALRecipeBuilder;

import gregtech.api.recipes.RecipeMap;

public final class ZBGTRecipeMaps {

    private ZBGTRecipeMaps() {}

    public static final RecipeMap<CoALRecipeBuilder> CoAL_RECIPES = new RecipeMapCoAL<>(
            "CoAL_RECIPES",
            new CoALRecipeBuilder());

    public static void modifyMaps() {
        POLARIZER_RECIPES.setMaxFluidInputs(1);
        POLARIZER_RECIPES.setMaxFluidOutputs(1);
    }
}
