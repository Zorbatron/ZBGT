package com.zorbatron.zbgt.common.metatileentities.multi.electric.mega;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.metatileentity.LaserCapableGCYMRecipeMapMultiblockController;
import com.zorbatron.zbgt.api.render.ZBGTTextures;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

public class MetaTileEntityMegaVF extends LaserCapableGCYMRecipeMapMultiblockController {

    public MetaTileEntityMegaVF(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.VACUUM_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMegaVF(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX",
                        "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX",
                        "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "X#############X",
                        "X#############X", "X#############X", "X#############X", "X#############X", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX",
                        "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXSXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX",
                        "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXX")
                .where('#', air())
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(1150)
                        .or(autoAbilities(false, true, true, true, true, true, false))
                        .or(autoEnergyInputsMega()))
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.FROST_PROOF_CASING;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return ZBGTTextures.GTPP_MACHINE_OVERLAY;
    }
}
