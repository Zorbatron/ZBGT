package com.zorbatron.zbgt.recipe;

import static gregtech.api.GTValues.UIV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Carbon;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.unification.ore.ZBGTOrePrefix;
import com.zorbatron.zbgt.common.ZBGTConfig;
import com.zorbatron.zbgt.recipe.cal.CALCircuits;
import com.zorbatron.zbgt.recipe.cal.WrapRecipes;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

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

        ZBGTRecipeMaps.NANO_FORGE_RECIPES.recipeBuilder()
                .notConsumable(OrePrefix.lens, NetherStar)
                .input(OrePrefix.block, Carbon, 8)
                .input(MetaItems.SYSTEM_ON_CHIP, 64)
                .fluidInputs(UUMatter.getFluid(200_000))
                .output(ZBGTOrePrefix.nanites, Carbon, 64)
                .EUt(VA[UIV]).duration(20 * 500)
                .nanoForgeTier(3)
                .buildAndRegister();
    }
}
