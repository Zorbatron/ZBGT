package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import com.filostorm.ulvcovers.items.ULVCoverMetaItems;
import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.util.ZBGTMods;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.MaterialCasing;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;

import gregtech.api.block.VariantBlock;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.MetaBlocks;

public class CasingRecipes {

    private static final int casingsPerCraft = ConfigHolder.recipes.casingsPerCraft;

    protected static void init() {
        materialCasings();
        preciseCasings();
        fusionCasings();
        coALCasings();
        miscCasings();
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

            ASSEMBLY_LINE_RECIPES.recipeBuilder()
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
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid((10 + fluidAdditive) * L * 2) : null)
                    .outputs(ZBGTMetaBlocks.CoAL_CASING.getItemVariant(CoALCasing.CasingType.getCasingByTier(tier)))
                    .stationResearch(research -> research
                            .researchStack(ZBGTMetaBlocks.CoAL_CASING
                                    .getItemVariant(CoALCasing.CasingType.getCasingByTier(finalTier - 1)))
                            .CWUt((int) Math.pow(16, finalTier - IV))
                            .EUt(VA[finalTier]))
                    .EUt(VA[tier]).duration(20 * 15)
                    .buildAndRegister();
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
                .input(wireGtQuadruple, Lumium, 2)
                .input(plateDouble, MAR_CE_M200, 2)
                .input(ZBGTMetaItems.GG_CIRCUIT_1)
                .input(bolt, Signalum, 32)
                .input(gearSmall, TanmolyiumBetaC, 8)
                .fluidInputs(BlackSteel.getFluid(L * 4))
                .outputs(casing1WithAmount)
                .EUt(VA[LuV]).duration(20 * 40)
                .buildAndRegister();

        ItemStack casing2WithAmount = GTUtility.copy(casingsPerCraft * 2, casing2);
        ASSEMBLER_RECIPES.recipeBuilder()
                .inputs(getMachineCasingByTier(ZPM, 3))
                .input(getRobotArmByTier(LuV), 2)
                .input(cableGtQuadruple, FluxedElectrum, 4)
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
                // BOLT!??
                // GEAR!?
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
    }
}
