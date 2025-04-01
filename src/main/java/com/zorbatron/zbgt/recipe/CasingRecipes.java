package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.*;
import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.filostorm.ulvcovers.items.ULVCoverMetaItems;
import com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials;
import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.util.ZBGTMods;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.*;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;

import gregicality.multiblocks.api.unification.GCYMMaterials;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.block.VariantBlock;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.builders.AssemblerRecipeBuilder;
import gregtech.api.recipes.builders.AssemblyLineRecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.*;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

public class CasingRecipes {

    private static final int casingsPerCraft = ConfigHolder.recipes.casingsPerCraft;

    protected static void init() {
        materialCasings();
        preciseCasings();
        fusionCasings();
        coALCasings();
        miscCasings();
        gtpp();
    }

    private static void materialCasings() {
        Arrays.stream(MaterialCasing.CasingType.values()).forEach(
                casing -> registerMetalCasingRecipe(casing.getMaterial(), ZBGTMetaBlocks.MATERIAL_CASINGS, casing));
    }

    // Copied from GCYL: CEu
    private static <
            T extends Enum<T> & IStringSerializable> void registerMetalCasingRecipe(Material inputMaterial,
                                                                                    VariantBlock<T> outputCasingType,
                                                                                    T outputCasing) {
        ModHandler.addShapedRecipe(String.format("metal_casing_%s", inputMaterial),
                outputCasingType.getItemVariant(outputCasing, casingsPerCraft),
                "PhP", "PFP", "PwP",
                'P', OreDictUnifier.get(plate, inputMaterial),
                'F', OreDictUnifier.get(frameGt, inputMaterial));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, inputMaterial, 6)
                .input(frameGt, inputMaterial)
                .circuitMeta(6)
                .outputs(outputCasingType.getItemVariant(outputCasing, casingsPerCraft))
                .EUt(16).duration(50)
                .buildAndRegister();
    }

    private static void coALCasings() {
        if (ZBGTMods.ULV_COVERS.isModLoaded()) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(frameGt, getMaterialByTier(ULV))
                    .input(plateDense, getMaterialByTier(ULV), 4)
                    .input(ULVCoverMetaItems.ROBOT_ARM_ULV, 4)
                    .input(ULVCoverMetaItems.ELECTRIC_PISTON_ULV, 8)
                    .input(ULVCoverMetaItems.ELECTRIC_MOTOR_ULV, 10)
                    .input(gear, getMaterialByTier(ULV), 4)
                    .input(cableGtQuadruple, getCableByTier(ULV), 6)
                    .input(circuit, getMarkerMaterialByTier(LV), 8)
                    .input(circuit, getMarkerMaterialByTier(ULV), 16)
                    .fluidInputs(SolderingAlloy.getFluid(L))
                    .outputs(ZBGTMetaBlocks.CoAL_CASING.getItemVariant(CoALCasing.CasingType.getCasingByTier(ULV)))
                    .EUt(VA[ULV]).duration(150)
                    .buildAndRegister();
        }

        for (int tier = LV; tier <= IV; tier++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(frameGt, getMaterialByTier(tier))
                    .input(plateDense, getMaterialByTier(tier), 4)
                    .input(getRobotArmByTier(tier), 4)
                    .input(getPistonByTier(tier), 8)
                    .input(getMotorByTier(tier), 10)
                    .input(gear, getMaterialByTier(tier), 4)
                    .input(cableGtQuadruple, getCableByTier(tier), 6)
                    .input(circuit, getMarkerMaterialByTier(tier), 8)
                    .input(circuit, getMarkerMaterialByTier(tier - 1), 16)
                    .fluidInputs(Materials.SolderingAlloy.getFluid(tier * L * 2))
                    .outputs(ZBGTMetaBlocks.CoAL_CASING.getItemVariant(CoALCasing.CasingType.getCasingByTier(tier)))
                    .EUt(VA[tier]).duration(150)
                    .buildAndRegister();
        }

        for (int tier = LuV; tier <= UV; tier++) {
            int fluidAdditive = (tier - ZPM) * 2;
            int finalTier = tier;

            AssemblyLineRecipeBuilder tempBuilder = ASSEMBLY_LINE_RECIPES.recipeBuilder()
                    .input(frameGt, getMaterialByTier(tier))
                    .input(plateDense, getMaterialByTier(tier), 6)
                    .input(getRobotArmByTier(tier), 8)
                    .input(getPistonByTier(tier))
                    .input(getMotorByTier(tier), 16)
                    .input(gear, getMaterialByTier(tier))
                    .input(gearSmall, getMaterialByTier(tier))
                    .input(cableGtQuadruple, getCableByTier(tier), 8)
                    .input(circuit, getMarkerMaterialByTier(tier), 8)
                    .input(circuit, getMarkerMaterialByTier(tier - 1), 16)
                    .fluidInputs(Materials.SolderingAlloy.getFluid((10 + fluidAdditive) * L))
                    .fluidInputs(tier > ZPM ?
                            Materials.Polybenzimidazole.getFluid((10 + fluidAdditive) * L * 2) :
                            Materials.Polyethylene.getFluid((10 + fluidAdditive) * L * 2))
                    .outputs(ZBGTMetaBlocks.CoAL_CASING.getItemVariant(CoALCasing.CasingType.getCasingByTier(tier)))
                    .EUt(VA[tier]).duration(20 * 15);

            ItemStack researchItemStack = ZBGTMetaBlocks.CoAL_CASING
                    .getItemVariant(CoALCasing.CasingType.getCasingByTier(finalTier - 1));
            if (tier == LuV) {
                tempBuilder.scannerResearch(research -> research
                        .researchStack(researchItemStack)
                        .EUt(VA[IV])
                        .duration(20 * 30));
            } else {
                tempBuilder.stationResearch(research -> research
                        .researchStack(researchItemStack)
                        .CWUt((int) Math.pow(2, finalTier - 1))
                        .EUt(VA[finalTier]));
            }

            if (tier > ZPM) {
                tempBuilder.fluidInputs(Naquadria.getFluid((10 + fluidAdditive) * L * 2));
            }

            tempBuilder.buildAndRegister();
        }
    }

    private static void preciseCasings() {
        ItemStack casing0 = ZBGTMetaBlocks.PRECISE_CASING.getItemVariant(PreciseCasing.CasingType.PRECISE_CASING_0);
        ItemStack casing1 = ZBGTMetaBlocks.PRECISE_CASING.getItemVariant(PreciseCasing.CasingType.PRECISE_CASING_1);
        ItemStack casing2 = ZBGTMetaBlocks.PRECISE_CASING.getItemVariant(PreciseCasing.CasingType.PRECISE_CASING_2);
        ItemStack casing3 = ZBGTMetaBlocks.PRECISE_CASING.getItemVariant(PreciseCasing.CasingType.PRECISE_CASING_3);
        ItemStack casing4 = ZBGTMetaBlocks.PRECISE_CASING.getItemVariant(PreciseCasing.CasingType.PRECISE_CASING_4);

        ItemStack casing0WithAmount = GTUtility.copy(casingsPerCraft, casing0);
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(getMachineCasingByTier(IV, 3))
                .input(getRobotArmByTier(EV), 2)
                .input(cableGtDouble, Osmiridium, 2)
                .input(plateDouble, MAR_M200, 2)
                .input(circuit, getMarkerMaterialByTier(EV))
                .input(bolt, Ruthenium, 32)
                .input(gearSmall, Platinum, 8)
                .fluidInputs(Chrome.getFluid(L * 4))
                .outputs(casing0WithAmount)
                .EUt(VA[IV]).duration(20 * 40)
                .buildAndRegister();

        ItemStack casing1WithAmount = GTUtility.copy(casingsPerCraft, casing1);
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(getMachineCasingByTier(LuV, 3))
                .input(getRobotArmByTier(IV), 2)
                .input(wireGtQuadruple, ZBGTAPI.nomiLabsCompat ? LabsMaterials.Lumium : Lumium, 2)
                .input(plateDouble, MAR_CE_M200, 2)
                .input(ZBGTMetaItems.GG_CIRCUIT_1)
                .input(bolt, ZBGTAPI.nomiLabsCompat ? LabsMaterials.Signalum : Signalum, 32)
                .input(gearSmall, TanmolyiumBetaC, 8)
                .fluidInputs(BlackSteel.getFluid(L * 4))
                .outputs(casing1WithAmount)
                .EUt(VA[LuV]).duration(20 * 40)
                .buildAndRegister();

        ItemStack casing2WithAmount = GTUtility.copy(casingsPerCraft * 2, casing2);
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(getMachineCasingByTier(ZPM, 3))
                .input(getRobotArmByTier(LuV), 2)
                .input(cableGtQuadruple, ZBGTAPI.nomiLabsCompat ? LabsMaterials.ElectrumFlux : FluxedElectrum, 4)
                .input(ZBGTMetaItems.GG_CIRCUIT_2)
                .inputs(casing1)
                .input(bolt, MAR_CE_M200, 32)
                .input(gearSmall, Artherium_Sn, 8)
                .fluidInputs(AdamantiumAlloy.getFluid(L * 4))
                .outputs(casing2WithAmount)
                .EUt(VA[ZPM]).duration(20 * 40)
                .buildAndRegister();

        ItemStack casing3WithAmount = GTUtility.copy(casingsPerCraft * 2, casing3);
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(getMachineCasingByTier(UV, 3))
                .input(getFieldGeneratorByTier(ZPM), 2)
                .input(wireGtQuadruple, RutheniumTriniumAmericiumNeutronate, 4)
                .input(ZBGTMetaItems.GG_CIRCUIT_3)
                .inputs(casing2)
                .input(bolt, TanmolyiumBetaC, 32)
                .input(gearSmall, Dalisenite, 8)
                .fluidInputs(Artherium_Sn.getFluid(L * 8))
                .outputs(casing3WithAmount)
                .EUt(VA[UV]).duration(20 * 40)
                .buildAndRegister();

        ItemStack casing4WithAmount = GTUtility.copy(casingsPerCraft * 2, casing4);
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(getMachineCasingByTier(UHV, 3))
                .input(getFieldGeneratorByTier(UV), 2)
                .input(cableGtQuadruple, NetherStar, 8)
                .input(ZBGTMetaItems.GG_CIRCUIT_4)
                .inputs(casing3)
                .input(bolt, NaquadahEnriched, 32)
                .input(gearSmall, Naquadria, 8)
                .fluidInputs(PreciousMetalsAlloy.getFluid(L * 8))
                .outputs(casing4WithAmount)
                .EUt(VA[UHV]).duration(20 * 40)
                .buildAndRegister();
    }

    private static void fusionCasings() {
        ItemStack superconductorCoilWithAmount = GTUtility.copy(3,
                MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL));
        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(superconductorCoilWithAmount)
                .input(ZBGTMetaItems.GG_CIRCUIT_2)
                .input(ZBGTMetaItems.SPECIAL_CERAMICS_PLATE, 2)
                .fluidInputs(MAR_M200.getFluid(L * 8))
                .fluidInputs(Chrome.getFluid(L * 2))
                .casingTier(1)
                .outputs(ZBGTMetaBlocks.MISC_CASING
                        .getItemVariant(MiscCasing.CasingType.AMELIORATED_SUPERCONDUCTOR_COIL))
                .EUt(VA[LuV]).duration(20 * 45)
                .buildAndRegister();

        ItemStack fusionCoilWithAmount = GTUtility.copy(3,
                MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL));
        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(fusionCoilWithAmount)
                .input(ZBGTMetaItems.QUARTZ_CRYSTAL_RESONATOR, 2)
                .input(ZBGTMetaItems.GG_CIRCUIT_3)
                .fluidInputs(Artherium_Sn.getFluid(L * 4))
                .fluidInputs(TanmolyiumBetaC.getFluid(L))
                .casingTier(2)
                .outputs(ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.COMPACT_FUSION_COIL_1))
                .EUt(VA[LuV]).duration(20 * 34)
                .buildAndRegister();

        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(fusionCoilWithAmount)
                .input(ZBGTMetaItems.RADIATION_PROTECTION_PLATE, 2)
                .input(MetaItems.QUANTUM_STAR, 4)
                .input(ZBGTMetaItems.GG_CIRCUIT_4)
                .fluidInputs(Dalisenite.getFluid(L * 4))
                .fluidInputs(Hikarium.getFluid(L))
                .outputs(ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.COMPACT_FUSION_COIL_2))
                .casingTier(3)
                .EUt(VA[ZPM]).duration(20 * 35)
                .buildAndRegister();
    }

    private static void miscCasings() {
        ModHandler.addShapedRecipe("yottank_casing",
                ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.YOTTANK_CASING,
                        casingsPerCraft),
                "BPB", "TFT", "BPB",
                'B', OreDictUnifier.get(plate, BlackSteel),
                'P', OreDictUnifier.get(pipeNormalFluid, StainlessSteel),
                'F', OreDictUnifier.get(frameGt, BlackSteel),
                'T', OreDictUnifier.get(plate, Polytetrafluoroethylene));

        ModHandler.addShapedRecipe("cryogenic_casing",
                ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.CRYOGENIC_CASING, casingsPerCraft),
                "PGP",
                "HFN",
                "PGP",
                'P', new UnificationEntry(plateDouble, Grisium),
                'G', new UnificationEntry(gear, GCYMMaterials.IncoloyMA956),
                'H', ZBGTMetaItems.COOLANT_CELL_360k_He.getStackForm(),
                'F', frameBox(Nitinol60),
                'N', ZBGTMetaItems.COOLANT_CELL_360k_NaK.getStackForm());

        ModHandler.addShapedRecipe("volcanus_casing",
                ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.VOLCANUS_CASING, casingsPerCraft),
                "PVP",
                "VFV",
                "PGP",
                'P', new UnificationEntry(plateDouble, HastelloyN),
                'V', GCYMMetaBlocks.UNIQUE_CASING.getItemVariant(BlockUniqueCasing.UniqueCasingType.HEAT_VENT),
                'F', frameBox(GCYMMaterials.HastelloyX),
                'G', new UnificationEntry(gear, HastelloyW));

        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(MetaBlocks.COMPUTER_CASING.getItemVariant(BlockComputerCasing.CasingType.HIGH_POWER_CASING))
                .input(plateDense, Osmiridium, 6)
                .input(foil, Trinium, 12)
                .input(screw, TungstenSteel, 24)
                .input(ring, TungstenSteel, 24)
                .input(MetaItems.FIELD_GENERATOR_IV)
                .fluidInputs(Osmium.getFluid(L * 8))
                .outputs(ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.MOLECULAR_CASING))
                .EUt(VA[LuV]).duration(25 * 20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.MOLECULAR_CASING))
                .input(plateDense, Europium, 2)
                .input(plateDense, Plutonium239, 5)
                .input(plateDouble, Lead, 8)
                .input(plate, Uranium238, 16)
                .input(screw, Quantium, 16)
                .fluidInputs(Trinium.getFluid(L * 8))
                .fluidInputs(Osmium.getFluid(L * 8))
                .fluidInputs(LowGradeCoolant.getFluid(2000))
                .fluidInputs(Argon.getFluid(1000))
                .scannerResearch(scanner -> scanner
                        .researchStack(
                                ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.MOLECULAR_CASING))
                        .EUt(VA[LuV]).duration(50 * 20))
                .outputs(ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.HOLLOW_CASING, 2))
                .EUt(VA[UV]).duration(20 * 20)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.HOLLOW_CASING))
                .inputs(MetaBlocks.FUSION_CASING.getItemVariant(BlockFusionCasing.CasingType.FUSION_COIL, 2))
                .inputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NAQUADAH, 2))
                .input(wireFine, Europium, 64)
                .input(foil, Europium, 64)
                .fluidInputs(Glass.getFluid(L * 16))
                .fluidInputs(SiliconeRubber.getFluid(L * 12))
                .fluidInputs(LowGradeCoolant.getFluid(2000))
                .fluidInputs(Trinium.getFluid(L * 8))
                .scannerResearch(scanner -> scanner
                        .researchStack(ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.HOLLOW_CASING))
                        .EUt(VA[LuV]).duration(25 * 20))
                .outputs(ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.MOLECULAR_COIL, 4))
                .EUt(VA[ZPM]).duration(40 * 20)
                .buildAndRegister();
    }

    private static void gtpp() {
        // GTPP casings 0
        ItemStack casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.CENTRIFUGE_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("centrifuge_casing", casing,
                "MTM",
                "ITI",
                "MTM",
                'M', plate(MaragingSteel250),
                'T', rod(Tumbaga),
                'I', plate(Inconel792));

        casingBuilder(casing)
                .input(plate, MaragingSteel250, 4)
                .input(plate, Inconel792, 2)
                .input(stick, Tumbaga, 3)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.STRUCTURAL_COKE_OVEN_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("structural_coke_oven_casing", casing,
                "PRP",
                "RFR",
                "PRP",
                'P', plate(Tantalloy61),
                'R', rod(Tantalloy61),
                'F', frameBox(Tantalloy61));

        casingBuilder(casing)
                .input(plate, Tantalloy61, 4)
                .input(stick, Tantalloy61, 4)
                .input(frameGt, Tantalloy61)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0
                .getItemVariant(RandomGTPPCasings0.CasingType.HEAT_RESISTANT_COKE_OVEN_CASING, casingsPerCraft);
        ModHandler.addShapedRecipe("heat_resistant_coke_oven_casing", casing,
                "PPP",
                "FGF",
                "PPP",
                'P', plate(Bronze),
                'F', frameBox(Bronze),
                'G', MetaBlocks.TURBINE_CASING.getItemVariant(BlockTurbineCasing.TurbineCasingType.BRONZE_GEARBOX));

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.HEAT_PROOF_COKE_OVEN_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("heat_proof_coke_oven_casing", casing,
                "PPP",
                "FGF",
                "PPP",
                'P', plate(Steel),
                'F', frameBox(Steel),
                'G', MetaBlocks.TURBINE_CASING.getItemVariant(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX));

        casing = ZBGTMetaBlocks.GTPP_CASING_0
                .getItemVariant(RandomGTPPCasings0.CasingType.MATERIAL_PRESS_MACHINE_CASING, casingsPerCraft);
        ModHandler.addShapedRecipe("material_press_machine_casing", casing,
                "PLP",
                "RFR",
                "PLP",
                'P', plate(Titanium),
                'L', longRod(Tumbaga),
                'R', longRod(Tantalloy60),
                'F', frameBox(Tumbaga));

        casingBuilder(casing)
                .input(plate, Titanium, 4)
                .input(stickLong, Tantalloy60, 2)
                .input(stickLong, Tumbaga, 2)
                .input(frameGt, Tumbaga)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.ELECTROLYZER_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("electrolyzer_casing", casing,
                "PCP",
                "LFL",
                "PLP",
                'P', plate(Potin),
                'C', longRod(Chrome),
                'L', longRod(Potin),
                'F', frameBox(Potin));

        casingBuilder(casing)
                .input(plate, Potin, 4)
                .input(stickLong, Potin, 3)
                .input(stickLong, Chrome)
                .input(frameGt, Potin)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.WIRE_FACTORY_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("wire_factory_casing", casing,
                "PRP",
                "RFR",
                "PRP",
                'P', plate(BlueSteel),
                'R', rod(BlueSteel),
                'F', frameBox(BlueSteel));

        casingBuilder(casing)
                .input(plate, BlueSteel, 4)
                .input(stick, BlueSteel, 4)
                .input(frameGt, BlueSteel)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.MACERATION_STACK_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("maceration_stack_casing", casing,
                "PPP",
                "RFR",
                "PLP",
                'P', plate(Palladium),
                'R', rod(Platinum),
                'F', frameBox(Inconel625),
                'L', longRod(Palladium));

        casingBuilder(casing)
                .input(plate, Palladium, 5)
                .input(stick, Platinum, 2)
                .input(stickLong, Palladium)
                .input(frameGt, Inconel625)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.MATTER_GENERATION_COIL,
                casingsPerCraft);
        ModHandler.addShapedRecipe("matter_generation_coil", casing,
                "ZPZ",
                "FCF",
                "ZPZ",
                'Z', plate(Zeron100),
                'P', plate(Pikyonium64b),
                'F', frameBox(Stellite100),
                'C', MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UV));

        casingBuilder(casing)
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.UV))
                .input(plate, Zeron100, 4)
                .input(plate, Pikyonium64b, 2)
                .input(frameGt, Stellite100, 2)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.MATTER_FABRICATION_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("matter_fabrication_casing", casing,
                "PRP",
                "RFR",
                "PRP",
                'P', plate(NiobiumCarbide),
                'R', rod(Inconel792),
                'F', frameBox(Inconel690));

        casingBuilder(casing)
                .input(plate, NiobiumCarbide, 4)
                .input(stick, Inconel792, 4)
                .input(frameGt, Inconel690)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.THERMAL_PROCESSING_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("thermal_processing_casing", casing,
                "PhP",
                "PFP",
                "PwP",
                'P', plate(RedSteel),
                'F', frameBox(BlackSteel));

        casingBuilder(casing)
                .input(plate, RedSteel, 6)
                .input(frameGt, BlackSteel)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.HASTELLOY_N_SEALANT_BLOCK,
                casingsPerCraft);
        ModHandler.addShapedRecipe("hastelloy_n_sealant_block", casing,
                "IHI",
                "HFH",
                "IHI",
                'I', plate(IncoloyMA956),
                'H', plate(HastelloyN),
                'F', frameBox(HastelloyC276));

        casingBuilder(casing)
                .input(plate, IncoloyMA956, 4)
                .input(plate, HastelloyN, 4)
                .input(frameGt, HastelloyC276)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0.getItemVariant(RandomGTPPCasings0.CasingType.HASTELLOY_X_STRUCTURAL_BLOCK,
                casingsPerCraft);
        ModHandler.addShapedRecipe("hastelloy_x_structural_block", casing,
                "RGP",
                "hFw",
                "PCR",
                'R', ring(Inconel792),
                'G', gear(HastelloyX),
                'P', plate(Steel),
                'F', frameBox(HastelloyC276),
                'C', MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.EV));

        casingBuilder(casing, VA[EV], 20 * 5)
                .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.EV))
                .input(plate, Steel, 2)
                .input(gear, HastelloyX)
                .input(frameGt, HastelloyC276)
                .input(ring, Inconel792, 2)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_0
                .getItemVariant(RandomGTPPCasings0.CasingType.INCOLOY_DS_FLUID_CONTAINMENT_BLOCK, casingsPerCraft);
        ModHandler.addShapedRecipe("incoloy_ds_fluid_containment_block", casing,
                "IPI",
                "GTG",
                "IPI",
                'I', plate(IncoloyDS),
                'P', new UnificationEntry(pipeHugeFluid, Staballoy),
                'G', gear(IncoloyDS),
                'T', MetaTileEntities.QUANTUM_TANK[IV].getStackForm());

        casingBuilder(casing, VA[HV], 20 * 10)
                .input(plate, IncoloyDS, 4)
                .input(pipeHugeFluid, Staballoy, 2)
                .input(gear, IncoloyDS, 2)
                .input(MetaTileEntities.QUANTUM_TANK[IV])
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.WASH_PLANT_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("wash_plant_casing", casing,
                "GhG",
                "TFT",
                "GwG",
                'G', plate(Grisium),
                'T', plate(Talonite),
                'F', frameBox(Grisium));

        casingBuilder(casing)
                .input(plate, Grisium, 4)
                .input(plate, Talonite, 2)
                .input(frameGt, Grisium)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.INDUSTRIAL_SIEVE_CASING,
                casingsPerCraft);
        ModHandler.addShapedRecipe("industrial_sieve_casing", casing,
                "PPP",
                "PFP",
                "PPP",
                'P', plate(EglinSteel),
                'F', frameBox(Tumbaga));

        casingBuilder(casing)
                .input(plate, EglinSteel, 8)
                .input(frameGt, Tumbaga)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.INDUSTRIAL_SIEVE_GRATE,
                casingsPerCraft);
        ModHandler.addShapedRecipe("industrial_sieve_grate", casing,
                "FWF",
                "WWW",
                "FWF",
                'F', frameBox(EglinSteel),
                'W', fineWire(Steel));

        casingBuilder(casing)
                .input(wireFine, Steel, 5)
                .input(frameGt, EglinSteel, 4)
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.CYCLOTRON_COIL,
                casingsPerCraft);
        casingBuilder(casing, VA[IV], 20 * 25)
                .inputs(MetaBlocks.WIRE_COIL.getItemVariant(BlockWireCoil.CoilType.NICHROME))
                // IV coil wire
                .input(plate, IncoloyMA956, 8)
                .input(bolt, Tantalloy61, 16)
                .input(screw, Incoloy020, 32)
                .input(MetaItems.FIELD_GENERATOR_EV)
                .fluidInputs(HG1223.getFluid(L * 5))
                .buildAndRegister();

        casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.CYCLOTRON_OUTER_CASING,
                casingsPerCraft);
        casingBuilder(casing, VA[EV], 20 * 20)
                .inputs(MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF))
                // EV coil wire
                .input(plate, IncoloyDS, 8)
                .input(screw, Inconel690, 16)
                .input(stickLong, EglinSteel, 4)
                .input(MetaItems.ELECTRIC_PISTON_HV, 2)
                .fluidInputs(ZirconiumCarbide.getFluid(L * 8))
                .buildAndRegister();

        // casing =
        // ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.THERMAL_CONTAINMENT_CASING,
        // casingsPerCraft);
        // ModHandler.addShapedRecipe("thermal_containment_casing", casing,
        // "",
        // "",
        // "");
        //
        //
        // casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.BULK_PRODUCTION_FRAME,
        // casingsPerCraft);
        // ModHandler.addShapedRecipe("bulk_production_frame", casing,
        // "",
        // "",
        // "");
        //
        //
        // casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.CUTTING_FACTORY_FRAME,
        // casingsPerCraft);
        // ModHandler.addShapedRecipe("cutting_factory_frame", casing,
        // "",
        // "",
        // "");
        //
        //
        // casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.STERILE_FARM_CASING,
        // casingsPerCraft);
        // ModHandler.addShapedRecipe("sterile_farm_casing", casing,
        // "",
        // "",
        // "");
        //
        //
        // casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.AQUATIC_CASING,
        // casingsPerCraft);
        // ModHandler.addShapedRecipe("aquatic_casing", casing,
        // "",
        // "",
        // "");
        //
        //
        // casing = ZBGTMetaBlocks.GTPP_CASING_1.getItemVariant(RandomGTPPCasings1.CasingType.INCONEL_REINFORCED_CASING,
        // casingsPerCraft);
        // ModHandler.addShapedRecipe("inconel_reinforced_casing", casing,
        // "",
        // "",
        // "");
    }

    private static AssemblerRecipeBuilder casingBuilder(ItemStack casing, int eut, int duration) {
        return ASSEMBLER_RECIPES.recipeBuilder()
                .outputs(casing)
                .EUt(eut).duration(duration);
    }

    private static AssemblerRecipeBuilder casingBuilder(ItemStack casing) {
        return casingBuilder(casing, VA[LV], 50);
    }

    private static UnificationEntry plate(@NotNull Material material) {
        return new UnificationEntry(plate, material);
    }

    private static UnificationEntry rod(@NotNull Material material) {
        return new UnificationEntry(stick, material);
    }

    private static UnificationEntry longRod(@NotNull Material material) {
        return new UnificationEntry(stickLong, material);
    }

    private static UnificationEntry frameBox(@NotNull Material material) {
        return new UnificationEntry(frameGt, material);
    }

    private static UnificationEntry ring(@NotNull Material material) {
        return new UnificationEntry(ring, material);
    }

    private static UnificationEntry gear(@NotNull Material material) {
        return new UnificationEntry(gear, material);
    }

    private static UnificationEntry fineWire(@NotNull Material material) {
        return new UnificationEntry(wireFine, material);
    }
}
