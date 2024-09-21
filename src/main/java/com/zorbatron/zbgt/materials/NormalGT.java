package com.zorbatron.zbgt.materials;

import static com.zorbatron.zbgt.materials.ZBGTMaterialOverrides.setFlags;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;

public class NormalGT {

    protected static void init() {
        magneticMaterialFluids();
        doublePlates();
        densePlates();
        frameBoxes();
        longRods();
        gears();
        wires();
        screws();
    }

    private static void magneticMaterialFluids() {
        Material[] materials = { IronMagnetic, SteelMagnetic, NeodymiumMagnetic, SamariumMagnetic };

        setFlags(materials, GENERATE_LONG_ROD);

        for (Material material : materials) {
            material.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        }
    }

    private static void doublePlates() {
        Material[] materials = { Invar };

        setFlags(materials, GENERATE_DOUBLE_PLATE);
    }

    private static void densePlates() {
        Material[] materials = { Steel, Aluminium, StainlessSteel, Titanium, TungstenSteel, Tritanium, HSSS,
                Osmiridium, NiobiumTitanium, Iridium, WroughtIron };

        setFlags(materials, GENERATE_DENSE);
    }

    private static void frameBoxes() {
        Material[] materials = { NaquadahAlloy, RhodiumPlatedPalladium, Darmstadtium, WroughtIron, Duranium };

        setFlags(materials, GENERATE_FRAME);
    }

    private static void longRods() {
        Material[] materials = { Chrome };

        setFlags(materials, GENERATE_LONG_ROD);
    }

    private static void gears() {
        Material[] materials = { RhodiumPlatedPalladium, Darmstadtium };

        setFlags(materials, GENERATE_GEAR);
    }

    private static void wires() {
        Titanium.setProperty(PropertyKey.WIRE, new WireProperties(2048, 4, 2));
    }

    private static void screws() {
        Material[] materials = { BlueSteel, CertusQuartz };

        setFlags(materials, GENERATE_BOLT_SCREW);
    }
}
