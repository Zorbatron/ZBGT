package com.zorbatron.zbgt.common;

import net.minecraftforge.common.config.Config;

import com.zorbatron.zbgt.ZBGTCore;

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
                "Default: true" })
        @Config.RequiresWorldRestart
        @Config.Name("YOTTank Sounds")
        public boolean yottankSound = true;

        @Config.Comment({ "Let Quackers quack",
                "Default: true" })
        @Config.RequiresWorldRestart
        @Config.Name("Quacker Quacks")
        public boolean quackerQuacks = true;

        @Config.Comment({ "Override the PSS battery section count.",
                "Default: false" })
        @Config.Name("Override PSS Height")
        public boolean overridePSSHeight = false;

        @Config.Comment({ "Maximum layer count of batteries in PSSs.",
                "Does nothing if \"Override PSS Height\" is false",
                "Default: 18" })
        @Config.RangeInt(min = 1, max = 255)
        @Config.Name("Overridden PSS Height")
        public int overriddenPSSHeight = 18;

        @Config.RequiresMcRestart
        @Config.Comment({
                "Require mega multiblocks to have muffler hatches.",
                "Due to how muffler hatches are implemented, it rolls the byproduct chance per parallel.",
                "So if you have 1 million parallels, its going to loop a randomizer function 1 million times which is quite bad for TPS.",
                "Default: true" })
        @Config.Name("Megas Need Mufflers")
        public boolean megasNeedMufflers = true;
    }

    @Config.Name("Recipe Settings")
    @Config.RequiresMcRestart
    public static RecipeSettings recipeSettings = new RecipeSettings();

    public static class RecipeSettings {

        @Config.Comment({ "Enable recipes for the large parallel hatches.",
                "Default: true" })
        @Config.Name("Parallel Hatch Recipes")
        public boolean enableParallelHatchRecipes = true;
    }

    @Config.Name("World Generation Settings")
    @Config.RequiresMcRestart
    public static WorldGenerationSettings worldGenerationSettings = new WorldGenerationSettings();

    public static class WorldGenerationSettings {

        @Config.Comment({ "Enable ore vein generation",
                "Default: true" })
        @Config.Name("Enable ore vein generation")
        public boolean enableOreGeneration = true;
    }

    public static CompatibilitySettings compatibilitySettings = new CompatibilitySettings();

    public static class CompatibilitySettings {

        @Config.Comment({ "Force disable Nomi Labs compatibility",
                "Default: false" })
        @Config.Name("Force disable Nomifactory compat")
        public boolean disableNomiLabsCompatibility = false;
    }

    @Config.Name("Creative Coil Settings")
    @Config.RequiresMcRestart
    public static CreativeCoilSettings creativeCoilSettings = new CreativeCoilSettings();

    public static class CreativeCoilSettings {

        @Config.Comment({ "Default: 1" })
        @Config.Name("Coil Temperature")
        public int temperature = 1;

        @Config.Comment({ "Default: 1" })
        @Config.Name("Coil Level")
        public int level = 1;

        @Config.Comment({ "Default: 1" })
        @Config.Name("Energy Discount")
        public int energyDiscount = 1;
    }
}
