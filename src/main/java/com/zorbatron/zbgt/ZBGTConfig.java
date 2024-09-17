package com.zorbatron.zbgt;

import net.minecraftforge.common.config.Config;

@Config(modid = ZBGTCore.MODID)
public class ZBGTConfig {

    @Config.Name("Multiblock Settings")
    public static MultiblockSettings multiblockSettings = new MultiblockSettings();

    public static class MultiblockSettings {

        @Config.Comment({
                "Not all of my multiblocks may allow substation energy hatches, but this is a global toggle.",
                "If false, no multiblock will work with substation hatches.",
                "Default: false" })
        @Config.RequiresMcRestart
        @Config.Name("Allow Substation Hatches")
        public boolean allowSubstationHatches = false;

        @Config.Comment({ "Let YOTTanks play their \"whoomp\" \"whoomp\" noise",
                "Default: false" })
        @Config.Name("YOTTank Sounds")
        public boolean yottankSound = false;
    }

    @Config.Name("Recipe Settings")
    @Config.RequiresMcRestart
    public static RecipeSettings recipeSettings = new RecipeSettings();

    public static class RecipeSettings {

        @Config.Comment({ "How silly are you?",
                "Default: true" })
        @Config.Name("Silly Recipes")
        public boolean enableSillyRecipes = true;

        @Config.Comment({ "Enable recipes for the large parallel hatches.",
                "Default: true" })
        @Config.Name("Parallel Hatch Recipes")
        public boolean enableParallelHatchRecipes = true;
    }
}
