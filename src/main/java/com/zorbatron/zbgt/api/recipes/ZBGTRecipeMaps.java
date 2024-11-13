package com.zorbatron.zbgt.api.recipes;

import static gregtech.api.recipes.RecipeMaps.*;

import com.zorbatron.zbgt.api.recipes.builders.CALRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.builders.ChemPlantRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.builders.CoALRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.builders.PreciseAssemblerRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapCAL;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapChemPlant;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapCoAL;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapPreciseAssembler;

import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
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
                    .setSound(GTSoundEvents.ASSEMBLER)
                    .onRecipeBuild(recipeBuilder -> {
                        if (recipeBuilder.getFluidInputs().isEmpty()) {
                            recipeBuilder.fluidInputs(Materials.SolderingAlloy
                                    .getFluid(Math.max(1, (GTValues.L / 2) * recipeBuilder.getSolderMultiplier())));
                        }
                    });

    public static final RecipeMapChemPlant<ChemPlantRecipeBuilder> CHEM_PLANT_RECIPES = new RecipeMapChemPlant<>(
            "chem_plant_recipes",
            new ChemPlantRecipeBuilder());

    public static void modifyMaps() {
        POLARIZER_RECIPES.setMaxFluidInputs(1);
        POLARIZER_RECIPES.setMaxFluidOutputs(1);

        COMPRESSOR_RECIPES.setMaxFluidInputs(1);
    }
}
