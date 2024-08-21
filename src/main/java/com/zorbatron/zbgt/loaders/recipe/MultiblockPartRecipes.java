package com.zorbatron.zbgt.loaders.recipe;

import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.common.metatileentities.MetaTileEntities.ITEM_IMPORT_BUS;

import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;

public class MultiblockPartRecipes {

    public static void init() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(ITEM_IMPORT_BUS[0])
                .circuitMeta(10)
                .output(ZBGTMetaTileEntities.SINGLE_ITEM_INPUT_BUS)
                .duration(20)
                .EUt(VA[1])
                .buildAndRegister();
    }
}
