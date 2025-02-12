package com.zorbatron.zbgt.api;

import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.FluidStack;

import com.zorbatron.zbgt.api.util.ZBGTMods;
import com.zorbatron.zbgt.common.ZBGTConfig;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;
import com.zorbatron.zbgt.common.block.blocks.YOTTankCell;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;

import gregtech.api.util.BaseCreativeTab;
import gregtech.common.blocks.BlockMachineCasing;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class ZBGTAPI {

    public static final Object2ObjectMap<IBlockState, CoALCasing.CasingType> CoAL_CASINGS = new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectMap<IBlockState, PreciseCasing.CasingType> PRECISE_CASINGS = new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectMap<IBlockState, BlockMachineCasing.MachineCasingType> MACHINE_CASINGS = new Object2ObjectOpenHashMap<>();

    public static final Object2ObjectMap<IBlockState, YOTTankCell.CasingType> YOTTANK_CELLS = new Object2ObjectOpenHashMap<>();

    public static final boolean nomiLabsCompat = ZBGTMods.NOMI_LABS.isModLoaded() &&
            !ZBGTConfig.compatibilitySettings.disableNomiLabsCompatibility;

    public static final BaseCreativeTab TAB_ZBGT = new BaseCreativeTab("ZBGT",
            () -> ZBGTMetaItems.ZBGT_ITEM.getStackForm(), false);

    public static FluidStack pyrotheum;
    public static FluidStack cryotheum;
}
