package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static gregicality.multiblocks.api.recipes.GCYMRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.loaders.recipe.CraftingComponent;

public class MaterialRecipes {

    protected static void init() {
        chemicalReactor();
        alloyBlast();
        mixer();
        ebf();
    }

    private static void chemicalReactor() {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Lithium)
                .fluidInputs(Chlorine.getFluid(L))
                .output(dust, LithiumChloride)
                .EUt(VA[MV]).duration(56)
                .buildAndRegister();
    }

    private static void alloyBlast() {
        blastHelper(ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(dust, Bismuth, 47)
                .input(dust, Lead, 25)
                .input(dust, Tin, 13)
                .input(dust, Cadmium, 10)
                .input(dust, Indium, 5)
                .fluidOutputs(Indalloy140.getFluid(L * 100))
                .blastFurnaceTemp(5475)
                .EUt(VA[IV]),
                20 * 40, BlastProperty.GasTier.MID);

        // blastHelper(ALLOY_BLAST_RECIPES.recipeBuilder()
        // .input(dust, Ruthenium)
        // .input(dust, Rhodium)
        // .input(dust, Palladium)
        // .input(dust, Platinum)
        // .input(dust, Osmium)
        // .input(dust, Iridium)
        // .fluidOutputs(PreciousMetalsAlloy.getFluid(L * 6))
        // .EUt(VA[UV]),
        // 20 * 95 + 5, BlastProperty.GasTier.HIGHEST);
    }

    private static void mixer() {
        MIXER_RECIPES.recipeBuilder()
                .input(dust, TungstenSteel, 12)
                .input(dust, HSSE, 9)
                .input(dust, HSSG, 6)
                .input(dust, Ruridit, 3)
                .input(dust, Titanium, 2)
                .input(dust, Plutonium239)
                .output(dust, HDCS, 33)
                .EUt(VA[IV]).duration(20 * 25)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Adamantium, 5)
                .input(dust, Naquadah, 2)
                .input(dust, Lanthanum, 3)
                .output(dust, AdamantiumAlloy, 10)
                .EUt(VA[EV]).duration(20 * 10 + 14)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, AdamantiumAlloy, 12)
                .input(dust, Tin, 8)
                .input(dust, Arsenic, 7)
                .input(dust, Caesium, 4)
                .input(dust, Osmiridium, 3)
                .output(dust, Artherium_Sn, 34)
                .EUt(VA[IV]).duration(20 * 15 + 9)
                .buildAndRegister();
    }

    private static void ebf() {
        BLAST_RECIPES.recipeBuilder()
                .input(ingot, MAR_M200, 18)
                .input(ingot, Cerium)
                .fluidInputs(LithiumChloride.getFluid(L))
                .output(ingotHot, MAR_CE_M200, 19)
                .EUt(VA[ZPM]).duration(20 * 75)
                .buildAndRegister();
    }

    private static void blastHelper(RecipeBuilder<?> blastBuilder, int duration, BlastProperty.GasTier gasTier) {
        blastHelper(blastBuilder, duration, gasTier, 1, 2);
    }

    private static void blastHelper(RecipeBuilder<?> blastBuilder, int duration, BlastProperty.GasTier gasTier,
                                    int circ1, int circ2) {
        FluidStack gas = CraftingComponent.EBF_GASES.get(gasTier).copy();

        blastBuilder.copy()
                .circuitMeta(1)
                .duration(duration)
                .buildAndRegister();

        blastBuilder.copy()
                .circuitMeta(2)
                .fluidInputs(gas)
                .duration((int) (duration * 0.67))
                .buildAndRegister();
    }
}
