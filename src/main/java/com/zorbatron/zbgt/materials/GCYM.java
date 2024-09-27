package com.zorbatron.zbgt.materials;

import static com.zorbatron.zbgt.materials.ZBGTMaterialOverrides.setFlags;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_DENSE;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_DOUBLE_PLATE;

import gregicality.multiblocks.api.unification.GCYMMaterials;
import gregtech.api.unification.material.Material;

public class GCYM {

    protected static void init() {
        doublePlates();
        densePlates();
    }

    private static void densePlates() {
        Material[] materials = { GCYMMaterials.Trinaquadalloy };

        setFlags(materials, GENERATE_DENSE);
    }

    private static void doublePlates() {
        Material[] materials = { GCYMMaterials.MaragingSteel300 };

        setFlags(materials, GENERATE_DOUBLE_PLATE);
    }
}
