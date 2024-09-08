package com.zorbatron.zbgt.loaders.recipe;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

public class ZBGTRecipes {

    public static void init() {
        MultiblockRecipes.init();
        MultiblockPartRecipes.init();
        CoverRecipes.init();
        CoALRecipes.init();
        MiscRecipes.init();
    }
}
