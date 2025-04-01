package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static gregicality.multiblocks.api.recipes.GCYMRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.fluids.FluidStack;

import com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials;
import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.util.ZBGTMods;

import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.builders.BlastRecipeBuilder;
import gregtech.api.recipes.builders.SimpleRecipeBuilder;
import gregtech.api.unification.material.properties.BlastProperty;
import gregtech.loaders.recipe.CraftingComponent;

public class MaterialRecipes {

    protected static void init() {
        chemicalReactor();
        vacuumFreezer();
        alloyBlast();
        macerator();
        chemBath();
        mixer();
        ebf();
    }

    private static void chemicalReactor() {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Lithium)
                .fluidInputs(Chlorine.getFluid(1000))
                .output(dust, LithiumChloride, 2)
                .EUt(VA[MV]).duration(56)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Aluminium, 2)
                .fluidInputs(Oxygen.getFluid(3000))
                .output(dust, Alumina, 5)
                .EUt(VA[MV]).duration(45)
                .buildAndRegister();

        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Yttrium, 2)
                .fluidInputs(Oxygen.getFluid(3000))
                .circuitMeta(5)
                .output(dust, YttriumOxide, 5)
                .EUt(VA[HV]).duration(20 * 14)
                .buildAndRegister();
    }

    private static void vacuumFreezer() {
        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(Nitrogen.getFluid(1000))
                .fluidOutputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 1000))
                .EUt(VA[HV]).duration(15)
                .buildAndRegister();

        if (!ZBGTMods.THERMAL_FOUNDATION.isModLoaded()) {
            VACUUM_RECIPES.recipeBuilder()
                    .input(stick, Blaze)
                    .output(stick, Blizz)
                    .EUt(VA[MV]).duration(20 * 25)
                    .buildAndRegister();
        }

        VACUUM_RECIPES.recipeBuilder()
                .fluidInputs(HotLowGradeCoolant.getFluid(1000))
                .fluidOutputs(LowGradeCoolant.getFluid(1000))
                .EUt(VA[HV]).duration(45)
                .buildAndRegister();
    }

    private static void alloyBlast() {
        blastHelper(ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(dust, Bismuth, 47)
                .input(dust, Lead, 25)
                .input(dust, Tin, 13)
                .input(dust, Cadmium, 10)
                .input(dust, Indium, 5)
                .fluidOutputs(Indalloy140.getFluid(L * 100))
                .blastFurnaceTemp(5475)
                .EUt(VA[IV]),
                20 * 40, BlastProperty.GasTier.MID, 5, 10);

        blastHelper(ALLOY_BLAST_RECIPES.recipeBuilder()
                .input(dust, Zirconium)
                .input(dust, Carbon)
                .fluidOutputs(ZirconiumCarbide.getFluid(L * 2))
                .blastFurnaceTemp(1830)
                .EUt(VA[LV]),
                20 * 10, BlastProperty.GasTier.LOW, 2, 12);
    }

    private static void macerator() {
        MACERATOR_RECIPES.recipeBuilder()
                .input(Blocks.SNOW)
                .output(dust, SnowPowder)
                .EUt(4).duration(16)
                .buildAndRegister();

        MACERATOR_RECIPES.recipeBuilder()
                .input(Items.SNOWBALL)
                .output(dustSmall, SnowPowder)
                .EUt(4).duration(16)
                .buildAndRegister();
    }

    private static void chemBath() {
        if (!ZBGTMods.THERMAL_FOUNDATION.isModLoaded()) {
            CHEMICAL_BATH_RECIPES.recipeBuilder()
                    .input(Items.SNOWBALL, 4)
                    .fluidInputs(Blaze.getFluid(L * 10))
                    .output(dust, Blizz)
                    .EUt(VA[HV]).duration(20 * 20)
                    .buildAndRegister();
        }
    }

    private static void mixer() {
        MIXER_RECIPES.recipeBuilder()
                .input(dust, TungstenSteel, 12)
                .input(dust, HSSE, 9)
                .input(dust, HSSG, 6)
                .input(dust, Ruridit, 3)
                .input(dust, Titanium, 2)
                .input(dust, Plutonium239)
                .output(dust, HDCS, 33)
                .EUt(VA[IV]).duration(20 * 25)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Adamantium, 5)
                .input(dust, Naquadah, 2)
                .input(dust, Lanthanum, 3)
                .output(dust, AdamantiumAlloy, 10)
                .EUt(VA[EV]).duration(20 * 10 + 14)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, AdamantiumAlloy, 12)
                .input(dust, Tin, 8)
                .input(dust, Arsenic, 7)
                .input(dust, Caesium, 4)
                .input(dust, Osmiridium, 3)
                .output(dust, Artherium_Sn, 34)
                .EUt(VA[IV]).duration(20 * 15 + 9)
                .buildAndRegister();

        RecipeBuilder<SimpleRecipeBuilder> specialCeramicsBuilder = MIXER_RECIPES.recipeBuilder()
                .input(dust, AluminumNitride, 4)
                .fluidInputs(Glue.getFluid(1000))
                .circuitMeta(9)
                .output(dust, SpecialCeramics, 9)
                .EUt(VA[EV]).duration(20 * 5);

        specialCeramicsBuilder.copy()
                .input(dust, YttriumOxide, 5)
                .buildAndRegister();
        specialCeramicsBuilder.copy()
                .input(dust, Uraninite, 5)
                .buildAndRegister();

        if (!ZBGTAPI.nomiLabsCompat) {
            MIXER_RECIPES.recipeBuilder()
                    .input(dust, RedSteel, 3)
                    .input(dust, Blaze)
                    .output(dust, Ardite, 4)
                    .EUt(VA[LV]).duration(20 * 10)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, TinAlloy, 4)
                    .input(dust, SterlingSilver, 2)
                    .input(dust, Luminessence, 2)
                    .fluidInputs(Glowstone.getFluid(L * 2))
                    .output(dust, Lumium, 4)
                    .EUt(VA[EV]).duration(20 * 15)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, Lead, 4)
                    .input(dust, Platinum, 2)
                    .input(dust, TungstenSteel)
                    .input(dust, Osmium)
                    .input(dust, EnderPearl)
                    .output(dust, Enderium, 4)
                    .EUt(VA[EV]).duration(20 * 15)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, Gold, 2)
                    .input(dust, Redstone)
                    .input(dust, Glowstone)
                    .output(dust, EnergeticAlloy, 2)
                    .EUt(VA[LV]).duration(20 * 7)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, Ardite, 4)
                    .input(dust, Osmium, 4)
                    .output(dust, Manyullyn, 4)
                    .EUt(VA[LV]).duration(20 * 20)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, AnnealedCopper, 4)
                    .input(dust, Ardite, 2)
                    .input(dust, RedAlloy, 2)
                    .input(dust, Redstone)
                    .output(dust, Signalum, 4)
                    .EUt(VA[EV]).duration(20 * 15)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, Electrum, 6)
                    .input(dust, Lumium)
                    .input(dust, Signalum)
                    .output(dust, FluxedElectrum, 9)
                    .EUt(VA[MV]).duration(20 * 50)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, EnergeticAlloy)
                    .input(dust, EnderPearl)
                    .output(dust, VibrantAlloy)
                    .EUt(VA[LV]).duration(20 * 13)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, TanmolyiumBetaC, 14)
                    .input(dust, Tungsten, 10)
                    .input(dust, NiobiumTitanium, 9)
                    .input(dust, RhodiumPlatedPalladium, 8)
                    .input(dust, Quantium, 7)
                    .circuitMeta(6)
                    .output(dust, Dalisenite, 48)
                    .EUt(VA[UV]).duration((int) (20 * 14.6))
                    .buildAndRegister();
        }

        MIXER_RECIPES.recipeBuilder()
                .input(dust, ZBGTAPI.nomiLabsCompat ? LabsMaterials.Lumium : Lumium, 18)
                .input(dust, Silver, 8)
                .circuitMeta(3)
                .output(dust, Hikarium, 26)
                .EUt(VA[LuV]).duration(20 * 13)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Iron, 4)
                .input(dust, Kanthal)
                .input(dust, Invar, 5)
                .circuitMeta(20)
                .output(dust, EglinSteelBase, 10)
                .EUt(VA[MV]).duration((int) (20 * 5.1))
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, EglinSteelBase, 10)
                .input(dust, Sulfur)
                .input(dust, Silicon, 4)
                .input(dust, Carbon)
                .output(dust, EglinSteel, 16)
                .EUt(VA[HV]).duration((int) (20 * 1.4))
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Nickel, 2)
                .input(dust, Niobium)
                .input(dust, Aluminium, 2)
                .input(dust, Nichrome)
                .output(dust, Inconel792, 6)
                .EUt(VA[HV]).duration((int) (20 * 2.6))
                .buildAndRegister();

        if (!ZBGTMods.THERMAL_FOUNDATION.isModLoaded()) {
            MIXER_RECIPES.recipeBuilder()
                    .input(dust, Redstone)
                    .input(dust, Blaze)
                    .input(dust, Sulfur)
                    .input(dust, Coal)
                    .output(dust, Pyrotheum, 4)
                    .EUt(VA[MV]).duration(20 * 8)
                    .buildAndRegister();

            MIXER_RECIPES.recipeBuilder()
                    .input(dust, Redstone)
                    .input(dust, SnowPowder)
                    .input(dust, Saltpeter)
                    .input(dust, Blizz)
                    .output(dust, Cryotheum, 4)
                    .EUt(VA[MV]).duration(20 * 8)
                    .buildAndRegister();
        }

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium, 9)
                .input(dust, Carbon, 9)
                .input(dust, Potassium, 9)
                .input(dust, Lithium, 9)
                .input(dust, Sulfur, 9)
                .circuitMeta(2)
                .fluidInputs(Hydrogen.getFluid(5000))
                .output(dust, Grisium, 50)
                .EUt(VA[EV]).duration(20 * 60)
                .buildAndRegister();

        RecipeBuilder<SimpleRecipeBuilder> ic2coolantBuilder = MIXER_RECIPES.recipeBuilder()
                .input(dust, Lapis)
                .circuitMeta(4)
                .EUt(VA[MV]).duration(256);

        ic2coolantBuilder.copy()
                .fluidInputs(Water.getFluid(125))
                .fluidOutputs(LowGradeCoolant.getFluid(125))
                .buildAndRegister();
        ic2coolantBuilder.copy()
                .fluidInputs(DistilledWater.getFluid(1000))
                .fluidOutputs(LowGradeCoolant.getFluid(1000))
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Sodium, 2)
                .input(dust, Silicon, 4)
                .input(dust, Nickel)
                .input(dust, Barium, 3)
                .output(dust, WoodsGlass)
                .EUt(VA[MV]).duration(127)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Tantalum, 23)
                .input(dust, Tungsten, 2)
                .output(dust, Tantalloy60, 25)
                .EUt(VA[HV]).duration(20 * 18 + 2)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Tantalloy60)
                .input(dust, Titanium, 6)
                .input(dust, Yttrium, 4)
                .output(dust, Tantalloy61, 11)
                .EUt(VA[HV]).duration(20 * 10 + 10)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Chrome)
                .input(dust, Niobium, 2)
                .input(dust, Molybdenum, 2)
                .input(dust, Nichrome, 3)
                .output(dust, Inconel690, 8)
                .EUt(VA[HV]).duration(20 * 7 + 9)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Niobium)
                .input(dust, Carbon)
                .output(dust, NiobiumCarbide)
                .EUt(VA[HV]).duration(20 * 5 + 2)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Chrome, 9)
                .input(dust, Iron, 23)
                .input(dust, Cobalt, 9)
                .input(dust, Nickel, 9)
                .output(dust, IncoloyDS, 50)
                .EUt(VA[HV]).duration(20 * 34 + 7)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Titanium)
                .input(dust, Uranium238, 9)
                .output(dust, Staballoy, 10)
                .EUt(VA[HV]).duration(20 * 14 + 6)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Cobalt, 4)
                .input(dust, Chrome, 3)
                .input(dust, Phosphorus, 2)
                .input(dust, Molybdenum, 1)
                .output(dust, Talonite, 10)
                .EUt(VA[HV]).duration(20 * 14 + 13)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder()
                .input(dust, Zirconium)
                .input(dust, Carbon)
                .output(dust, ZirconiumCarbide, 2)
                .EUt(VA[LV]).duration(20 * 5 + 2)
                .buildAndRegister();
    }

    private static void ebf() {
        BLAST_RECIPES.recipeBuilder()
                .input(ingot, MAR_M200, 18)
                .input(ingot, Cerium)
                .fluidInputs(LithiumChloride.getFluid(L))
                .output(ingotHot, MAR_CE_M200, 19)
                .EUt(VA[ZPM]).duration(20 * 75)
                .buildAndRegister();

        RecipeBuilder<BlastRecipeBuilder> aluminumNitrideBuilder = BLAST_RECIPES.recipeBuilder()
                .fluidInputs(Nitrogen.getFluid(FluidStorageKeys.LIQUID, 2000))
                .output(dust, AluminumNitride, 2)
                .fluidOutputs(CarbonMonoxide.getFluid(3000))
                .EUt(VA[EV]).duration(20 * 10)
                .blastFurnaceTemp(4600);

        aluminumNitrideBuilder.copy()
                .input(dust, Sapphire, 5)
                .input(dust, Coal, 3)
                .circuitMeta(5)
                .buildAndRegister();
        aluminumNitrideBuilder.copy()
                .input(dust, Sapphire, 5)
                .input(dust, Carbon, 3)
                .circuitMeta(5)
                .buildAndRegister();
        aluminumNitrideBuilder.copy()
                .input(dust, GreenSapphire, 5)
                .input(dust, Coal, 3)
                .circuitMeta(5)
                .buildAndRegister();
        aluminumNitrideBuilder.copy()
                .input(dust, GreenSapphire, 5)
                .input(dust, Carbon, 3)
                .circuitMeta(5)
                .buildAndRegister();
        aluminumNitrideBuilder.copy()
                .input(dust, Alumina, 5)
                .input(dust, Coal, 3)
                .circuitMeta(5)
                .buildAndRegister();
        aluminumNitrideBuilder.copy()
                .input(dust, Alumina, 5)
                .input(dust, Carbon, 3)
                .circuitMeta(5)
                .buildAndRegister();
    }

    @SuppressWarnings("SameParameterValue")
    private static void blastHelper(RecipeBuilder<?> blastBuilder, int duration, BlastProperty.GasTier gasTier,
                                    int circ1, int circ2) {
        FluidStack gas = CraftingComponent.EBF_GASES.get(gasTier).copy();

        blastBuilder.copy()
                .circuitMeta(circ1)
                .duration(duration)
                .buildAndRegister();

        blastBuilder.copy()
                .circuitMeta(circ2)
                .fluidInputs(gas)
                .duration((int) (duration * 0.67))
                .buildAndRegister();
    }
}
