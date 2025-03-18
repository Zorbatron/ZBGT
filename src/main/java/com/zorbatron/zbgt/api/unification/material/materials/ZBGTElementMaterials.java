package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.ZBGTElements.Ad;
import static com.zorbatron.zbgt.api.unification.ZBGTElements.Qt;
import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;
import static gregtech.api.GTValues.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.material.properties.BlastProperty;

public final class ZBGTElementMaterials {

    private static int id = 0;

    public static void register() {
        Adamantium = new Material.Builder(id++, zbgtId("adamantium"))
                .dust(7).iconSet(MaterialIconSet.SHINY)
                .ore(true)
                .element(Ad)
                .build();

        Quantium = new Material.Builder(id++, zbgtId("quantium"))
                .dust().ore(true).liquid(new FluidBuilder().temperature(9900))
                .color(0x00d10b).iconSet(MaterialIconSet.SHINY)
                .flags(MaterialFlags.GENERATE_BOLT_SCREW)
                .blast(blast -> blast
                        .temp(5400, BlastProperty.GasTier.HIGH)
                        .blastStats(VA[IV], 45 * 20))
                .element(Qt)
                .build();
    }
}
