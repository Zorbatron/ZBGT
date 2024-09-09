package com.zorbatron.zbgt.materials;

import static gregtech.api.unification.material.Materials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;

public class ZBGTMaterialOverrides {

    public static void init() {
        magneticMaterialFluids();
        densePlates();
        frameBoxes();
        longRods();
        gears();
        wire();
    }

    private static void magneticMaterialFluids() {
        Material[] materials = { IronMagnetic, SteelMagnetic, NeodymiumMagnetic, SamariumMagnetic };

        for (Material material : materials) {
            material.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
            material.addFlags(MaterialFlags.GENERATE_LONG_ROD);
        }
    }

    private static void densePlates() {
        Material[] materials = { Steel, Aluminium, StainlessSteel, Titanium, TungstenSteel, Tritanium, HSSS,
                Osmiridium, NiobiumTitanium, Iridium, WroughtIron };

        for (Material material : materials) {
            material.addFlags(MaterialFlags.GENERATE_DENSE);
        }
    }

    private static void frameBoxes() {
        Material[] materials = { NaquadahAlloy, RhodiumPlatedPalladium, Darmstadtium, WroughtIron };

        for (Material material : materials) {
            material.addFlags(MaterialFlags.GENERATE_FRAME);
        }
    }

    private static void longRods() {
        Material[] materials = { Chrome };

        for (Material material : materials) {
            material.addFlags(MaterialFlags.GENERATE_LONG_ROD);
        }
    }

    private static void gears() {
        Material[] materials = { RhodiumPlatedPalladium, Darmstadtium };

        for (Material material : materials) {
            material.addFlags(MaterialFlags.GENERATE_GEAR);
        }
    }

    private static void wire() {
        Material[] materials = { Titanium };

        for (Material material : materials) {
            material.setProperty(PropertyKey.WIRE, new WireProperties(2048, 4, 2));
        }
    }
}
