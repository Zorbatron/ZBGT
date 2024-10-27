package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.api.unification.ZBGTElements.Ad;
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
        CosmicNeutronium = new Material.Builder(id++, zbgtId("cosmicneutronium"))
                .liquid(new FluidBuilder().temperature(0))
                .ingot(7).iconSet(MaterialIconSet.SHINY)
                .color(0x000000)
                .secondaryColor(0x888888)
                .blastTemp(10500)
                .gasTier('highest')
                .cableProperties(8388608,12,1)
                .element(Elements.SpNt)
                .build();
    }
}
