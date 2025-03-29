package com.zorbatron.zbgt.recipe.chemistry;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.Alumina;
import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.SluiceSand;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;

import com.zorbatron.zbgt.api.unification.material.ZBGTMaterials;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;

public class SluiceJuice {

    public static void init() {
        makeJuice();
        processJuice();
    }

    @SuppressWarnings("DuplicatedCode")
    private static void makeJuice() {
        RecipeBuilder<SimpleRecipeBuilder> baseRecipe = CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(NitricAcid.getFluid(10))
                .fluidOutputs(ZBGTMaterials.SluiceJuice.getFluid(10))
                .EUt(VA[MV]).duration(45);

        baseRecipe.copy()
                .input(dust, Andradite)
                .chancedOutput(dust, Quicklime, 5000, 500)
                .chancedOutput(dust, Iron, 4000, 400)
                .chancedOutput(dust, Alumina, 300, 300)
                .chancedOutput(dust, Gold, 300, 300)
                .chancedOutput(dust, Vanadium, 200, 200)
                .chancedOutput(dust, Rutile, 600, 600)
                .buildAndRegister();

        baseRecipe.copy()
                .input(dust, Spessartine)
                .chancedOutput(dust, Alumina, 5000, 500)
                .chancedOutput(dust, Pyrolusite, 4000, 400)
                .chancedOutput(dust, Iron, 300, 300)
                .chancedOutput(dust, Calcite, 300, 300)
                .chancedOutput(dust, Magnesium, 300, 300)
                .chancedOutput(dust, Tantalum, 200, 200)
                .buildAndRegister();

        baseRecipe.copy()
                .input(dust, Almandine)
                .chancedOutput(dust, Alumina, 5000, 500)
                .chancedOutput(dust, Iron, 4000, 400)
                .chancedOutput(dust, Gold, 300, 300)
                .chancedOutput(dust, Calcite, 300, 300)
                .chancedOutput(dust, Chrome, 200, 200)
                .chancedOutput(dust, Vanadium, 200, 200)
                .buildAndRegister();

        baseRecipe.copy()
                .input(dust, Pyrope)
                .chancedOutput(dust, Alumina, 5000, 500)
                .chancedOutput(dust, Magnesia, 4000, 400)
                .chancedOutput(dust, Silver, 300, 300)
                .chancedOutput(dust, Iron, 300, 300)
                .chancedOutput(dust, Calcite, 300, 300)
                .chancedOutput(dust, Vanadium, 200, 200)
                .buildAndRegister();

        baseRecipe.copy()
                .input(dust, Grossular)
                .chancedOutput(dust, Quicklime, 5000, 500)
                .chancedOutput(dust, Alumina, 4000, 400)
                .chancedOutput(dust, Iron, 300, 300)
                .chancedOutput(dust, Gold, 300, 300)
                .chancedOutput(dust, Calcite, 300, 300)
                .chancedOutput(dust, Vanadium, 200, 200)
                .buildAndRegister();

        baseRecipe.copy()
                .input(dust, Uvarovite)
                .chancedOutput(dust, Quicklime, 5000, 500)
                .chancedOutput(dust, Chrome, 4000, 400)
                .chancedOutput(dust, Iron, 300, 300)
                .chancedOutput(dust, Silver, 300, 300)
                .chancedOutput(dust, Alumina, 200, 200)
                .chancedOutput(dust, Manganese, 200, 200)
                .buildAndRegister();
    }

    private static void processJuice() {
        CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(ZBGTMaterials.SluiceJuice.getFluid(1000))
                .output(dust, Stone)
                .chancedOutput(dust, Iron, 4000, 400)
                .chancedOutput(dust, Copper, 2000, 200)
                .chancedOutput(dust, Tin, 2000, 200)
                .chancedOutput(dust, Nickel, 2000, 200)
                .chancedOutput(dust, Silver, 2000, 200)
                .fluidOutputs(Water.getFluid(500))
                .EUt(VA[MV]).duration(40)
                .buildAndRegister();

        DISTILLERY_RECIPES.recipeBuilder()
                .fluidInputs(ZBGTMaterials.SluiceJuice.getFluid(1000))
                .output(dust, SluiceSand)
                .fluidOutputs(Water.getFluid(500))
                .EUt(VA[LV]).duration(100)
                .buildAndRegister();

        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                .input(dust, SluiceSand)
                .chancedOutput(dust, Iron, 4000, 400)
                .chancedOutput(dust, Neodymium, 2000, 200)
                .chancedOutput(dust, Chrome, 2000, 200)
                .EUt(VA[HV]).duration(200)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, SluiceSand)
                .fluidInputs(Water.getFluid(500))
                .fluidOutputs(ZBGTMaterials.SluiceJuice.getFluid(1000))
                .EUt(VA[LV]).duration(100)
                .buildAndRegister();
    }
}
