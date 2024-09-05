package com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.capability.impl.HeatingCoilGCYMMultiblockRecipeLogic;
import com.zorbatron.zbgt.api.metatileentity.LaserCapableGCYMRecipeMapMultiblockController;

import gregicality.multiblocks.api.metatileentity.GCYMMultiblockAbility;
import gregicality.multiblocks.api.recipes.GCYMRecipeMaps;
import gregicality.multiblocks.api.render.GCYMTextures;
import gregicality.multiblocks.common.block.GCYMMetaBlocks;
import gregicality.multiblocks.common.block.blocks.BlockLargeMultiblockCasing;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
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

public class MetaTileEntityMegaABS extends LaserCapableGCYMRecipeMapMultiblockController implements IHeatingCoil {

    private int temperature;

    public MetaTileEntityMegaABS(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GCYMRecipeMaps.ALLOY_BLAST_RECIPES);
        this.recipeMapWorkable = new HeatingCoilGCYMMultiblockRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMegaABS(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("###BBBBB###", "###VVVVV###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###VVVVV###",
                        "###XXXXX###", "###########", "###########", "###########", "###########", "###########",
                        "###########", "###########", "###########", "###########", "###########", "###########",
                        "###########", "###########")
                .aisle("##BBBBBBB##", "##V#####V##", "##G#####G##", "##G#####G##", "##G#####G##", "##V#####V##",
                        "##X#####X##", "###XXXXX###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###",
                        "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###",
                        "###GGGGG###", "###XXXXX###")
                .aisle("#BBBBBBBBB#", "#V#CCCCC#V#", "#G#CCCCC#G#", "#G#CCCCC#G#", "#G#CCCCC#G#", "#V#CCCCC#V#",
                        "#X#CCCCC#X#", "##XCCCCCX##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##",
                        "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##",
                        "##GCCCCCG##", "##XXXXXXX##")
                .aisle("BBBBBBBBBBB", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXXXXXX#")
                .aisle("BBBBBBBBBBB", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXXXXXX#")
                .aisle("BBBBBBBBBBB", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXMXXXX#")
                .aisle("BBBBBBBBBBB", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXXXXXX#")
                .aisle("BBBBBBBBBBB", "V#C#####C#V", "G#C#####C#G", "G#C#####C#G", "G#C#####C#G", "V#C#####C#V",
                        "X#C#####C#X", "#XC#####CX#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#", "#GC#####CG#",
                        "#GC#####CG#", "#XXXXXXXXX#")
                .aisle("#BBBBBBBBB#", "#V#CCCCC#V#", "#G#CCCCC#G#", "#G#CCCCC#G#", "#G#CCCCC#G#", "#V#CCCCC#V#",
                        "#X#CCCCC#X#", "##XCCCCCX##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##",
                        "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##", "##GCCCCCG##",
                        "##GCCCCCG##", "##XXXXXXX##")
                .aisle("##BBBBBBB##", "##V#####V##", "##G#####G##", "##G#####G##", "##G#####G##", "##V#####V##",
                        "##X#####X##", "###XXXXX###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###",
                        "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###", "###GGGGG###",
                        "###GGGGG###", "###XXXXX###")
                .aisle("###BBBBB###", "###VVVVV###", "###GWWWG###", "###GWSWG###", "###GWWWG###", "###VVVVV###",
                        "###XXXXX###", "###########", "###########", "###########", "###########", "###########",
                        "###########", "###########", "###########", "###########", "###########", "###########",
                        "###########", "###########")
                .where('S', selfPredicate())
                .where('B', states(getCasingState()).or(busesAndHatches()).or(autoEnergyInputs(1, 8)))
                .where('X', states(getCasingState()))
                .where('V', states(getVentState()))
                .where('G', states(getGlassState()))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', heatingCoils())
                .where('W', abilities(MultiblockAbility.MAINTENANCE_HATCH).setMinGlobalLimited(1).setMaxGlobalLimited(1)
                        .or(abilities(GCYMMultiblockAbility.PARALLEL_HATCH).setMaxGlobalLimited(1).setPreviewCount(1))
                        .or(states(getCasingState())))
                .where('#', air())
                .build();
    }

    protected static IBlockState getCasingState() {
        return GCYMMetaBlocks.LARGE_MULTIBLOCK_CASING
                .getState(BlockLargeMultiblockCasing.CasingType.HIGH_TEMPERATURE_CASING);
    }

    protected static IBlockState getVentState() {
        return GCYMMetaBlocks.UNIQUE_CASING.getState(BlockUniqueCasing.UniqueCasingType.HEAT_VENT);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
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

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GCYMTextures.BLAST_CASING;
    }

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
        return true;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public int getCurrentTemperature() {
        return this.temperature;
    }
}
