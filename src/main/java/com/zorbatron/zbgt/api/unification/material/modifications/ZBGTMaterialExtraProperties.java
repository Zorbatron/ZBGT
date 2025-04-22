package com.zorbatron.zbgt.api.unification.material.modifications;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.LithiumChloride;
import static gregtech.api.unification.material.properties.PropertyKey.*;

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
import gregtech.api.unification.material.properties.*;

public final class ZBGTMaterialExtraProperties {

    public static void init() {
        ingots();
        fluids();
        wires();
        dusts();
        ores();
    }

    public static void initLate() {
        if (Nitrogen.getProperty(PropertyKey.FLUID).getQueuedBuilder(FluidStorageKeys.LIQUID) == null) {
            Nitrogen.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.LIQUID,
                    new FluidBuilder()
                            .temperature(77)
                            .color(0x008D8F)
                            .name("liquid_nitrogen")
                            .translation("gregtech.fluid.liquid_generic"));
        }
    }

    private static void ingots() {
        Material[] materials = { Cerium };

        for (Material material : materials) {
            if (material.hasProperty(INGOT)) continue;
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
            if (materialPair.getLeft().hasProperty(FLUID)) continue;
            materialPair.getLeft().setProperty(FLUID, new FluidProperty(FluidStorageKeys.LIQUID,
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
            if (materialPair.getLeft().hasProperty(WIRE)) continue;
            materialPair.getLeft().setProperty(WIRE, new WireProperties(
                    materialPair.getRight()[0],
                    materialPair.getRight()[1],
                    materialPair.getRight()[2]));
        }
    }

    private static void dusts() {
        Material[] materials = { Ytterbium, Zirconium };

        for (Material material : materials) {
            if (material.hasProperty(DUST)) continue;
            material.setProperty(DUST, new DustProperty());
        }
    }

    private static void ores() {
        Material[] materials = { Ytterbium, Titanium, Niobium };

        for (Material material : materials) {
            if (material.hasProperty(ORE)) continue;

            OreProperty oreProperty = new OreProperty();
            if (material == Titanium) {
                oreProperty.addOreByProducts(Almandine);
            }

            material.setProperty(ORE, oreProperty);
        }
    }
}
