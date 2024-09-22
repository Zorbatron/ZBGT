package com.zorbatron.zbgt.api.unification;

import static com.zorbatron.zbgt.ZBGTUtility.zbgtId;
import static com.zorbatron.zbgt.api.unification.ZBGTMaterials.*;
import static gregtech.api.GTValues.IV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;

public class ZBGTSecondDegreeMaterials {

    private static int id = 1000;

    protected static void init() {
        MAR_CE_M200 = new Material.Builder(id++, zbgtId("mar_ce_m_200"))
                .liquid(new FluidBuilder().temperature(5000))
                .color(0x383030)
                .flags(GENERATE_PLATE, GENERATE_LONG_ROD)
                .components(MAR_M200, 18, Cerium, 1)
                .blast(5000, BlastProperty.GasTier.HIGH)
                .build();

        HDCS = new Material.Builder(id++, zbgtId("hdcs"))
                .liquid(new FluidBuilder().temperature(9000))
                .color(0x334433)
                .flags(GENERATE_SMALL_GEAR)
                .components(TungstenSteel, 12, HSSE, 9, HSSG, 6, Ruridit, 6, Titanium, 2, Plutonium239, 1)
                .blast(b -> b
                        .temp(9000, BlastProperty.GasTier.HIGHER)
                        .blastStats(VA[IV], 20 * 95))
                .build();
    }
}
