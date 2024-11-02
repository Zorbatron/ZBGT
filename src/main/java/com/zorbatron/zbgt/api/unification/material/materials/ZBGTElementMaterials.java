package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.ZBGTElements.Ad;
import static com.zorbatron.zbgt.api.unification.ZBGTElements.Qt;
import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.Elements;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

public final class ZBGTElementMaterials {

    private static int id = 0;

    public static void register() {
        Adamantium = new Material.Builder(id++, zbgtId("adamantium"))
                .dust(7).iconSet(MaterialIconSet.SHINY)
                .ore(true)
                .element(Ad)
                .build();

        LiquidNitrogen = new Material.Builder(id++, zbgtId("zb_liquid_nitrogen"))
                .liquid(new FluidBuilder().temperature(77))
                .color(0xE1FAFA)
                .element(Elements.N)
                .build();

        Quantium = new Material.Builder(id++, zbgtId("quantium"))
                .dust().ore(true)
                .color(0x00d10b).iconSet(MaterialIconSet.SHINY)
                .element(Qt)
                .build();
    }
}
