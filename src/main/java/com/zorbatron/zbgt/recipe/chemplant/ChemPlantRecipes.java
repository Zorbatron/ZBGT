package com.zorbatron.zbgt.recipe.chemplant;

import static com.zorbatron.zbgt.common.items.ZBGTCatalystItems.ORANGE_CATALYST;
import static gregtech.api.GTValues.HV;
import static gregtech.api.GTValues.VA;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;

public class ChemPlantRecipes {

    public static void init() {
        ZBGTRecipeMaps.CHEM_PLANT_RECIPES.recipeBuilder()
                .input(dust, Copper)
                .input(ORANGE_CATALYST)
                .fluidInputs(NitricOxide.getFluid(2000))
                .output(dust, CupricOxide, 2)
                .fluidOutputs(DinitrogenTetroxide.getFluid(1000))
                .casingTier(4)
                .EUt(VA[HV]).duration(20 * 30)
                .buildAndRegister();
    }
}
