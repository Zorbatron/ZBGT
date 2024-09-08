package com.zorbatron.zbgt;

import net.minecraftforge.common.config.Config;

@Config(modid = ZBGTCore.MODID)
public class ZBGTConfig {

    public static MultiblockSettings multiblockSettings = new MultiblockSettings();

    public static class MultiblockSettings {

        @Config.Comment({ "Not all of my multiblocks may allow substation energy hatches, " +
                "but this is a global toggle. If false, no multiblock will work with substation hatches.",
                "Default: false" })
        @Config.RequiresMcRestart
        @Config.Name("allowSubstationHatches")
        public boolean allowSubstationHatches = false;
    }

    public static MiscSettings miscSettings = new MiscSettings();

    public static class MiscSettings {

        @Config.Comment({ "How silly are you?",
                "Default: true" })
        @Config.RequiresMcRestart
        @Config.Name("sillyRecipes")
        public boolean enableSillyRecipes = true;
    }
}
