package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;

import com.zorbatron.zbgt.api.ZBGTAPI;

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
                .flags(GENERATE_PLATE, GENERATE_LONG_ROD, GCYMMaterialFlags.DISABLE_ALLOY_PROPERTY,
                        GENERATE_DOUBLE_PLATE, GENERATE_BOLT_SCREW)
                .components(MAR_M200, 18, Cerium, 1)
                .blast(5000, BlastProperty.GasTier.HIGH)
                .build();

        Artherium_Sn = new Material.Builder(id++, zbgtId("artherium_sn"))
                .liquid(new FluidBuilder().temperature(6500))
                .flags(DISABLE_DECOMPOSITION, GENERATE_SMALL_GEAR)
                .color(0x6036F7).iconSet(MaterialIconSet.SHINY)
                .components(AdamantiumAlloy, 12, Tin, 8, Arsenic, 7, Caesium, 4, Osmiridium, 3)
                .blast(b -> b
                        .temp(6500, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[IV], 20 * 23 + 16))
                .build();

        Dalisenite = new Material.Builder(id++, zbgtId("dalisenite"))
                .liquid(new FluidBuilder().temperature(8700))
                .color(0xB0B812).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION, GENERATE_SMALL_GEAR)
                .components(TanmolyiumBetaC, 14, Tungsten, 10, NiobiumTitanium, 9, RhodiumPlatedPalladium, 8)
                .blast(b -> b
                        .temp(8700, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[UV], 20 * 46 + 8))
                .build();

        if (!ZBGTAPI.nomiLabsCompat) {
            // Copied from Nomi Labs
            Manyullyn = new Material.Builder(id++, zbgtId("manyullyn"))
                    .ingot().liquid()
                    .color(0x9949cc).iconSet(METALLIC)
                    .flags(GENERATE_PLATE)
                    .components(Ardite, 4, Cobalt, 4)
                    .build();

            Signalum = new Material.Builder(id++, zbgtId("signalum"))
                    .ingot().liquid(new FluidBuilder().temperature(4000))
                    .color(0xff7f0f).iconSet(SHINY)
                    .blast(builder -> builder
                            .temp(4000, BlastProperty.GasTier.MID)
                            .blastStats(VA[IV], 1400)
                            .vacuumStats(VA[HV], 500))
                    .flags(GENERATE_BOLT_SCREW)
                    .components(AnnealedCopper, 4, Ardite, 2, RedAlloy, 2, Redstone, 1)
                    .build();

            VibrantAlloy = new Material.Builder(id++, zbgtId("vibrant_alloy"))
                    .ingot().liquid(new FluidBuilder().temperature(3300))
                    .color(0xa4ff70).iconSet(SHINY)
                    .blast(builder -> builder
                            .temp(3000, BlastProperty.GasTier.LOW)
                            .blastStats(VA[MV], 400))
                    .components(EnergeticAlloy, 1, EnderPearl, 1)
                    .build();

            Lumium = new Material.Builder(id++, zbgtId("lumium"))
                    .ingot().liquid(new FluidBuilder().temperature(4000))
                    .flags(DISABLE_DECOMPOSITION)
                    .color(0xf6ff99).iconSet(BRIGHT)
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
    }
}
