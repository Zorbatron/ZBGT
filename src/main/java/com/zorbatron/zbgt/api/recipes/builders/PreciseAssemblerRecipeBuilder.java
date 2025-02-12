package com.zorbatron.zbgt.api.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.recipes.properties.PreciseAssemblerProperty;
import com.zorbatron.zbgt.api.util.ZBGTLog;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;

public class PreciseAssemblerRecipeBuilder extends RecipeBuilder<PreciseAssemblerRecipeBuilder> {

    public PreciseAssemblerRecipeBuilder() {}

    public PreciseAssemblerRecipeBuilder(Recipe recipe, RecipeMap<PreciseAssemblerRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public PreciseAssemblerRecipeBuilder(RecipeBuilder<PreciseAssemblerRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public PreciseAssemblerRecipeBuilder copy() {
        return new PreciseAssemblerRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        if (key.equals(PreciseAssemblerProperty.KEY)) {
            this.casingTier(((Number) value).intValue());
            return true;
        }

        return super.applyProperty(key, value);
    }

    public PreciseAssemblerRecipeBuilder casingTier(int tier) {
        if (tier < 0) {
            ZBGTLog.logger.error("Precise casing tier cannot be less than 0 (Imprecise)!",
                    new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        } else if (tier > 4) {
            ZBGTLog.logger.error("Precise casing tier cannot be more than 4 (Mk-IV)!", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }

        this.applyProperty(PreciseAssemblerProperty.getInstance(), tier);

        return this;
    }

    public int getCasingTier() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(PreciseAssemblerProperty.getInstance(), 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(super.toString())
                .append(PreciseAssemblerProperty.getInstance().getKey(), getCasingTier())
                .toString();
    }
}
