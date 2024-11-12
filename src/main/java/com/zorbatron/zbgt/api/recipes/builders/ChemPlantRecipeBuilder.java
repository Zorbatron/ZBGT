package com.zorbatron.zbgt.api.recipes.builders;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.recipes.properties.ChemPlantProperty;
import com.zorbatron.zbgt.api.recipes.properties.PreciseAssemblerProperty;
import com.zorbatron.zbgt.api.util.ZBGTLog;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;

public class ChemPlantRecipeBuilder extends RecipeBuilder<ChemPlantRecipeBuilder> {

    public ChemPlantRecipeBuilder() {}

    public ChemPlantRecipeBuilder(Recipe recipe, RecipeMap<ChemPlantRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ChemPlantRecipeBuilder(RecipeBuilder<ChemPlantRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public ChemPlantRecipeBuilder copy() {
        return new ChemPlantRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        if (key.equals(ChemPlantProperty.KEY)) {
            this.casingTier(((Number) value).intValue());
            return true;
        }

        return super.applyProperty(key, value);
    }

    public ChemPlantRecipeBuilder casingTier(int tier) {
        if (tier < 0) {
            ZBGTLog.logger.error("Chem Plant casing tier cannot be less than 1 (Bronze)!",
                    new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        } else if (tier > 4) {
            ZBGTLog.logger.error("Precise casing tier cannot be more than 4 (Tungstensteel)!",
                    new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(PreciseAssemblerProperty.getInstance(), tier);

        return this;
    }
}
