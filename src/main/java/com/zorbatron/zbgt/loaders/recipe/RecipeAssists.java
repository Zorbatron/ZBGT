package com.zorbatron.zbgt.loaders.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;

import gregtech.api.unification.material.Material;

public class RecipeAssists {

    public static Material getMaterialByTier(int tier) {
        return switch (tier) {
            case (LV)  -> Steel;
            case (MV)  -> Aluminium;
            case (HV)  -> StainlessSteel;
            case (EV)  -> Titanium;
            case (IV)  -> TungstenSteel;
            case (LuV) -> RhodiumPlatedPalladium;
            case (ZPM) -> NaquadahAlloy;
            case (UV)  -> Darmstadtium;
            case (UHV) -> Neutronium;
            default    -> WroughtIron;
        };
    }
}
