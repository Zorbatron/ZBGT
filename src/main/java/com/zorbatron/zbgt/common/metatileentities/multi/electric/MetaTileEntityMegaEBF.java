package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static gregtech.api.recipes.logic.OverclockingLogic.heatingCoilOverclockingLogic;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
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
import gregtech.core.sound.GTSoundEvents;

public class MetaTileEntityMegaEBF extends GCYMRecipeMapMultiblockController implements IHeatingCoil {

    private int blastFurnaceTemperature;

    public MetaTileEntityMegaEBF(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.BLAST_RECIPES);
        this.recipeMapWorkable = new MegaBlastFurnaceRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMegaEBF(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXXXXXXXXXX", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
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
                .aisle("XXXXXXXXXXXXXXX", "GGGGGGGGGGGGGGG", "GGGGGGGSGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG",
                        "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "GGGGGGGGGGGGGGG", "XXXXXXXXXXXXXXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState())
                        .or(autoAbilities(false, true, true, true, true, true, false))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY, MultiblockAbility.SUBSTATION_INPUT_ENERGY,
                                MultiblockAbility.INPUT_LASER)
                                        .setMinGlobalLimited(1).setMaxGlobalLimited(8)))
                .where('G', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('C', heatingCoils())
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
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
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
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

    @SuppressWarnings("InnerClassMayBeStatic")
    private class MegaBlastFurnaceRecipeLogic extends GCYMMultiblockRecipeLogic {

        public MegaBlastFurnaceRecipeLogic(RecipeMapMultiblockController metaTileEntity) {
            super(metaTileEntity);
        }

        @Override
        protected int @NotNull [] runOverclockingLogic(@NotNull IRecipePropertyStorage propertyStorage, int recipeEUt,
                                                       long maxVoltage, int duration, int maxOverclocks) {
            return heatingCoilOverclockingLogic(Math.abs(recipeEUt),
                    maxVoltage,
                    duration,
                    maxOverclocks,
                    ((IHeatingCoil) metaTileEntity).getCurrentTemperature(),
                    propertyStorage.getRecipePropertyValue(TemperatureProperty.getInstance(), 0));
        }
    }
}
