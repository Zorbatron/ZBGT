package com.zorbatron.zbgt.api.metatileentity;

import com.zorbatron.zbgt.api.capability.ICatalystProvider;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

@SuppressWarnings("InstantiationOfUtilityClass")
public class ZBGTMultiblockAbility<T> {

    public static final MultiblockAbility<ICatalystProvider> CATALYST_PROVIDER = new MultiblockAbility<>(
            "catalyst_provider");
}
