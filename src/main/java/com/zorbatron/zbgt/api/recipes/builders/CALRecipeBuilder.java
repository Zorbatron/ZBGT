package com.zorbatron.zbgt.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;

public class CALRecipeBuilder extends RecipeBuilder<CALRecipeBuilder> {

    public CALRecipeBuilder() {}

    public CALRecipeBuilder(Recipe recipe, RecipeMap<CALRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public CALRecipeBuilder(RecipeBuilder<CALRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public CALRecipeBuilder copy() {
        return new CALRecipeBuilder(this);
    }
}
