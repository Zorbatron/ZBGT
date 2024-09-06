package com.zorbatron.zbgt.api;

import net.minecraft.block.state.IBlockState;

import com.zorbatron.zbgt.api.block.ICoALTier;
import com.zorbatron.zbgt.api.block.IPreciseTier;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class ZBGTAPI {

    public static final Object2ObjectMap<IBlockState, ICoALTier> CoAL_CASINGS = new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectMap<IBlockState, IPreciseTier> PRECISE_CASINGS = new Object2ObjectOpenHashMap<>();
}
