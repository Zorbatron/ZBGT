package com.zorbatron.zbgt.materials;

import static gregtech.api.unification.material.Materials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.PropertyKey;

public class ZBGTMaterialOverride {

    public static void init() {
        magneticFluids();
    }

    private static void magneticFluids() {
        Material[] magneticMaterials = { IronMagnetic, SteelMagnetic, NeodymiumMagnetic, SamariumMagnetic };

        for (Material material : magneticMaterials) {
            material.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        }
    }
}
