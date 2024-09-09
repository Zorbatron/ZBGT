package com.zorbatron.zbgt.recipe;

import static gregtech.api.unification.material.Materials.Water;
import static net.minecraft.init.Blocks.COBBLESTONE;

import net.minecraft.item.ItemStack;

import appeng.api.AEApi;
import appeng.api.definitions.IMaterials;
import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;

public class AE2Recipes {

    protected static void init() {
        final IMaterials ae2Materials = AEApi.instance().definitions().materials();

        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .input(COBBLESTONE, 256_000)
                .outputs(ae2Materials.singularity().maybeStack(1).orElse(ItemStack.EMPTY))
                .EUt(GTValues.VA[GTValues.HV]).duration(20 * 60)
                .buildAndRegister();

        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .fluidInputs(Water.getFluid(256_000_000))
                .outputs(ae2Materials.singularity().maybeStack(1).orElse(ItemStack.EMPTY))
                .EUt(GTValues.VA[GTValues.HV]).duration(20 * 60)
                .buildAndRegister();
    }
}
