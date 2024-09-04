package com.zorbatron.zbgt;

import net.minecraftforge.common.config.Config;

@Config(modid = ZBGTCore.MODID)
public class ZBGTConfig {

    public static MultiblockSettings multiblockSettings = new MultiblockSettings();

    public static class MultiblockSettings {

        @Config.Comment("Not all of my multiblocks may allow substation energy hatches, " +
                "but this is a global toggle. If false, no multiblock will work with substation hatches.")
        @Config.RequiresMcRestart
        @Config.Name("Allow Substation Hatches")
        public boolean allowSubstationHatches = false;
    }
}
