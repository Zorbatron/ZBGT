package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import gregtech.api.metatileentity.multiblock.CleanroomType;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityCleaningMaintenanceHatch;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntitySterileCleaningHatch extends MetaTileEntityCleaningMaintenanceHatch {

    static {
        CLEANED_TYPES.add(CleanroomType.STERILE_CLEANROOM);
    }

    public MetaTileEntitySterileCleaningHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
}
