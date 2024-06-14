package com.zorbatron.zbgt;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

public class ZBUtility {

    public static @NotNull ResourceLocation zbgtId(@NotNull String path) {
        return new ResourceLocation("zbgt", path);
    }
}
