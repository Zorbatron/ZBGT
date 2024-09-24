package com.zorbatron.zbgt.api.unification.material.materials;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.LithiumChloride;
import static gregtech.api.unification.material.properties.PropertyKey.INGOT;
import static gregtech.api.unification.material.properties.PropertyKey.WIRE;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials;
import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.util.ZBGTUtility;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;

public class ZBGTMaterialExtraProperties {

    public static void register() {
        ingots();
        fluids();
        wires();
    }

    private static void ingots() {
        Material[] materials = { Cerium };

        for (Material material : materials) {
            material.setProperty(INGOT, new IngotProperty());
        }
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

    private static void wires() {
        List<Pair<Material, Integer[]>> wirePairs = new ArrayList<>();

        wirePairs.add(new ImmutablePair<>(Titanium, new Integer[] { ZBGTUtility.intV[EV], 4, 2 }));
        wirePairs.add(new ImmutablePair<>(NetherStar, new Integer[] { ZBGTUtility.intV[UIV], 4, 16 }));
        wirePairs.add(new ImmutablePair<>(Osmiridium, new Integer[] { ZBGTUtility.intV[LuV], 8, 2 }));

        if (ZBGTAPI.nomiLabsCompat) {
            wirePairs.add(new ImmutablePair<>(
                    LabsMaterials.ElectrumFlux,
                    new Integer[] { ZBGTUtility.intV[IV], 3, 2 }));
        }

        for (Pair<Material, Integer[]> materialPair : wirePairs) {
            materialPair.getLeft().setProperty(WIRE, new WireProperties(
                    materialPair.getRight()[0],
                    materialPair.getRight()[1],
                    materialPair.getRight()[2]));
        }
    }
}
