package com.zorbatron.zbgt.mixin.chem_plant;

import net.minecraftforge.items.SlotItemHandler;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import com.llamalad7.mixinextras.sugar.Local;
import com.zorbatron.zbgt.api.recipes.maps.RecipeMapChemPlant;
import com.zorbatron.zbgt.integration.jei.utils.render.SpecializedItemStackTextRenderer;

import gregtech.api.recipes.RecipeMap;
import gregtech.integration.jei.recipe.GTRecipeWrapper;
import gregtech.integration.jei.recipe.RecipeMapCategory;

@Mixin(value = RecipeMapCategory.class, remap = false)
public class RecipeMapCategoryMixin {

    @Shadow
    @Final
    private RecipeMap<?> recipeMap;

    @ModifyArgs(method = "setRecipe(Lmezz/jei/api/gui/IRecipeLayout;Lgregtech/integration/jei/recipe/GTRecipeWrapper;Lmezz/jei/api/ingredients/IIngredients;)V",
                at = @At(value = "INVOKE",
                         target = "Lmezz/jei/api/gui/IGuiItemStackGroup;init(IZLmezz/jei/api/ingredients/IIngredientRenderer;IIIIII)V",
                         ordinal = 0))
    private void injectCustomRenderer(Args args, @Local SlotItemHandler handle,
                                      @Local(argsOnly = true) @NotNull GTRecipeWrapper recipeWrapper) {
        if (recipeMap instanceof RecipeMapChemPlant<?> && !recipeWrapper.isNotConsumedItem(handle.getSlotIndex())) {
            args.set(2, new SpecializedItemStackTextRenderer("NC*"));
        }
    }
}
