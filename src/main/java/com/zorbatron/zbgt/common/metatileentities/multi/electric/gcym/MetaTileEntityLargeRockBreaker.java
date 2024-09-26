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
import gregtech.common.blocks.BlockMetalCasing;
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
                .aisle("TTTTT   TTSTT", "TTTMG   F   F", "F   MM  FVVVF", "F   FMM FLLLF", "FHHHF MMMMMMM",
                        "FHHHF  MGMMMM", "FHHHF   MMMMM")
                .where('S', selfPredicate())
                .where('T', states(getStressProofCasingState()))
                .where('M', states(getSecureMacerationCasingState()))
                .where('G', states(getGearBoxState()))
                .where('L', states(getGlassState()))
                .where('V', states(getVibrationSafeCasingState()))
                .where('H', states(getHeatProofCasingState()))
                .where('C', states(getCleanStainlessCasingState()))
                .where('F', frames(getFrameMaterial()))
                .where(' ', any())
                .build();
    }

    protected IBlockState getStressProofCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(
                BlockLargeMultiblockCasing.CasingType.STRESS_PROOF_CASING);
    }

    protected IBlockState getSecureMacerationCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(
                BlockLargeMultiblockCasing.CasingType.MACERATOR_CASING);
    }

    protected IBlockState getGearBoxState() {
        return MetaBlocks.TURBINE_CASING.getState(
                BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX);
    }

    protected IBlockState getVibrationSafeCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING.getState(
                BlockLargeMultiblockCasing.CasingType.VIBRATION_SAFE_CASING);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(
                BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }

    protected IBlockState getCleanStainlessCasingState() {
        return MetaBlocks.METAL_CASING.getState(
                BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
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
