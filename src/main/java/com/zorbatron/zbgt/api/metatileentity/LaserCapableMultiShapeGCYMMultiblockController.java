package com.zorbatron.zbgt.api.metatileentity;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.recipes.RecipeMap;

// Copied from M-W-K's LFF branch of GCYM
public abstract class LaserCapableMultiShapeGCYMMultiblockController extends
                                                                     LaserCapableGCYMRecipeMapMultiblockController {

    public LaserCapableMultiShapeGCYMMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMap) {
        super(metaTileEntityId, recipeMap);
    }

    public LaserCapableMultiShapeGCYMMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMap,
                                                          boolean allowSubstationHatches) {
        super(metaTileEntityId, recipeMap, allowSubstationHatches);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        if (this.getWorld() != null && this.getPos() != null) {
            if (this.getWorld().getTileEntity(this.getPos()) instanceof IGregTechTileEntity gtte) {
                if (gtte.getMetaTileEntity() instanceof LaserCapableMultiShapeGCYMMultiblockController controller) {
                    return getStructurePattern(controller.getRecipeMapIndex());
                }
            }
        }
        return getStructurePattern(0);
    }

    protected abstract @NotNull BlockPattern getStructurePattern(int index);

    @Override
    public void checkStructurePattern() {
        if (!this.isStructureFormed()) {
            this.reinitializeStructurePattern();
        }
        super.checkStructurePattern();
    }

    @Override
    public void setRecipeMapIndex(int index) {
        super.setRecipeMapIndex(index);
        this.reinitializeStructurePattern();
    }
}
