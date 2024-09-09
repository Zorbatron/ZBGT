package com.zorbatron.zbgt.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.util.ZBGTMods;
import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;

import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.unification.material.Materials;
import gregtech.common.metatileentities.MetaTileEntities;

public class MultiblockPartRecipes {

    public static void init() {
        misc();

        if (ZBGTMods.GCYM.isModLoaded()) {
            largeParallelHatches();
        }
    }

    private static void misc() {
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

    private static void largeParallelHatches() {
        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GCYMMetaTileEntities.PARALLEL_HATCH[3], 4)
                .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[0])
                .CasingTier(0)
                .EUt(VA[LuV]).duration(20 * 30)
                .buildAndRegister();

        for (int tier = 0; tier < ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES.length - 2; tier++) {
            ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[tier], 4)
                    .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[tier + 1])
                    .CasingTier(tier)
                    .EUt(VA[tier + 7]).duration(20 * 30)
                    .buildAndRegister();
        }

        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[5], 2048)
                .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[6])
                .EUt(VA[MAX]).duration(20 * 3600)
                .CasingTier(4)
                .buildAndRegister();
    }
}
