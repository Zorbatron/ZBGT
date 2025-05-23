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

        ModHandler.addShapedRecipe("creative_item_bus_to_chest", ZBGTMetaTileEntities.CREATIVE_ITEM_BUS.getStackForm(),
                "C",
                'C', MetaTileEntities.CREATIVE_CHEST.getStackForm());
        ModHandler.addShapedRecipe("creative_chest_to_item_bus", MetaTileEntities.CREATIVE_CHEST.getStackForm(),
                "C",
                'C', ZBGTMetaTileEntities.CREATIVE_ITEM_BUS.getStackForm());

        ModHandler.addShapedRecipe("creative_tank_to_fluid_hatch",
                ZBGTMetaTileEntities.CREATIVE_RESERVOIR_HATCH.getStackForm(),
                "T",
                'T', MetaTileEntities.CREATIVE_TANK.getStackForm());
        ModHandler.addShapedRecipe("creative_fluid_hatch_to_tank", MetaTileEntities.CREATIVE_TANK.getStackForm(),
                "T",
                'T', ZBGTMetaTileEntities.CREATIVE_RESERVOIR_HATCH.getStackForm());

        ModHandler.addShapedRecipe("creative_emitter_to_energy_hatch",
                ZBGTMetaTileEntities.CREATIVE_ENERGY_SOURCE.getStackForm(),
                "E",
                'E', MetaTileEntities.CREATIVE_ENERGY.getStackForm());
        ModHandler.addShapedRecipe("creative_energy_hatch_to_emitter", MetaTileEntities.CREATIVE_ENERGY.getStackForm(),
                "E",
                'E', ZBGTMetaTileEntities.CREATIVE_ENERGY_SOURCE.getStackForm());

        ModHandler.addShapedRecipe("creative_energy_hatch_to_energy_sink",
                ZBGTMetaTileEntities.CREATIVE_ENERGY_SINK.getStackForm(),
                "E",
                'E', ZBGTMetaTileEntities.CREATIVE_ENERGY_SOURCE.getStackForm());
        ModHandler.addShapedRecipe("creative_energy_sink_to_energy_source",
                ZBGTMetaTileEntities.CREATIVE_ENERGY_SOURCE.getStackForm(),
                "E",
                'E', ZBGTMetaTileEntities.CREATIVE_ENERGY_SINK.getStackForm());

        for (int i = 0; i <= UHV; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(MetaTileEntities.ENERGY_CONVERTER[0][i])
                    .input(MetaTileEntities.ENERGY_INPUT_HATCH[i])
                    .circuitMeta(1)
                    .fluidInputs(Materials.Glue.getFluid(L * (i + 1)))
                    .output(ZBGTMetaTileEntities.RF_ENERGY_HATCH_INPUT[i])
                    .EUt(VA[i]).duration(42)
                    .buildAndRegister();

            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(MetaTileEntities.ENERGY_CONVERTER[0][i])
                    .input(MetaTileEntities.ENERGY_OUTPUT_HATCH[i])
                    .circuitMeta(2)
                    .fluidInputs(Materials.Glue.getFluid(L * (i + 1)))
                    .output(ZBGTMetaTileEntities.RF_ENERGY_HATCH_OUTPUT[i])
                    .EUt(VA[i]).duration(42)
                    .buildAndRegister();
        }
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
