package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static com.zorbatron.zbgt.api.pattern.TraceabilityPredicates.*;
import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.CoAL_RECIPES;
import static gregtech.api.unification.material.Materials.TungstenSteel;
import static gregtech.api.util.RelativeDirection.*;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.metatileentity.LaserCapableRecipeMapMultiblockController;
import com.zorbatron.zbgt.api.recipes.properties.CoALProperty;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.MaterialCasing;

import gregtech.api.GTValues;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.*;

public class MetaTileEntityCoAL extends LaserCapableRecipeMapMultiblockController
                                implements IOpticalComputationReceiver {

    private IOpticalComputationProvider computationProvider;
    private int tier;

    public MetaTileEntityCoAL(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, CoAL_RECIPES);
        this.recipeMapWorkable = new CoALRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCoAL(metaTileEntityId);
    }

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, UP, FRONT)
                .aisle("CCCCCCCCC", "C##F#F##C", "C##CCC##C", "C##CCC##C", "C#######C", "C#######C", "CC#CCC#CC",
                        "#CCCSCCC#", "###CCC###", "#########")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TCCCCCT#", "#########")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#A###A#G", "GB#####BG", "GB#####BG", "CB#####BC",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "GP#####PG", "GP#CCC#PG", "GP#####PG", "GP#####PG", "GP##B##PG", "CPP#A#PPC",
                        "T#PPAPP#T", "#TC###CT#", "###III###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#A###A#G", "GB#####BG", "GB#####BG", "CB#####BC",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "CP#####PC", "CP#CCC#PC", "CP#####PC", "CP#####PC", "CP##B##PC", "CPP#A#PPC",
                        "T#PPAPP#T", "#TC###CT#", "###III###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#A###A#G", "GB#####BG", "GB#####BG", "CB#####BC",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "GP#####PG", "GP#CCC#PG", "GP#####PG", "GP#####PG", "GP##B##PG", "CPP#A#PPC",
                        "T#PPAPP#T", "#TC###CT#", "###III###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#A###A#G", "GB#####BG", "GB#####BG", "CB#####BC",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "CP#####PC", "CP#CCC#PC", "CP#####PC", "CP#####PC", "CP##B##PC", "CPP#A#PPC",
                        "T#PPAPP#T", "#TC###CT#", "###III###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#A###A#G", "GB#####BG", "GB#####BG", "CB#####BC",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "GP#####PG", "GP#CCC#PG", "GP#####PG", "GP#####PG", "GP##B##PG", "CPP#A#PPC",
                        "T#PPAPP#T", "#TC###CT#", "###III###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#A###A#G", "GB#####BG", "GB#####BG", "CB#####BC",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "CP#####PC", "CP#CCC#PC", "CP#####PC", "CP#####PC", "CP##B##PC", "CPP#A#PPC",
                        "T#PPAPP#T", "#TC###CT#", "###III###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#A###A#G", "GB#####BG", "GB#####BG", "CB#####BC",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "GP#####PG", "GP#CCC#PG", "GP#####PG", "GP#####PG", "GP##B##PG", "CPP#A#PPC",
                        "T#PPAPP#T", "#TC###CT#", "###III###")
                .aisle("CCCCCCCCC", "G##F#F##G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#A###A#G", "GB#####BG", "GB#####BG", "CB#####BC",
                        "T#######T", "#TC###CT#", "###CIC###")
                .aisle("CCCCCCCCC", "G#######G", "G##CCC##G", "G#######G", "G#######G", "G#######G", "C#######C",
                        "T#######T", "#TCCCCCT#", "#########")
                .aisle("CCCCCCCCC", "C##CCC##C", "C#######C", "C#######C", "C#######C", "C#######C", "CC#####CC",
                        "#CCCCCCC#", "#########", "#########")
                .where('S', selfPredicate())
                .where('F', frames(TungstenSteel))
                .where('G', states(getGlassState()))
                .where('T', states(getFilterState()))
                .where('I', coALCasings())
                .where('A', states(getAssemblyState()))
                .where('B', states(getAssemblyControlState()))
                .where('P', states(getPipeCasingState()))
                .where('C', states(getCasingState()).setMinGlobalLimited(630)
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                        .or(autoAbilities(true, true, true, true, true, true, false)))
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState() {
        return ZBGTMetaBlocks.MATERIAL_CASINGS.getState(MaterialCasing.CasingType.IRIDIUM_CASING);
    }

    protected IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }

    protected IBlockState getFilterState() {
        return MetaBlocks.CLEANROOM_CASING.getState(BlockCleanroomCasing.CasingType.FILTER_CASING);
    }

    protected IBlockState getAssemblyState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_CONTROL);
    }

    protected IBlockState getAssemblyControlState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_LINE_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return ZBGTTextures.IRIDIUM_CASING;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("CoALTier");
        if (type instanceof CoALCasing.CasingType coalTier) {
            this.tier = coalTier.ordinal();
        } else
            this.tier = -1;

        List<IOpticalComputationHatch> providers = getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            computationProvider = providers.get(0);
        }

        if (computationProvider == null) {
            invalidateStructure();
        }
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.tier = 0;
    }

    public int getTier() {
        return this.tier;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(recipeMapWorkable.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        ITextComponent textTier = TextComponentUtil.translationWithColor(TextFormatting.GRAY,
                                "zbgt.machine.coal.max_recipe_tier",
                                GTValues.VNF[this.tier]);

                        ITextComponent hoverTextTier = TextComponentUtil.translationWithColor(TextFormatting.GRAY,
                                "zbgt.machine.coal.max_recipe_tier.hover");

                        tl.add(TextComponentUtil.setHover(textTier, hoverTextTier));

                        ITextComponent textComputation = TextComponentUtil.translationWithColor(TextFormatting.GRAY,
                                "zbgt.machine.coal.computation_tier",
                                TextComponentUtil.stringWithColor(TextFormatting.WHITE,
                                        TextFormattingUtil.formatNumbers(computationProvider.getMaxCWUt())));

                        ITextComponent hoverTextComputation = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "zbgt.machine.coal.computation_tier.hover");

                        tl.add(TextComponentUtil.setHover(textComputation, hoverTextComputation));
                    }
                })
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        for (int i = 1; i <= 3; i++) {
            tooltip.add(I18n.format(String.format("zbgt.machine.coal.description.%d", i)));
        }
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return computationProvider;
    }

    protected class CoALRecipeLogic extends ComputationRecipeLogic {

        public CoALRecipeLogic(MetaTileEntityCoAL metaTileEntity) {
            super(metaTileEntity, ComputationType.STEADY);
        }

        @Override
        public boolean checkRecipe(@NotNull Recipe recipe) {
            if (!super.checkRecipe(recipe)) return false;

            int recipeCasingTier = recipe.getProperty(CoALProperty.getInstance(), 0);

            setSpeedBonus(1 / Math.pow(2, tier - recipeCasingTier));

            return recipeCasingTier <= tier;
        }
    }
}
