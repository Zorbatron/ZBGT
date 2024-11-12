package com.zorbatron.zbgt.api.recipes.maps;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;

public class RecipeMapChemPlant<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapChemPlant(@NotNull String unlocalizedName, @NotNull R defaultRecipeBuilder) {
        super(unlocalizedName, 4, 6, 4, 3, defaultRecipeBuilder, false);
    }
}
