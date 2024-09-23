package com.zorbatron.zbgt.api.unification.material.materials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.LithiumChloride;
import static gregtech.api.unification.material.properties.PropertyKey.INGOT;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.FluidProperty;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;

public class ZBGTMaterialExtraProperties {

    public static void register() {
        ingots();
        fluids();
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
}
