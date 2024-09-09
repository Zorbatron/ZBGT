package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;

import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

public class MultiblockRecipes {

    protected static void init() {
        assemblerRecipes();
        assemblyLineRecipes();
    }

    private static void assemblerRecipes() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GCYMMetaTileEntities.ALLOY_BLAST_SMELTER, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_ABS)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ELECTRIC_BLAST_FURNACE, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_EBF)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.LARGE_CHEMICAL_REACTOR, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_LCR)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CRACKER, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_OCU)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.VACUUM_FREEZER, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .circuitMeta(17)
                .output(ZBGTMetaTileEntities.MEGA_VF)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ELECTRIC_BLAST_FURNACE, 4)
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .circuitMeta(4)
                .output(ZBGTMetaTileEntities.QUAD_EBF)
                .duration(20 * 18).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(getRobotArmByTier(IV))
                .input(circuit, getMarkerMaterialByTier(IV), 4)
                .input(MetaItems.TOOL_DATA_ORB, 3)
                .input(cableGtOctal, Titanium, 4)
                .input(gear, Titanium, 4)
                .input(plateDouble, TungstenSteel, 2)
                .input(plate, Iridium, 2)
                .input(bolt, Electrum, 48)
                .fluidInputs(Palladium.getFluid(L * 8))
                .output(ZBGTMetaTileEntities.PRASS)
                .EUt(VA[IV]).duration(20 * 18)
                .buildAndRegister();
    }

    private static void assemblyLineRecipes() {
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ASSEMBLY_LINE, 16)
                .inputs(MetaBlocks.MULTIBLOCK_CASING
                        .getItemVariant(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_LINE_CASING, 16))
                .inputs(MetaBlocks.MULTIBLOCK_CASING
                        .getItemVariant(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_CONTROL, 32))
                .input(getRobotArmByTier(ZPM), 16)
                .input(getConveyorByTier(ZPM), 32)
                .input(getMotorByTier(ZPM), 32)
                .input(pipeNormalFluid, Polybenzimidazole, 16)
                .input(plateDense, Iridium, 32)
                .input(MetaTileEntities.FLUID_SOLIDIFIER[ZPM - 1], 16)
                .input(circuit, getMarkerMaterialByTier(ZPM), 16)
                .input(circuit, getMarkerMaterialByTier(ZPM - 1), 20)
                .input(circuit, getMarkerMaterialByTier(ZPM - 2), 24)
                .fluidInputs(SolderingAlloy.getFluid(L * 12))
                .fluidInputs(Naquadria.getFluid(L * 16))
                .fluidInputs(Lubricant.getFluid(5000))
                .stationResearch(research -> research
                        .researchStack(ZBGTMetaBlocks.CoAL_CASING.getItemVariant(CoALCasing.CasingType.CASING_EV))
                        .CWUt(128)
                        .EUt(VA[UV]))
                .output(ZBGTMetaTileEntities.CoAL)
                .EUt(VA[UV]).duration(20 * 30)
                .buildAndRegister();
    }
}
