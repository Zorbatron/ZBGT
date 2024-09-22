package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.unification.ZBGTMaterials.*;
import static gregicality.multiblocks.api.recipes.GCYMRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

public class MaterialRecipes {

    protected static void init() {
        alloyBlast();
        ebf();
    }

    private static void ebf() {
        BLAST_RECIPES.recipeBuilder()
                .input(ingot, MAR_M200, 18)
                .input(ingot, Cerium)
                .fluidInputs(LithiumChloride.getFluid(L))
                .output(ingotHot, MAR_CE_M200, 19)
                .EUt(VA[ZPM]).duration(20 * 75)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(ingot, MAR_M200, 18)
                .input(ingot, Cerium)
                .fluidInputs(LithiumChloride.getFluid(L))
                .output(ingotHot, MAR_CE_M200, 19)
                .EUt(VA[ZPM]).duration(1004)
                .buildAndRegister();
    }

    private static void alloyBlast() {
        ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(dust, Bismuth, 47)
                .input(dust, Lead, 25)
                .input(dust, Tin, 13)
                .input(dust, Cadmium, 10)
                .input(dust, Indium, 5)
                .circuitMeta(5)
                .fluidOutputs(Indalloy140.getFluid(L * 100))
                .EUt(VA[IV]).duration(20 * 40)
                .buildAndRegister();

        ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(dust, Bismuth, 47)
                .input(dust, Lead, 25)
                .input(dust, Tin, 13)
                .input(dust, Cadmium, 10)
                .input(dust, Indium, 5)
                .fluidInputs(Helium.getFluid(1000))
                .circuitMeta(15)
                .fluidOutputs(Indalloy140.getFluid(L * 100))
                .EUt(VA[IV]).duration(536)
                .buildAndRegister();
    }
}
