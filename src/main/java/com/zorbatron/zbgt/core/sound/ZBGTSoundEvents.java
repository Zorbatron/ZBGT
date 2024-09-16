package com.zorbatron.zbgt.core.sound;

import net.minecraft.util.SoundEvent;

import com.zorbatron.zbgt.api.ZBGTAPI;

public class ZBGTSoundEvents {

    public static SoundEvent FX_LOW_FREQ;

    public static void register() {
        FX_LOW_FREQ = ZBGTAPI.soundManager.registerSound("fx_lo_freq");
    }
}
