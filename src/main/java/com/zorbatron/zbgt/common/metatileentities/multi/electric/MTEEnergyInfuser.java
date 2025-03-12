package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

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
import gregtech.common.blocks.BlockWarningSign;
import gregtech.common.blocks.BlockWireCoil;
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
        // todo: replace with the actual coils
        return MetaBlocks.WIRE_COIL.getState(BlockWireCoil.CoilType.CUPRONICKEL);
    }

    protected IBlockState getMolecularCasingState() {
        // todo: replace with the actual molecular casings
        return MetaBlocks.WARNING_SIGN.getState(BlockWarningSign.SignType.ANTIMATTER_HAZARD);
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
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        // todo: replace with molecular casing
        return Textures.HIGH_POWER_CASING;
    }
}
