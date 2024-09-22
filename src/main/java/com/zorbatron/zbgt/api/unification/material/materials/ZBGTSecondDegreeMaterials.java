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

public class ZBGTSecondDegreeMaterials {

    private static int id = 2000;

    public static void register() {
        MAR_CE_M200 = new Material.Builder(id++, zbgtId("mar_ce_m_200"))
                .liquid(new FluidBuilder().temperature(5000))
                .color(0x383030).iconSet(MaterialIconSet.METALLIC)
                .flags(GENERATE_PLATE, GENERATE_LONG_ROD)
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

        AdamantiumAlloy = new Material.Builder(id++, zbgtId("adamantium_alloy"))
                .dust().color(0xA0A0A0).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Adamantium, 5, Naquadah, 2, Lanthanum, 3)
                .build();

        Artherium_Sn = new Material.Builder(id++, zbgtId("artherium_sn"))
                .liquid(new FluidBuilder().temperature(6500))
                .flags(DISABLE_DECOMPOSITION)
                .color(0x6036F7).iconSet(MaterialIconSet.SHINY)
                .components(AdamantiumAlloy, 12, Tin, 8, Arsenic, 7, Caesium, 4, Osmiridium, 3)
                .blast(b -> b
                        .temp(6500, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[IV], 20 * 23 + 16))
                .build();
    }
}
