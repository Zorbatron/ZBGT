package com.zorbatron.zbgt.api.recipes.maps;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;

public class RecipeMapCAL<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapCAL(@NotNull String unlocalizedName, @NotNull R defaultRecipeBuilder) {
        super(unlocalizedName, 6, 1, 1, 0, defaultRecipeBuilder, false);
    }
}
