package com.zorbatron.zbgt.recipe.chemplant;

import static com.zorbatron.zbgt.common.items.ZBGTCatalystItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.Material;

public class CatalystRecipes {

    public static void init() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(plate, Steel, 8)
                .input(wireFine, Copper, 4)
                .input(screw, Tin, 6)
                .circuitMeta(20)
                .output(EMPTY_CATALYST)
                .EUt(VA[LV]).duration(20 * 15)
                .buildAndRegister();

        registerCatalystCarrierRecipe(GREEN_CATALYST, Aluminium, 4, Silver, 4, VA[LV], 20);
        registerCatalystCarrierRecipe(RED_CATALYST, Iron, 2, Copper, 2, VA[LV], 20);
        registerCatalystCarrierRecipe(YELLOW_CATALYST, Tungsten, 4, Nickel, 4, VA[EV], 60);
        registerCatalystCarrierRecipe(BLUE_CATALYST, Cobalt, 3, Titanium, 3, VA[HV], 40);
        registerCatalystCarrierRecipe(ORANGE_CATALYST, Vanadium, 5, Palladium, 5, VA[HV], 40);
        registerCatalystCarrierRecipe(PURPLE_CATALYST, Iridium, 6, Ruthenium, 6, VA[IV], 120);
        registerCatalystCarrierRecipe(BROWN_CATALYST, Nickel, 4, Aluminium, 4, VA[LV], 15);
        registerCatalystCarrierRecipe(PINK_CATALYST, Platinum, 4, Rhodium, 4, VA[EV], 30);
    }

    private static void registerCatalystCarrierRecipe(MetaItem<?>.MetaValueItem outputCatalyst, Material material1,
                                                      int materialAmount1, Material material2, int materialAmount2,
                                                      int EUt, int seconds) {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(EMPTY_CATALYST, 10)
                .input(dust, material1, materialAmount1)
                .input(dust, material2, materialAmount2)
                .circuitMeta(20)
                .output(outputCatalyst, 10)
                .EUt(EUt).duration(20 * seconds)
                .buildAndRegister();
    }
}
