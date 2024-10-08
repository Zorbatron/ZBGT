package com.zorbatron.zbgt.common.metatileentities.multi.electric.large;

import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;

public class MetaTileEntityLargeAlloySmelter extends GCYMRecipeMapMultiblockController implements IHeatingCoil {

    private int temperature;

    public MetaTileEntityLargeAlloySmelter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.ALLOY_SMELTER_RECIPES);
        this.recipeMapWorkable = new LargeAlloySmelterRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeAlloySmelter(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .build();
    }

    @Override
    public int getCurrentTemperature() {
        return this.temperature;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GCYMTextures.BLAST_CASING;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class LargeAlloySmelterRecipeLogic extends MultiblockRecipeLogic {

        public LargeAlloySmelterRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void modifyOverclockPre(int @NotNull [] values, @NotNull IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);


        }
    }
}
