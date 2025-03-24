package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES;
import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials;
import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.recipe.helpers.RecipeAssists;

import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.builders.AssemblerRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

public class MiscRecipes {

    protected static void init() {
        genericCircuits();
        magneticFluids();
        coolantCells();
        ggCircuits();
        engraver();

        EXTRUDER_RECIPES.recipeBuilder()
                .input(dust, SpecialCeramics, 2)
                .notConsumable(MetaItems.SHAPE_EXTRUDER_PLATE)
                .output(SPECIAL_CERAMICS_PLATE)
                .EUt(VA[HV]).duration(20 * 15)
                .buildAndRegister();

        RecipeBuilder<SimpleRecipeBuilder> quartzWaferBuilder = AUTOCLAVE_RECIPES.recipeBuilder()
                .input(plate, Quartzite)
                .input(dust, Sodium, 4)
                .EUt(VA[LV]);

        quartzWaferBuilder.copy()
                .fluidInputs(DistilledWater.getFluid(1000))
                .output(QUARTZ_WAFER)
                .duration(20 * 60)
                .buildAndRegister();
        quartzWaferBuilder.copy()
                .fluidInputs(Water.getFluid(1000))
                .chancedOutput(QUARTZ_WAFER, 3333, 750)
                .duration(20 * 110)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.PLASTIC_CIRCUIT_BOARD)
                .input(MetaItems.ULTRA_LOW_POWER_INTEGRATED_CIRCUIT)
                .input(MetaItems.VOLTAGE_COIL_ULV, 2)
                .input(MetaItems.BATTERY_ULV_TANTALUM)
                .fluidInputs(RedAlloy.getFluid(L))
                .fluidInputs(Aluminium.getFluid(L))
                .output(MICRO_HEATER)
                .casingTier(1)
                .EUt(VA[HV]).duration(20 * 3)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(QUARTZ_WAFER)
                .input(SPECIAL_CERAMICS_PLATE, 2)
                .input(MICRO_HEATER)
                .input(MetaItems.INTEGRATED_LOGIC_CIRCUIT, 4)
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.EnergeticAlloy : EnergeticAlloy).getFluid(72))
                .fluidInputs(Silver.getFluid(18))
                .output(QUARTZ_CRYSTAL_RESONATOR)
                .casingTier(1)
                .EUt(VA[HV]).duration(20 * 5)
                .buildAndRegister();

        if (!ZBGTAPI.nomiLabsCompat) {
            MIXER_RECIPES.recipeBuilder()
                    .input(dust, Glowstone, 4)
                    .input(dust, Redstone, 2)
                    .input(dust, Aluminium)
                    .output(HIGH_ENERGY_MIXTURE, 4)
                    .EUt(VA[MV]).duration(20 * 12)
                    .buildAndRegister();

            FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                    .input(HIGH_ENERGY_MIXTURE, 2)
                    .fluidInputs(PhosphoricAcid.getFluid(4000))
                    .output(dust, Luminessence)
                    .EUt(VA[HV]).duration(20 * 24)
                    .buildAndRegister();
        }

        RecipeBuilder<AssemblerRecipeBuilder> radiationPlateBuilder = ASSEMBLER_RECIPES.recipeBuilder()
                .fluidInputs(Lead.getFluid(L * 8))
                .circuitMeta(1)
                .output(RADIATION_PROTECTION_PLATE)
                .EUt(VA[EV]).duration(20 * 20);

        radiationPlateBuilder.copy()
                .input(plateDense, Iridium, 6)
                .input(plate, NaquadahAlloy, 6)
                .buildAndRegister();
        radiationPlateBuilder.copy()
                .input(plate, Lanthanum, 3)
                .input(plate, NaquadahAlloy, 6)
                .buildAndRegister();

        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(RADIATION_PROTECTION_PLATE)
                .input(plate, ZBGTAPI.nomiLabsCompat ? LabsMaterials.ElectrumFlux : FluxedElectrum, 4)
                .input(plate, Trinium, 4)
                .input(plate, NaquadahAlloy, 4)
                .input(plate, Osmiridium, 4)
                .input(plate, ZBGTAPI.nomiLabsCompat ? LabsMaterials.VibrantAlloy : VibrantAlloy, 4)
                .input(RADIATION_PROTECTION_PLATE)
                .fluidInputs(Indalloy140.getFluid(L * 8))
                .stationResearch(research -> research
                        .researchStack(RADIATION_PROTECTION_PLATE.getStackForm())
                        .EUt(VA[ZPM]).CWUt(128))
                .output(ADVANCED_RADIATION_PROTECTION_PLATE)
                .EUt(VA[ZPM]).duration(20 * 50)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.TOOL_DATA_ORB, 4)
                .input(MetaItems.COVER_SCREEN, 4)
                .input(MetaTileEntities.HULL[IV])
                .input(circuit, RecipeAssists.getMarkerMaterialByTier(ZPM))
                .fluidInputs(Tantalum.getFluid(L * 16))
                .output(GREGTECH_COMPUTER_CUBE)
                .EUt(VA[IV]).duration(20 * 180)
                .buildAndRegister();

        ALLOY_SMELTER_RECIPES.recipeBuilder()
                .input(dust, WoodsGlass, 5)
                .notConsumable(MetaItems.SHAPE_MOLD_BALL)
                .output(WOODS_GLASS_LENS)
                .EUt(VA[HV]).duration(596)
                .buildAndRegister();
    }

    private static void magneticFluids() {
        POLARIZER_RECIPES.recipeBuilder()
                .fluidInputs(Iron.getFluid(144))
                .fluidOutputs(IronMagnetic.getFluid(144))
                .EUt(VHA[LV]).duration(144)
                .buildAndRegister();

        POLARIZER_RECIPES.recipeBuilder()
                .fluidInputs(Steel.getFluid(144))
                .fluidOutputs(SteelMagnetic.getFluid(144))
                .EUt(VHA[MV]).duration(144)
                .buildAndRegister();

        POLARIZER_RECIPES.recipeBuilder()
                .fluidInputs(Neodymium.getFluid(144))
                .fluidOutputs(NeodymiumMagnetic.getFluid(144))
                .EUt(VHA[HV]).duration(144)
                .buildAndRegister();

        POLARIZER_RECIPES.recipeBuilder()
                .fluidInputs(Samarium.getFluid(144))
                .fluidOutputs(SamariumMagnetic.getFluid(144))
                .EUt(VHA[EV]).duration(150)
                .buildAndRegister();
    }

    private static void genericCircuits() {
        for (int tier = ULV; tier <= MAX; tier++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(circuit, RecipeAssists.getMarkerMaterialByTier(tier), 16)
                    .circuitMeta(29)
                    .output(RecipeAssists.getGenericCircuitByTier(tier), 16)
                    .EUt(8).duration(20)
                    .buildAndRegister();
        }
    }

    private static void coolantCells() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.FLUID_CELL)
                .input(plate, Tin, 4)
                .circuitMeta(1)
                .output(COOLANT_CELL_10k)
                .EUt(VA[LV]).duration(20 * 10)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(COOLANT_CELL_10k, 3)
                .input(plate, Tin, 6)
                .circuitMeta(1)
                .output(COOLANT_CELL_30k)
                .EUt(VA[MV]).duration(20 * 15)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.FLUID_CELL, 3)
                .input(plate, Aluminium, 6)
                .circuitMeta(1)
                .output(COOLANT_CELL_30k)
                .EUt(VA[MV]).duration(20 * 15)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(COOLANT_CELL_30k, 2)
                .input(plate, Tin, 8)
                .circuitMeta(1)
                .output(COOLANT_CELL_60k)
                .EUt(VA[MV]).duration(20 * 20)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.FLUID_CELL, 6)
                .input(plate, StainlessSteel, 8)
                .circuitMeta(1)
                .output(COOLANT_CELL_60k)
                .EUt(VA[MV]).duration(20 * 20)
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(COOLANT_CELL_10k)
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 1000))
                .output(COOLANT_CELL_60k_He)
                .EUt(VA[HV]).duration(16)
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(COOLANT_CELL_30k)
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 3000))
                .output(COOLANT_CELL_180k_He)
                .EUt(VA[HV]).duration(48)
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(COOLANT_CELL_60k)
                .fluidInputs(Helium.getFluid(FluidStorageKeys.LIQUID, 6000))
                .output(COOLANT_CELL_360k_He)
                .EUt(VA[HV]).duration(96)
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(COOLANT_CELL_10k)
                .fluidInputs(SodiumPotassium.getFluid(1000))
                .output(COOLANT_CELL_60k_NaK)
                .EUt(VA[MV]).duration(16)
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(COOLANT_CELL_30k)
                .fluidInputs(SodiumPotassium.getFluid(3000))
                .output(COOLANT_CELL_180k_NaK)
                .EUt(VA[MV]).duration(48)
                .buildAndRegister();

        CANNER_RECIPES.recipeBuilder()
                .input(COOLANT_CELL_60k)
                .fluidInputs(SodiumPotassium.getFluid(6000))
                .output(COOLANT_CELL_360k_NaK)
                .EUt(VA[MV]).duration(96)
                .buildAndRegister();
    }

    private static void ggCircuits() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaItems.ELITE_CIRCUIT_BOARD)
                .input(ZBGTMetaItems.ENGRAVED_GOLD_CHIP, 16)
                .input(MetaItems.ADVANCED_SYSTEM_ON_CHIP, 8)
                .input(MetaItems.NOR_MEMORY_CHIP, 32)
                .input(bolt, ZBGTAPI.nomiLabsCompat ? LabsMaterials.Signalum : Signalum, 32)
                .input(wireGtSingle, Aluminium, 8)
                .input(rotor, TinAlloy)
                .fluidInputs(SolderingAlloy.getFluid(L * 2))
                .output(GG_CIRCUIT_1)
                .EUt(VA[IV]).duration(20 * 30)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GG_CIRCUIT_1, 2)
                .input(ENGRAVED_DIAMOND_CHIP, 8)
                .input(MetaItems.NOR_MEMORY_CHIP, 16)
                .input(rotor, Aluminium, 2)
                .fluidInputs(Polyethylene.getFluid(L * 2))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Signalum : Signalum).getFluid(144))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Lumium : Lumium).getFluid(72))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Enderium : Enderium).getFluid(72))
                .output(GG_CIRCUIT_2)
                .casingTier(1)
                .EUt(VA[LuV]).duration(20 * 8)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GG_CIRCUIT_2, 2)
                .input(MetaItems.ENGRAVED_LAPOTRON_CHIP, 8)
                .input(MetaItems.CRYSTAL_CENTRAL_PROCESSING_UNIT)
                .input(rotor, StainlessSteel, 2)
                .fluidInputs(AdamantiumAlloy.getFluid(L * 4))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Signalum : Signalum).getFluid(L * 2))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Lumium : Lumium).getFluid(L))
                .fluidInputs(TungstenCarbide.getFluid(72))
                .output(GG_CIRCUIT_3)
                .casingTier(2)
                .EUt(VA[ZPM]).duration(20 * 10)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GG_CIRCUIT_3, 2)
                .input(ENGRAVED_ENERGY_CHIP, 8)
                .input(MetaItems.QUBIT_CENTRAL_PROCESSING_UNIT, 16)
                .input(rotor, ZBGTAPI.nomiLabsCompat ? LabsMaterials.EnergeticAlloy : EnergeticAlloy, 2)
                .fluidInputs(MAR_M200.getFluid(L * 8))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Signalum : Signalum).getFluid(L * 4))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Lumium : Lumium).getFluid(L * 2))
                .fluidInputs(Artherium_Sn.getFluid(L))
                .output(GG_CIRCUIT_4)
                .casingTier(3)
                .EUt(VA[UV]).duration(20 * 13)
                .buildAndRegister();

        PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(GG_CIRCUIT_4, 2)
                .input(ENGRAVED_MANYULLYN_CHIP, 8)
                .input(MetaItems.NEURO_PROCESSOR)
                .input(rotor, TungstenCarbide, 2)
                .fluidInputs(TanmolyiumBetaC.getFluid(L * 12))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Signalum : Signalum).getFluid(L * 8))
                .fluidInputs((ZBGTAPI.nomiLabsCompat ? LabsMaterials.Lumium : Lumium).getFluid(L * 4))
                .fluidInputs(Dalisenite.getFluid(L * 2))
                .output(GG_CIRCUIT_5)
                .casingTier(3)
                .EUt(VA[UHV]).duration(20 * 15)
                .buildAndRegister();
    }

    private static void engraver() {
        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .notConsumable(craftingLens, MarkerMaterials.Color.White)
                .input(plate, Gold)
                .output(ZBGTMetaItems.ENGRAVED_GOLD_CHIP)
                .EUt(VA[MV]).duration(20 * 30)
                .buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .notConsumable(craftingLens, MarkerMaterials.Color.LightBlue)
                .input(plate, Diamond, 4)
                .output(ZBGTMetaItems.ENGRAVED_DIAMOND_CHIP)
                .EUt(VA[IV]).duration(20 * 30)
                .buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .notConsumable(craftingLens, MarkerMaterials.Color.Red)
                .input(MetaItems.ENERGIUM_CRYSTAL)
                .output(ZBGTMetaItems.ENGRAVED_ENERGY_CHIP)
                .EUt(VA[IV]).duration(20 * 30)
                .buildAndRegister();

        LASER_ENGRAVER_RECIPES.recipeBuilder()
                .notConsumable(craftingLens, MarkerMaterials.Color.Pink)
                .input(plate, ZBGTAPI.nomiLabsCompat ? LabsMaterials.Manyullyn : Manyullyn, 4)
                .output(ZBGTMetaItems.ENGRAVED_MANYULLYN_CHIP)
                .EUt(VA[IV]).duration(20 * 30)
                .buildAndRegister();
    }
}
