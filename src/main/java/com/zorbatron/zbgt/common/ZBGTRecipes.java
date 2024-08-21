package com.zorbatron.zbgt.common;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.ITEM_IMPORT_BUS;

import com.zorbatron.zbgt.ZBGTCore;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.ingredients.GTRecipeOreInput;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.loaders.recipe.CraftingComponent;

public class ZBGTRecipes {

    public static void init() {
        ZBGTCore.LOGGER.info("Registering recipes...");
        coverRecipes();
        multiblockPartRecipes();
    }

    private static void coverRecipes() {
        final int initialSolderAmount = 144;
        final int initialLubricantAmount = 250;

        MetaItem<?>.MetaValueItem[] conveyors = { CONVEYOR_MODULE_LV, CONVEYOR_MODULE_MV, CONVEYOR_MODULE_HV,
                CONVEYOR_MODULE_EV, CONVEYOR_MODULE_IV, CONVEYOR_MODULE_LuV, CONVEYOR_MODULE_ZPM, CONVEYOR_MODULE_UV,
                CONVEYOR_MODULE_UHV, CONVEYOR_MODULE_UEV, CONVEYOR_MODULE_UIV, CONVEYOR_MODULE_UXV,
                CONVEYOR_MODULE_OpV };
        MetaItem<?>.MetaValueItem[] pumps = { ELECTRIC_PUMP_LV, ELECTRIC_PUMP_MV, ELECTRIC_PUMP_HV, ELECTRIC_PUMP_EV,
                ELECTRIC_PUMP_IV, ELECTRIC_PUMP_LuV, ELECTRIC_PUMP_ZPM, ELECTRIC_PUMP_UV, ELECTRIC_PUMP_UHV,
                ELECTRIC_PUMP_UEV, ELECTRIC_PUMP_UIV, ELECTRIC_PUMP_UXV, ELECTRIC_PUMP_OpV };
        MetaItem<?>.MetaValueItem[] dualCovers = { DUAL_COVER_LV, DUAL_COVER_MV, DUAL_COVER_HV, DUAL_COVER_EV,
                DUAL_COVER_IV, DUAL_COVER_LuV, DUAL_COVER_ZPM, DUAL_COVER_UV, DUAL_COVER_UHV, DUAL_COVER_UEV,
                DUAL_COVER_UIV, DUAL_COVER_UXV, DUAL_COVER_OpV };
        Material[] markerMaterialTiers = { MarkerMaterials.Tier.LV, MarkerMaterials.Tier.MV, MarkerMaterials.Tier.HV,
                MarkerMaterials.Tier.EV, MarkerMaterials.Tier.IV, MarkerMaterials.Tier.LuV, MarkerMaterials.Tier.ZPM,
                MarkerMaterials.Tier.UV, MarkerMaterials.Tier.UHV, MarkerMaterials.Tier.UEV, MarkerMaterials.Tier.UIV,
                MarkerMaterials.Tier.UXV, MarkerMaterials.Tier.OpV };

        // Dual Covers in assemblers, LV-IV
        for (int i = 0; i < IV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(conveyors[i])
                    .input(OrePrefix.frameGt,
                            ((UnificationEntry) CraftingComponent.PLATE.getIngredient(i + 1)).material)
                    .input(pumps[i])
                    .input(OrePrefix.circuit, markerMaterialTiers[i])
                    .circuitMeta(2)
                    .fluidInputs(Tin.getFluid(L))
                    .output(dualCovers[i])
                    .EUt(VA[i + 1])
                    .duration(20)
                    .buildAndRegister();
        }

        // Dual Covers in assembly lines, LuV - OpV
        // LuV separate, so it can use the scanner instead of the research station.
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(conveyors[LuV - 1])
                .input(OrePrefix.frameGt,
                        ((UnificationEntry) CraftingComponent.PLATE.getIngredient(LuV)).material)
                .input(pumps[LuV - 1])
                .input(OrePrefix.circuit, markerMaterialTiers[LuV - 1])
                .fluidInputs(SolderingAlloy.getFluid(initialSolderAmount))
                .fluidInputs(Lubricant.getFluid(initialLubricantAmount))
                .scannerResearch(scanner -> scanner
                        .researchStack(DUAL_COVER_IV.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .output(dualCovers[LuV - 1])
                .EUt(VA[LuV])
                .duration(20 * 15)
                .buildAndRegister();

        for (int i = ZPM - 1; i < (GregTechAPI.isHighTier() ? OpV : UV); i++) {
            GTRecipeOreInput circuit = new GTRecipeOreInput(OrePrefix.circuit, markerMaterialTiers[i]);
            if (circuit.getInputStacks().length == 0) continue;

            final int tier = i + 1;
            ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(conveyors[i])
                    .input(OrePrefix.frameGt,
                            ((UnificationEntry) CraftingComponent.PLATE.getIngredient(i + 1)).material)
                    .input(pumps[i])
                    .input(circuit)
                    .fluidInputs(SolderingAlloy.getFluid(initialSolderAmount * (int) Math.pow(2, tier - LuV)))
                    .fluidInputs(Lubricant.getFluid(initialLubricantAmount * (int) Math.pow(2, tier - LuV)))
                    .stationResearch(research -> research
                            .researchStack(dualCovers[tier - 1].getStackForm())
                            .CWUt((int) (16 * Math.pow(2, tier - ZPM)))
                            .EUt(VA[tier]))
                    .output(dualCovers[i])
                    .EUt(VA[i + 1])
                    .duration(20 * 30)
                    .buildAndRegister();
        }

        MetaItem<?>.MetaValueItem[] robotArms = { ROBOT_ARM_LV, ROBOT_ARM_MV, ROBOT_ARM_HV, ROBOT_ARM_EV, ROBOT_ARM_IV,
                ROBOT_ARM_LuV, ROBOT_ARM_ZPM, ROBOT_ARM_UV, ROBOT_ARM_UHV, ROBOT_ARM_UEV, ROBOT_ARM_UIV, ROBOT_ARM_UXV,
                ROBOT_ARM_OpV };
        // FLUID_REGULATOR_UHV, FLUID_REGULATOR_UEV, FLUID_REGULATOR_UIV, FLUID_REGULATOR_UXV, FLUID_REGULATOR_OpV
        // There are UHV-OpV robot arms but not for fluid regulators!?
        MetaItem<?>.MetaValueItem[] fluidRegulators = { FLUID_REGULATOR_LV, FLUID_REGULATOR_MV, FLUID_REGULATOR_HV,
                FLUID_REGULATOR_EV, FLUID_REGULATOR_IV, FLUID_REGULATOR_LUV, FLUID_REGULATOR_ZPM, FLUID_REGULATOR_UV };
        MetaItem<?>.MetaValueItem[] preciseDualCovers = { PRECISE_DUAL_COVER_LV, PRECISE_DUAL_COVER_MV,
                PRECISE_DUAL_COVER_HV, PRECISE_DUAL_COVER_EV, PRECISE_DUAL_COVER_IV, PRECISE_DUAL_COVER_LuV,
                PRECISE_DUAL_COVER_ZPM, PRECISE_DUAL_COVER_UV, PRECISE_DUAL_COVER_UHV, PRECISE_DUAL_COVER_UEV,
                PRECISE_DUAL_COVER_UIV, PRECISE_DUAL_COVER_UXV, PRECISE_DUAL_COVER_OpV };

        // Precise Dual Covers, LV-IV
        for (int i = 0; i < IV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(robotArms[i])
                    .input(OrePrefix.frameGt,
                            ((UnificationEntry) CraftingComponent.PLATE.getIngredient(i + 1)).material)
                    .input(fluidRegulators[i])
                    .input(OrePrefix.circuit, markerMaterialTiers[i])
                    .circuitMeta(2)
                    .fluidInputs(Tin.getFluid(L))
                    .output(preciseDualCovers[i])
                    .EUt(VA[i + 1])
                    .duration(20)
                    .buildAndRegister();
        }

        // Dual Covers in assembly lines, LuV - UV
        // LuV separate, so it can use the scanner instead of the research station.
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(robotArms[LuV - 1])
                .input(OrePrefix.frameGt,
                        ((UnificationEntry) CraftingComponent.PLATE.getIngredient(LuV)).material)
                .input(fluidRegulators[LuV - 1])
                .input(OrePrefix.circuit, markerMaterialTiers[LuV - 1])
                .fluidInputs(SolderingAlloy.getFluid(initialSolderAmount))
                .fluidInputs(Lubricant.getFluid(initialLubricantAmount))
                .scannerResearch(scanner -> scanner
                        .researchStack(PRECISE_DUAL_COVER_IV.getStackForm())
                        .duration(1200)
                        .EUt(VA[IV]))
                .output(preciseDualCovers[LuV - 1])
                .EUt(VA[LuV])
                .duration(20 * 15)
                .buildAndRegister();

        for (int i = ZPM - 1; i < UV; i++) {
            GTRecipeOreInput circuit = new GTRecipeOreInput(OrePrefix.circuit, markerMaterialTiers[i]);
            if (circuit.getInputStacks().length == 0) continue;

            final int tier = i;
            ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(robotArms[i])
                    .input(OrePrefix.frameGt,
                            ((UnificationEntry) CraftingComponent.PLATE.getIngredient(i + 1)).material)
                    .input(fluidRegulators[i])
                    .input(OrePrefix.circuit, markerMaterialTiers[i])
                    .fluidInputs(SolderingAlloy.getFluid(initialSolderAmount * (int) Math.pow(2, tier - LuV)))
                    .fluidInputs(Lubricant.getFluid(initialLubricantAmount * (int) Math.pow(2, tier - LuV)))
                    .stationResearch(research -> research
                            .researchStack(preciseDualCovers[tier - 1].getStackForm())
                            .CWUt((int) (16 * Math.pow(2, tier - ZPM)))
                            .EUt(VA[tier + 1]))
                    .output(preciseDualCovers[i])
                    .EUt(VA[i + 1])
                    .duration(20 * 30)
                    .buildAndRegister();
        }
    }

    private static void multiblockPartRecipes() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ITEM_IMPORT_BUS[0])
                .circuitMeta(10)
                .output(ZBGTMetaTileEntities.SINGLE_ITEM_INPUT_BUS)
                .duration(20)
                .EUt(VA[1])
                .buildAndRegister();
    }
}
