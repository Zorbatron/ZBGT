package com.zorbatron.zbgt.api;

import net.minecraft.block.state.IBlockState;

import com.zorbatron.zbgt.api.block.IComponentALTier;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class ZBGTAPI {

    public static final Object2ObjectMap<IBlockState, IComponentALTier> COMPONENT_AL_CASINGS = new Object2ObjectOpenHashMap<>();
}
