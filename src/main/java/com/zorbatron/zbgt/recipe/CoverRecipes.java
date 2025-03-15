package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.common.items.MetaItems.*;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.zorbatron.zbgt.recipe.helpers.RecipeAssists;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;

public class CoverRecipes {

    protected static void init() {
        dualCovers();
        preciseDualCovers();
        dropperCovers();
        misc();
    }

    private static void dualCovers() {
        for (int tier = LV; tier < LuV; tier++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(RecipeAssists.getConveyorByTier(tier))
                    .input(RecipeAssists.getPumpByTier(tier))
                    .input(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(tier))
                    .circuitMeta(2)
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L / 2))
                    .output(RecipeAssists.getDualCoverByTier(tier))
                    .EUt(VA[tier]).duration(20)
                    .buildAndRegister();
        }

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
        for (int tier = LV; tier < LuV; tier++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(RecipeAssists.getRobotArmByTier(tier))
                    .input(RecipeAssists.getFluidRegulatorByTier(tier))
                    .input(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(tier))
                    .circuitMeta(2)
                    .fluidInputs(Materials.SolderingAlloy.getFluid(L / 2))
                    .output(RecipeAssists.getPreciseDualCoverByTier(tier))
                    .EUt(VA[tier]).duration(20)
                    .buildAndRegister();
        }

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

    private static void dropperCovers() {
        for (int tier = LV; tier < LuV; tier++) {
            ModHandler.addShapedRecipe(String.format("dropper_cover_%s", VN[tier]),
                    getDropperCoverByTier(tier).getStackForm(),
                    "CD ",
                    "TwP",
                    'C', getConveyorByTier(tier),
                    'D', new ItemStack(Blocks.DROPPER),
                    'T', new UnificationEntry(OrePrefix.circuit, getMarkerMaterialByTier(tier)),
                    'P', getPistonByTier(tier));
        }

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getConveyorByTier(LuV))
                .input(Blocks.DROPPER)
                .input(OrePrefix.circuit, getMarkerMaterialByTier(LuV))
                .input(getPistonByTier(LuV))
                .fluidInputs(Materials.HSSS.getFluid(L))
                .output(getDropperCoverByTier(LuV))
                .EUt(VA[LuV]).duration(20 * 10)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getConveyorByTier(ZPM))
                .input(Blocks.DROPPER)
                .input(OrePrefix.circuit, getMarkerMaterialByTier(ZPM))
                .input(getPistonByTier(ZPM))
                .fluidInputs(Materials.Naquadria.getFluid(L * 2))
                .output(getDropperCoverByTier(ZPM))
                .EUt(VA[ZPM]).duration(20 * 10)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getConveyorByTier(UV))
                .input(Blocks.DROPPER)
                .input(OrePrefix.circuit, getMarkerMaterialByTier(UV))
                .input(getPistonByTier(UV))
                .fluidInputs(Materials.Neutronium.getFluid(L * 4))
                .output(getDropperCoverByTier(UV))
                .EUt(VA[UV]).duration(20 * 10)
                .buildAndRegister();
    }

    private static void misc() {
        ModHandler.addShapedRecipe("regname_item_filter", REGNAME_FILTER_ITEM.getStackForm(),
                "FFF",
                "SPF",
                "FFF",
                'F', new UnificationEntry(OrePrefix.foil, Materials.Zinc),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
                'S', SENSOR_EV);

        ModHandler.addShapedRecipe("local_name_fluid_filter_lapis", REGNAME_FILTER_FLUID.getStackForm(),
                "LFL",
                "SPF",
                "LFL",
                'F', new UnificationEntry(OrePrefix.foil, Materials.Zinc),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'S', SENSOR_IV,
                'L', new UnificationEntry(OrePrefix.plate, Materials.Lapis));

        ModHandler.addShapedRecipe("local_name_fluid_filter_lazurite", REGNAME_FILTER_FLUID.getStackForm(),
                "LFL",
                "SPF",
                "LFL",
                'F', new UnificationEntry(OrePrefix.foil, Materials.Zinc),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'S', SENSOR_IV,
                'L', new UnificationEntry(OrePrefix.plate, Materials.Lazurite));

        ModHandler.addShapedRecipe("local_name_fluid_filter_sodalite", REGNAME_FILTER_FLUID.getStackForm(),
                "LFL",
                "SPF",
                "LFL",
                'F', new UnificationEntry(OrePrefix.foil, Materials.Zinc),
                'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
                'S', SENSOR_IV,
                'L', new UnificationEntry(OrePrefix.plate, Materials.Sodalite));
    }
}
