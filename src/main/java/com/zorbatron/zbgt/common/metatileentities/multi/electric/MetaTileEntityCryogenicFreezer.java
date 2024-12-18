package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.metatileentity.ZBGTMultiblockAbilities;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;

public class MetaTileEntityCryogenicFreezer extends RecipeMapMultiblockController {

    private final int CRYOTHEUM_DRAIN_AMOUNT = 10;

    private IFluidTank cryotheumTank;

    public MetaTileEntityCryogenicFreezer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.VACUUM_RECIPES);
        recipeMapWorkable = new MultiblockRecipeLogic(this) {

            @Override
            protected boolean shouldSearchForRecipes() {
                return hasCryotheum() && super.shouldSearchForRecipes();
            }

            @Override
            protected boolean canProgressRecipe() {
                return hasCryotheum() && super.canProgressRecipe();
            }
        };
        recipeMapWorkable.setSpeedBonus(0.5);
        recipeMapWorkable.setParallelLimit(4);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCryogenicFreezer(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();

        if (isActive() && getOffsetTimer() % 20 == 0) {
            cryotheumTank.drain(CRYOTHEUM_DRAIN_AMOUNT, true);
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        cryotheumTank = getAbilities(ZBGTMultiblockAbilities.CRYOTHEUM_HATCH).get(0);
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        return cryotheumTank.getFluidAmount() != 0;
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XSX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasing()).setMinGlobalLimited(10)
                        .or(autoAbilities(true, true, true, true, true, true, false))
                        .or(abilities(ZBGTMultiblockAbilities.CRYOTHEUM_HATCH).setExactLimit(1)))
                .where('#', air())
                .build();
    }

    protected IBlockState getCasing() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.CRYOGENIC_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.CRYOGENIC_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected @NotNull ICubeRenderer getFrontOverlay() {
        return ZBGTTextures.GTPP_MACHINE_OVERLAY;
    }

    private boolean hasCryotheum() {
        return cryotheumTank.getFluidAmount() > CRYOTHEUM_DRAIN_AMOUNT;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);

        tooltip.add(I18n.format("zbgt.machine.cryogenic_freezer.tooltip.1"));
        tooltip.add(I18n.format("zbgt.machine.cryogenic_freezer.tooltip.2"));
    }
}
