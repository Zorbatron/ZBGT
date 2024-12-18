package com.zorbatron.zbgt.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.unification.material.ZBGTMaterials;
import com.zorbatron.zbgt.common.ZBGTConfig;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.recipe.helpers.RecipeAssists;

import gregicality.multiblocks.api.unification.GCYMMaterials;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

public class MultiblockPartRecipes {

    protected static void init() {
        misc();
        if (ZBGTConfig.recipeSettings.enableParallelHatchRecipes) {
            largeParallelHatches();
        }
    }

    private static void misc() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ITEM_IMPORT_BUS[0])
                .circuitMeta(10)
                .output(ZBGTMetaTileEntities.SINGLE_ITEM_INPUT_BUS)
                .duration(20 * 2).EUt(VA[LV])
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.ITEM_IMPORT_BUS[3])
                .input(MetaTileEntities.QUANTUM_CHEST[3])
                .fluidInputs(Materials.Polyethylene.getFluid(288))
                .circuitMeta(5)
                .output(ZBGTMetaTileEntities.SUPER_INPUT_BUS)
                .duration(20 * 5).EUt(VA[HV])
                .buildAndRegister();

        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.CLEANING_MAINTENANCE_HATCH)
                .input(MetaItems.BLACKLIGHT, 8)
                .input(ZBGTMetaItems.GG_CIRCUIT_3, 2)
                .input(OrePrefix.rotor, Materials.NaquadahAlloy)
                .fluidInputs(ZBGTMaterials.Indalloy140.getFluid(L * 4))
                .fluidInputs(Materials.Lubricant.getFluid(L * 2))
                .output(ZBGTMetaTileEntities.STERILE_CLEANING_HATCH)
                .casingTier(2)
                .EUt(VA[UHV]).duration(20 * 15)
                .buildAndRegister();

        ModHandler.addShapedRecipe("air_intake_hatch", ZBGTMetaTileEntities.AIR_INTAKE_HATCH.getStackForm(),
                "PGP",
                "PUP",
                "CHC",
                'P', new UnificationEntry(OrePrefix.plate, Materials.Redstone),
                'G', MetaBlocks.MULTIBLOCK_CASING.getItemVariant(
                        BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING),
                'U', MetaItems.ELECTRIC_PUMP_IV,
                'C', new UnificationEntry(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(IV)),
                'H', MetaTileEntities.FLUID_IMPORT_HATCH[IV].getStackForm());

        ModHandler.addShapedRecipe("extreme_air_intake_hatch",
                ZBGTMetaTileEntities.EXTREME_AIR_INTAKE_HATCH.getStackForm(),
                "PGP",
                "PUP",
                "CHC",
                'P', new UnificationEntry(OrePrefix.plate, ZBGTMaterials.Pikyonium64b),
                'G', ZBGTMetaTileEntities.AIR_INTAKE_HATCH.getStackForm(),
                'U', MetaItems.ELECTRIC_PUMP_ZPM,
                'C', new UnificationEntry(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(ZPM)),
                'H', MetaTileEntities.FLUID_IMPORT_HATCH[ZPM].getStackForm());

        ModHandler.addShapedRecipe("pyrotheum_hatch", ZBGTMetaTileEntities.PYROTHEUM_HEATING_HATCH.getStackForm(),
                "PGP",
                "TCT",
                "PHP",
                'P', new UnificationEntry(OrePrefix.plate, ZBGTMaterials.MaragingSteel250),
                'G', new UnificationEntry(OrePrefix.gear, GCYMMaterials.MaragingSteel300),
                'T', new UnificationEntry(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(IV)),
                'C', ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.VOLCANUS_CASING),
                'H', MetaTileEntities.FLUID_IMPORT_HATCH[IV].getStackForm());

        ModHandler.addShapedRecipe("cryotheum_hatch", ZBGTMetaTileEntities.CRYOTHEUM_COOLING_HATCH.getStackForm(),
                "PGP",
                "TCT",
                "AHA",
                'P', new UnificationEntry(OrePrefix.plate, ZBGTMaterials.MaragingSteel250),
                'G', new UnificationEntry(OrePrefix.gear, ZBGTMaterials.MaragingSteel250),
                'T', new UnificationEntry(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(EV)),
                'C', ZBGTMetaBlocks.MISC_CASING.getItemVariant(MiscCasing.CasingType.CRYOGENIC_CASING),
                'H', MetaTileEntities.FLUID_IMPORT_HATCH[IV].getStackForm(),
                'A', new UnificationEntry(OrePrefix.plate, Materials.Aluminium));
    }

    private static void largeParallelHatches() {
        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GCYMMetaTileEntities.PARALLEL_HATCH[3], 4)
                .fluidInputs(Materials.SolderingAlloy.getFluid(L * 128))
                .fluidInputs(Materials.Polyethylene.getFluid(L * 96))
                .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[0])
                .casingTier(0)
                .EUt(VA[LuV]).duration(20 * 30)
                .buildAndRegister();

        for (int tier = 0; tier < ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES.length - 2; tier++) {
            int t2FluidAmount = (int) (L * 24 * Math.pow(2, tier + 2));
            ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                    .input(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[tier], 4)
                    .fluidInputs(Materials.SolderingAlloy.getFluid((int) (L * 32 * Math.pow(4, tier))))
                    .fluidInputs(tier > 2 ? Materials.Polybenzimidazole.getFluid(t2FluidAmount) :
                            Materials.Polytetrafluoroethylene.getFluid(t2FluidAmount))
                    .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[tier + 1])
                    .casingTier(tier)
                    .EUt(VA[tier + 7]).duration(20 * 30)
                    .buildAndRegister();
        }

        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[5], 2048)
                .input(OrePrefix.plateDense, GCYMMaterials.Trinaquadalloy, 512)
                .input(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(UHV), 128)
                .fluidInputs(Materials.RutheniumTriniumAmericiumNeutronate.getFluid(L * 2048))
                .fluidInputs(Materials.NaquadahEnriched.getFluid(L * 1024))
                .fluidInputs(Materials.PolychlorinatedBiphenyl.getFluid(L * 512))
                .output(ZBGTMetaTileEntities.ZBGT_PARALLEL_HATCHES[6])
                .EUt(VA[MAX]).duration(20 * 3600)
                .casingTier(4)
                .buildAndRegister();
    }
}
