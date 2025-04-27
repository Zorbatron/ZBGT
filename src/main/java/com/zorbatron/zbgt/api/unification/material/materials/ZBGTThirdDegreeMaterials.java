package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DECOMPOSITION_BY_CENTRIFUGING;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_DOUBLE_PLATE;
import static gregtech.api.unification.material.info.MaterialIconSet.*;

import com.nomiceu.nomilabs.gregtech.material.registry.LabsMaterials;
import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.BlastProperty;

public final class ZBGTThirdDegreeMaterials {

    private static int id = 10_000;

    public static void register() {
        if (!ZBGTAPI.nomiLabsCompat) {
            FluxedElectrum = new Material.Builder(id++, zbgtId("fluxed_electrum"))
                    .ingot().liquid()
                    .color(0xf7be20).iconSet(BRIGHT)
                    .flags(DECOMPOSITION_BY_CENTRIFUGING)
                    .blast(builder -> builder
                            .temp(1100, BlastProperty.GasTier.LOW)
                            .blastStats(VA[EV], 1600)
                            .vacuumStats(VA[MV], 600))
                    .components(Electrum, 6, Lumium, 1, Signalum, 1)
                    .cableProperties(V[IV], 3, 2)
                    .build();
        } else {
            id += 1;
        }

        Hikarium = new Material.Builder(id++, zbgtId("hikarium"))
                .liquid(new FluidBuilder().temperature(5400))
                .color(0xFFD6FB).iconSet(SHINY)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(ZBGTAPI.nomiLabsCompat ? LabsMaterials.Lumium : Lumium, 18, Silver, 8)
                .blast(b -> b
                        .temp(5400, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[LuV], 20 * 34))
                .build();

        Pikyonium64b = new Material.Builder(id++, zbgtId("pikyonium_64_b"))
                .ingot().liquid(new FluidBuilder().temperature(7125))
                .color(0x3467ba).iconSet(METALLIC)
                .flags(DECOMPOSITION_BY_CENTRIFUGING, GENERATE_DOUBLE_PLATE)
                .flags(EXT2_METAL)
                .blast(b -> b
                        .temp(7125, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[ZPM], 20 * 150))
                .components(Inconel792, 8, EglinSteel, 5, NaquadahEnriched, 4, Cerium, 3, Antimony, 2, Platinum, 2,
                        Ytterbium, 1, TungstenSteel, 4)
                .build();
    }
}
