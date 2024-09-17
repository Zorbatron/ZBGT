package com.zorbatron.zbgt.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;

import com.zorbatron.zbgt.ZBGTConfig;
import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.recipe.helpers.RecipeAssists;

import gregicality.multiblocks.api.unification.GCYMMaterials;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.metatileentities.MetaTileEntities;

public class MultiblockPartRecipes {

    protected static void init() {
        misc();
        if (ZBGTConfig.recipeSettings.enableParallelHatchRecipes) {
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
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 128))
                .fluidInputs(Materials.Polyethylene.getFluid(L * 96))
                .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[0])
                .CasingTier(0)
                .EUt(VA[LuV]).duration(20 * 30)
                .buildAndRegister();

        for (int tier = 0; tier < ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES.length - 2; tier++) {
            int t2FluidAmount = (int) (L * 24 * Math.pow(2, tier + 2));
            ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[tier], 4)
                    .fluidInputs(Materials.SolderingAlloy.getFluid((int) (L * 32 * Math.pow(4, tier))))
                    .fluidInputs(tier > 2 ? Materials.Polybenzimidazole.getFluid(t2FluidAmount) :
                            Materials.Polytetrafluoroethylene.getFluid(t2FluidAmount))
                    .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[tier + 1])
                    .CasingTier(tier)
                    .EUt(VA[tier + 7]).duration(20 * 30)
                    .buildAndRegister();
        }

        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[5], 2048)
                .input(OrePrefix.plateDense, GCYMMaterials.Trinaquadalloy, 512)
                .input(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(UHV), 128)
                .fluidInputs(Materials.RutheniumTriniumAmericiumNeutronate.getFluid(L * 2048))
                .fluidInputs(Materials.NaquadahEnriched.getFluid(L * 1024))
                .fluidInputs(Materials.PolychlorinatedBiphenyl.getFluid(L * 512))
                .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[6])
                .EUt(VA[MAX]).duration(20 * 3600)
                .CasingTier(4)
                .buildAndRegister();
    }
}
