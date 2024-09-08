package com.zorbatron.zbgt.api.recipes;

import static gregtech.api.recipes.RecipeMaps.*;

import java.util.ArrayList;
import java.util.List;

import com.zorbatron.zbgt.api.recipes.builders.CoALRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.helpers.RecipeIOMod;

import gregtech.api.recipes.RecipeMap;

public final class ZBGTRecipeMaps {

    private ZBGTRecipeMaps() {}

    public static final RecipeMap<CoALRecipeBuilder> CoAL_RECIPES = new RecipeMapCoAL<>(
            "coal_recipes",
            new CoALRecipeBuilder());

    public static void modifyMaps() {
        List<RecipeIOMod> recipeModList = new ArrayList<>();
        recipeModList.add(new RecipeIOMod(POLARIZER_RECIPES, 0, 0, 1, 1));
        recipeModList.add(new RecipeIOMod(COMPRESSOR_RECIPES, 0, 0, 1, 1));

        recipeModList.forEach(recipeIOMod -> {
            int recipeMaxItemInputs = recipeIOMod.recipeMap().getMaxInputs();
            int recipeMaxItemOutputs = recipeIOMod.recipeMap().getMaxOutputs();
            int recipeMaxFluidInputs = recipeIOMod.recipeMap().getMaxFluidInputs();
            int recipeMaxFluidOutputs = recipeIOMod.recipeMap().getMaxFluidOutputs();

            int minItemInputs = recipeIOMod.minItemInputs();
            int minItemOutputs = recipeIOMod.minItemOutputs();
            int minFluidInputs = recipeIOMod.minFluidInputs();
            int minFluidOutputs = recipeIOMod.minFluidOutputs();

            if (recipeMaxItemInputs < minItemInputs) {
                recipeIOMod.recipeMap().setMaxInputs(minItemInputs);
            }

            if (recipeMaxItemOutputs < minItemOutputs) {
                recipeIOMod.recipeMap().setMaxOutputs(minItemOutputs);
            }

            if (recipeMaxFluidInputs < minFluidInputs) {
                recipeIOMod.recipeMap().setMaxFluidInputs(minFluidInputs);
            }

            if (recipeMaxFluidOutputs < minFluidOutputs) {
                recipeIOMod.recipeMap().setMaxFluidOutputs(minFluidOutputs);
            }
        });
    }
}
