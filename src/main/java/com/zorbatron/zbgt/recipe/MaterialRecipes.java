package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static gregicality.multiblocks.api.recipes.GCYMRecipeMaps.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import net.minecraftforge.fluids.FluidStack;

import com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials;
import com.zorbatron.zbgt.api.ZBGTAPI;

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
        mixer();
        ebf();
    }

    private static void chemicalReactor() {
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Lithium)
                .fluidInputs(Chlorine.getFluid(L))
                .output(dust, LithiumChloride)
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
                .fluidOutputs(LiquidNitrogen.getFluid(1000))
                .EUt(VA[HV]).duration(15)
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
        }

        MIXER_RECIPES.recipeBuilder()
                .input(dust, ZBGTAPI.nomiLabsCompat ? LabsMaterials.Lumium : Lumium, 18)
                .input(dust, Silver, 8)
                .circuitMeta(3)
                .output(dust, Hikarium, 26)
                .EUt(VA[LuV]).duration(20 * 13)
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
                .fluidInputs(LiquidNitrogen.getFluid(2000))
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
