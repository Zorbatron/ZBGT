package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static gregtech.api.util.RelativeDirection.*;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;

public class MetaTileEntityCircuitAssemblyLine extends MultiMapMultiblockController {

    public MetaTileEntityCircuitAssemblyLine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new gregtech.api.recipes.RecipeMap[] { ZBGTRecipeMaps.CIRCUIT_ASSEMBLY_LINE_RECIPES,
                RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES });
        this.recipeMapWorkable = new MultiblockRecipeLogic(this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCircuitAssemblyLine(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(FRONT, UP, RIGHT)
                .aisle("FIF", "RTR", "SGG")
                .aisle("FIF", "RTR", "GGG").setRepeatable(3, 7)
                .aisle("FOF", "RTR", "GGG")
                .where('S', selfPredicate())
                .where('G', states(getGrateState())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1)))
                .where('R', states(getGlassState()))
                .where('T', states(getAssemblyCasingState()))
                .where('F', states(getCasingState()))
                .where('I', abilities(MultiblockAbility.IMPORT_ITEMS))
                .where('O', abilities(MultiblockAbility.EXPORT_ITEMS))
                .where('F', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1)))
                .build();
    }

    protected IBlockState getGrateState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }

    protected IBlockState getAssemblyCasingState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_LINE_CASING);
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }
}
