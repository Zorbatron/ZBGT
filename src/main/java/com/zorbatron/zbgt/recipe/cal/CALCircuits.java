package com.zorbatron.zbgt.recipe.cal;

import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.CIRCUIT_ASSEMBLY_LINE_RECIPES;
import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;

public class CALCircuits {

    public static void init() {
        // LV
        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_PLASTIC)
                .input(WRAPPED_CHIP_CPU)
                .input(WRAPPED_SMD_RESISTOR, 2)
                .input(WRAPPED_SMD_CAPACITOR, 2)
                .input(WRAPPED_SMD_TRANSISTOR, 2)
                .input(wireGtQuadruple, Copper)
                .output(MICROPROCESSOR_LV, 16 * 3)
                .EUt(VA[MV]).duration(12 * (10 * 20))
                .buildAndRegister();

        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_PLASTIC)
                .input(WRAPPED_CHIP_SOC)
                .input(wireGtQuadruple, Copper)
                .input(bolt, Tin, 16 * 2)
                .output(MICROPROCESSOR_LV, 16 * 3)
                .EUt(VA[EV]).duration((int) (12 * (2.5 * 20)))
                .buildAndRegister();

        // MV
        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_PLASTIC)
                .input(WRAPPED_CHIP_CPU)
                .input(WRAPPED_SMD_RESISTOR, 2)
                .input(WRAPPED_SMD_CAPACITOR, 2)
                .input(WRAPPED_SMD_TRANSISTOR, 2)
                .input(wireGtOctal, RedAlloy)
                .output(PROCESSOR_MV, 16 * 2)
                .EUt(VA[MV]).duration(12 * (10 * 20))
                .buildAndRegister();

        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_PLASTIC)
                .input(WRAPPED_CHIP_SOC)
                .input(wireGtOctal, RedAlloy)
                .input(bolt, AnnealedCopper, 16 * 4)
                .output(PROCESSOR_MV, 16 * 4)
                .EUt(VA[IV]).duration((int) (12 * (2.5 * 20)))
                .buildAndRegister();

        // HV
        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_ADVANCED)
                .input(WRAPPED_CHIP_CPU_NANO)
                .input(WRAPPED_SMD_RESISTOR, 8)
                .input(WRAPPED_SMD_CAPACITOR, 8)
                .input(WRAPPED_SMD_TRANSISTOR, 8)
                .input(wireGtHex, Electrum)
                .output(NANO_PROCESSOR_HV, 16 * 2)
                .EUt(VA[EV]).duration((10 * 20) * 12)
                .buildAndRegister();

        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_ADVANCED)
                .input(WRAPPED_CHIP_CPU_NANO)
                .input(WRAPPED_ADVANCED_SMD_RESISTOR, 2)
                .input(WRAPPED_ADVANCED_SMD_CAPACITOR, 2)
                .input(WRAPPED_ADVANCED_SMD_TRANSISTOR, 2)
                .input(wireGtHex, Electrum)
                .output(NANO_PROCESSOR_HV, 16 * 2)
                .EUt(VA[EV]).duration((5 * 20) * 12)
                .buildAndRegister();

        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_ADVANCED)
                .input(WRAPPED_CHIP_SOC_ADVANCED)
                .input(wireGtOctal, Electrum)
                .input(bolt, Platinum, 16 * 4)
                .output(NANO_PROCESSOR_HV, 16 * 4)
                .EUt(VA[LuV]).duration((int) (12 * (2.5 * 20)))
                .buildAndRegister();

        // EV
        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_EXTREME)
                .input(WRAPPED_CHIP_CPU_QUBIT)
                .input(WRAPPED_CHIP_CPU_NANO)
                .input(WRAPPED_SMD_CAPACITOR, 12)
                .input(WRAPPED_SMD_TRANSISTOR, 12)
                .input(wireGtOctal, Platinum, 3)
                .output(QUANTUM_PROCESSOR_EV, 16 * 2)
                .EUt(VA[IV]).duration(12 * (10 * 20))
                .buildAndRegister();

        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_EXTREME)
                .input(WRAPPED_CHIP_CPU_QUBIT)
                .input(WRAPPED_CHIP_CPU_NANO)
                .input(WRAPPED_ADVANCED_SMD_CAPACITOR, 3)
                .input(WRAPPED_ADVANCED_SMD_TRANSISTOR, 3)
                .input(wireGtOctal, Platinum, 3)
                .output(QUANTUM_PROCESSOR_EV, 16 * 2)
                .EUt(VA[IV]).duration(12 * (10 * 20))
                .buildAndRegister();

        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_EXTREME)
                .input(WRAPPED_CHIP_SOC_ADVANCED)
                .input(wireGtOctal, Platinum, 3)
                .input(stick, NiobiumTitanium, 16 * 2)
                .output(QUANTUM_PROCESSOR_EV, 16 * 4)
                .EUt(VA[ZPM]).duration((int) (12 * (2.5 * 20)))
                .buildAndRegister();

        // IV
        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_ELITE)
                .input(WRAPPED_CRYSTAL_CPU)
                .input(WRAPPED_CHIP_CPU_NANO, 2)
                .input(WRAPPED_ADVANCED_SMD_CAPACITOR, 6)
                .input(WRAPPED_ADVANCED_SMD_TRANSISTOR, 6)
                .input(wireGtHex, NiobiumTitanium)
                .output(CRYSTAL_PROCESSOR_IV, 16 * 2)
                .EUt(VA[LuV]).duration(12 * (10 * 20))
                .buildAndRegister();

        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_CIRCUIT_BOARD_ELITE)
                .input(WRAPPED_CRYSTAL_SOC)
                .input(wireGtHex, NiobiumTitanium)
                .input(stick, NiobiumTitanium, 16 * 2)
                .output(CRYSTAL_PROCESSOR_IV, 16 * 4)
                .EUt(VA[ZPM]).duration(12 * (5 * 20))
                .buildAndRegister();

        // LuV
        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_NEURO_PROCESSOR)
                .input(WRAPPED_CRYSTAL_CPU)
                .input(WRAPPED_CHIP_CPU_NANO)
                .input(WRAPPED_ADVANCED_SMD_CAPACITOR, 8)
                .input(WRAPPED_ADVANCED_SMD_TRANSISTOR, 8)
                .input(wireGtHex, YttriumBariumCuprate)
                .output(WETWARE_PROCESSOR_LUV, 16 * 2)
                .EUt(VA[ZPM]).duration(12 * (10 * 20))
                .buildAndRegister();

        CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(WRAPPED_NEURO_PROCESSOR)
                .input(WRAPPED_CHIP_SOC_HIGHLY_ADVANCED)
                .input(wireGtHex, YttriumBariumCuprate)
                .input(stick, Naquadah, 16 * 2)
                .output(WETWARE_PROCESSOR_LUV, 16 * 4)
                .EUt(VA[UV]).duration(12 * (5 * 20))
                .buildAndRegister();
    }
}
