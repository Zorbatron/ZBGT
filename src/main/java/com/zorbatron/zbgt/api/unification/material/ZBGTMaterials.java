package com.zorbatron.zbgt.api.unification.material;

import com.zorbatron.zbgt.api.unification.material.materials.ZBGTElementMaterials;
import com.zorbatron.zbgt.api.unification.material.materials.ZBGTFirstDegreeMaterials;
import com.zorbatron.zbgt.api.unification.material.materials.ZBGTMaterialExtraFlags;
import com.zorbatron.zbgt.api.unification.material.materials.ZBGTSecondDegreeMaterials;

import gregtech.api.unification.material.Material;

public final class ZBGTMaterials {

    // Elements
    public static Material Adamantium;

    // First degree materials
    public static Material Indalloy140;
    public static Material MAR_M200;
    public static Material TanmolyiumBetaC;
    public static Material AdamantiumAlloy;
    public static Material Artherium_Sn;

    // Second degree materials
    public static Material MAR_CE_M200;
    public static Material HDCS;

    public static void init() {
        ZBGTMaterialExtraFlags.register();

        ZBGTElementMaterials.register();
        ZBGTFirstDegreeMaterials.register();
        ZBGTSecondDegreeMaterials.register();
    }
}
