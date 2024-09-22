package com.zorbatron.zbgt.api.unification;

import static gregicality.multiblocks.api.unification.GCYMMaterials.Trinaquadalloy;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;

public class ZBGTMaterialExtraFlags {

    public static void setFlags(Material[] materials, MaterialFlag... flags) {
        for (Material material : materials) {
            material.addFlags(flags);
        }
    }

    protected static void init() {
        doublePlates();
        densePlates();
        frameBoxes();
        longRods();
        fluids();
        gears();
        wires();
        screws();
    }

    private static void doublePlates() {
        Material[] materials = { Invar };

        setFlags(materials, GENERATE_DOUBLE_PLATE);
    }

    private static void densePlates() {
        Material[] materials = { Steel, Aluminium, StainlessSteel, Titanium, TungstenSteel, Tritanium, HSSS,
                Osmiridium, NiobiumTitanium, Iridium, WroughtIron, Trinaquadalloy };

        setFlags(materials, GENERATE_DENSE);
    }

    private static void frameBoxes() {
        Material[] materials = { NaquadahAlloy, RhodiumPlatedPalladium, Darmstadtium, WroughtIron, Duranium };

        setFlags(materials, GENERATE_FRAME);
    }

    private static void longRods() {
        Material[] materials = { Chrome, IronMagnetic, SteelMagnetic, NeodymiumMagnetic, SamariumMagnetic };

        setFlags(materials, GENERATE_LONG_ROD);
    }

    private static void fluids() {
        List<Pair<Material, Integer>> materialList = new ArrayList<>();
        materialList.add(new ImmutablePair<>(IronMagnetic, 1811));
        materialList.add(new ImmutablePair<>(SteelMagnetic, 2046));
        materialList.add(new ImmutablePair<>(NeodymiumMagnetic, 1297));
        materialList.add(new ImmutablePair<>(SamariumMagnetic, 1345));
        materialList.add(new ImmutablePair<>(LithiumChloride, 1123));

        for (Pair<Material, Integer> materialPair : materialList) {
            materialPair.getLeft().setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID,
                    new FluidBuilder().temperature(materialPair.getRight())));
        }
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
