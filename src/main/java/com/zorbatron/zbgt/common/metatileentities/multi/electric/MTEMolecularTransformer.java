package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static gregtech.api.util.RelativeDirection.*;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.RandomGTPPCasings3;
import com.zorbatron.zbgt.common.block.blocks.TransparentBlock;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;

public class MTEMolecularTransformer extends RecipeMapMultiblockController {

    public MTEMolecularTransformer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, ZBGTRecipeMaps.MOLECULAR_TRANSFORMER);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTEMolecularTransformer(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                // spotless:off
                .aisle("   T   ", "   C   ", "   T   ", "       ", "       " , "       " , "       ")
                .aisle(" TTTTT ", " CCRCC ", " TTOTT ", "  OOO  ", "  OOO  " , "  OOO  " , "       ")
                .aisle(" TTTTT ", " CRMRC ", " THHHT ", " OHHHO ", " OHHHO " , " OHHHO " , "  OOO  ")
                .aisle("TTTTTTT", "CRMRMRC", "TOHPHOT", " OHPHO ", " OHPHO " , " OHPHO " , "  OOO  ")
                .aisle(" TTTTT ", " CRMRC ", " THHHT ", " OHHHO ", " OHHHO " , " OHHHO " , "  OOO  ")
                .aisle(" TTTTT ", " CCRCC ", " TTOTT ", "  OOO  ", "  OSO  " , "  OOO  " , "       ")
                .aisle("   T   ", "   C   ", "   T   ", "       ", "       " , "       " , "       ")
                // spotless:on
                .where('S', selfPredicate())
                .where('T',
                        states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST))
                                .or(autoAbilities(true, true, true, true, false, false, false)))
                .where('C', states(MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.RTM_ALLOY)))
                .where('R',
                        states(ZBGTMetaBlocks.GTPP_CASING_3
                                .getState(RandomGTPPCasings3.CasingType.RESONANCE_CHAMBER_1)))
                .where('M', states(ZBGTMetaBlocks.GTPP_CASING_3.getState(RandomGTPPCasings3.CasingType.MODULATOR_1)))
                .where('O',
                        states(ZBGTMetaBlocks.GTPP_CASING_3
                                .getState(RandomGTPPCasings3.CasingType.MOLECULAR_CONTAINMENT_CASING)))
                .where('P',
                        states(ZBGTMetaBlocks.TRANSPARENT_BLOCK
                                .getState(TransparentBlock.CasingType.PARTICLE_CONTAINMENT_CASING)))
                .where('H',
                        states(ZBGTMetaBlocks.GTPP_CASING_3
                                .getState(RandomGTPPCasings3.CasingType.HIGH_VOLTAGE_CURRENT_CAPACITOR)))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (sourcePart == null) {
            return ZBGTTextures.MOLECULAR_CONTAINMENT_CASING;
        } else {
            return Textures.ROBUST_TUNGSTENSTEEL_CASING;
        }
    }

    @Override
    protected @NotNull ICubeRenderer getFrontOverlay() {
        return ZBGTTextures.GTPP_MACHINE_OVERLAY;
    }
}
