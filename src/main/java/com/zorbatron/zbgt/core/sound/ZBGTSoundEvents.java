package com.zorbatron.zbgt.core.sound;

import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;

import java.util.Arrays;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import gregtech.api.GregTechAPI;

public class ZBGTSoundEvents {

    public static SoundEvent FX_LOW_FREQ;

    public static List<SoundEvent> QUACKS;
    public static SoundEvent QUACK_1;
    public static SoundEvent QUACK_2;
    public static SoundEvent QUACK_3;
    public static SoundEvent QUACK_4;
    public static SoundEvent QUACK_5;

    public static void register() {
        FX_LOW_FREQ = registerSoundHelper(zbgtId("yottank_pulse"));

        QUACK_1 = registerSoundHelper(zbgtId("quack_1"));
        QUACK_2 = registerSoundHelper(zbgtId("quack_2"));
        QUACK_3 = registerSoundHelper(zbgtId("quack_3"));
        QUACK_4 = registerSoundHelper(zbgtId("quack_4"));
        QUACK_5 = registerSoundHelper(zbgtId("quack_5"));
        QUACKS = Arrays.asList(QUACK_1, QUACK_2, QUACK_3, QUACK_4, QUACK_5);
    }

    private static SoundEvent registerSoundHelper(ResourceLocation resourceLocation) {
        return GregTechAPI.soundManager.registerSound(resourceLocation.getNamespace(), resourceLocation.getPath());
    }
}
