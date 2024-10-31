package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.render.ZBGTTextures;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;

public class MetaTileEntityNanoForge extends RecipeMapMultiblockController {

    private int tier;

    public MetaTileEntityNanoForge(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, ZBGTRecipeMaps.NANO_FORGE_RECIPES);
        tier = 0;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return null;
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return switch (tier) {
            case (2) -> FactoryBlockPattern.start()
                    .build();
            case (3) -> FactoryBlockPattern.start()
                    .build();
            default -> FactoryBlockPattern.start()
                    .build();
        };
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.RADIANT_NAQUADAH_CASING;
    }
}
