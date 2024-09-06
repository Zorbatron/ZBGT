package com.zorbatron.zbgt.api.recipes;

import com.zorbatron.zbgt.api.recipes.builders.ComponentALRecipeBuilder;

import gregtech.api.recipes.RecipeMap;

public final class ZBGTRecipeMaps {

    private ZBGTRecipeMaps() {}

    public static final RecipeMap<ComponentALRecipeBuilder> COMPONENT_AL_RECIPES = new RecipeMapComponentAL<>(
            "component_al_recipes",
            new ComponentALRecipeBuilder());
}
