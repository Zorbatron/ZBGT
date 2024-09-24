package com.zorbatron.zbgt.api.unification.material.materials;

import com.zorbatron.zbgt.api.unification.material.ZBGTMaterials;
import com.zorbatron.zbgt.api.util.ZBGTUtility;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

public final class ZBGTUnknownCompositionMaterials {

    private static int id = 15_000;

    public static void register() {
        ZBGTMaterials.SpecialCeramics = new Material.Builder(id++, ZBGTUtility.zbgtId("special_ceramics"))
                .dust()
                .color(0x8f8c2c).iconSet(MaterialIconSet.FINE)
                .build();
    }
}
