package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static gregtech.api.util.RelativeDirection.*;

import java.util.List;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;

import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;

public class MTECircuitAssemblyLine extends MultiMapMultiblockController {

    public MTECircuitAssemblyLine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new gregtech.api.recipes.RecipeMap[] { ZBGTRecipeMaps.CIRCUIT_ASSEMBLY_LINE_RECIPES,
                RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES });
        this.recipeMapWorkable = new MultiblockRecipeLogic(this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTECircuitAssemblyLine(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(FRONT, UP, RIGHT)
                .aisle("FIF", "RTR", "SGG")
                .aisle("FIF", "RTR", "GGG").setRepeatable(5)
                .aisle("FOF", "RTR", "GGG")
                .where('S', selfPredicate())
                .where('G', states(getGrateState())
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setExactLimit(1)))
                .where('R', states(getGlassState()))
                .where('T', states(getAssemblyCasingState()))
                .where('I', abilities(MultiblockAbility.IMPORT_ITEMS))
                .where('O', abilities(MultiblockAbility.EXPORT_ITEMS))
                .where('F', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1))
                        .or(autoAbilities(true, false)))
                .build();
    }

    protected IBlockState getGrateState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }

    protected IBlockState getAssemblyCasingState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.ASSEMBLY_LINE_CASING);
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @Override
    protected Function<BlockPos, Integer> multiblockPartSorter() {
        // player's right when looking at the controller, but the controller's left
        return RelativeDirection.LEFT.getSorter(getFrontFacing(), getUpwardsFacing(), isFlipped());
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        if (consumeIfSuccess || getRecipeMapIndex() == 1) return true; // don't check twice and don't check ordered-ness
                                                                       // when in normal circuit assembler mode
        // check ordered items
        if (ConfigHolder.machines.orderedAssembly) {
            List<GTRecipeInput> inputs = recipe.getInputs();
            List<IItemHandlerModifiable> itemInputInventory = getAbilities(MultiblockAbility.IMPORT_ITEMS);

            // slot count is not enough, so don't try to match it
            if (itemInputInventory.size() < inputs.size()) return false;

            for (int i = 0; i < inputs.size(); i++) {
                if (!inputs.get(i).acceptsStack(itemInputInventory.get(i).getStackInSlot(0))) {
                    return false;
                }
            }
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (ConfigHolder.machines.orderedAssembly) {
            tooltip.add(I18n.format("zbgt.machine.cal.tooltip_ordered_items"));
        }
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
