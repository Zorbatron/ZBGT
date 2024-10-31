package com.zorbatron.zbgt.api.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.recipes.properties.NanoForgeProperty;
import com.zorbatron.zbgt.api.util.ZBGTLog;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.EnumValidationResult;

public class NanoForgeRecipeBuilder extends RecipeBuilder<NanoForgeRecipeBuilder> {

    public NanoForgeRecipeBuilder() {}

    public NanoForgeRecipeBuilder(Recipe recipe, RecipeMap<NanoForgeRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NanoForgeRecipeBuilder(RecipeBuilder<NanoForgeRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public NanoForgeRecipeBuilder copy() {
        return new NanoForgeRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, @Nullable Object value) {
        if (key.equals(NanoForgeProperty.KEY)) {

            return true;
        }

        return super.applyProperty(key, value);
    }

    public NanoForgeRecipeBuilder nanoForgeTier(int tier) {
        if (tier < 1) {
            ZBGTLog.logger.error("Nano forge tier cannot be less than 1!",
                    new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        } else if (tier > 3) {
            ZBGTLog.logger.error("Nano forge tier cannot be more than 3!", new IllegalArgumentException());
            this.recipeStatus = EnumValidationResult.INVALID;
        }

        applyProperty(NanoForgeProperty.getInstance(), tier);

        return this;
    }

    public int getNanoForgeTier() {
        return recipePropertyStorage == null ? 0 :
                recipePropertyStorage.getRecipePropertyValue(NanoForgeProperty.getInstance(), 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(super.toString())
                .append(NanoForgeProperty.getInstance().getKey(), getNanoForgeTier())
                .toString();
    }
}
