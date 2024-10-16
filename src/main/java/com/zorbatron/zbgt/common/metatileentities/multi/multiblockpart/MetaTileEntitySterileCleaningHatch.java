package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import net.minecraft.util.ResourceLocation;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityCleaningMaintenanceHatch;

public class MetaTileEntitySterileCleaningHatch extends MetaTileEntityCleaningMaintenanceHatch {

    static {
        CLEANED_TYPES.add(CleanroomType.STERILE_CLEANROOM);
    }

    public MetaTileEntitySterileCleaningHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
}
