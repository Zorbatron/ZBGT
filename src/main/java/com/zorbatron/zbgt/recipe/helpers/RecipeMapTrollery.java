package com.zorbatron.zbgt.recipe.helpers;

import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;

import net.minecraft.item.ItemStack;

import gregtech.api.recipes.RecyclingHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.ItemMaterialInfo;

public class RecipeMapTrollery {

    public static void clearAssemblerOnBuild() {
        ASSEMBLER_RECIPES.onRecipeBuild(null);
    }

    public static void resetAssemblerOnBuilder() {
        ASSEMBLER_RECIPES.onRecipeBuild(recipeBuilder -> {
            if (recipeBuilder.getFluidInputs().size() == 1 &&
                    recipeBuilder.getFluidInputs().get(0).getInputFluidStack().getFluid() ==
                            Materials.SolderingAlloy.getFluid()) {
                int amount = recipeBuilder.getFluidInputs().get(0).getInputFluidStack().amount;

                recipeBuilder.copy().clearFluidInputs().fluidInputs(Materials.Tin.getFluid(amount * 2))
                        .buildAndRegister();
            }

            if (recipeBuilder.isWithRecycling()) {
                // ignore input fluids for recycling
                ItemStack outputStack = recipeBuilder.getOutputs().get(0);
                ItemMaterialInfo info = RecyclingHandler.getRecyclingIngredients(recipeBuilder.getInputs(),
                        outputStack.getCount());
                if (info != null) {
                    OreDictUnifier.registerOre(outputStack, info);
                }
            }
        });
    }
}
