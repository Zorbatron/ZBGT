package com.zorbatron.zbgt.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;

import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;

import gregtech.api.unification.material.Materials;
import gregtech.common.metatileentities.MetaTileEntities;

public class MultiblockPartRecipes {

    public static void init() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ITEM_IMPORT_BUS[0])
                .circuitMeta(10)
                .output(ZBGTMetaTileEntities.SINGLE_ITEM_INPUT_BUS)
                .duration(20 * 2).EUt(VA[LV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ITEM_IMPORT_BUS[3])
                .input(MetaTileEntities.QUANTUM_CHEST[3])
                .fluidInputs(Materials.Polyethylene.getFluid(288))
                .circuitMeta(5)
                .output(ZBGTMetaTileEntities.SUPER_INPUT_BUS)
                .duration(20 * 5).EUt(VA[HV])
                .buildAndRegister();
    }
}
