package com.zorbatron.zbgt.common.metatileentities.multi.electric.large;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.GasCollectorDimensionProperty;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.*;
import it.unimi.dsi.fastutil.ints.IntLists;

public class MTELargeAirCollector extends GCYMRecipeMapMultiblockController {

    public MTELargeAirCollector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.GAS_COLLECTOR_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTELargeAirCollector(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.UP)
                .aisle(" XSX ", "XIXIX", "XXXXX", "XIXIX", " XXX ")
                .aisle(" LGL ", "L I L", "GIPIG", "L I L", " LGL ")
                .aisle(" LGL ", "L I L", "GIPIG", "L I L", " LGL ")
                .aisle(" LGL ", "L I L", "GIPIG", "L I L", " LGL ")
                .aisle(" LGL ", "L I L", "GIPIG", "L I L", " LGL ")
                .aisle(" LGL ", "L I L", "GIPIG", "L I L", " LGL ")
                .aisle(" XXX ", "XIXIX", "XXPXX", "XIXIX", " XXX ")
                .where('S', selfPredicate())
                .where('X', states(getCasingState())
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('I', states(getIntakeState()))
                .where('L', states(getGlassState()))
                .where('G', states(getGrateState()))
                .where('P', states(getPipeCasingState()))
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.TURBINE_CASING.getState(
                BlockTurbineCasing.TurbineCasingType.TUNGSTENSTEEL_TURBINE_CASING);
    }

    protected IBlockState getIntakeState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(
                BlockMultiblockCasing.MultiblockCasingType.EXTREME_ENGINE_INTAKE_CASING);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(
                BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    protected IBlockState getGrateState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(
                BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }

    protected IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(
                BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.ROBUST_TUNGSTENSTEEL_CASING;
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        int currentDim = getWorld().provider.getDimension();

        for (int recipeDim : recipe.getProperty(GasCollectorDimensionProperty.getInstance(), IntLists.EMPTY_LIST)) {
            if (recipeDim == currentDim) return super.checkRecipe(recipe, consumeIfSuccess);
        }

        return false;
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
