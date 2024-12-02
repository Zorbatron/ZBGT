package com.zorbatron.zbgt.api.unification.material.info;

import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

public class ZBGTMaterialFlags {

    public static final MaterialFlag GENERATE_NANITES = new MaterialFlag.Builder("generate_nanites")
            .requireProps(PropertyKey.INGOT)
            .build();
}
