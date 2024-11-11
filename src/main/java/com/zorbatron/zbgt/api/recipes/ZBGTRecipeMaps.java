package com.zorbatron.zbgt.api.recipes;

import static gregtech.api.recipes.RecipeMaps.*;

import com.zorbatron.zbgt.api.recipes.builders.CoALRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.builders.PreciseAssemblerRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapCoAL;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapPreciseAssembler;

import gregtech.api.recipes.RecipeMap;

public final class ZBGTRecipeMaps {

    public static final RecipeMap<CoALRecipeBuilder> CoAL_RECIPES = new RecipeMapCoAL<>(
            "coal_recipes",
            new CoALRecipeBuilder());

    public static final RecipeMap<PreciseAssemblerRecipeBuilder> PRECISE_ASSEMBLER_RECIPES = new RecipeMapPreciseAssembler<>(
            "precise_assembler_recipes",
            new PreciseAssemblerRecipeBuilder());

    public static void modifyMaps() {
        POLARIZER_RECIPES.setMaxFluidInputs(1);
        POLARIZER_RECIPES.setMaxFluidOutputs(1);

        COMPRESSOR_RECIPES.setMaxFluidInputs(1);
    }
}
