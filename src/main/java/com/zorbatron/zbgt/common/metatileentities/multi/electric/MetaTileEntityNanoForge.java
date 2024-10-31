package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.RelativeDirection;
import net.minecraft.block.state.IBlockState;
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

import java.util.ArrayList;
import java.util.List;

public class MetaTileEntityNanoForge extends RecipeMapMultiblockController {

    private int tier;

    public MetaTileEntityNanoForge(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, ZBGTRecipeMaps.NANO_FORGE_RECIPES);
        tier = 0;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNanoForge(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.DOWN)
                .aisle("         ","         ","    F    ","    C    ","    C    ","    C    ","    C    ","    F    ","         ","         ")
                .aisle("         ","         ","    F    ","    C    ","    C    ","    C    ","    C    ","    F    ","         ","         ")
                .aisle("         ","         ","    F    ","    C    ","    C    ","    C    ","    C    ","    F    ","         ","         ")
                .aisle("         ","         ","    F    ","    C    ","    C    ","    C    ","    C    ","    F    ","         ","         ")
                .aisle("         ","         ","    F    ","    C    ","    C    ","    C    ","    C    ","    F    ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","   FCF   ","  FC CF  ","   C C   ","   C C   ","   C C   ","   C C   ","  FC CF  ","   FCF   ","         ")
                .aisle("         ","   FCF   ","  FC CF  ","   C C   ","   C C   ","   C C   ","   C C   ","  FC CF  ","   FCF   ","         ")
                .aisle("         ","   FCF   ","  FC CF  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  FC CF  ","   FCF   ","         ")
                .aisle("         ","   FCF   ","  FC CF  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  FC CF  ","   FCF   ","         ")
                .aisle("    C    ","   FCF   ","  CC CC  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  CC CC  ","   FCF   ","    C    ")
                .aisle("    C    ","   FCF   ","  CC CC  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  CC CC  ","   FCF   ","    C    ")
                .aisle("    C    ","   FCF   ","  CC CC  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  CC CC  ","   FCF   ","    C    ")
                .aisle("    C    ","   FCF   ","  CC CC  "," CC   CC "," CC   CC "," CC   CC "," CC   CC ","  CC CC  ","   FCF   ","    C    ")
                .aisle("    C    ","   FCF   ","  CC CC  "," CC   CC "," CC   CC "," CC   CC "," CC   CC ","  CC CC  ","   FCF   ","    C    ")
                .aisle("    C    ","   FCF   ","  CC CC  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  CC CC  ","   FCF   ","    C    ")
                .aisle("    C    ","   FCF   ","  CC CC  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  CC CC  ","   FCF   ","    C    ")
                .aisle("    C    ","   FCF   ","  CC CC  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  CC CC  ","   FCF   ","    C    ")
                .aisle("         ","   FCF   ","  FC CF  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  FC CF  ","   FCF   ","         ")
                .aisle("         ","   FCF   ","  FC CF  ","  C   C  ","  C   C  ","  C   C  ","  C   C  ","  FC CF  ","   FCF   ","         ")
                .aisle("         ","   FCF   ","  FC CF  ","   C C   ","   C C   ","   C C   ","   C C   ","  FC CF  ","   FCF   ","         ")
                .aisle("         ","   FCF   ","  FC CF  ","   C C   ","   C C   ","   C C   ","   C C   ","  FC CF  ","   FCF   ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","         ","   FCF   ","   C C   ","   C C   ","   C C   ","   C C   ","   FCF   ","         ","         ")
                .aisle("         ","  BBSBB  "," BBBBBBB ","BBBBBBBBB","BBBBBBBBB","BBBBBBBBB","BBBBBBBBB"," BBBBBBB ","  BBBBB  ","         ")
                .where('S', selfPredicate())
                .where('B', states(getCasingState())
                        .or(autoAbilities(true, false)))
                .where('C', states(getCasingState()))
                .where('F', frames(Materials.Neutronium))
                .build();
    }

    public IBlockState getCasingState() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.RADIANT_NAQUADAH);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.RADIANT_NAQUADAH_CASING;
    }
}
