package com.zorbatron.zbgt.common.metatileentities.multi.electric.large;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.client.renderer.ICubeRenderer;

public class MetaTileEntityLargeAlloySmelter extends GCYMRecipeMapMultiblockController {

    private int coilTier;

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
                .aisle("XXX", "CCC", "VVV", "CCC", "XXX")
                .aisle("XXX", "C#C", "V#V", "C#C", "XXX")
                .aisle("XSX", "CCC", "VVV", "CCC", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(10)
                        .or(autoAbilities()))
                .where('C', heatingCoils())
                .where('V', states(getVentState()))
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.INCONEL_REINFORCED_CASING);
    }

    protected IBlockState getVentState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.INCONEL_REINFORCED_CASING;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class LargeAlloySmelterRecipeLogic extends MultiblockRecipeLogic {

        public LargeAlloySmelterRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void modifyOverclockPre(int @NotNull [] values, @NotNull IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);

            values[1] = (int) (values[1] * Math.pow(0.95, coilTier));
        }
    }
}
