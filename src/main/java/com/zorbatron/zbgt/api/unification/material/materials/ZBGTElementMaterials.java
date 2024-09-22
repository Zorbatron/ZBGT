package com.zorbatron.zbgt.api.unification.material.materials;

import static com.zorbatron.zbgt.ZBGTUtility.zbgtId;
import static com.zorbatron.zbgt.api.unification.ZBGTElements.Ad;
import static com.zorbatron.zbgt.api.unification.material.ZBGTMaterials.*;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

public class ZBGTElementMaterials {

    private static int id = 0;

    public static void register() {
        Adamantium = new Material.Builder(id++, zbgtId("adamantium"))
                .dust(7).iconSet(MaterialIconSet.SHINY)
                .ore()
                .element(Ad)
                .build();
    }
}
