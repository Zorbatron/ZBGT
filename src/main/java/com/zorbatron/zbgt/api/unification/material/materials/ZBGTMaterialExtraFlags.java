package com.zorbatron.zbgt.api.unification.material.materials;

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

    public static void register() {
        doublePlates();
        densePlates();
        screwsBolts();
        frameBoxes();
        smallGears();
        longRods();
        rotors();
        plates();
        gears();
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

    private static void screwsBolts() {
        List<Material> materials = new ArrayList<>(Arrays.asList(BlueSteel, CertusQuartz, Ruthenium));

        if (ZBGTAPI.nomiLabsCompat) {
            materials.add(LabsMaterials.Signalum);
        }

        setFlags(materials, GENERATE_BOLT_SCREW);
    }

    private static void frameBoxes() {
        Material[] materials = { NaquadahAlloy, RhodiumPlatedPalladium, Darmstadtium, WroughtIron, Duranium };

        setFlags(materials, GENERATE_FRAME);
    }

    private static void smallGears() {
        Material[] materials = { Platinum };

        setFlags(materials, GENERATE_SMALL_GEAR);
    }

    private static void longRods() {
        Material[] materials = { Chrome, IronMagnetic, SteelMagnetic, NeodymiumMagnetic, SamariumMagnetic };

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
        Material[] materials = { RhodiumPlatedPalladium, Darmstadtium };

        setFlags(materials, GENERATE_GEAR);
    }
}
