package com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis;

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
import com.zorbatron.zbgt.api.block.IPreciseTier;
import com.zorbatron.zbgt.api.capability.ZBGTDataCodes;
import com.zorbatron.zbgt.api.metatileentity.LaserCapableMultiShapeGCYMMultiblockController;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.recipes.ITier;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregtech.api.GTValues;
import gregtech.api.capability.IDataAccessHatch;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

public class MetaTileEntityPreciseAssembler extends LaserCapableMultiShapeGCYMMultiblockController implements ITier {

    private int tier = -1;

    public MetaTileEntityPreciseAssembler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new gregtech.api.recipes.RecipeMap[] { RecipeMaps.ASSEMBLER_RECIPES,
                RecipeMaps.ASSEMBLY_LINE_RECIPES,
                RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES });
        this.recipeMapWorkable = new GCYMMultiblockRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPreciseAssembler(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern getStructurePattern(int index) {
        FactoryBlockPattern pattern = FactoryBlockPattern.start()
                .aisle("XXXXXXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "X#######X", "X#######X", "X#######X", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXXSXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .where('S', selfPredicate())
                // .where('H', metaTileEntities(MetaTileEntities.HULL))
                .where('F', frames(Materials.TungstenSteel))
                .where('G', states(getGlassState()))
                .where('#', air());

        if (index == 1) {
            pattern.where('X', getCasingState().or(autoAbilities()).or(dataHatchPredicate()));
        } else {
            pattern.where('X', getCasingState().or(autoAbilities()));
        }

        return pattern.build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();

        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XXXXXXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "X#######X", "X#######X", "X#######X", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXESMXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .where('S', ZBGTMetaTileEntities.PRASS, EnumFacing.SOUTH)
                .where('G', MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS))
                .where('F', MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel))
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV], EnumFacing.SOUTH)
                .where('M', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState());

        ZBGTAPI.PRECISE_CASINGS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('X', entry.getKey()).build()));

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
                        if (getTier() == -1) {
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "zbgt.machine.precise_assembler.tier.error"));
                        } else {
                            tl.add(TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "zbgt.machine.precise_assembler.tier",
                                    getTier()));
                        }
                    }
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    protected TraceabilityPredicate getCasingState() {
        return TraceabilityPredicates.preciseCasings();
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    protected TraceabilityPredicate dataHatchPredicate() {
        return abilities(MultiblockAbility.DATA_ACCESS_HATCH, MultiblockAbility.OPTICAL_DATA_RECEPTION)
                .setExactLimit(1);
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        if (getRecipeMapIndex() == 1) {
            return isRecipeAvailable(getAbilities(MultiblockAbility.DATA_ACCESS_HATCH), recipe) ||
                    isRecipeAvailable(getAbilities(MultiblockAbility.OPTICAL_DATA_RECEPTION), recipe);
        } else {
            return true;
        }
    }

    private static boolean isRecipeAvailable(@NotNull Iterable<? extends IDataAccessHatch> hatches,
                                             @NotNull Recipe recipe) {
        for (IDataAccessHatch hatch : hatches) {
            // creative hatches do not need to check, they always have the recipe
            if (hatch.isCreative()) return true;

            // hatches need to have the recipe available
            if (hatch.isRecipeAvailable(recipe)) return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISPLAY;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return getTier() == -1 ? ZBGTTextures.PRECISE_CASING_1 : ZBGTTextures.getPrassTextureByTier(getTier());
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("PreciseTier");
        if (type instanceof IPreciseTier preciseTier) {
            this.tier = preciseTier.getTier();
        } else {
            this.tier = -1;
        }

        writeCustomData(ZBGTDataCodes.MULTIBLOCK_TIER_CHANGE, packetBuffer -> packetBuffer.writeInt(this.tier));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.tier = 0;
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == ZBGTDataCodes.MULTIBLOCK_TIER_CHANGE) {
            this.tier = buf.readInt();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.tier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.tier = buf.readInt();
    }
}
