package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import java.util.Arrays;

import net.minecraft.util.IStringSerializable;

import com.filostorm.ulvcovers.items.ULVCoverMetaItems;
import com.zorbatron.zbgt.api.util.ZBGTMods;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.MaterialCasing;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;

import gregtech.api.block.VariantBlock;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.common.ConfigHolder;

public class CasingRecipes {

    protected static void init() {
        materialCasings();
        coALCasings();
        preciseCasings();
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
                outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft),
                "PhP", "PFP", "PwP",
                'P', OreDictUnifier.get(plate, inputMaterial),
                'F', OreDictUnifier.get(frameGt, inputMaterial));

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, inputMaterial, 6)
                .input(frameGt, inputMaterial)
                .circuitMeta(6)
                .outputs(outputCasingType.getItemVariant(outputCasing, ConfigHolder.recipes.casingsPerCraft))
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
        for (int tier = EV; tier < UHV; tier++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .inputs(getMachineCasingByTier(tier))
                    .input(getRobotArmByTier(tier), 2)
                    .input(cableGtDouble, getCableByTier(tier), 2)
                    .input(plateDouble, getMaterialByTier(tier), 2)
                    .input(circuit, getMarkerMaterialByTier(tier))
                    .input(bolt, getSecondaryComponentMaterialByTier(tier), 32)
                    .input(gearSmall, getSecondaryComponentMaterialByTier(tier), 8)
                    .fluidInputs(SolderingAlloy.getFluid(L * 4 * tier))
                    .circuitMeta(18)
                    .outputs(getPreciseCasingByTier(tier - EV))
                    .EUt(VA[tier]).duration(20 * 8 * (tier - HV))
                    .buildAndRegister();
        }
    }

    private static void miscCasings() {
        ModHandler.addShapedRecipe("yottank_casing",
                ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.YOTTANK_CASING,
                        ConfigHolder.recipes.casingsPerCraft),
                "BPB", "TFT", "BPB",
                'B', OreDictUnifier.get(plate, BlackSteel),
                'P', OreDictUnifier.get(pipeNormalFluid, StainlessSteel),
                'F', OreDictUnifier.get(frameGt, BlackSteel),
                'T', OreDictUnifier.get(plate, Polytetrafluoroethylene));
    }
}
