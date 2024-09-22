package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.ZBGTUtility.zbgtId;
import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;

public class ZBGTFirstDegreeMaterials {

    private static int id = 1000;

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
                .flags(DISABLE_DECOMPOSITION)
                .components(Niobium, 2, Chrome, 9, Aluminium, 5, Titanium, 2, Cobalt, 10, Tungsten, 13, Nickel, 18)
                .blast(b -> b
                        .temp(5000, BlastProperty.GasTier.MID)
                        .blastStats(VA[IV], 205)
                        .vacuumStats(VA[MV], 246))
                .build();

        TanmolyiumBetaC = new Material.Builder(id++, zbgtId("tanmolyium_beta_c"))
                .dust().liquid(new FluidBuilder().temperature(5300))
                .color(0xC72FCC).iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Titanium, 5, Molybdenum, 5, Vanadium, 2, Chrome, 3, Aluminium, 1)
                .blast(b -> b
                        .temp(5300, BlastProperty.GasTier.LOW)
                        .blastStats(VA[IV], 20 * 7))
                .build();
    }
}
