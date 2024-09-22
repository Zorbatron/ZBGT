package com.zorbatron.zbgt.api.unification;

import gregtech.api.unification.material.Material;

public final class ZBGTMaterials {

    // First degree materials
    public static Material Indalloy140;
    public static Material MAR_M200;

    // Second degree materials
    public static Material MAR_CE_M200;
    public static Material HDCS;

    public static void init() {
        ZBGTMaterialExtraFlags.init();
        ZBGTFirstDegreeMaterials.init();
        ZBGTSecondDegreeMaterials.init();
    }
}
