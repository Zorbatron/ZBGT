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
                new MetaItem<?>.MetaValueItem[] { WRAPPED_CIRCUIT_BOARD_BASIC, WRAPPED_CIRCUIT_BOARD_GOOD,
                        WRAPPED_CIRCUIT_BOARD_PLASTIC, WRAPPED_CIRCUIT_BOARD_ADVANCED, WRAPPED_CIRCUIT_BOARD_ELITE,
                        WRAPPED_CIRCUIT_BOARD_EXTREME, WRAPPED_CIRCUIT_BOARD_WETWARE });

        registerWrappingRecipe(
                new MetaItem<?>.MetaValueItem[] { SIMPLE_SYSTEM_ON_CHIP, SYSTEM_ON_CHIP, ADVANCED_SYSTEM_ON_CHIP,
                        HIGHLY_ADVANCED_SOC, CENTRAL_PROCESSING_UNIT, NANO_CENTRAL_PROCESSING_UNIT,
                        QUBIT_CENTRAL_PROCESSING_UNIT, ULTRA_LOW_POWER_INTEGRATED_CIRCUIT,
                        LOW_POWER_INTEGRATED_CIRCUIT, POWER_INTEGRATED_CIRCUIT, HIGH_POWER_INTEGRATED_CIRCUIT,
                        ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, RANDOM_ACCESS_MEMORY, NOR_MEMORY_CHIP, NAND_MEMORY_CHIP,
                        INTEGRATED_LOGIC_CIRCUIT },
                new MetaItem<?>.MetaValueItem[] { WRAPPED_CHIP_SOC_SIMPLE, WRAPPED_CHIP_SOC, WRAPPED_CHIP_SOC_ADVANCED,
                        WRAPPED_CHIP_SOC_HIGHLY_ADVANCED, WRAPPED_CHIP_CPU, WRAPPED_CHIP_CPU_NANO,
                        WRAPPED_CHIP_CPU_QUBIT, WRAPPED_CHIP_PIC_ULTRA_LOW, WRAPPED_CHIP_PIC_LOW, WRAPPED_CHIP_PIC,
                        WRAPPED_CHIP_PIC_HIGH, WRAPPED_CHIP_PIC_ULTRA_HIGH, WRAPPED_CHIP_RAM, WRAPPED_CHIP_NOR,
                        WRAPPED_CHIP_NAND, WRAPPED_CHIP_INTEGRATED_LOGIC });
    }

    private static void registerWrappingRecipe(MetaItem<?>.MetaValueItem[] unwrapped,
                                               MetaItem<?>.MetaValueItem[] wrapped) {
        for (int x = 0; x < wrapped.length; x++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(unwrapped[x], 16)
                    .fluidInputs(Polyethylene.getFluid(L / 2))
                    .circuitMeta(16)
                    .output(wrapped[x])
                    .EUt(VA[LV]).duration(20 * 5)
                    .buildAndRegister();
        }
    }
}
