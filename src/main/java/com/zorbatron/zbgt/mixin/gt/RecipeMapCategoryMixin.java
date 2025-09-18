package com.zorbatron.zbgt.mixin.gt;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.common.items.behaviors.imprints.ImprintBehavior;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.integration.jei.recipe.GTRecipeWrapper;
import gregtech.integration.jei.recipe.RecipeMapCategory;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

@Mixin(value = RecipeMapCategory.class, remap = false)
public class RecipeMapCategoryMixin {

    @Shadow
    @Final
    private RecipeMap<?> recipeMap;

    @Inject(method = "setRecipe(Lmezz/jei/api/gui/IRecipeLayout;Lgregtech/integration/jei/recipe/GTRecipeWrapper;Lmezz/jei/api/ingredients/IIngredients;)V",
            at = @At(value = "RETURN"))
    public void insertImprintIntoIngredients(IRecipeLayout recipeLayout, GTRecipeWrapper recipeWrapper,
                                             IIngredients ingredients, CallbackInfo ci) {
        if (recipeMap == ZBGTRecipeMaps.CIRCUIT_ASSEMBLY_LINE_RECIPES) {
            Recipe recipe = recipeWrapper.getRecipe();
            ItemStack recipeOutput = recipe.getOutputs().get(0);

            ItemStack imprintStack = ZBGTMetaItems.CIRCUIT_IMPRINT.getStackForm();
            ImprintBehavior.setImprintedCircuit(imprintStack, recipeOutput);

            IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
            itemStackGroup.set(6, imprintStack);
        }
    }
}
