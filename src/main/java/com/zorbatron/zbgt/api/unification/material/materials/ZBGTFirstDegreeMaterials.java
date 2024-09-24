package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials;
import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;

public class ZBGTFirstDegreeMaterials {

    private static int id = 500;

    public static void register() {
        Indalloy140 = new Material.Builder(id++, zbgtId("indalloy_140"))
                .liquid(new FluidBuilder().temperature(5475))
                .color(0x59536E).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Bismuth, 47, Lead, 25, Tin, 13, Cadmium, 10, Indium, 5)
                .build();

        MAR_M200 = new Material.Builder(id++, zbgtId("mar_200"))
                .dust().ingot().liquid(new FluidBuilder().temperature(5000))
                .color(0x515151).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION, GENERATE_DOUBLE_PLATE)
                .components(Niobium, 2, Chrome, 9, Aluminium, 5, Titanium, 2, Cobalt, 10, Tungsten, 13, Nickel, 18)
                .blast(b -> b
                        .temp(5000, BlastProperty.GasTier.MID)
                        .blastStats(VA[IV], 205)
                        .vacuumStats(VA[MV], 246))
                .build();

        TanmolyiumBetaC = new Material.Builder(id++, zbgtId("tanmolyium_beta_c"))
                .dust().liquid(new FluidBuilder().temperature(5300))
                .color(0xC72FCC).iconSet(MaterialIconSet.METALLIC)
                .flags(DISABLE_DECOMPOSITION, GENERATE_SMALL_GEAR, GENERATE_BOLT_SCREW)
                .components(Titanium, 5, Molybdenum, 5, Vanadium, 2, Chrome, 3, Aluminium, 1)
                .blast(b -> b
                        .temp(5300, BlastProperty.GasTier.LOW)
                        .blastStats(VA[IV], 20 * 7))
                .build();

        PreciousMetalsAlloy = new Material.Builder(id++, zbgtId("precious_metals_alloy"))
                .liquid(new FluidBuilder().temperature(10000))
                .color(0x9D90C6).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Ruthenium, 1, Rhodium, 1, Palladium, 1, Platinum, 1, Osmium, 1, Iridium, 1)
                .blast(b -> b
                        .temp(10000, BlastProperty.GasTier.HIGHEST)
                        .blastStats(VA[UV], 20 * 87))
                .build();

        AdamantiumAlloy = new Material.Builder(id++, zbgtId("adamantium_alloy"))
                .dust().liquid(new FluidBuilder().temperature(5550))
                .color(0xA0A0A0).iconSet(MaterialIconSet.SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Adamantium, 5, Naquadah, 2, Lanthanum, 3)
                .blast(b -> b
                        .temp(5500, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[EV], 20 * 56))
                .build();

        if (ZBGTAPI.nomiLabsCompat) {
            Ardite = LabsMaterials.Ardite;
            Manyullyn = LabsMaterials.Manyullyn;
            Signalum = LabsMaterials.Signalum;
            Lumium = LabsMaterials.Lumium;
            Enderium = LabsMaterials.Enderium;
            FluxedElectrum = LabsMaterials.ElectrumFlux;
            EnergeticAlloy = LabsMaterials.EnergeticAlloy;

            id += 7;
        } else {
            // TODO: Finish these xD
            Ardite = new Material.Builder(id++, zbgtId("ardite"))
                    .build();

            Manyullyn = new Material.Builder(id++, zbgtId("manyullyn"))
                    .build();

            Signalum = new Material.Builder(id++, zbgtId("signalum"))
                    .build();

            Lumium = new Material.Builder(id++, zbgtId("lumium"))
                    .build();

            Enderium = new Material.Builder(id++, zbgtId("enderium"))
                    .build();

            FluxedElectrum = new Material.Builder(id++, zbgtId("fluxed_electrum"))
                    .build();

            EnergeticAlloy = new Material.Builder(id++, zbgtId("energetic_alloy"))
                    .build();
        }
    }
}
