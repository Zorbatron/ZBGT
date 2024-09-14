package com.zorbatron.zbgt.common.metatileentities.storage;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.render.ZBGTTextures;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;

public class MetaTileEntityYOTTank extends MultiblockWithDisplayBase {

    public MetaTileEntityYOTTank(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityYOTTank(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {}

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("S")
                .where('S', selfPredicate())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.YOTTANK_CASING;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return ZBGTTextures.SWIRLY_INFINITY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }
}
