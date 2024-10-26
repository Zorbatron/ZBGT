package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.getMarkerMaterialByTier;
import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.getWrappedCircuitByTier;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.SolderingAlloy;

import com.zorbatron.zbgt.recipe.helpers.RecipeMapTrollery;

import gregtech.api.unification.ore.OrePrefix;

public class WrapRecipes {

    protected static void init() {
        circuits();
    }

    private static void circuits() {
        RecipeMapTrollery.clearAssemblerOnBuild();

        for (int tier = ULV; tier <= MAX; tier++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(OrePrefix.circuit, getMarkerMaterialByTier(tier))
                    .circuitMeta(16)
                    .fluidInputs(SolderingAlloy.getFluid(L / 2))
                    .output(getWrappedCircuitByTier(tier))
                    .EUt(VA[LV]).duration(20 * 30)
                    .buildAndRegister();
        }

        RecipeMapTrollery.resetAssemblerOnBuilder();
    }
}
