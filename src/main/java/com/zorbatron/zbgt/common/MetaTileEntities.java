package com.zorbatron.zbgt.common;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.MetaTileEntityCreativeEnergyHatch;

public class MetaTileEntities {

    public static MetaTileEntityCreativeEnergyHatch CREATIVE_ENERGY_HATCH;

    public static void init() {
        CREATIVE_ENERGY_HATCH = registerMetaTileEntity(18000, new MetaTileEntityCreativeEnergyHatch());
    }
}
