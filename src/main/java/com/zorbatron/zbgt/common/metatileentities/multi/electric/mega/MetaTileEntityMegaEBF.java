package com.zorbatron.zbgt.common.metatileentities.multi.electric.mega;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.capability.impl.HeatingCoilGCYMMultiblockRecipeLogic;
import com.zorbatron.zbgt.api.metatileentity.LaserCapableGCYMRecipeMapMultiblockController;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.ZBGTConfig;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;

public class MetaTileEntityMegaEBF extends LaserCapableGCYMRecipeMapMultiblockController implements IHeatingCoil {

    private int blastFurnaceTemperature;

    public MetaTileEntityMegaEBF(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.BLAST_RECIPES);
        this.recipeMapWorkable = new HeatingCoilGCYMMultiblockRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMegaEBF(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        // spotless:off
        FactoryBlockPattern pattern = FactoryBlockPattern.start()
                .aisle("XXXXXXXXXXXXXXX", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXMXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GGGGGGGGGGGGGGG", "GGGGGGGSGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "XXXXXXXXXXXXXXX")
                // spotless:on
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(420)
                        .or(autoAbilities(false, true, true, true, true, true, false))
                        .or(autoEnergyInputsMega()))
                .where('G', states(getGlassState()))
                .where('C', heatingCoils())
                .where('#', air());

        if (ZBGTConfig.multiblockSettings.megasNeedMufflers) {
            pattern.where('M', abilities(MultiblockAbility.MUFFLER_HATCH));
        } else {
            pattern.where('M', states(getCasingState()));
        }

        return pattern.build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();

        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("EEEEXXXXXXXEEEE", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG",
                        "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG",
                        "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG",
                        "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXMXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG",
                        "GC###########CG", "GC###########CG", "GC###########CG", "GC###########CG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXXXXXXXXXX", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG",
                        "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG",
                        "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG",
                        "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "GCCCCCCCCCCCCCG", "XXXXXXXXXXXXXXX")
                .aisle("XXXXXXIPOXXXXXX", "GGGGGGGGGGGGGGG", "GGGGGGGSGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "XXXXXXXZXXXXXXX")
                .where('S', ZBGTMetaTileEntities.MEGA_EBF, EnumFacing.SOUTH)
                .where('X', getCasingState())
                .where('G', getGlassState())
                .where('Z', TraceabilityPredicates.getMaintenanceHatchMTE(getCasingState()), EnumFacing.SOUTH)
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[1], EnumFacing.NORTH)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[1], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[1], EnumFacing.SOUTH)
                .where('P', GCYMMetaTileEntities.PARALLEL_HATCH[0], EnumFacing.SOUTH);

        if (ZBGTConfig.multiblockSettings.megasNeedMufflers) {
            builder.where('M', MetaTileEntities.MUFFLER_HATCH[1], EnumFacing.UP);
        } else {
            builder.where('M', getCasingState());
        }

        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('C', entry.getKey()).build()));

        return shapeInfo;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(tl -> {
                    // Coil heat capacity line
                    if (isStructureFormed()) {
                        ITextComponent heatString = TextComponentUtil.stringWithColor(
                                TextFormatting.RED,
                                TextFormattingUtil.formatNumbers(blastFurnaceTemperature) + "K");

                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.blast_furnace.max_temperature",
                                heatString));
                    }
                })
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.blastFurnaceTemperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
        } else {
            this.blastFurnaceTemperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
        }
        // the subtracted tier gives the starting level (exclusive) of the +100K heat bonus
        this.blastFurnaceTemperature += 100 *
                Math.max(0, GTUtility.getFloorTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.blastFurnaceTemperature = 0;
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        return this.blastFurnaceTemperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return ZBGTConfig.multiblockSettings.megasNeedMufflers;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    @NotNull
    @Override
    public List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = super.getDataInfo();
        list.add(new TextComponentTranslation("gregtech.multiblock.blast_furnace.max_temperature",
                new TextComponentTranslation(TextFormattingUtil.formatNumbers(blastFurnaceTemperature) + "K")
                        .setStyle(new Style().setColor(TextFormatting.RED))));
        return list;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return ZBGTTextures.GTPP_MACHINE_OVERLAY;
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
