package com.zorbatron.zbgt.common;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.*;

import net.minecraftforge.fluids.FluidStack;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.ore.OrePrefix;

public class ZBGTRecipes {

    public static void init() {
        assemblerRecipes();
    }

    private static void assemblerRecipes() {
        FluidStack tinAlloyFluid = Tin.getFluid(L);

        // Dual Covers
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_LV)
                .input(ELECTRIC_PUMP_LV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(DUAL_COVER_LV)
                .EUt(GTValues.VA[LV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_MV)
                .input(ELECTRIC_PUMP_MV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.MV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(DUAL_COVER_MV)
                .EUt(GTValues.VA[MV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_HV)
                .input(ELECTRIC_PUMP_HV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(DUAL_COVER_HV)
                .EUt(GTValues.VA[HV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_EV)
                .input(ELECTRIC_PUMP_EV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(DUAL_COVER_EV)
                .EUt(GTValues.VA[EV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_IV)
                .input(ELECTRIC_PUMP_IV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.IV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(DUAL_COVER_IV)
                .EUt(GTValues.VA[IV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_LuV)
                .input(ELECTRIC_PUMP_LuV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LuV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(DUAL_COVER_LuV)
                .EUt(GTValues.VA[LuV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_ZPM)
                .input(ELECTRIC_PUMP_ZPM)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.ZPM)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(DUAL_COVER_ZPM)
                .EUt(GTValues.VA[ZPM]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_UV)
                .input(ELECTRIC_PUMP_UV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.UV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(DUAL_COVER_UV)
                .EUt(GTValues.VA[UV]).duration(20)
                .buildAndRegister();

        // Precise Dual Covers
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_LV)
                .input(FLUID_REGULATOR_LV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(PRECISE_DUAL_COVER_LV)
                .EUt(GTValues.VA[LV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_MV)
                .input(FLUID_REGULATOR_MV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.MV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(PRECISE_DUAL_COVER_MV)
                .EUt(GTValues.VA[MV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_HV)
                .input(FLUID_REGULATOR_HV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(PRECISE_DUAL_COVER_HV)
                .EUt(GTValues.VA[HV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_EV)
                .input(FLUID_REGULATOR_EV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(PRECISE_DUAL_COVER_EV)
                .EUt(GTValues.VA[EV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_IV)
                .input(FLUID_REGULATOR_IV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.IV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(PRECISE_DUAL_COVER_IV)
                .EUt(GTValues.VA[IV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_LuV)
                .input(FLUID_REGULATOR_LUV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LuV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(PRECISE_DUAL_COVER_LuV)
                .EUt(GTValues.VA[LuV]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_ZPM)
                .input(FLUID_REGULATOR_ZPM)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.ZPM)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(PRECISE_DUAL_COVER_ZPM)
                .EUt(GTValues.VA[ZPM]).duration(20)
                .buildAndRegister();

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_UV)
                .input(FLUID_REGULATOR_UV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.UV)
                .circuitMeta(2)
                .fluidInputs(tinAlloyFluid)
                .output(PRECISE_DUAL_COVER_UV)
                .EUt(GTValues.VA[UV]).duration(20)
                .buildAndRegister();
    }
}
