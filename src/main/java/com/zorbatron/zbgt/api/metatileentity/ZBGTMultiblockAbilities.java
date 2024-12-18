package com.zorbatron.zbgt.api.metatileentity;

import net.minecraftforge.fluids.IFluidTank;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

@SuppressWarnings("InstantiationOfUtilityClass")
public class ZBGTMultiblockAbilities {

    public static final MultiblockAbility<IFluidTank> PYROTHEUM_HATCH = new MultiblockAbility<>("pyrotheum_hatch");
    public static final MultiblockAbility<IFluidTank> CRYOTHEUM_HATCH = new MultiblockAbility<>("cryotheum_hatch");
}
