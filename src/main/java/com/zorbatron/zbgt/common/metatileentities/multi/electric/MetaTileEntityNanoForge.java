package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static gregtech.api.util.RelativeDirection.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.recipes.properties.NanoForgeProperty;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.api.unification.ore.ZBGTOrePrefix;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.BlockableSlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.ingredients.GTRecipeOreInput;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

public class MetaTileEntityNanoForge extends RecipeMapMultiblockController {

    private final Material[] nanites = new Material[3];

    private IItemHandlerModifiable controllerSlot;
    private int naniteTier = 0;

    public MetaTileEntityNanoForge(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, ZBGTRecipeMaps.NANO_FORGE_RECIPES);
        this.recipeMapWorkable = new NanoForgeRecipeLogic(this);
        this.nanites[0] = Materials.Carbon;
        this.nanites[1] = Materials.Neutronium;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNanoForge(metaTileEntityId);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();

        this.controllerSlot = new GTItemStackHandler(this) {

            private int checkNanite(@NotNull ItemStack stack) {
                for (int tier = 0; tier < nanites.length; tier++) {
                    if (nanites[tier] == null) continue;

                    if (new GTRecipeOreInput(ZBGTOrePrefix.nanites, nanites[tier]).acceptsStack(stack)) {
                        return tier + 1;
                    }
                }

                return 0;
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return checkNanite(stack) > 0;
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                naniteTier = checkNanite(getStackInSlot(0));
            }
        };
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        // spotless:off
        return FactoryBlockPattern.start(RIGHT, FRONT, DOWN)
                .aisle("         ", "         ", "    F    ", "    C    ", "    C    ", "    C    ", "    C    ", "    F    ", "         ", "         ")
                .aisle("         ", "         ", "    F    ", "    C    ", "    C    ", "    C    ", "    C    ", "    F    ", "         ", "         ")
                .aisle("         ", "         ", "    F    ", "    C    ", "    C    ", "    C    ", "    C    ", "    F    ", "         ", "         ")
                .aisle("         ", "         ", "    F    ", "    C    ", "    C    ", "    C    ", "    C    ", "    F    ", "         ", "         ")
                .aisle("         ", "         ", "    F    ", "    C    ", "    C    ", "    C    ", "    C    ", "    F    ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "   FCF   ", "  FC CF  ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "  FC CF  ", "   FCF   ", "         ")
                .aisle("         ", "   FCF   ", "  FC CF  ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "  FC CF  ", "   FCF   ", "         ")
                .aisle("         ", "   FCF   ", "  FC CF  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  FC CF  ", "   FCF   ", "         ")
                .aisle("         ", "   FCF   ", "  FC CF  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  FC CF  ", "   FCF   ", "         ")
                .aisle("    C    ", "   FCF   ", "  CC CC  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  CC CC  ", "   FCF   ", "    C    ")
                .aisle("    C    ", "   FCF   ", "  CC CC  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  CC CC  ", "   FCF   ", "    C    ")
                .aisle("    C    ", "   FCF   ", "  CC CC  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  CC CC  ", "   FCF   ", "    C    ")
                .aisle("    C    ", "   FCF   ", "  CC CC  ", " CC   CC ", " CC   CC ", " CC   CC ", " CC   CC ", "  CC CC  ", "   FCF   ", "    C    ")
                .aisle("    C    ", "   FCF   ", "  CC CC  ", " CC   CC ", " CC   CC ", " CC   CC ", " CC   CC ", "  CC CC  ", "   FCF   ", "    C    ")
                .aisle("    C    ", "   FCF   ", "  CC CC  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  CC CC  ", "   FCF   ", "    C    ")
                .aisle("    C    ", "   FCF   ", "  CC CC  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  CC CC  ", "   FCF   ", "    C    ")
                .aisle("    C    ", "   FCF   ", "  CC CC  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  CC CC  ", "   FCF   ", "    C    ")
                .aisle("         ", "   FCF   ", "  FC CF  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  FC CF  ", "   FCF   ", "         ")
                .aisle("         ", "   FCF   ", "  FC CF  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  FC CF  ", "   FCF   ", "         ")
                .aisle("         ", "   FCF   ", "  FC CF  ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "  FC CF  ", "   FCF   ", "         ")
                .aisle("         ", "   FCF   ", "  FC CF  ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "  FC CF  ", "   FCF   ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "         ", "   FCF   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   FCF   ", "         ", "         ")
                .aisle("         ", "  BBSBB  ", " BBBBBBB ", "BBBBBBBBB", "BBBBBBBBB", "BBBBBBBBB", "BBBBBBBBB", " BBBBBBB ", "  BBBBB  ", "         ")
                .where('S', selfPredicate())
                .where('B', states(getCasingState())
                        .or(autoAbilities()))
                .where('C', states(getCasingState()))
                .where('F', frames(getFrameMaterial()))
                .where(' ', any())
                .build();

        //spotless:on
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> shapes = new ArrayList<>();

        // spotless:off
        String[] front    = new String[] { "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "    C    ", "    C    ", "    C    ", "    C    ", "    C    ", "    C    ", "    C    ", "    C    ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         " };
        String[] second   = new String[] { "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "  CESMC  " };
        String[] third    = new String[] { "    F    ", "    F    ", "    F    ", "    F    ", "    F    ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "  FC CF  ", "  FC CF  ", "  FC CF  ", "  FC CF  ", "  CC CC  ", "  CC CC  ", "  CC CC  ", "  CC CC  ", "  CC CC  ", "  CC CC  ", "  CC CC  ", "  CC CC  ", "  FC CF  ", "  FC CF  ", "  FC CF  ", "  FC CF  ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", " CCCCCCC " };
        String[] middle   = new String[] { "    C    ", "    C    ", "    C    ", "    C    ", "    C    ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", " CC   CC ", " CC   CC ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "  C   C  ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "   C C   ", "CCCCCCCCC" };
        String[] secondNC = new String[] { "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "   FCF   ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "  CCCCC  " };
        //spotless:on

        List<String> frontList = Arrays.asList(front);
        List<String> secondList = Arrays.asList(second);
        List<String> thirdList = Arrays.asList(third);
        List<String> middleList = Arrays.asList(middle);
        List<String> secondNCList = Arrays.asList(secondNC);

        Collections.reverse(frontList);
        Collections.reverse(secondList);
        Collections.reverse(thirdList);
        Collections.reverse(middleList);
        Collections.reverse(secondNCList);

        String[] frontReversed = frontList.toArray(new String[0]);
        String[] secondReversed = secondList.toArray(new String[0]);
        String[] thirdReversed = thirdList.toArray(new String[0]);
        String[] middleReversed = middleList.toArray(new String[0]);
        String[] secondNCReversed = secondNCList.toArray(new String[0]);

        shapes.add(
                MultiblockShapeInfo.builder()
                        .aisle(frontReversed)
                        .aisle(secondNCReversed)
                        .aisle(thirdReversed)
                        .aisle(middleReversed)
                        .aisle(middleReversed)
                        .aisle(middleReversed)
                        .aisle(middleReversed)
                        .aisle(thirdReversed)
                        .aisle(secondReversed)
                        .aisle(frontReversed)
                        .where('S', ZBGTMetaTileEntities.NANO_FORGE, EnumFacing.SOUTH)
                        .where('C', getCasingState())
                        .where('F', MetaBlocks.FRAMES.get(getFrameMaterial()).getBlock(getFrameMaterial()))
                        .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.UHV], EnumFacing.SOUTH)
                        .where('M', TraceabilityPredicates.getMaintenanceHatchMTE(getCasingState()), EnumFacing.SOUTH)
                        .where(' ', Blocks.AIR.getDefaultState())
                        .build());

        return shapes;
    }

    protected IBlockState getCasingState() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.RADIANT_NAQUADAH);
    }

    protected Material getFrameMaterial() {
        // TODO: Implement stellar alloy
        return Materials.Neutronium;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.RADIANT_NAQUADAH_CASING;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(recipeMapWorkable.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addCustom(list -> list.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY,
                        "zbgt.machine.nano_forge.nanite_tier",
                        TextComponentUtil.stringWithColor(TextFormatting.WHITE, String.valueOf(naniteTier)))))
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    @Override
    protected @NotNull Widget getFlexButton(int x, int y, int width, int height) {
        return new BlockableSlotWidget(controllerSlot, 0, x, y)
                .setIsBlocked(this::isActive)
                .setBackgroundTexture(GuiTextures.SLOT);
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        itemBuffer.add(controllerSlot.getStackInSlot(0));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        GTUtility.writeItems(controllerSlot, "ControllerSlot", data);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        GTUtility.readItems(controllerSlot, "ControllerSlot", data);
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class NanoForgeRecipeLogic extends MultiblockRecipeLogic {

        public NanoForgeRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public boolean checkRecipe(@NotNull Recipe recipe) {
            if (!super.checkRecipe(recipe)) return false;

            int recipeTier = recipe.getProperty(NanoForgeProperty.getInstance(), 0);

            this.hasPerfectOC = recipeTier < naniteTier;

            return recipeTier <= naniteTier;
        }
    }
}
