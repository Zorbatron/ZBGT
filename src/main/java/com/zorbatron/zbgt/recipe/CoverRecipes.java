package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.common.items.MetaItems.*;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

public class CoverRecipes {

    protected static void init() {
        dualCovers();
        preciseDualCovers();
    }

    private static void dualCovers() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_LV)
                .input(ELECTRIC_PUMP_LV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(DUAL_COVER_LV)
                .EUt(VA[LV]).duration(20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_MV)
                .input(ELECTRIC_PUMP_MV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.MV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(DUAL_COVER_MV)
                .EUt(VA[MV]).duration(20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_HV)
                .input(ELECTRIC_PUMP_HV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(DUAL_COVER_HV)
                .EUt(VA[HV]).duration(20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_EV)
                .input(ELECTRIC_PUMP_EV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(DUAL_COVER_EV)
                .EUt(VA[EV]).duration(20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_IV)
                .input(ELECTRIC_PUMP_IV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.IV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(DUAL_COVER_IV)
                .EUt(VA[IV]).duration(20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_LuV)
                .input(ELECTRIC_PUMP_LuV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LuV)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L))
                .fluidInputs(Materials.Lubricant.getFluid(250))
                .scannerResearch(scanner -> scanner
                        .researchStack(DUAL_COVER_IV.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .output(DUAL_COVER_LuV)
                .EUt(VA[LuV]).duration(20 * 15)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_ZPM)
                .input(ELECTRIC_PUMP_ZPM)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.ZPM)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 2))
                .fluidInputs(Materials.Lubricant.getFluid(250 * 2))
                .stationResearch(research -> research
                        .researchStack(DUAL_COVER_LuV.getStackForm())
                        .CWUt(16)
                        .EUt(VA[LuV]))
                .output(DUAL_COVER_ZPM)
                .EUt(VA[ZPM]).duration(20 * 20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(CONVEYOR_MODULE_UV)
                .input(ELECTRIC_PUMP_UV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.UV)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Materials.Lubricant.getFluid(250 * 4))
                .fluidInputs(Materials.Naquadria.getFluid(L * 4))
                .stationResearch(research -> research
                        .researchStack(DUAL_COVER_ZPM.getStackForm())
                        .CWUt(32)
                        .EUt(VA[ZPM]))
                .output(DUAL_COVER_UV)
                .EUt(VA[UV]).duration(20 * 25)
                .buildAndRegister();
    }

    private static void preciseDualCovers() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_LV)
                .input(FLUID_REGULATOR_LV)
                .input(SENSOR_LV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(PRECISE_DUAL_COVER_LV)
                .EUt(VA[LV]).duration(20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_MV)
                .input(FLUID_REGULATOR_MV)
                .input(SENSOR_MV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.MV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(PRECISE_DUAL_COVER_MV)
                .EUt(VA[MV]).duration(20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_HV)
                .input(FLUID_REGULATOR_HV)
                .input(SENSOR_HV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.HV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(PRECISE_DUAL_COVER_HV)
                .EUt(VA[HV]).duration(20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_EV)
                .input(FLUID_REGULATOR_EV)
                .input(SENSOR_EV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.EV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(PRECISE_DUAL_COVER_EV)
                .EUt(VA[EV]).duration(20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_IV)
                .input(FLUID_REGULATOR_IV)
                .input(SENSOR_IV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.IV)
                .circuitMeta(2)
                .fluidInputs(Materials.Tin.getFluid(L))
                .output(PRECISE_DUAL_COVER_IV)
                .EUt(VA[IV]).duration(20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_LuV)
                .input(FLUID_REGULATOR_LUV)
                .input(SENSOR_LuV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.LuV)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L))
                .fluidInputs(Materials.Lubricant.getFluid(250))
                .scannerResearch(scanner -> scanner
                        .researchStack(PRECISE_DUAL_COVER_IV.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .output(PRECISE_DUAL_COVER_LuV)
                .EUt(VA[LuV]).duration(20 * 15)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_ZPM)
                .input(FLUID_REGULATOR_ZPM)
                .input(SENSOR_ZPM)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.ZPM)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 2))
                .fluidInputs(Materials.Lubricant.getFluid(250 * 2))
                .stationResearch(research -> research
                        .researchStack(PRECISE_DUAL_COVER_LuV.getStackForm())
                        .CWUt(16)
                        .EUt(VA[LuV]))
                .output(PRECISE_DUAL_COVER_ZPM)
                .EUt(VA[ZPM]).duration(20 * 20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(ROBOT_ARM_UV)
                .input(FLUID_REGULATOR_UV)
                .input(SENSOR_UV)
                .input(OrePrefix.circuit, MarkerMaterials.Tier.UV)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 4))
                .fluidInputs(Materials.Lubricant.getFluid(250 * 4))
                .fluidInputs(Materials.Naquadria.getFluid(L * 4))
                .stationResearch(research -> research
                        .researchStack(PRECISE_DUAL_COVER_ZPM.getStackForm())
                        .CWUt(32)
                        .EUt(VA[ZPM]))
                .output(PRECISE_DUAL_COVER_UV)
                .EUt(VA[UV]).duration(20 * 25)
                .buildAndRegister();
    }
}
