package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.combineRGB;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.util.ZBGTMods;

import gregicality.multiblocks.api.unification.GCYMMaterialFlags;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;

public final class ZBGTSecondDegreeMaterials {

    private static int id = 5_000;

    public static void register() {
        MAR_CE_M200 = new Material.Builder(id++, zbgtId("mar_ce_m_200"))
                .liquid(new FluidBuilder().temperature(5000))
                .color(0x383030).iconSet(MaterialIconSet.METALLIC)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .flags(GENERATE_PLATE, GENERATE_LONG_ROD, GCYMMaterialFlags.DISABLE_ALLOY_PROPERTY,
                        GENERATE_DOUBLE_PLATE, GENERATE_BOLT_SCREW)
                .components(MAR_M200, 18, Cerium, 1)
                .blast(5000, BlastProperty.GasTier.HIGH)
                .build();

        Artherium_Sn = new Material.Builder(id++, zbgtId("artherium_sn"))
                .liquid(new FluidBuilder().temperature(6500))
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .flags(GENERATE_SMALL_GEAR)
                .color(0x6036F7).iconSet(MaterialIconSet.SHINY)
                .components(AdamantiumAlloy, 12, Tin, 8, Arsenic, 7, Caesium, 4, Osmiridium, 3)
                .blast(b -> b
                        .temp(6500, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[IV], 20 * 23 + 16))
                .build();

        Dalisenite = new Material.Builder(id++, zbgtId("dalisenite"))
                .liquid(new FluidBuilder().temperature(8700))
                .color(0xB0B812).iconSet(MaterialIconSet.SHINY)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .flags(GENERATE_SMALL_GEAR)
                .components(TanmolyiumBetaC, 14, Tungsten, 10, NiobiumTitanium, 9, RhodiumPlatedPalladium, 8, Quantium,
                        7)
                .blast(b -> b
                        .temp(8700, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[UV], 20 * 46 + 8))
                .build();

        if (!ZBGTAPI.nomiLabsCompat) {
            // Copied from Nomi Labs
            Manyullyn = new Material.Builder(id++, zbgtId("manyullyn"))
                    .ingot().liquid()
                    .color(0x9949cc).iconSet(METALLIC)
                    .flags(DECOMPOSITION_BY_CENTRIFUGING)
                    .flags(GENERATE_PLATE)
                    .components(Ardite, 4, Cobalt, 4)
                    .build();

            Signalum = new Material.Builder(id++, zbgtId("signalum"))
                    .ingot().liquid(new FluidBuilder().temperature(4000))
                    .color(0xff7f0f).iconSet(SHINY)
                    .flags(DECOMPOSITION_BY_CENTRIFUGING)
                    .flags(GENERATE_BOLT_SCREW)
                    .blast(builder -> builder
                            .temp(4000, BlastProperty.GasTier.MID)
                            .blastStats(VA[IV], 1400)
                            .vacuumStats(VA[HV], 500))
                    .components(AnnealedCopper, 4, Ardite, 2, RedAlloy, 2, Redstone, 1)
                    .build();

            VibrantAlloy = new Material.Builder(id++, zbgtId("vibrant_alloy"))
                    .ingot().liquid(new FluidBuilder().temperature(3300))
                    .color(0xa4ff70).iconSet(SHINY)
                    .flags(DECOMPOSITION_BY_CENTRIFUGING)
                    .flags(GENERATE_PLATE)
                    .blast(builder -> builder
                            .temp(3000, BlastProperty.GasTier.LOW)
                            .blastStats(VA[MV], 400))
                    .components(EnergeticAlloy, 1, EnderPearl, 1)
                    .build();

            Lumium = new Material.Builder(id++, zbgtId("lumium"))
                    .ingot().liquid(new FluidBuilder().temperature(4000))
                    .color(0xf6ff99).iconSet(BRIGHT)
                    .flags(DECOMPOSITION_BY_CENTRIFUGING)
                    .blast(builder -> builder
                            .temp(4000, BlastProperty.GasTier.MID)
                            .blastStats(VA[IV], 1600)
                            .vacuumStats(VA[HV], 600))
                    .components(TinAlloy, 4, SterlingSilver, 2, Luminessence, 2)
                    .cableProperties(V[IV], 1, 0, true)
                    .build();
        } else {
            id += 4;
        }

        EglinSteel = new Material.Builder(id++, zbgtId("eglin_steel"))
                .ingot().liquid(new FluidBuilder().temperature(1320))
                .color(0x8b4513).iconSet(METALLIC)
                .flags(DECOMPOSITION_BY_CENTRIFUGING, GENERATE_FRAME, GENERATE_LONG_ROD, GENERATE_PLATE)
                .flags(EXT_METAL)
                .components(EglinSteelBase, 10, Sulfur, 1, Silicon, 4, Carbon, 1)
                .build();

        if (!ZBGTMods.THERMAL_FOUNDATION.isModLoaded()) {
            Cryotheum = new Material.Builder(id++, zbgtId("cryotheum"))
                    .dust().liquid(new FluidBuilder().temperature(5).translation("zbgt.material.cryotheum.liquid"))
                    .color(0x0094CB).iconSet(SHINY)
                    .flags(DECOMPOSITION_BY_CENTRIFUGING)
                    .components(Redstone, 1, SnowPowder, 1, Saltpeter, 1, Blizz, 1)
                    .build();
        } else {
            id++;
        }

        Tantalloy61 = new Material.Builder(id++, zbgtId("tantalloy_61"))
                .ingot().liquid(new FluidBuilder().temperature(3030))
                .color(combineRGB(193, 211, 217)).iconSet(METALLIC)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_FRAME, DECOMPOSITION_BY_CENTRIFUGING, GENERATE_BOLT_SCREW)
                .blast(b -> b
                        .temp(3305, BlastProperty.GasTier.MID)
                        .blastStats(VA[EV], 20 * 45 + 9))
                .components(Tantalloy60, 1, Titanium, 6, Yttrium, 4)
                .build();

        TriniumNaquadahCarbonite = new Material.Builder(id++, zbgtId("trinium_naquadah_carbonite"))
                .ingot().liquid(new FluidBuilder().temperature(6775))
                .iconSet(METALLIC)
                .flags(GENERATE_BOLT_SCREW)
                .blast(b -> b
                        .temp(6775, BlastProperty.GasTier.HIGHER)
                        .blastStats(VA[LuV], 20 * 40 + 14))
                .components(TriniumNaquadahAlloy, 9, Carbon, 1)
                .build();
    }
}
