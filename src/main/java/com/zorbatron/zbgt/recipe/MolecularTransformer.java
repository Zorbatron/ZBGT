package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.MOLECULAR_TRANSFORMER;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;

import gregtech.api.unification.material.Material;

public class MolecularTransformer {

    public static void init() {
        dustToDust(NetherQuartz, CertusQuartz, IV, 33);
        dustToDust(CertusQuartz, NetherQuartz, IV, 33);
        dustToDust(Redstone, Ruby, IV, 326);
        dustToDust(Copper, Nickel, IV, 326);
        dustToDust(Tin, Silver, IV, 326);
        dustToDust(GarnetRed, GarnetYellow, IV, 326);
        dustToDust(GarnetYellow, GarnetRed, IV, 326);
        dustToDust(Silver, Gold, IV, 326);
        dustToDust(Carbon, Graphene, IV, 652);
        dustToDust(Gold, Platinum, IV, 5209);
    }

    @SuppressWarnings("SameParameterValue")
    private static void dustToDust(Material from, Material to, int tier, int duration) {
        MOLECULAR_TRANSFORMER.recipeBuilder()
                .input(dust, from)
                .output(dust, to)
                .EUt(VA[tier]).duration(duration)
                .buildAndRegister();
    }
}
