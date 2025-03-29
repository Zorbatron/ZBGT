package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.combineRGB;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

public final class ZBGTUnknownCompositionMaterials {

    private static int id = 15_000;

    public static void register() {
        SpecialCeramics = new Material.Builder(id++, zbgtId("special_ceramics"))
                .dust()
                .color(0x8f8c2c).iconSet(MaterialIconSet.FINE)
                .build();

        SluiceJuice = new Material.Builder(id++, zbgtId("sluice_juice"))
                .liquid(new FluidBuilder().temperature(295))
                .color(combineRGB(92, 60, 36))
                .build();

        SluiceSand = new Material.Builder(id++, zbgtId("sluice_sand"))
                .dust()
                .color(combineRGB(165, 165, 120)).iconSet(MaterialIconSet.FINE)
                .build();
    }
}
