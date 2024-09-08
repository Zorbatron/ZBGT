package com.zorbatron.zbgt.recipe;

import gregtech.api.util.Mods;

public class ZBGTRecipes {

    public static void init() {
        MultiblockRecipes.init();
        MultiblockPartRecipes.init();
        CoverRecipes.init();
        CoALRecipes.init();
        MiscRecipes.init();
        CasingRecipes.init();

        if (Mods.AppliedEnergistics2.isModLoaded()) {
            AE2Recipes.init();
        }
    }
}
