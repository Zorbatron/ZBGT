package com.zorbatron.zbgt.api.recipes.builders;

import gregtech.api.GTValues;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;

public class CALRecipeBuilder extends RecipeBuilder<CALRecipeBuilder> {

    private int solderMultiplier = 1;

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

    public CALRecipeBuilder solderMultiplier(int multiplier) {
        if (1 > GTValues.L * multiplier || GTValues.L * multiplier > 64000) {
            GTLog.logger.error("Fluid multiplier cannot exceed 64000mb total. Multiplier: {}", multiplier);
            GTLog.logger.error("Stacktrace:", new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.solderMultiplier = multiplier;
        return this;
    }

    public int getSolderMultiplier() {
        return this.solderMultiplier;
    }
}
