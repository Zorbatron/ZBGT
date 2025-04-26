package com.zorbatron.zbgt.api.capability.impl;

import static gregtech.api.recipes.logic.OverclockingLogic.heatingCoilOverclockingLogic;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.common.metatileentities.multi.electric.mega.MTEMegaBase;

import gregtech.api.capability.IHeatingCoil;
import gregtech.api.recipes.logic.OverclockingLogic;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;

public class HeatingCoilMegaMultiblockRecipeLogic extends MTEMegaBase.MegaRecipeLogic {

    public HeatingCoilMegaMultiblockRecipeLogic(MTEMegaBase metaTileEntity) {
        super(metaTileEntity);

        if (!(metaTileEntity instanceof IHeatingCoil)) {
            throw new IllegalArgumentException("MetaTileEntity must be instanceof IHeatingCoil");
        }
    }

    @Override
    protected void modifyOverclockPre(int @NotNull [] values, @NotNull IRecipePropertyStorage storage) {
        super.modifyOverclockPre(values, storage);
        // coil EU/t discount
        values[0] = OverclockingLogic.applyCoilEUtDiscount(values[0],
                ((IHeatingCoil) metaTileEntity).getCurrentTemperature(),
                storage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
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
