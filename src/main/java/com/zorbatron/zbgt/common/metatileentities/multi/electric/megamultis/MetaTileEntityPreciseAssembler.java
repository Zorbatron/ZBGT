package com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis;

import static com.zorbatron.zbgt.api.capability.ZBGTDataCodes.MULTIBLOCK_TIER_CHANGE_1;
import static com.zorbatron.zbgt.api.capability.ZBGTDataCodes.MULTIBLOCK_TIER_CHANGE_2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.metatileentity.LaserCapableMultiShapeGCYMMultiblockController;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.*;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

public class MetaTileEntityPreciseAssembler extends LaserCapableMultiShapeGCYMMultiblockController {

    private int preciseCasingTier = -1;
    private int machineCasingTier = -1;

    public MetaTileEntityPreciseAssembler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new gregtech.api.recipes.RecipeMap[] { RecipeMaps.ASSEMBLER_RECIPES });
        this.recipeMapWorkable = new GCYMMultiblockRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPreciseAssembler(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern getStructurePattern(int index) {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "X#######X", "X#######X", "X#######X", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXXSXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .where('S', selfPredicate())
                .where('H', getMachineHull())
                .where('X', getCasing().or(autoAbilities()))
                .where('F', frames(Materials.TungstenSteel))
                .where('G', states(getGlassState()))
                .where('#', air())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();

        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XXXXXXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "X#######X", "X#######X", "X#######X", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXESMXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .where('S', ZBGTMetaTileEntities.PRASS, EnumFacing.SOUTH)
                .where('G', MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS))
                .where('F', MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel))
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV], EnumFacing.SOUTH)
                .where('M', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState());

        ZBGTAPI.MACHINE_CASINGS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().ordinal()))
                .forEach(entry -> shapeInfo.add(builder
                        .where('H', entry.getKey())
                        .where('X',
                                ZBGTMetaBlocks.PRECISE_CASING.getState(
                                        PreciseCasing.CasingType.getCasingByTier(entry.getValue().ordinal())))
                        .build()));

        return shapeInfo;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        if (getPreciseCasingTier() == -1) {
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "zbgt.machine.precise_assembler.preciseCasingTier.error"));
                        } else {
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "zbgt.machine.precise_assembler.preciseCasingTier",
                                    getPreciseCasingTier()));
                        }
                    }
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    protected TraceabilityPredicate getCasing() {
        return TraceabilityPredicates.preciseCasings();
    }

    protected TraceabilityPredicate getMachineHull() {
        return TraceabilityPredicates.machineCasings();
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISPLAY;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return getPreciseCasingTier() == -1 ? ZBGTTextures.PRECISE_CASING_1 :
                ZBGTTextures.getPrassTextureByTier(getPreciseCasingTier());
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        Object preciseType = context.get("PreciseTier");
        if (preciseType instanceof PreciseCasing.CasingType preciseCasingType) {
            this.preciseCasingTier = preciseCasingType.ordinal();
        } else {
            this.preciseCasingTier = -1;
        }

        Object hullType = context.get("MachineCasingTier");
        if (hullType instanceof BlockMachineCasing.MachineCasingType machineCasingType) {
            this.machineCasingTier = machineCasingType.ordinal();
        }

        writeCustomData(MULTIBLOCK_TIER_CHANGE_1, buf -> buf.writeInt(this.preciseCasingTier));
        writeCustomData(MULTIBLOCK_TIER_CHANGE_2, buf -> buf.writeInt(this.machineCasingTier));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.preciseCasingTier = 0;
    }

    public int getPreciseCasingTier() {
        return this.preciseCasingTier;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == MULTIBLOCK_TIER_CHANGE_1) {
            this.preciseCasingTier = buf.readInt();
        } else if (dataId == MULTIBLOCK_TIER_CHANGE_2) {
            this.machineCasingTier = buf.readInt();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.preciseCasingTier);
        buf.writeInt(this.machineCasingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.preciseCasingTier = buf.readInt();
        this.machineCasingTier = buf.readInt();
    }
}
