package com.zorbatron.zbgt.common.metatileentities.multi.electric.gcym;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;

public class MetaTileEntityLargeRockBreaker extends GCYMRecipeMapMultiblockController {

    public MetaTileEntityLargeRockBreaker(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.ROCK_BREAKER_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityLargeRockBreaker(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("TTTTT   TTSTT", "TTTTG   F   F", "F   TT  FTTTF", "F   FTT FLLLF", "FHHHF TTTTTTT",
                        "FHHHF  TGTTTT", "FHHHF   TTTTT")
                .where('S', selfPredicate())
                .where('T', states(getStressProofCasingState()))
                .where('G', states(getGearBoxState()))
                .where('L', states(getGlassState()))
                .where('H', states(getHeatProofCasingState()))
                .where('F', frames(getFrameMaterial()))
                .where(' ', any())
                .build();
    }

    protected IBlockState getStressProofCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(
                BlockLargeMultiblockCasing.CasingType.STRESS_PROOF_CASING);
    }

    protected IBlockState getGearBoxState() {
        return MetaBlocks.TURBINE_CASING.getState(
                BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(
                BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }

    protected IBlockState getHeatProofCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(
                BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }

    protected Material getFrameMaterial() {
        return Materials.Steel;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GCYMTextures.STRESS_PROOF_CASING;
    }
}
