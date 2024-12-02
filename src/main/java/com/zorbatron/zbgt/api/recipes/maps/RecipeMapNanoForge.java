package com.zorbatron.zbgt.api.recipes.maps;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;

public class RecipeMapNanoForge<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapNanoForge(@NotNull String unlocalizedName, @NotNull R defaultRecipeBuilder) {
        super(unlocalizedName, 6, 2, 3, 0, defaultRecipeBuilder, false);
    }
}
