package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static com.zorbatron.zbgt.api.pattern.TraceabilityPredicates.componentALCasings;
import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.COMPONENT_AL_RECIPES;
import static gregtech.api.unification.material.Materials.TungstenSteel;
import static gregtech.api.util.RelativeDirection.*;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.block.IComponentALTier;
import com.zorbatron.zbgt.api.recipes.ITier;
import com.zorbatron.zbgt.api.recipes.properties.ComponentALProperty;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.ZBGTBlockMultiblockCasing;

import gregtech.api.GTValues;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.*;

public class MetaTileEntityComponentAL extends RecipeMapMultiblockController
                                       implements ITier, IOpticalComputationReceiver {

    private IOpticalComputationProvider computationProvider;
    private int tier;

    public MetaTileEntityComponentAL(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, COMPONENT_AL_RECIPES);
        this.recipeMapWorkable = new ComponentALRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityComponentAL(metaTileEntityId);
    }

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
                .where('G', states(MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS)))
                .where('T', states(MetaBlocks.CLEANROOM_CASING.getState(BlockCleanroomCasing.CasingType.FILTER_CASING)))
                .where('I', componentALCasings())
                .where('A',
                        states(MetaBlocks.MULTIBLOCK_CASING
                                .getState(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_CONTROL)))
                .where('B',
                        states(MetaBlocks.MULTIBLOCK_CASING
                                .getState(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_LINE_CASING)))
                .where('P',
                        states(MetaBlocks.BOILER_CASING
                                .getState(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE)))
                .where('C',
                        states(ZBGTMetaBlocks.MULTIBLOCK_CASING
                                .getState(ZBGTBlockMultiblockCasing.CasingType.IRIDIUM_CASING))
                                        .setMinGlobalLimited(630)
                                        .or(autoAbilities(true, true, true, true, true, false, false))
                                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1)))
                .where('#', air())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return ZBGTTextures.IRIDIUM_CASING;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("ComponentALTier");
        if (type instanceof IComponentALTier) {
            this.tier = ((IComponentALTier) type).getTier() + 1;
        } else
            this.tier = 0;

        List<IOpticalComputationHatch> providers = getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && providers.size() >= 1) {
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

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("zbgt.machine.coal.max_recipe_tier", GTValues.VN[this.tier]));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("zbgt.machine.coal.description.1"));
        tooltip.add(I18n.format("zbgt.machine.coal.description.2"));
    }

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return computationProvider;
    }

    private class ComponentALRecipeLogic extends ComputationRecipeLogic {

        MetaTileEntityComponentAL componentAL;

        public ComponentALRecipeLogic(MetaTileEntityComponentAL metaTileEntity) {
            super(metaTileEntity, ComputationType.STEADY);
            this.componentAL = metaTileEntity;
        }

        @Override
        public boolean checkRecipe(@NotNull Recipe recipe) {
            if (!super.checkRecipe(recipe))
                return false;

            return recipe.getProperty(ComponentALProperty.getInstance(), 0) <= tier;
        }
    }
}
