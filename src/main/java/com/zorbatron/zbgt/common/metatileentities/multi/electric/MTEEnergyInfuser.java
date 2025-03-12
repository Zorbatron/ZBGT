package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

public class MTEEnergyInfuser extends MultiblockWithDisplayBase {

    public MTEEnergyInfuser(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTEEnergyInfuser(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {}

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("HHH", "CCC", "MMM", "CCC", "HHH")
                .aisle("HHH", "CMC", "MMM", "CMC", "HHH")
                .aisle("HHH", "CCC", "MSM", "CCC", "HHH")
                .where('S', selfPredicate())
                .where('H', states(getCasingState())
                        .setMinGlobalLimited(9)
                        .or(TraceabilityPredicates.inputBusesNormal()
                                .setExactLimit(1))
                        .or(TraceabilityPredicates.outputBusesNormal()
                                .setMaxGlobalLimited(1)
                                .setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY, MultiblockAbility.SUBSTATION_INPUT_ENERGY,
                                MultiblockAbility.INPUT_LASER)
                                        .setMinGlobalLimited(1)))
                .where('C', states(getCoilState()))
                .where('M', states(getMolecularCasingState()))
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.COMPUTER_CASING.getState(BlockComputerCasing.CasingType.HIGH_POWER_CASING);
    }

    protected IBlockState getCoilState() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.MOLECULAR_COIL);
    }

    protected IBlockState getMolecularCasingState() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.MOLECULAR_CASING);
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> shapeInfoList = new ArrayList<>();

        shapeInfoList.add(MultiblockShapeInfo.builder()
                .aisle("HHH", "CCC", "MMM", "CCC", "HHH")
                .aisle("HHH", "CMC", "MMM", "CMC", "HHH")
                .aisle("IEO", "CCC", "MSM", "CCC", "HHH")
                .where('S', ZBGTMetaTileEntities.ENERGY_INFUSER, EnumFacing.SOUTH)
                .where('H', getCasingState())
                .where('C', getCoilState())
                .where('M', getMolecularCasingState())
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.IV], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.LV], EnumFacing.SOUTH)
                .build());

        return shapeInfoList;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), false, false);
    }

    @Override
    public ICubeRenderer getBaseTexture(@Nullable IMultiblockPart sourcePart) {
        if (sourcePart == null) {
            // part is null when it's the controller
            return ZBGTTextures.MOLECULAR_CASING;
        } else {
            return Textures.HIGH_POWER_CASING;
        }
    }

    @Override
    protected @NotNull ICubeRenderer getFrontOverlay() {
        return Textures.DATA_BANK_OVERLAY;
    }
}
