package com.zorbatron.zbgt.api.recipes.helpers;

import com.github.bsideup.jabel.Desugar;

import gregtech.api.recipes.RecipeMap;

@Desugar
public record RecipeIOMod(RecipeMap<?> recipeMap, int minItemInputs, int minItemOutputs, int minFluidInputs,
                          int minFluidOutputs) {}
