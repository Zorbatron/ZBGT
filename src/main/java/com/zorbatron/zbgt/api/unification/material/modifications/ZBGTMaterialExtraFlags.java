package com.zorbatron.zbgt.api.unification.material.modifications;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.Tumbaga;
import static gregicality.multiblocks.api.unification.GCYMMaterials.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials;
import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;

public final class ZBGTMaterialExtraFlags {

    public static void setFlags(Material[] materials, MaterialFlag... flags) {
        for (Material material : materials) {
            material.addFlags(flags);
        }
    }

    public static void setFlags(List<Material> materials, MaterialFlag... flags) {
        for (Material material : materials) {
            material.addFlags(flags);
        }
    }

    public static void init() {
        doublePlates();
        densePlates();
        screwsBolts();
        frameBoxes();
        smallGears();
        longRods();
        rotors();
        plates();
        gears();
        rings();
        rods();
    }

    private static void doublePlates() {
        Material[] materials = { Invar, MaragingSteel300, HastelloyC276 };

        setFlags(materials, GENERATE_DOUBLE_PLATE);
    }

    private static void densePlates() {
        Material[] materials = { Steel, Aluminium, StainlessSteel, Titanium, TungstenSteel, Tritanium, HSSS,
                Osmiridium, NiobiumTitanium, Iridium, WroughtIron, Trinaquadalloy, Trinaquadalloy,
                Europium, Plutonium239 };

        setFlags(materials, GENERATE_DENSE);
    }

    private static void screwsBolts() {
        List<Material> materials = new ArrayList<>(
                Arrays.asList(BlueSteel, CertusQuartz, Ruthenium, NaquadahEnriched, IncoloyMA956));

        if (ZBGTAPI.nomiLabsCompat) {
            materials.add(LabsMaterials.Signalum);
        }

        setFlags(materials, GENERATE_BOLT_SCREW);
    }

    private static void frameBoxes() {
        Material[] materials = { NaquadahAlloy, RhodiumPlatedPalladium, Darmstadtium, WroughtIron, Duranium, Tumbaga,
                Potin, Zeron100, Stellite100 };

        setFlags(materials, GENERATE_FRAME);
    }

    private static void smallGears() {
        Material[] materials = { Platinum, Naquadria, HSSE };

        setFlags(materials, GENERATE_SMALL_GEAR);
    }

    private static void longRods() {
        Material[] materials = { Chrome, IronMagnetic, SteelMagnetic, NeodymiumMagnetic, SamariumMagnetic, Palladium };

        setFlags(materials, GENERATE_LONG_ROD);
    }

    private static void rotors() {
        List<Material> materials = new ArrayList<>(Arrays.asList(TinAlloy, Aluminium, TungstenCarbide));

        if (ZBGTAPI.nomiLabsCompat) {
            materials.add(LabsMaterials.EnergeticAlloy);
        }

        setFlags(materials, GENERATE_ROTOR);
    }

    private static void plates() {
        Material[] materials = { Lanthanum };

        setFlags(materials, GENERATE_PLATE);
    }

    private static void gears() {
        Material[] materials = { RhodiumPlatedPalladium, Darmstadtium, IncoloyMA956, MaragingSteel300, HastelloyX,
                Zeron100 };

        setFlags(materials, GENERATE_GEAR);
    }

    private static void rings() {
        Material[] materials = { IncoloyMA956, Stellite100 };

        setFlags(materials, GENERATE_RING);
    }

    private static void rods() {
        Material[] materials = { Zeron100 };

        setFlags(materials, GENERATE_BOLT_SCREW);
    }
}
