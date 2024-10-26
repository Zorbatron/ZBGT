package com.zorbatron.zbgt.api.recipes;

import static gregtech.api.recipes.RecipeMaps.*;

import java.util.ArrayList;
import java.util.List;

import com.zorbatron.zbgt.api.recipes.builders.CALRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.builders.CoALRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.builders.PreciseAssemblerRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.helpers.RecipeIOMod;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapCAL;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapCoAL;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapPreciseAssembler;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.core.sound.GTSoundEvents;

public final class ZBGTRecipeMaps {

    public static final RecipeMap<CoALRecipeBuilder> CoAL_RECIPES = new RecipeMapCoAL<>(
            "coal_recipes",
            new CoALRecipeBuilder());

    public static final RecipeMap<PreciseAssemblerRecipeBuilder> PRECISE_ASSEMBLER_RECIPES = new RecipeMapPreciseAssembler<>(
            "precise_assembler_recipes",
            new PreciseAssemblerRecipeBuilder());

    public static final RecipeMap<CALRecipeBuilder> CIRCUIT_ASSEMBLY_LINE_RECIPES = new RecipeMapCAL<>(
            "circuit_assembly_line_recipes",
            new CALRecipeBuilder())
                    .setProgressBar(GuiTextures.PROGRESS_BAR_CIRCUIT_ASSEMBLER, ProgressWidget.MoveType.HORIZONTAL)
                    .setSound(GTSoundEvents.ASSEMBLER);

    public static void modifyMaps() {
        List<RecipeIOMod> recipeModList = new ArrayList<>();
        recipeModList.add(new RecipeIOMod(POLARIZER_RECIPES, 0, 0, 1, 1));
        recipeModList.add(new RecipeIOMod(COMPRESSOR_RECIPES, 0, 0, 1, 1));
        // recipeModList.add(new RecipeIOMod(MIXER_RECIPES, 9, 0, 0, 0));

        recipeModList.forEach(recipeIOMod -> {
            recipeIOMod.recipeMap().setMaxInputs(recipeIOMod.minItemInputs());
            recipeIOMod.recipeMap().setMaxOutputs(recipeIOMod.minItemOutputs());
            recipeIOMod.recipeMap().setMaxFluidInputs(recipeIOMod.minFluidInputs());
            recipeIOMod.recipeMap().setMaxFluidOutputs(recipeIOMod.minFluidOutputs());
        });
    }
}
