package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class MetaTileEntityCreativeEnergySink extends MetaTileEntityCreativeEnergyHatch {

    public MetaTileEntityCreativeEnergySink(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        setIsSource(false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCreativeEnergySink(metaTileEntityId);
    }

    @Override
    @NotNull
    protected SimpleOverlayRenderer getOverlay() {
        return Textures.ENERGY_OUT_MULTI;
    }

    @Override
    public MultiblockAbility<IEnergyContainer> getAbility() {
        return MultiblockAbility.OUTPUT_ENERGY;
    }
}
