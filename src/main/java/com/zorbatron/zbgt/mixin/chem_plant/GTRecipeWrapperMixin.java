package com.zorbatron.zbgt.mixin.chem_plant;

import java.util.Collection;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.zorbatron.zbgt.api.recipes.maps.RecipeMapChemPlant;
import com.zorbatron.zbgt.common.items.ZBGTCatalystItem;

import gregtech.api.recipes.RecipeMap;
import gregtech.integration.jei.recipe.GTRecipeWrapper;

@Mixin(value = GTRecipeWrapper.class, remap = false)
public abstract class GTRecipeWrapperMixin {

    @Shadow
    @Final
    private RecipeMap<?> recipeMap;

    @Inject(method = "addIngredientTooltips", at = @At(value = "RETURN", ordinal = 2))
    private void catalystTooltip(@NotNull Collection<String> tooltip, boolean notConsumed, boolean input,
                                 @Nullable Object ingredient, @Nullable Object ingredient2, CallbackInfo ci) {
        if (recipeMap instanceof RecipeMapChemPlant<?> && ingredient instanceof ItemStack stack &&
                stack.getItem() instanceof ZBGTCatalystItem) {
            tooltip.add(I18n.format("recipemap.chem_plant.catalyst.0"));
            tooltip.add(I18n.format("recipemap.chem_plant.catalyst.1"));
        }
    }
}
