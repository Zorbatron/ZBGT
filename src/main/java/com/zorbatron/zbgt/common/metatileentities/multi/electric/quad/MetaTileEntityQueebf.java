package com.zorbatron.zbgt.common.metatileentities.multi.electric.quad;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
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

import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.IHeatingCoil;
import gregtech.api.capability.IMufflerHatch;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
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
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;

public class MetaTileEntityQueebf extends RecipeMapMultiblockController implements IHeatingCoil {

    private int blastFurnaceTemperature;

    public MetaTileEntityQueebf(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.BLAST_RECIPES);
        this.recipeMapWorkable.setParallelLimit(4);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityQueebf(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "CCCCC", "CCCCC", "XXXXX")
                .aisle("XXXXX", "C#C#C", "C#C#C", "XMXMX")
                .aisle("XXXXX", "CCCCC", "CCCCC", "XXXXX")
                .aisle("XXXXX", "C#C#C", "C#C#C", "XMXMX")
                .aisle("XXSXX", "CCCCC", "CCCCC", "XXXXX")
                .where('S', selfPredicate())
                .where('C', heatingCoils())
                .where('X', states(getCasingState())
                        .or(autoAbilities(false, true, true, true, true, true, false))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY)
                                .setMinGlobalLimited(1)
                                .setMaxGlobalLimited(8)
                                .setPreviewCount(8)))
                .where('M', abilities(MultiblockAbility.MUFFLER_HATCH))
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();

        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("EEXEE", "CCCCC", "CCCCC", "EEXEE")
                .aisle("XXXXX", "C#C#C", "C#C#C", "XMXMX")
                .aisle("XXXXX", "CCCCC", "CCCCC", "XXFXX")
                .aisle("XXXXX", "C#C#C", "C#C#C", "XMXMX")
                .aisle("XISOX", "CCCCC", "CCCCC", "XXZXX")
                .where('S', ZBGTMetaTileEntities.QUAD_EBF, EnumFacing.SOUTH)
                .where('X', getCasingState())
                .where('Z', TraceabilityPredicates.getMaintenanceHatchMTE(getCasingState()), EnumFacing.SOUTH)
                .where('M', MetaTileEntities.MUFFLER_HATCH[GTValues.LV], EnumFacing.UP)
                .where('I', MetaTileEntities.ITEM_IMPORT_BUS[1], EnumFacing.SOUTH)
                .where('O', MetaTileEntities.ITEM_EXPORT_BUS[1], EnumFacing.SOUTH)
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[1], EnumFacing.NORTH)
                .where('F', MetaTileEntities.FLUID_IMPORT_HATCH[1], EnumFacing.UP)
                .where('#', Blocks.AIR.getDefaultState());

        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('C', entry.getKey()).build()));

        return shapeInfo;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
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
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.electric_blast_furnace.tooltip.3"));
    }

    @Override
    public int getCurrentTemperature() {
        return this.blastFurnaceTemperature;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLAST_FURNACE_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return true;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
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

    @Override
    public void outputRecoveryItems() {
        List<IMufflerHatch> mufflers = getAbilities(MultiblockAbility.MUFFLER_HATCH);
        for (IMufflerHatch muffler : mufflers) {
            muffler.recoverItemsTable(recoveryItems);
        }
    }

    @Override
    public void outputRecoveryItems(int parallel) {
        List<IMufflerHatch> mufflers = getAbilities(MultiblockAbility.MUFFLER_HATCH);
        for (IMufflerHatch muffler : mufflers) {
            for (int i = 0; i < parallel; i++) {
                muffler.recoverItemsTable(recoveryItems);
            }
        }
    }
}
