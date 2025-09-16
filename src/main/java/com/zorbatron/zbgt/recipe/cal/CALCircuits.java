package com.zorbatron.zbgt.recipe.cal;

import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.CIRCUIT_ASSEMBLY_LINE_RECIPES;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;

import com.zorbatron.zbgt.api.recipes.builders.CALRecipeBuilder;
import com.zorbatron.zbgt.api.recipes.builders.CALRecipeBuilder.*;

import gregtech.api.metatileentity.multiblock.CleanroomType;

public final class CALCircuits {

    public static void init() {
        ulv();
        lv();
        mv();
        hv();
        ev();
        iv();
        luv();
        zpm();
    }

    private static void ulv() {
        // TODO
    }

    private static void lv() {
        builder(16, 10 * 20)
                .board(BoardType.BASIC)
                .component(ComponentType.RESISTOR, 2)
                .wire(RedAlloy, 2)
                .anyTierCircuit(ULV, 32)
                .output(ELECTRONIC_CIRCUIT_LV, 16 * 2)
                .buildAndRegister();

        builder(16, 10 * 20)
                .board(BoardType.BASIC)
                .chip(ChipType.INTEGRATED_LOGIC)
                .component(ComponentType.RESISTOR, 2)
                .component(ComponentType.DIODE, 2)
                .fineWire(Copper, 2)
                .bolt(Tin, 2)
                .output(INTEGRATED_CIRCUIT_LV, 16 * 2)
                .buildAndRegister();

        builder(60, 10 * 20)
                .board(BoardType.PLASTIC)
                .cpu(CPUType.NORMAL)
                .component(ComponentType.RESISTOR)
                .component(ComponentType.CAPACITOR)
                .component(ComponentType.TRANSISTOR)
                .fineWire(Copper, 2)
                .output(MICROPROCESSOR_LV, 16 * 3)
                .buildAndRegister();

        builder(600, 50)
                .board(BoardType.PLASTIC)
                .soc(SOCType.NORMAL)
                .fineWire(Copper, 2)
                .bolt(Tin, 2)
                .output(MICROPROCESSOR_LV, 16 * 6)
                .buildAndRegister();
    }

    private static void mv() {
        builder(16, 15 * 20)
                .board(BoardType.GOOD)
                .anyTierCircuit(LV, 32)
                .component(ComponentType.DIODE, 2)
                .wire(Copper, 2)
                .output(ELECTRONIC_CIRCUIT_MV, 16)
                .buildAndRegister();

        builder(24, 20 * 20)
                .board(BoardType.GOOD)
                .input(INTEGRATED_CIRCUIT_LV, 32)
                .component(ComponentType.RESISTOR, 2)
                .component(ComponentType.DIODE, 2)
                .fineWire(Gold, 4)
                .bolt(Silver, 4)
                .output(INTEGRATED_CIRCUIT_MV, 16 * 2)
                .buildAndRegister();

        builder(60, 10 * 20)
                .board(BoardType.PLASTIC)
                .cpu(CPUType.NORMAL)
                .component(ComponentType.RESISTOR, 4)
                .component(ComponentType.CAPACITOR, 4)
                .component(ComponentType.TRANSISTOR, 4)
                .fineWire(RedAlloy, 4)
                .output(PROCESSOR_MV, 16 * 2)
                .buildAndRegister();

        builder(2400, 50)
                .board(BoardType.PLASTIC)
                .soc(SOCType.NORMAL)
                .fineWire(RedAlloy, 4)
                .bolt(AnnealedCopper, 4)
                .output(PROCESSOR_MV, 16 * 4)
                .buildAndRegister();
    }

    private static void hv() {
        builder(30, 40 * 20)
                .input(INTEGRATED_CIRCUIT_MV, 16 * 2)
                .chip(ChipType.INTEGRATED_LOGIC, 2)
                .chip(ChipType.RAM, 2)
                .component(ComponentType.TRANSISTOR, 4)
                .fineWire(Electrum, 8)
                .bolt(AnnealedCopper, 8)
                .output(INTEGRATED_CIRCUIT_HV, 16)
                .buildAndRegister();

        builder(90, 20 * 20, 2)
                .board(BoardType.PLASTIC)
                .input(INTEGRATED_CIRCUIT_MV, 16 * 2)
                .component(ComponentType.INDUCTOR, 4)
                .component(ComponentType.CAPACITOR, 8)
                .chip(ChipType.RAM, 4)
                .fineWire(RedAlloy, 8)
                .output(INTEGRATED_CIRCUIT_HV, 16)
                .buildAndRegister();

        builder(600, 10 * 20)
                .board(BoardType.ADVANCED)
                .cpu(CPUType.NANO)
                .component(ComponentType.RESISTOR, 8)
                .component(ComponentType.CAPACITOR, 8)
                .component(ComponentType.TRANSISTOR, 8)
                .fineWire(Electrum, 8)
                .output(NANO_PROCESSOR_HV, 16 * 2)
                .buildAndRegister();

        builder(600, 5 * 20)
                .board(BoardType.ADVANCED)
                .cpu(CPUType.NANO)
                .component(ComponentType.ADVANCED_RESISTOR, 2)
                .component(ComponentType.ADVANCED_CAPACITOR, 2)
                .component(ComponentType.ADVANCED_TRANSISTOR, 2)
                .fineWire(Electrum, 8)
                .output(NANO_PROCESSOR_HV, 16 * 2)
                .buildAndRegister();

        builder(9600, 50)
                .board(BoardType.ADVANCED)
                .soc(SOCType.ADVANCED)
                .fineWire(Electrum, 4)
                .bolt(Platinum, 4)
                .output(NANO_PROCESSOR_HV, 16 * 4)
                .buildAndRegister();
    }

    private static void ev() {
        builder(120, 20 * 20, 2)
                .board(BoardType.PLASTIC)
                .input(INTEGRATED_CIRCUIT_HV, 16 * 2)
                .component(ComponentType.DIODE, 4)
                .chip(ChipType.RAM, 4)
                .fineWire(Electrum, 16)
                .bolt(BlueAlloy, 16)
                .output(WORKSTATION_EV, 16)
                .buildAndRegister();

        builder(600, 20 * 20, 2)
                .board(BoardType.ADVANCED)
                .input(NANO_PROCESSOR_HV, 16 * 2)
                .component(ComponentType.INDUCTOR, 4)
                .component(ComponentType.CAPACITOR, 8)
                .chip(ChipType.RAM, 8)
                .fineWire(Electrum, 16)
                .output(NANO_PROCESSOR_ASSEMBLY_EV, 16)
                .buildAndRegister();

        builder(2400, 10 * 20)
                .board(BoardType.EXTREME)
                .cpu(CPUType.QUBIT)
                .cpu(CPUType.NANO)
                .component(ComponentType.CAPACITOR, 12)
                .component(ComponentType.TRANSISTOR, 12)
                .fineWire(Platinum, 12)
                .output(QUANTUM_PROCESSOR_EV, 16 * 2)
                .buildAndRegister();

        builder(2400, 5 * 20)
                .board(BoardType.EXTREME)
                .cpu(CPUType.QUBIT)
                .cpu(CPUType.NANO)
                .component(ComponentType.CAPACITOR, 3)
                .component(ComponentType.TRANSISTOR, 3)
                .fineWire(Platinum, 12)
                .output(QUANTUM_PROCESSOR_EV, 16 * 2)
                .buildAndRegister();

        builder(2400, 5 * 20)
                .board(BoardType.EXTREME)
                .soc(SOCType.ADVANCED)
                .fineWire(Platinum, 12)
                .bolt(NiobiumTitanium, 8)
                .output(QUANTUM_PROCESSOR_EV, 16 * 4)
                .buildAndRegister();
    }

    private static void iv() {
        builder(480, 40 * 20, 4)
                .frameBox(Aluminium, 2)
                .input(WORKSTATION_EV, 16 * 2)
                .component(ComponentType.INDUCTOR, 8)
                .component(ComponentType.CAPACITOR, 16)
                .chip(ChipType.RAM, 16)
                .wire(AnnealedCopper, 16)
                .output(MAINFRAME_IV, 16)
                .buildAndRegister();

        builder(480, 20 * 20, 4)
                .frameBox(Aluminium, 2)
                .input(WORKSTATION_EV, 16 * 2)
                .component(ComponentType.ADVANCED_INDUCTOR, 2)
                .component(ComponentType.ADVANCED_CAPACITOR, 4)
                .chip(ChipType.RAM, 16)
                .wire(AnnealedCopper, 16)
                .output(MAINFRAME_IV, 16)
                .buildAndRegister();

        builder(600, 20 * 20, 2)
                .board(BoardType.ADVANCED)
                .input(NANO_PROCESSOR_ASSEMBLY_EV, 16 * 2)
                .component(ComponentType.DIODE, 8)
                .chip(ChipType.NOR, 4)
                .chip(ChipType.RAM, 16)
                .fineWire(Electrum, 16)
                .output(NANO_COMPUTER_IV, 16)
                .buildAndRegister();

        builder(600, 10 * 20, 2)
                .board(BoardType.ADVANCED)
                .input(NANO_PROCESSOR_ASSEMBLY_EV, 16 * 2)
                .component(ComponentType.ADVANCED_DIODE, 8)
                .chip(ChipType.NOR, 4)
                .chip(ChipType.RAM, 16)
                .fineWire(Electrum, 16)
                .output(NANO_COMPUTER_IV, 16)
                .buildAndRegister();

        builder(2400, 20 * 20, 2)
                .board(BoardType.EXTREME)
                .input(QUANTUM_PROCESSOR_EV, 16 * 2)
                .component(ComponentType.INDUCTOR, 8)
                .component(ComponentType.CAPACITOR, 16)
                .chip(ChipType.RAM, 4)
                .fineWire(Platinum, 16)
                .output(QUANTUM_ASSEMBLY_IV, 16)
                .buildAndRegister();

        builder(2400, 10 * 20, 2)
                .board(BoardType.EXTREME)
                .input(QUANTUM_PROCESSOR_EV, 16 * 2)
                .component(ComponentType.ADVANCED_INDUCTOR, 2)
                .component(ComponentType.ADVANCED_CAPACITOR, 4)
                .chip(ChipType.RAM, 4)
                .fineWire(Platinum, 16)
                .output(QUANTUM_ASSEMBLY_IV, 16)
                .buildAndRegister();

        builder(9600, 10 * 20)
                .board(BoardType.ELITE)
                .cpu(CPUType.CRYSTAL)
                .cpu(CPUType.NANO, 2)
                .component(ComponentType.ADVANCED_CAPACITOR, 6)
                .component(ComponentType.ADVANCED_TRANSISTOR, 6)
                .fineWire(NiobiumTitanium, 8)
                .output(CRYSTAL_PROCESSOR_IV, 16 * 2)
                .buildAndRegister();

        builder(86000, 5 * 10)
                .board(BoardType.ELITE)
                .soc(SOCType.CRYSTAL)
                .fineWire(NiobiumTitanium, 8)
                .bolt(YttriumBariumCuprate, 8)
                .output(CRYSTAL_PROCESSOR_IV, 16 * 4)
                .buildAndRegister();
    }

    private static void luv() {
        builder(1920, 40 * 20, 4)
                .frameBox(Aluminium, 2)
                .input(NANO_COMPUTER_IV, 16 * 2)
                .component(ComponentType.INDUCTOR, 16)
                .component(ComponentType.CAPACITOR, 32)
                .chip(ChipType.RAM, 16)
                .wire(AnnealedCopper, 32)
                .output(NANO_MAINFRAME_LUV, 16)
                .buildAndRegister();

        builder(1920, 20 * 20, 4)
                .frameBox(Aluminium, 2)
                .input(NANO_COMPUTER_IV, 16 * 2)
                .component(ComponentType.ADVANCED_INDUCTOR, 4)
                .component(ComponentType.ADVANCED_CAPACITOR, 8)
                .chip(ChipType.RAM, 16)
                .wire(AnnealedCopper, 32)
                .output(NANO_MAINFRAME_LUV, 16)
                .buildAndRegister();

        builder(2400, 20 * 20, 2)
                .board(BoardType.EXTREME)
                .input(QUANTUM_ASSEMBLY_IV, 16 * 2)
                .component(ComponentType.DIODE, 8)
                .chip(ChipType.NOR, 4)
                .chip(ChipType.RAM, 16)
                .fineWire(Platinum, 32)
                .output(QUANTUM_COMPUTER_LUV, 16)
                .buildAndRegister();

        builder(2400, 10 * 20, 2)
                .board(BoardType.EXTREME)
                .input(QUANTUM_ASSEMBLY_IV, 16 * 2)
                .component(ComponentType.DIODE, 2)
                .chip(ChipType.NOR, 4)
                .chip(ChipType.RAM, 16)
                .fineWire(Platinum, 32)
                .output(QUANTUM_COMPUTER_LUV, 16)
                .buildAndRegister();

        builder(9600, 20 * 20, 2)
                .board(BoardType.ELITE)
                .input(CRYSTAL_PROCESSOR_IV, 16 * 2)
                .component(ComponentType.ADVANCED_INDUCTOR, 4)
                .component(ComponentType.ADVANCED_CAPACITOR, 8)
                .chip(ChipType.RAM, 24)
                .fineWire(NiobiumTitanium, 16)
                .output(CRYSTAL_ASSEMBLY_LUV, 16)
                .buildAndRegister();

        builder(38400, 10 * 20)
                .cpu(CPUType.NEURO)
                .cpu(CPUType.CRYSTAL)
                .cpu(CPUType.NANO)
                .component(ComponentType.ADVANCED_CAPACITOR, 8)
                .component(ComponentType.ADVANCED_TRANSISTOR, 8)
                .fineWire(YttriumBariumCuprate, 8)
                .output(WETWARE_PROCESSOR_LUV, 16 * 2)
                .buildAndRegister();

        builder(150000, 5 * 20)
                .cpu(CPUType.NEURO)
                .soc(SOCType.HIGHLY_ADVANCED)
                .fineWire(YttriumBariumCuprate, 8)
                .bolt(Naquadah, 8)
                .output(WETWARE_PROCESSOR_LUV, 16 * 4)
                .buildAndRegister();
    }

    private static void zpm() {
        builder(7680, 40 * 20, 4)
                .frameBox(HSSG, 2)
                .input(QUANTUM_COMPUTER_LUV, 16 * 2)
                .component(ComponentType.INDUCTOR, 24)
                .component(ComponentType.CAPACITOR, 48)
                .chip(ChipType.RAM, 24)
                .wire(AnnealedCopper, 48)
                .output(QUANTUM_MAINFRAME_ZPM, 16)
                .buildAndRegister();

        builder(7680, 20 * 20, 4)
                .frameBox(HSSG, 2)
                .input(QUANTUM_COMPUTER_LUV, 16 * 2)
                .component(ComponentType.ADVANCED_INDUCTOR, 6)
                .component(ComponentType.ADVANCED_CAPACITOR, 12)
                .chip(ChipType.RAM, 24)
                .wire(AnnealedCopper, 48)
                .output(QUANTUM_MAINFRAME_ZPM, 16)
                .buildAndRegister();

        builder(9600, 20 * 20, 2)
                .board(BoardType.ELITE)
                .input(CRYSTAL_ASSEMBLY_LUV, 16 * 2)
                .chip(ChipType.RAM, 4)
                .chip(ChipType.NOR, 32)
                .chip(ChipType.NAND, 64)
                .fineWire(NiobiumTitanium, 32)
                .output(CRYSTAL_COMPUTER_ZPM, 16)
                .buildAndRegister();

        builder(38400, 20 * 20, 2)
                .board(BoardType.MASTER)
                .input(WETWARE_PROCESSOR_LUV, 16 * 2)
                .component(ComponentType.ADVANCED_INDUCTOR, 6)
                .component(ComponentType.ADVANCED_CAPACITOR, 12)
                .chip(ChipType.RAM, 24)
                .fineWire(YttriumBariumCuprate, 16)
                .output(WETWARE_PROCESSOR_ASSEMBLY_ZPM, 16)
                .buildAndRegister();
    }

    private static CALRecipeBuilder builder(int eut, int baseDuration) {
        return builder(eut, baseDuration, 1);
    }

    private static CALRecipeBuilder builder(int eut, int baseDuration, int solderMultiplier) {
        return CIRCUIT_ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .fluidInputs(SolderingAlloy.getFluid(72 * 16 * solderMultiplier))
                .cleanroom(CleanroomType.CLEANROOM)
                .EUt(eut).duration(baseDuration * 12);
    }
}
