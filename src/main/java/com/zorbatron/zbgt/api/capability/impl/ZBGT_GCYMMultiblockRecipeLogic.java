package com.zorbatron.zbgt.api.capability.impl;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregicality.multiblocks.api.metatileentity.GCYMMultiblockAbility;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.common.GCYMConfigHolder;
import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.ITieredMetaTileEntity;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;

// I need to copy GCYMMultiblockRecipeLogic because it doesn't have a constructor for hasPerfectOC
public class ZBGT_GCYMMultiblockRecipeLogic extends MultiblockRecipeLogic {

    public ZBGT_GCYMMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    public ZBGT_GCYMMultiblockRecipeLogic(RecipeMapMultiblockController tileEntity, boolean hasPerfectOC) {
        super(tileEntity, hasPerfectOC);
    }

    @Override
    public int getParallelLimit() {
        if (metaTileEntity instanceof IParallelMultiblock && ((IParallelMultiblock) metaTileEntity).isParallel())
            return ((IParallelMultiblock) metaTileEntity).getMaxParallel();

        return 1;
    }

    @Override
    public @NotNull RecipeMapMultiblockController getMetaTileEntity() {
        return (RecipeMapMultiblockController) super.getMetaTileEntity();
    }

    @Override
    public long getMaxVoltage() {
        if (!GCYMConfigHolder.globalMultiblocks.enableTieredCasings)
            return super.getMaxVoltage();

        if (getMetaTileEntity() instanceof GCYMRecipeMapMultiblockController controller && !controller.isTiered())
            return super.getMaxVoltage();

        List<ITieredMetaTileEntity> list = getMetaTileEntity().getAbilities(GCYMMultiblockAbility.TIERED_HATCH);

        if (list.isEmpty())
            return super.getMaxVoltage();

        return Math.min(GTValues.V[list.get(0).getTier()], super.getMaxVoltage());
    }
}
