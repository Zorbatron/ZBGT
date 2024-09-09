package com.zorbatron.zbgt.materials;

import com.zorbatron.zbgt.api.util.ZBGTMods;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;

public class ZBGTMaterialOverrides {

    public static void init() {
        NormalGT.init();

        if (ZBGTMods.GCYM.isModLoaded()) {
            GCYM.init();
        }
    }

    protected static void setFlags(Material[] materials, MaterialFlag... flags) {
        for (Material material : materials) {
            material.addFlags(flags);
        }
    }
}
