package com.zorbatron.zbgt.api.unification.material.materials;

import static gregicality.multiblocks.api.unification.GCYMMaterials.Trinaquadalloy;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.properties.PropertyKey.WIRE;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.zorbatron.zbgt.ZBGTUtility;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.WireProperties;

public class ZBGTMaterialExtraFlags {

    public static void setFlags(Material[] materials, MaterialFlag... flags) {
        for (Material material : materials) {
            material.addFlags(flags);
        }
    }

    public static void register() {
        doublePlates();
        densePlates();
        frameBoxes();
        longRods();
        screws();
        gears();
        wires();
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

    private static void gears() {
        Material[] materials = { RhodiumPlatedPalladium, Darmstadtium };

        setFlags(materials, GENERATE_GEAR);
    }

    private static void wires() {
        List<Pair<Material, Integer[]>> wirePairs = new ArrayList<>();

        wirePairs.add(new ImmutablePair<>(Titanium, new Integer[] { ZBGTUtility.intV[EV], 4, 2 }));
        wirePairs.add(new ImmutablePair<>(NetherStar, new Integer[] { ZBGTUtility.intV[UIV], 4, 16 }));

        for (Pair<Material, Integer[]> materialPair : wirePairs) {
            materialPair.getLeft().setProperty(WIRE, new WireProperties(
                    materialPair.getRight()[0],
                    materialPair.getRight()[1],
                    materialPair.getRight()[2]));
        }
    }

    private static void screws() {
        Material[] materials = { BlueSteel, CertusQuartz };

        setFlags(materials, GENERATE_BOLT_SCREW);
    }
}
