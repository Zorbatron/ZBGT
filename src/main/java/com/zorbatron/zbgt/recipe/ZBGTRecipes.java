package com.zorbatron.zbgt.recipe;

import com.zorbatron.zbgt.common.ZBGTConfig;
import com.zorbatron.zbgt.recipe.cal.CALCircuits;
import com.zorbatron.zbgt.recipe.cal.WrapRecipes;
import com.zorbatron.zbgt.recipe.chemistry.SluiceJuice;

public class ZBGTRecipes {

    public static void init() {
        MultiblockRecipes.init();
        MultiblockPartRecipes.init();
        CoverRecipes.init();
        CoALRecipes.init();
        MiscRecipes.init();
        CasingRecipes.init();
        MaterialRecipes.init();
        AE2Recipes.init();

        if (ZBGTConfig.recipeSettings.wrapRecipes) {
            WrapRecipes.init();
        }

        if (ZBGTConfig.recipeSettings.calCircuitRecipes) {
            CALCircuits.init();
        }

        SluiceJuice.init();
    }
}
