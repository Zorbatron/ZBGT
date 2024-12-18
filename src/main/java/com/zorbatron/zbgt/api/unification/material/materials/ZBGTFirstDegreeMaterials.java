package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.unification.material.info.ZBGTMaterialIconSet;
import com.zorbatron.zbgt.api.util.ZBGTMods;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;

public final class ZBGTFirstDegreeMaterials {

    private static int id = 500;

    public static void register() {
        Indalloy140 = new Material.Builder(id++, zbgtId("indalloy_140"))
                .liquid(new FluidBuilder().temperature(5475))
                .color(0x59536E).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Bismuth, 47, Lead, 25, Tin, 13, Cadmium, 10, Indium, 5)
                .build();

        MAR_M200 = new Material.Builder(id++, zbgtId("mar_200"))
                .dust().ingot().liquid(new FluidBuilder().temperature(5000))
                .color(0x515151).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION, GENERATE_DOUBLE_PLATE)
                .components(Niobium, 2, Chrome, 9, Aluminium, 5, Titanium, 2, Cobalt, 10, Tungsten, 13, Nickel, 18)
                .blast(b -> b
                        .temp(5000, BlastProperty.GasTier.MID)
                        .blastStats(VA[IV], 205)
                        .vacuumStats(VA[MV], 246))
                .build();

        TanmolyiumBetaC = new Material.Builder(id++, zbgtId("tanmolyium_beta_c"))
                .dust().liquid(new FluidBuilder().temperature(5300))
                .color(0xC72FCC).iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW)
                .components(Titanium, 5, Molybdenum, 5, Vanadium, 2, Chrome, 3, Aluminium, 1)
                .blast(b -> b
                        .temp(5300, BlastProperty.GasTier.LOW)
                        .blastStats(VA[IV], 20 * 7))
                .build();

        PreciousMetalsAlloy = new Material.Builder(id++, zbgtId("precious_metals_alloy"))
                .liquid(new FluidBuilder().temperature(10000))
                .color(0x9D90C6).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Ruthenium, 1, Rhodium, 1, Palladium, 1, Platinum, 1, Osmium, 1, Iridium, 1)
                .blast(b -> b
                        .temp(10000, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[UV], 20 * 87))
                .build();

        AdamantiumAlloy = new Material.Builder(id++, zbgtId("adamantium_alloy"))
                .dust().liquid(new FluidBuilder().temperature(5550))
                .color(0xA0A0A0).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Adamantium, 5, Naquadah, 2, Lanthanum, 3)
                .blast(b -> b
                        .temp(5500, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[EV], 20 * 56))
                .build();

        Alumina = new Material.Builder(id++, zbgtId("alumina"))
                .dust()
                .color(0xDAE8ED).iconSet(MaterialIconSet.FINE)
                .components(Aluminium, 2, Oxygen, 3)
                .build();

        AluminumNitride = new Material.Builder(id++, zbgtId("aluminum_nitride"))
                .dust()
                .color(0xA4DED3).iconSet(MaterialIconSet.FINE)
                .components(Aluminium, 1, Nitrogen, 1)
                .build();

        YttriumOxide = new Material.Builder(id++, zbgtId("yttrium_oxide"))
                .dust()
                .color(0xFFFFFF).iconSet(MaterialIconSet.DULL)
                .components(Yttrium, 2, Oxygen, 3)
                .build();

        HDCS = new Material.Builder(id++, zbgtId("hdcs"))
                .liquid(new FluidBuilder().temperature(9000))
                .color(0x334433).iconSet(MaterialIconSet.SHINY)
                .flags(GENERATE_SMALL_GEAR)
                .components(TungstenSteel, 12, HSSE, 9, HSSG, 6, Ruridit, 6, Titanium, 2, Plutonium239, 1)
                .blast(b -> b
                        .temp(9000, BlastProperty.GasTier.HIGHER)
                        .blastStats(VA[IV], 20 * 95))
                .build();

        if (!ZBGTAPI.nomiLabsCompat) {
            // Copied from Nomi Labs
            Ardite = new Material.Builder(id++, zbgtId("ardite"))
                    .ingot().liquid()
                    .color(0xad2f05).iconSet(DULL)
                    .components(RedSteel, 3, Blaze, 1)
                    .build();

            Enderium = new Material.Builder(id++, zbgtId("enderium"))
                    .ingot().liquid(new FluidBuilder().temperature(4500))
                    .color(0x1f6b62).iconSet(SHINY)
                    .blast(builder -> builder
                            .temp(4500, BlastProperty.GasTier.HIGHEST)
                            .blastStats(VA[LuV], 1600)
                            .vacuumStats(VA[EV], 600))
                    .components(Lead, 4, Platinum, 2, BlueSteel, 1, Osmium, 1, EnderPearl, 1)
                    .build();

            EnergeticAlloy = new Material.Builder(id++, zbgtId("energetic_alloy"))
                    .ingot().liquid(new FluidBuilder().temperature(1424))
                    .color(0xffb545).iconSet(SHINY)
                    .flags(GENERATE_ROTOR)
                    .blast(builder -> builder
                            .temp(2200, BlastProperty.GasTier.LOW)
                            .blastStats(VA[MV], 400))
                    .components(Gold, 2, Redstone, 1, Glowstone, 1)
                    .build();

            Luminessence = new Material.Builder(id++, zbgtId("luminessence"))
                    .dust()
                    .color(0xe8f224).iconSet(DULL)
                    .build();
        } else {
            id += 4;
        }

        EglinSteelBase = new Material.Builder(id++, zbgtId("eglin_steel_base"))
                .dust()
                .color(0xbfc4b5).iconSet(METALLIC)
                .components(Iron, 4, Kanthal, 1, Invar, 5)
                .build();

        Inconel792 = new Material.Builder(id++, zbgtId("inconel_792"))
                .ingot().liquid(new FluidBuilder().temperature(3700))
                .color(0x6cf076).iconSet(METALLIC)
                .flags(EXT_METAL)
                .blast(b -> b
                        .temp(3700, BlastProperty.GasTier.MID)
                        .blastStats(VA[HV], (int) (20 * 37.5)))
                .components(Nickel, 2, Niobium, 1, Aluminium, 2, Nichrome, 1)
                .build();

        if (!ZBGTMods.THERMAL_FOUNDATION.isModLoaded()) {
            Blizz = new Material.Builder(id++, zbgtId("blizz"))
                    .dust()
                    .flags(GENERATE_ROD)
                    .color(0xDCE9FF).iconSet(SHINY)
                    .build();

            Pyrotheum = new Material.Builder(id++, zbgtId("pyrotheum"))
                    .dust().liquid(new FluidBuilder().temperature(4000))
                    .color(0xFF9000).iconSet(ZBGTMaterialIconSet.FIERY)
                    .flags(DECOMPOSITION_BY_CENTRIFUGING)
                    .components(Redstone, 1, Blaze, 1, Sulfur, 1, Coal, 1)
                    .build();
        } else {
            id += 2;
        }

        SnowPowder = new Material.Builder(id++, zbgtId("snowpowder"))
                .dust()
                .color(0xFAFAFA).iconSet(FINE)
                .build();

        Grismium = new Material.Builder(id++, zbgtId("grisium"))
                .ingot().liquid(new FluidBuilder().temperature(4125))
                .color(0x355D6A)
                .flags(STD_METAL, GENERATE_DOUBLE_PLATE)
                .blast(b -> b
                        .temp(4125, BlastProperty.GasTier.MID)
                        .blastStats(VA[EV], 20 * 25))
                .components(Titanium, 9, Carbon, 9, Potassium, 9, Lithium, 9, Sulfur, 9, Hydrogen, 5)
                .build();

        Nitinol60 = new Material.Builder(id++, zbgtId("nitinol_60"))
                .ingot().liquid(new FluidBuilder().temperature(5925))
                .color(0xD2B4F5)
                .flags(GENERATE_FRAME)
                .blast(b -> b
                        .temp(5925, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[IV]))
                .components(Titanium, 3, Nickel, 2)
                .build();

        HastelloyN = new Material.Builder(id++, zbgtId("hastelloy_n"))
                .ingot().liquid(new FluidBuilder().temperature(4625))
                .color(0xD7CCE8)
                .flags(GENERATE_DOUBLE_PLATE)
                .components(Yttrium, 2, Molybdenum, 4, Chrome, 2, Titanium, 2, Nickel, 15)
                .build();

        HastelloyW = new Material.Builder(id++, zbgtId("hastelloy_w"))
                .ingot().liquid(new FluidBuilder().temperature(3625))
                .color(0xB7B2E6)
                .flags(GENERATE_GEAR)
                .components(Iron, 3, Cobalt, 1, Molybdenum, 12, Chrome, 3, Nickel, 31)
                .build();

        MaragingSteel250 = new Material.Builder(id++, zbgtId("maraging_steel_250"))
                .ingot().liquid(new FluidBuilder().temperature(2685))
                .color(0xA195D9)
                .flags(GENERATE_PLATE, GENERATE_GEAR)
                .components(Steel, 16, Molybdenum, 1, Titanium, 1, Nickel, 4, Cobalt, 2)
                .build();
    }
}
