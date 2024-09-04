package com.zorbatron.zbgt.api.capability.impl;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;

public class ZBGT_GCYMMultiblockRecipeLogic extends GCYMMultiblockRecipeLogic {

    public ZBGT_GCYMMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity, boolean hasPerfectOC) {
        super(tileEntity);
        this.hasPerfectOC = hasPerfectOC;
    }
}
