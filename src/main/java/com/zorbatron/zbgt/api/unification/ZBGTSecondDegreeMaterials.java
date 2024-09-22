package com.zorbatron.zbgt.api.unification;

import static com.zorbatron.zbgt.ZBGTUtility.zbgtId;
import static com.zorbatron.zbgt.api.unification.ZBGTMaterials.*;
import static gregtech.api.unification.material.Materials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;

public class ZBGTSecondDegreeMaterials {

    private static int id = 1000;

    protected static void init() {
        MAR_CE_M200 = new Material.Builder(id++, zbgtId("mar_ce_m_200"))
                .liquid(new FluidBuilder().temperature(5000))
                .color(0x383030)
                .blast(5000, BlastProperty.GasTier.HIGH)
                .components(MAR_M200, 18, Cerium, 1)
                .build();
    }
}
