package com.zorbatron.zbgt.loaders.recipe;

import static com.zorbatron.zbgt.ZBGTUtility.getCWUt;
import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.CoAL_RECIPES;
import static gregtech.api.GTValues.*;

import com.zorbatron.zbgt.api.recipes.builders.CoALRecipeBuilder;

public class CoALRecipes2 {

    public static void init() {}

    private static void lvToEV() {
        for (int tier = LV; tier < EV; tier++) {
            CoALRecipeBuilder builder = CoAL_RECIPES.recipeBuilder().EUt(VA[tier - 1]).duration(600)
                    .CasingTier(tier)
                    .CWUt(getCWUt(tier))
                    .circuitMeta(1);
        }
    }

    public enum coalRecipeType {

        MOTOR,
        PISTON,
        PUMP,
        ROBOT_ARM,
        CONVEYOR,
        EMITTER,
        SENSOR,
        FIELD_GEN,
    }
}
