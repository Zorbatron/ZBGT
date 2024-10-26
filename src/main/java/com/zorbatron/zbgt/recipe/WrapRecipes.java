package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.getMarkerMaterialByTier;
import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.getWrappedCircuitByTier;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.Polyethylene;
import static gregtech.api.unification.material.Materials.SolderingAlloy;
import static gregtech.common.items.MetaItems.*;

import com.zorbatron.zbgt.recipe.helpers.RecipeMapTrollery;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.ore.OrePrefix;

public class WrapRecipes {

    protected static void init() {
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

        registerWrappingRecipe(
                new MetaItem<?>.MetaValueItem[] { SMD_CAPACITOR, SMD_DIODE, SMD_RESISTOR, SMD_TRANSISTOR,
                        SMD_INDUCTOR },
                new MetaItem<?>.MetaValueItem[] { WRAPPED_SMD_CAPACITOR, WRAPPED_SMD_DIODE, WRAPPED_SMD_RESISTOR,
                        WRAPPED_SMD_TRANSISTOR, WRAPPED_SMD_INDUCTOR });
        registerWrappingRecipe(
                new MetaItem<?>.MetaValueItem[] { ADVANCED_SMD_CAPACITOR, ADVANCED_SMD_DIODE, ADVANCED_SMD_RESISTOR,
                        ADVANCED_SMD_TRANSISTOR, ADVANCED_SMD_INDUCTOR },
                new MetaItem<?>.MetaValueItem[] { WRAPPED_ADVANCED_SMD_CAPACITOR, WRAPPED_ADVANCED_SMD_DIODE,
                        WRAPPED_ADVANCED_SMD_RESISTOR, WRAPPED_ADVANCED_SMD_TRANSISTOR,
                        WRAPPED_ADVANCED_SMD_INDUCTOR });
        registerWrappingRecipe(
                new MetaItem<?>.MetaValueItem[] { BASIC_CIRCUIT_BOARD, GOOD_CIRCUIT_BOARD, PLASTIC_CIRCUIT_BOARD,
                        ADVANCED_CIRCUIT_BOARD, ELITE_CIRCUIT_BOARD, EXTREME_CIRCUIT_BOARD, WETWARE_CIRCUIT_BOARD },
                new MetaItem<?>.MetaValueItem[] { WRAPPED_BOARD_BASIC, WRAPPED_BOARD_GOOD, WRAPPED_BOARD_PLASTIC,
                        WRAPPED_BOARD_ADVANCED, WRAPPED_BOARD_ELITE, WRAPPED_BOARD_EXTREME, WRAPPED_BOARD_WETWARE });
    }

    private static void registerWrappingRecipe(MetaItem<?>.MetaValueItem[] unwrapped,
                                               MetaItem<?>.MetaValueItem[] wrapped) {
        for (int x = 0; x < wrapped.length; x++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(unwrapped[x], 16)
                    .fluidInputs(Polyethylene.getFluid(L / 2))
                    .output(wrapped[x])
                    .EUt(VA[LV]).duration(20 * 5)
                    .buildAndRegister();
        }
    }
}
