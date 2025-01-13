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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.metatileentity.LaserCapableGCYMRecipeMapMultiblockController;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.GregTechAPI;
import gregtech.api.block.IHeatingCoilBlockStats;
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
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.core.sound.GTSoundEvents;

public class MTEMegaOCU extends LaserCapableGCYMRecipeMapMultiblockController {

    private int coilTier;

    public MTEMegaOCU(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.CRACKING_RECIPES);
        this.recipeMapWorkable = new CrackingUnitWorkableHandler(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTEMegaOCU(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        // spotless:off
        return FactoryBlockPattern.start()
                .aisle("XXEEEEEEEEEXX", " X         X ", " X         X ", " X         X ", " X         X ", " X         X ", " X         X ")
                .aisle("XXXXXXXXXXXXX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XXGGGGGGGGGXX")
                .aisle("XXXXXXXXXXXXX", " G#C#C#C#C#G ", " G#C#C#C#C#G ", " G#C#C#C#C#G ", " G#C#C#C#C#G ", " G#C#C#C#C#G ", " XGGGGGGGGGX ")
                .aisle("XXXXXXXXXXXXX", " G#C#C#C#C#G ", " X###C###C#X ", " X#C#C#C#C#X ", " X###C###C#X ", " G#C#C#C#C#G ", " XGGGXXXGGGX ")
                .aisle("XXXXXXXXXXXXX", " G#C#C#C#C#G ", " X#C#C#C#C#X ", " I#C#C#C#C#O ", " X#C#C#C#C#X ", " G#C#C#C#C#G ", " XGGGXTXGGGX ")
                .aisle("XXXXXXXXXXXXX", " G#C#C#C#C#G ", " X###C###C#X ", " X#C#C#C#C#X ", " X###C###C#X ", " G#C#C#C#C#G ", " XGGGXXXGGGX ")
                .aisle("XXXXXXXXXXXXX", " G#C#C#C#C#G ", " G#C#C#C#C#G ", " G#C#C#C#C#G ", " G#C#C#C#C#G ", " G#C#C#C#C#G ", " XGGGGGGGGGX ")
                .aisle("XXXXXXXXXXXXX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XXGGGGGGGGGXX")
                .aisle("XXEEEESEEEEXX", " X         X ", " X         X ", " X         X ", " X         X ", " X         X ", " X         X ")
                // spotless:on
                .where('S', selfPredicate())
                .where('C', heatingCoils())
                .where('X', states(getCasingState()))
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS))
                .where('T', abilities(MultiblockAbility.IMPORT_FLUIDS).or(states(getCasingState())))
                .where('O', abilities(MultiblockAbility.EXPORT_FLUIDS))
                .where('E', states(getCasingState())
                        .or(autoAbilities(false, true, true, true, false, false, false))
                        .or(autoEnergyInputsMega()))
                .where('G', states(getGlassState()))
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();

        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XXEEEEXEEEEXX", "#X#########X#", "#X#########X#", "#X#########X#", "#X#########X#",
                        "#X#########X#", "#X#########X#")
                .aisle("XXXXXXXXXXXXX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX",
                        "XGGGGGGGGGGGX", "XXGGGGGGGGGXX")
                .aisle("XXXXXXXXXXXXX", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#",
                        "#G#C#C#C#C#G#", "#XGGGGGGGGGX#")
                .aisle("XXXXXXXXXXXXX", "#G#C#C#C#C#G#", "#X###C###C#X#", "#X#C#C#C#C#X#", "#X###C###C#X#",
                        "#G#C#C#C#C#G#", "#XGGGXXXGGGX#")
                .aisle("XXXXXXXXXXXXX", "#G#C#C#C#C#G#", "#X#C#C#C#C#X#", "#I#C#C#C#C#O#", "#X#C#C#C#C#X#",
                        "#G#C#C#C#C#G#", "#XGGGXTXGGGX#")
                .aisle("XXXXXXXXXXXXX", "#G#C#C#C#C#G#", "#X###C###C#X#", "#X#C#C#C#C#X#", "#X###C###C#X#",
                        "#G#C#C#C#C#G#", "#XGGGXXXGGGX#")
                .aisle("XXXXXXXXXXXXX", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#", "#G#C#C#C#C#G#",
                        "#G#C#C#C#C#G#", "#XGGGGGGGGGX#")
                .aisle("XXXXXXXXXXXXX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX", "XGGGGGGGGGGGX",
                        "XGGGGGGGGGGGX", "XXGGGGGGGGGXX")
                .aisle("XXXXXPSMXXXXX", "#X#########X#", "#X#########X#", "#X#########X#", "#X#########X#",
                        "#X#########X#", "#X#########X#")
                .where('S', ZBGTMetaTileEntities.MEGA_OCU, EnumFacing.SOUTH)
                .where('G', getGlassState())
                .where('X', getCasingState())
                .where('M', TraceabilityPredicates.getMaintenanceHatchMTE(getCasingState()), EnumFacing.SOUTH)
                .where('P', GCYMMetaTileEntities.PARALLEL_HATCH[0], EnumFacing.SOUTH)
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[1], EnumFacing.NORTH)
                .where('I', MetaTileEntities.FLUID_IMPORT_HATCH[1], EnumFacing.WEST)
                .where('T', MetaTileEntities.FLUID_IMPORT_HATCH[1], EnumFacing.UP)
                .where('O', MetaTileEntities.FLUID_EXPORT_HATCH[0], EnumFacing.EAST);

        GregTechAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                .forEach(entry -> shapeInfo.add(builder.where('C', entry.getKey()).build()));

        return shapeInfo;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.CLEAN_STAINLESS_STEEL_CASING;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.cracker.tooltip.1"));
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addCustom(tl -> {
                    // Coil energy discount line
                    if (isStructureFormed()) {
                        ITextComponent energyDiscount = TextComponentUtil.stringWithColor(TextFormatting.AQUA,
                                (100 - 10 * coilTier) + "%");

                        ITextComponent base = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.cracking_unit.energy",
                                energyDiscount);

                        ITextComponent hover = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.cracking_unit.energy_hover");

                        tl.add(TextComponentUtil.setHover(base, hover));
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
            this.coilTier = ((IHeatingCoilBlockStats) type).getTier();
        } else {
            this.coilTier = 0;
        }
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.coilTier = -1;
    }

    protected int getCoilTier() {
        return this.coilTier;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class CrackingUnitWorkableHandler extends GCYMMultiblockRecipeLogic {

        public CrackingUnitWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        protected void modifyOverclockPost(int[] resultOverclock, @NotNull IRecipePropertyStorage storage) {
            super.modifyOverclockPost(resultOverclock, storage);

            int coilTier = ((MTEMegaOCU) metaTileEntity).getCoilTier();
            if (coilTier <= 0)
                return;

            resultOverclock[0] *= 1.0f - coilTier * 0.1; // each coil above cupronickel (coilTier = 0) uses 10% less
            // energy
            resultOverclock[0] = Math.max(1, resultOverclock[0]);
        }
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
