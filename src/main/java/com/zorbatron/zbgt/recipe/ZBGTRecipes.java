package com.zorbatron.zbgt.recipe;

import net.minecraft.init.Blocks;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
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

        ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES.recipeBuilder()
                .input(Blocks.DIRT, 64)
                .fluidInputs(Materials.Water.getFluid(GTValues.L))
                .output(Blocks.DIAMOND_BLOCK)
                .CasingTier(PreciseCasing.CasingType.PRECISE_CASING_1.ordinal())
                .EUt(GTValues.VA[GTValues.MV]).duration(20)
                .buildAndRegister();
    }
}
