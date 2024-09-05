package com.zorbatron.zbgt.loaders.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;

import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;

import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.unification.material.Materials;
import gregtech.common.metatileentities.MetaTileEntities;

public class MultiblockRecipes {

    public static void init() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GCYMMetaTileEntities.ALLOY_BLAST_SMELTER)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_ABS)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ELECTRIC_BLAST_FURNACE)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_EBF)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.LARGE_CHEMICAL_REACTOR)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_LCR)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CRACKER)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_OCU)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.VACUUM_FREEZER)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_VF)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();
    }
}
