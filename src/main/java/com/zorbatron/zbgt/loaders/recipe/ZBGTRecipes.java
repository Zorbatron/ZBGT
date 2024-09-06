package com.zorbatron.zbgt.loaders.recipe;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

public class ZBGTRecipes {

    public static final Material[] tierMaterials = { Materials.WroughtIron, Materials.Steel, Materials.Aluminium,
            Materials.StainlessSteel, Materials.Titanium, Materials.TungstenSteel, Materials.RhodiumPlatedPalladium,
            Materials.NaquadahAlloy, Materials.Darmstadtium, Materials.Neutronium };

    public static void init() {
        MultiblockRecipes.init();
        MultiblockPartRecipes.init();
        CoverRecipes.init();
        CoALRecipes.init();
    }
}
