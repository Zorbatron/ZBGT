package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import net.minecraft.init.Items;

import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import gregicality.multiblocks.api.unification.GCYMMaterials;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockCleanroomCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

public class MultiblockRecipes {

    protected static void init() {
        gcyms();
        quads();
        megas();
        misc();
    }

    private static void gcyms() {
        ModHandler.addShapedRecipe("large_gas_collector", ZBGTMetaTileEntities.LARGE_AIR_COLLECTOR.getStackForm(),
                "FCF",
                "RGR",
                "MWM",
                'F', MetaBlocks.CLEANROOM_CASING.getItemVariant(BlockCleanroomCasing.CasingType.FILTER_CASING),
                'C', new UnificationEntry(circuit, getMarkerMaterialByTier(IV)),
                'R', new UnificationEntry(rotor, TungstenSteel),
                'G', MetaTileEntities.GAS_COLLECTOR[IV].getStackForm(),
                'M', getMotorByTier(IV),
                'W', new UnificationEntry(cableGtSingle, Platinum));

        ModHandler.addShapedRecipe("large_rock_breaker", ZBGTMetaTileEntities.LARGE_ROCK_BREAKER.getStackForm(),
                "ABC",
                "DEF",
                "GHI",
                'A', new UnificationEntry(plateDouble, GCYMMaterials.MaragingSteel300),
                'B', new UnificationEntry(circuit, getMarkerMaterialByTier(IV)),
                'C', new UnificationEntry(plateDouble, GCYMMaterials.HSLASteel),
                'D', Items.WATER_BUCKET,
                'E', MetaTileEntities.ROCK_BREAKER[IV].getStackForm(),
                'F', Items.LAVA_BUCKET,
                'G', getConveyorByTier(IV),
                'H', new UnificationEntry(cableGtSingle, Platinum),
                'I', GCYMMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.CRUSHING_WHEELS));

        ModHandler.addShapedRecipe("large_alloy_smelter", ZBGTMetaTileEntities.LARGE_ALLOY_SMELTER.getStackForm(),
                "CVC",
                "WMW",
                "PAP",
                'C', new UnificationEntry(circuit, getMarkerMaterialByTier(IV)),
                'V', GCYMMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.HEAT_VENT),
                'W', new UnificationEntry(wireGtQuadruple, RTMAlloy),
                'M', MetaTileEntities.ALLOY_SMELTER[IV].getStackForm(),
                'P', new UnificationEntry(plateDouble, GCYMMaterials.HSLASteel),
                'A', MetaItems.VOLTAGE_COIL_IV);
    }

    private static void quads() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ELECTRIC_BLAST_FURNACE, 4)
                .input(plate, Invar, 4)
                .input(circuit, getMarkerMaterialByTier(HV))
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .circuitMeta(4)
                .output(ZBGTMetaTileEntities.QUAD_EBF)
                .duration(20 * 5).EUt(VA[HV])
                .buildAndRegister();

        ModHandler.addShapedRecipe("quad_ebf", ZBGTMetaTileEntities.QUAD_EBF.getStackForm(),
                "PBP",
                "BCB",
                "PBP",
                'P', new UnificationEntry(plateDouble, Invar),
                'B', MetaTileEntities.ELECTRIC_BLAST_FURNACE.getStackForm(),
                'C', new UnificationEntry(circuit, getMarkerMaterialByTier(HV)));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.VACUUM_FREEZER, 4)
                .input(plate, Aluminium, 4)
                .input(circuit, getMarkerMaterialByTier(HV))
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .circuitMeta(4)
                .output(ZBGTMetaTileEntities.QUEEZER)
                .duration(20 * 5).EUt(VA[HV])
                .buildAndRegister();

        ModHandler.addShapedRecipe("queezer", ZBGTMetaTileEntities.QUEEZER.getStackForm(),
                "PBP",
                "BCB",
                "PBP",
                'P', new UnificationEntry(plateDouble, Aluminium),
                'B', MetaTileEntities.VACUUM_FREEZER.getStackForm(),
                'C', new UnificationEntry(circuit, getMarkerMaterialByTier(HV)));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CRACKER, 4)
                .input(plate, StainlessSteel, 4)
                .input(circuit, getMarkerMaterialByTier(HV))
                .fluidInputs(SolderingAlloy.getFluid(L * 4))
                .circuitMeta(4)
                .output(ZBGTMetaTileEntities.QUACKER)
                .duration(20 * 5).EUt(VA[HV])
                .buildAndRegister();

        ModHandler.addShapedRecipe("quacker", ZBGTMetaTileEntities.QUACKER.getStackForm(),
                "PBP",
                "BCB",
                "PBP",
                'P', new UnificationEntry(plateDouble, StainlessSteel),
                'B', MetaTileEntities.CRACKER.getStackForm(),
                'C', new UnificationEntry(circuit, getMarkerMaterialByTier(HV)));
    }

    private static void megas() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GCYMMetaTileEntities.ALLOY_BLAST_SMELTER, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .output(ZBGTMetaTileEntities.MEGA_ABS)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ELECTRIC_BLAST_FURNACE, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .output(ZBGTMetaTileEntities.MEGA_EBF)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.LARGE_CHEMICAL_REACTOR, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .output(ZBGTMetaTileEntities.MEGA_LCR)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CRACKER, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .output(ZBGTMetaTileEntities.MEGA_OCU)
                .duration(20 * 30).EUt(VA[HV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.VACUUM_FREEZER, 64)
                .fluidInputs(SolderingAlloy.getFluid(L * 64))
                .output(ZBGTMetaTileEntities.MEGA_VF)
                .duration(20 * 30).EUt(VA[HV])
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

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.FUSION_REACTOR[0], 48)
                .input(plate, MAR_CE_M200, 32)
                .input(circuit, getMarkerMaterialByTier(LuV), 8)
                .input(MetaItems.HIGH_POWER_INTEGRATED_CIRCUIT_WAFER, 16)
                .input(getFieldGeneratorByTier(LuV), 4)
                .input(stickLong, MAR_CE_M200, 8)
                .fluidInputs(Polyethylene.getFluid(L * 64))
                .output(ZBGTMetaTileEntities.MEGA_FUSION[0])
                .EUt(VA[LuV]).duration(20 * 60)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.FUSION_REACTOR[1], 48)
                .input(circuit, getMarkerMaterialByTier(ZPM))
                .input(circuit, getMarkerMaterialByTier(ZPM))
                .input(circuit, getMarkerMaterialByTier(ZPM))
                .input(circuit, getMarkerMaterialByTier(ZPM))
                .input(MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER, 32)
                .input(MetaItems.VOLTAGE_COIL_LuV, 16)
                .input(MetaItems.NEUTRON_REFLECTOR, 16)
                .input(getFieldGeneratorByTier(ZPM), 8)
                .input(gearSmall, Artherium_Sn, 32)
                .fluidInputs(Indalloy140.getFluid(L * 32))
                .fluidInputs(MAR_CE_M200.getFluid(L * 16))
                .fluidInputs(HDCS.getFluid(L * 8))
                .fluidInputs(Artherium_Sn.getFluid(L * 2))
                .scannerResearch(ZBGTMetaTileEntities.MEGA_FUSION[0].getStackForm())
                .output(ZBGTMetaTileEntities.MEGA_FUSION[1])
                .EUt(VA[ZPM]).duration(20 * 150)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.FUSION_REACTOR[2], 48)
                .input(circuit, getMarkerMaterialByTier(UV))
                .input(circuit, getMarkerMaterialByTier(UV))
                .input(circuit, getMarkerMaterialByTier(UV))
                .input(circuit, getMarkerMaterialByTier(UV))
                .input(MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER, 64)
                .input(MetaItems.ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT_WAFER, 64)
                .input(MetaItems.VOLTAGE_COIL_UV, 16)
                .input(ZBGTMetaItems.ADVANCED_RADIATION_PROTECTION_PLATE, 8)
                .input(getFieldGeneratorByTier(UV), 8)
                .input(gearSmall, HDCS, 64)
                .fluidInputs(Indalloy140.getFluid(L * 128))
                .fluidInputs(TanmolyiumBetaC.getFluid(L * 16))
                .fluidInputs(Dalisenite.getFluid(L * 8))
                .fluidInputs(Americium.getFluid(L * 4))
                .stationResearch(scanner -> scanner
                        .researchStack(ZBGTMetaTileEntities.MEGA_FUSION[1].getStackForm())
                        .EUt(VA[UV]).CWUt(128))
                .output(ZBGTMetaTileEntities.MEGA_FUSION[2])
                .EUt(VA[UV]).duration(20 * 300)
                .buildAndRegister();
    }

    private static void misc() {
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
                .input(plateDouble, Iridium, 32)
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

        ModHandler.addShapedRecipe("yottank", ZBGTMetaTileEntities.YOTTANK.getStackForm(),
                "STS",
                "CYC",
                "SPS",
                'S', new UnificationEntry(screw, BlueSteel),
                'Y', ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.YOTTANK_CASING),
                'C', new UnificationEntry(circuit, getMarkerMaterialByTier(EV)),
                'T', MetaItems.COVER_SCREEN,
                'P', new UnificationEntry(pipeNormalFluid, TungstenSteel));

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CIRCUIT_ASSEMBLER[LuV])
                .input(getRobotArmByTier(LuV), 4)
                .input(getMotorByTier(LuV), 4)
                .input(getFieldGeneratorByTier(LuV))
                .input(getEmitterByTier(LuV))
                .input(getSensorByTier(LuV))
                .input(plate, RhodiumPlatedPalladium, 8)
                .scannerResearch(research -> research
                        .researchStack(MetaTileEntities.CIRCUIT_ASSEMBLER[5].getStackForm())
                        .duration(600)
                        .EUt(VA[IV]))
                .fluidInputs(Indalloy140.getFluid(L * 10))
                .output(ZBGTMetaTileEntities.CAL)
                .EUt(VA[LuV]).duration(20 * 45)
                .buildAndRegister();

        ModHandler.addShapedRecipe("volcanus_controller", ZBGTMetaTileEntities.VOLCANUS.getStackForm(),
                "GCG",
                "AVA",
                "PBP",
                'G', new UnificationEntry(gear, HastelloyW),
                'C', new UnificationEntry(circuit, getMarkerMaterialByTier(LuV)),
                'A', getRobotArmByTier(IV),
                'V', ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.VOLCANUS_CASING),
                'P', new UnificationEntry(plateDouble, HastelloyN),
                'B', ZBGTMetaItems.GREGTECH_COMPUTER_CUBE);

        ModHandler.addShapedRecipe("cryogenic_freezer_controller",
                ZBGTMetaTileEntities.CRYOGENIC_FREEZER.getStackForm(),
                "GCG",
                "TYT",
                "PBP",
                'G', new UnificationEntry(gear, GCYMMaterials.IncoloyMA956),
                'C', new UnificationEntry(circuit, getMarkerMaterialByTier(LuV)),
                'T', getPistonByTier(IV),
                'Y', ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.CRYOGENIC_CASING),
                'P', new UnificationEntry(plateDouble, Grismium),
                'B', ZBGTMetaItems.GREGTECH_COMPUTER_CUBE);
    }
}
