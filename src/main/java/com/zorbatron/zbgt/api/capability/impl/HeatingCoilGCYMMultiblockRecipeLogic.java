package com.zorbatron.zbgt.api.capability.impl;

import static gregtech.api.recipes.logic.OverclockingLogic.heatingCoilOverclockingLogic;

import org.jetbrains.annotations.NotNull;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;

public class HeatingCoilGCYMMultiblockRecipeLogic extends GCYMMultiblockRecipeLogic {

    public HeatingCoilGCYMMultiblockRecipeLogic(RecipeMapMultiblockController metaTileEntity) {
        super(metaTileEntity);
    }

    @Override
    protected int @NotNull [] runOverclockingLogic(@NotNull IRecipePropertyStorage propertyStorage, int recipeEUt,
                                                   long maxVoltage, int duration, int maxOverclocks) {
        return heatingCoilOverclockingLogic(Math.abs(recipeEUt),
                maxVoltage,
                duration,
                maxOverclocks,
                ((IHeatingCoil) metaTileEntity).getCurrentTemperature(),
                propertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
    }
}
