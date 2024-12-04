package com.zorbatron.zbgt.common.metatileentities.multi.electric.large;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.*;

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
                .aisle("TTTTT   OOOOO", "TTTTG   F   F", "F   TT  FTTTF", "F   FTT FLLLF", "FHHHF TTTTTTT",
                        "FHHHF  TGTTTT", "FHHHF   TTTTT", "             ")
                .aisle("TPPPT   OPPPO", "T   T        ", "     T  TRRRT", "  P   T L   L", "HPBPH  TTMMMT",
                        "H   H   T   T", "H   H       T", " HHH         ")
                .aisle("TPPPT   OPPPO", "T   P        ", "     P  TRRRT", " P P  P L   L", "HB BH  PTMMMT",
                        "H   H   P   T", "H   H       T", " HIH         ")
                .aisle("TPPPT   OPPPO", "T   T        ", "     T  TRRRT", "  P   T L   L", "HPBPH  TTMMMT",
                        "H   H   T   T", "H   H       T", " HHH         ")
                .aisle("TTTTT   OOSOO", "TTTTG   F   F", "F   TT  FTTTF", "F   FTT FLLLF", "FHHHF TTTTTTT",
                        "FHHHF  TGTTTT", "FHHHF   TTTTT", "             ")
                .where('S', selfPredicate())
                .where('T', states(getStressProofCasingState()))
                .where('G', states(getGearBoxState()))
                .where('L', states(getGlassState()))
                .where('H', states(getHeatProofCasingState()))
                .where('F', frames(getFrameMaterial()))
                .where('R', states(getGrateState()))
                .where('P', states(getPipeCasingState()))
                .where('B', states(getFireBoxState()))
                .where('M', states(getCrushingWheelState()))
                .where('I', states(getIntakeState()))
                .where('O', states(getStressProofCasingState())
                        .or(autoAbilities(true, true, true, true, true, true, false)))
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

    protected IBlockState getGrateState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(
                BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }

    protected IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(
                BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE);
    }

    protected IBlockState getFireBoxState() {
        return MetaBlocks.BOILER_FIREBOX_CASING.getState(
                BlockFireboxCasing.FireboxCasingType.TUNGSTENSTEEL_FIREBOX);
    }

    protected IBlockState getCrushingWheelState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(
                BlockUniqueCasing.UniqueCasingType.CRUSHING_WHEELS);
    }

    protected IBlockState getIntakeState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(
                BlockMultiblockCasing.MultiblockCasingType.EXTREME_ENGINE_INTAKE_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GCYMTextures.STRESS_PROOF_CASING;
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
