package com.zorbatron.zbgt.common.metatileentities.multi.electric.mega;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.capability.impl.HeatingCoilMegaMultiblockRecipeLogic;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.ZBGTConfig;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
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
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

public class MTEMegaABS extends MTEMegaBase implements IHeatingCoil {

    private int temperature;

    public MTEMegaABS(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GCYMRecipeMaps.ALLOY_BLAST_RECIPES);
        this.recipeMapWorkable = new HeatingCoilMegaMultiblockRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTEMegaABS(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        // spotless:off
        FactoryBlockPattern pattern = FactoryBlockPattern.start()
                .aisle("   BBBBB   ", "   VVVVV   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   VVVVV   ", "   XXXXX   ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ")
                .aisle("  BBBBBBB  ", "  V#####V  ", "  G#####G  ", "  G#####G  ", "  G#####G  ", "  V#####V  ", "  X#####X  ", "   XXXXX   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   XXXXX   ")
                .aisle(" BBBBBBBBB ", " V#CCCCC#V ", " G#CCCCC#G ", " G#CCCCC#G ", " G#CCCCC#G ", " V#CCCCC#V ", " X#CCCCC#X ", "  XCCCCCX  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  XXXXXXX  ")
                .aisle("BBBBBBBBBBB", "V#C     C#V", "G#C     C#G", "G#C     C#G", "G#C     C#G", "V#C     C#V", "X#C     C#X", " XC     CX ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " XXXXXXXXX ")
                .aisle("BBBBBBBBBBB", "V#C     C#V", "G#C     C#G", "G#C     C#G", "G#C     C#G", "V#C     C#V", "X#C     C#X", " XC     CX ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " XXXXXXXXX ")
                .aisle("BBBBBBBBBBB", "V#C     C#V", "G#C     C#G", "G#C     C#G", "G#C     C#G", "V#C     C#V", "X#C     C#X", " XC     CX ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " XXXXMXXXX ")
                .aisle("BBBBBBBBBBB", "V#C     C#V", "G#C     C#G", "G#C     C#G", "G#C     C#G", "V#C     C#V", "X#C     C#X", " XC     CX ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " XXXXXXXXX ")
                .aisle("BBBBBBBBBBB", "V#C     C#V", "G#C     C#G", "G#C     C#G", "G#C     C#G", "V#C     C#V", "X#C     C#X", " XC     CX ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " GC     CG ", " XXXXXXXXX ")
                .aisle(" BBBBBBBBB ", " V#CCCCC#V ", " G#CCCCC#G ", " G#CCCCC#G ", " G#CCCCC#G#", " V#CCCCC#V ", " X#CCCCC#X ", "  XCCCCCX  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  GCCCCCG  ", "  XXXXXXX  ")
                .aisle("  BBBBBBB  ", "  V#####V  ", "  G#####G  ", "  G#####G  ", "  G#####G  ", "  V#####V  ", "  X#####X  ", "   XXXXX   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   GGGGG   ", "   XXXXX   ")
                .aisle("   BBBBB   ", "   VVVVV   ", "   GWWWG   ", "   GWSWG   ", "   GWWWG   ", "   VVVVV   ", "   XXXXX   ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ")
                // spotless:on
                .where('S', selfPredicate())
                .where('B', states(getCasingState())
                        .or(TraceabilityPredicates.autoBusesAndHatches(recipeMap))
                        .or(autoEnergyInputs()))
                .where('X', states(getCasingState()))
                .where('V', states(getVentState()))
                .where('G', states(getGlassState()))
                .where('C', heatingCoils())
                .where('W', states(getCasingState())
                        .or(autoAbilities(false, true, false, false, false, false, false)))
                .where('#', air());

        if (ZBGTConfig.multiblockSettings.megasNeedMufflers) {
            pattern.where('M', abilities(MultiblockAbility.MUFFLER_HATCH));
        } else {
            pattern.where('M', states(getCasingState()));
        }

        return pattern.build();
    }

    protected IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING
                .getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }

    protected IBlockState getVentState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();

        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("###XXXXX###", "###VVVVV###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###VVVVV###",
                        "###XXXXX###", "###########", "###########", "###########", "###########", "###########",
                        "###########", "###########", "###########", "###########", "###########", "###########",
                        "###########", "###########")
                .aisle("##XXXXXXX##", "##V#####V##", "##G#####G##", "##G#####G##", "##G#####G##", "##V#####V##",
                        "##X#####X##", "###XXXXX###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###",
                        "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###",
                        "###GGGGG###", "###XXXXX###")
                .aisle("#XXXXXXXXX#", "#V#CCCCC#V#", "#G#CCCCC#G#", "#G#CCCCC#G#", "#G#CCCCC#G#", "#V#CCCCC#V#",
                        "#X#CCCCC#X#", "##XCCCCCX##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##",
                        "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##",
                        "##GCCCCCG##", "##XXXXXXX##")
                .aisle("DXXXXXXXXXE", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXXXXXX#")
                .aisle("DXXXXXXXXXE", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXXXXXX#")
                .aisle("XXXXXXXXXXX", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXMXXXX#")
                .aisle("DXXXXXXXXXE", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXXXXXX#")
                .aisle("DXXXXXXXXXE", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXXXXXX#")
                .aisle("#XXXXXXXXX#", "#V#CCCCC#V#", "#G#CCCCC#G#", "#G#CCCCC#G#", "#G#CCCCC#G#", "#V#CCCCC#V#",
                        "#X#CCCCC#X#", "##XCCCCCX##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##",
                        "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##",
                        "##GCCCCCG##", "##XXXXXXX##")
                .aisle("##XXXXXXX##", "##V#####V##", "##G#####G##", "##G#####G##", "##G#####G##", "##V#####V##",
                        "##X#####X##", "###XXXXX###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###",
                        "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###",
                        "###GGGGG###", "###XXXXX###")
                .aisle("###IFXXO###", "###VVVVV###", "###GWPWG###", "###GWSWG###", "###GWZWG###", "###VVVVV###",
                        "###XXXXX###", "###########", "###########", "###########", "###########", "###########",
                        "###########", "###########", "###########", "###########", "###########", "###########",
                        "###########", "###########")
                .where('S', ZBGTMetaTileEntities.MEGA_ABS, EnumFacing.SOUTH)
                .where('Z', TraceabilityPredicates.getMaintenanceHatchMTE(getCasingState()), EnumFacing.SOUTH)
                .where('P', GCYMMetaTileEntities.PARALLEL_HATCH[0], EnumFacing.SOUTH)
                .where('X', getCasingState())
                .where('V', getVentState())
                .where('G', getGlassState())
                .where('W', getCasingState())
                .where('X', getCasingState())
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[1], EnumFacing.SOUTH)
                .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[1], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.FLUID_EXPORT_HATCH[1], EnumFacing.SOUTH)
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[1], EnumFacing.EAST)
                .where('D', MetaTileEntities.ENERGY_INPUT_HATCH[1], EnumFacing.WEST)
                .where('#', Blocks.AIR.getDefaultState());

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
                                TextFormattingUtil.formatNumbers(temperature) + "K");

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

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.BLAST_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoilType");
        if (type instanceof IHeatingCoilBlockStats) {
            this.temperature = ((IHeatingCoilBlockStats) type).getCoilTemperature();
        } else {
            this.temperature = BlockWireCoil.CoilType.CUPRONICKEL.getCoilTemperature();
        }

        this.temperature += 100 *
                Math.max(0, GTUtility.getTierByVoltage(getEnergyContainer().getInputVoltage()) - GTValues.MV);
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.temperature = 0;
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        return this.temperature >= recipe.getProperty(TemperatureProperty.getInstance(), 0);
    }

    @Override
    public boolean hasMufflerMechanics() {
        return ZBGTConfig.multiblockSettings.megasNeedMufflers;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public int getCurrentTemperature() {
        return this.temperature;
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
