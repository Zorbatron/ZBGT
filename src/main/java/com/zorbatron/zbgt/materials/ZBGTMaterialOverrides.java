package com.zorbatron.zbgt.materials;

import static gregtech.api.unification.material.Materials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.PropertyKey;

public class ZBGTMaterialOverrides {

    public static void init() {
        magneticMaterialFluids();
        densePlates();
    }

    private static void magneticMaterialFluids() {
        Material[] materials = { IronMagnetic, SteelMagnetic, NeodymiumMagnetic, SamariumMagnetic };

        for (Material material : materials) {
            material.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
            material.addFlags(MaterialFlags.GENERATE_LONG_ROD);
        }
    }

    private static void densePlates() {
        Material[] materials = { Steel, Aluminium, StainlessSteel, Titanium, TungstenSteel, Tritanium, HSSS };

        for (Material material : materials) {
            material.addFlags(MaterialFlags.GENERATE_DENSE);
        }
    }
}
