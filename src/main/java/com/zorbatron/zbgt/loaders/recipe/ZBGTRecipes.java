package com.zorbatron.zbgt.loaders.recipe;

import com.zorbatron.zbgt.ZBGTCore;

public class ZBGTRecipes {

    public static void init() {
        ZBGTCore.LOGGER.info("Registering recipes...");
        MultiblockPartRecipes.init();
        CoverRecipes.init();
    }
}
