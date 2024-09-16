package com.zorbatron.zbgt.core.sound;

import net.minecraft.util.SoundEvent;

import gregtech.api.GregTechAPI;

public class ZBGTSoundEvents {

    public static SoundEvent FX_LOW_FREQ;

    public static void register() {
        FX_LOW_FREQ = GregTechAPI.soundManager.registerSound("fx_lo_freq");
    }
}
