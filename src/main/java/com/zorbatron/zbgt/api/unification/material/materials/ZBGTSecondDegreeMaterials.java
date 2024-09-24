package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import gregicality.multiblocks.api.unification.GCYMMaterialFlags;
import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;

public class ZBGTSecondDegreeMaterials {

    private static int id = 5000;

    public static void register() {
        MAR_CE_M200 = new Material.Builder(id++, zbgtId("mar_ce_m_200"))
                .liquid(new FluidBuilder().temperature(5000))
                .color(0x383030).iconSet(MaterialIconSet.METALLIC)
                .flags(GENERATE_PLATE, GENERATE_LONG_ROD, GCYMMaterialFlags.DISABLE_ALLOY_PROPERTY,
                        GENERATE_DOUBLE_PLATE, GENERATE_BOLT_SCREW)
                .components(MAR_M200, 18, Cerium, 1)
                .blast(5000, BlastProperty.GasTier.HIGH)
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
    }
}
