package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static gregtech.api.util.RelativeDirection.*;

import java.util.List;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.common.items.behaviors.imprints.ImprintBehavior;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockMultiblockCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

public class MTECircuitAssemblyLine extends MultiMapMultiblockController {

    private IItemHandlerModifiable controllerSlot;
    private ItemStack imprintStack = ItemStack.EMPTY;

    public MTECircuitAssemblyLine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] { ZBGTRecipeMaps.CIRCUIT_ASSEMBLY_LINE_RECIPES,
                RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES });
        this.recipeMapWorkable = new CALRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTECircuitAssemblyLine(metaTileEntityId);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        controllerSlot = new NotifiableItemStackHandler(this, 1, this, false) {

            @Nullable
            private static ItemStack imprint = null;

            private static @NotNull ItemStack getReferenceImprint() {
                return imprint == null ? imprint = ZBGTMetaItems.CIRCUIT_IMPRINT.getStackForm() : imprint;
            }

            @SuppressWarnings("BooleanMethodIsAlwaysInverted")
            private static boolean isValidImprint(@NotNull ItemStack stack) {
                if (!getReferenceImprint().isItemEqual(stack)) return false;
                return !ImprintBehavior.getImprintedCircuit(stack).isEmpty();
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (!isValidImprint(stack)) return stack;
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            public void setStackInSlot(int slot, @NotNull ItemStack stack) {
                if (!isValidImprint(stack)) return;
                super.setStackInSlot(slot, stack);
            }

            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                MTECircuitAssemblyLine.this.imprintStack = ImprintBehavior.getImprintedCircuit(getStackInSlot(0));
            }
        };
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        MetaTileEntityMultiblockPart[] allowedHatches = new MetaTileEntityMultiblockPart[MetaTileEntities.ENERGY_INPUT_HATCH.length +
                1];
        System.arraycopy(MetaTileEntities.ENERGY_INPUT_HATCH, 0, allowedHatches, 0,
                MetaTileEntities.ENERGY_INPUT_HATCH.length);
        allowedHatches[allowedHatches.length - 1] = ZBGTMetaTileEntities.CREATIVE_ENERGY_SOURCE;

        return FactoryBlockPattern.start(FRONT, UP, RIGHT)
                .aisle("FIF", "RTR", "SGG")
                .aisle("FIF", "RTR", "GGG").setRepeatable(5)
                .aisle("FOF", "RTR", "GGG")
                .where('S', selfPredicate())
                .where('G', states(getGrateState())
                        .or(metaTileEntities(allowedHatches)
                                .setExactLimit(1)))
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
        // Don't check twice and don't check ordered-ness when in normal circuit assembler
        if (consumeIfSuccess || getRecipeMapIndex() == 1) {
            return true;
        }

        ItemStack recipeOutput = recipe.getOutputs().get(0);
        if (!imprintStack.isItemEqual(recipeOutput)) {
            return false;
        }

        // check ordered items
        if (ConfigHolder.machines.orderedAssembly) {
            List<GTRecipeInput> inputs = recipe.getInputs();
            List<IItemHandlerModifiable> itemInputInventory = getAbilities(MultiblockAbility.IMPORT_ITEMS);

            // slot count is not enough, so don't try to match it
            if (itemInputInventory.size() < inputs.size()) return false;

            for (int handlerIndex = 0; handlerIndex < inputs.size(); handlerIndex++) {
                IItemHandler handler = itemInputInventory.get(handlerIndex);
                boolean oneSuccess = false;
                for (int handlerStackIndex = 0; handlerStackIndex < handler.getSlots(); handlerStackIndex++) {
                    oneSuccess = inputs.get(handlerIndex).acceptsStack(handler.getStackInSlot(handlerStackIndex));
                    if (oneSuccess) break;
                }

                if (!oneSuccess) return false;
            }
        }

        return true;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 198, 208);

        // Display
        builder.image(4, 4, 190, 117, GuiTextures.DISPLAY);
        builder.widget(new IndicatorImageWidget(174, 101, 17, 17, getLogo())
                .setWarningStatus(getWarningLogo(), this::addWarningText)
                .setErrorStatus(getErrorLogo(), this::addErrorText));

        builder.label(9, 9, getMetaFullName(), 0xFFFFFF);
        builder.widget(new AdvancedTextWidget(9, 20, this::addDisplayText, 0xFFFFFF)
                .setMaxWidthLimit(181)
                .setClickHandler(this::handleDisplayClick));

        // Power Button
        IControllable controllable = getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null);
        if (controllable != null) {
            builder.widget(new ImageCycleButtonWidget(173, 183, 18, 18, GuiTextures.BUTTON_POWER,
                    controllable::isWorkingEnabled, controllable::setWorkingEnabled));
            builder.widget(new ImageWidget(173, 201, 18, 6, GuiTextures.BUTTON_POWER_DETAIL));
        }

        // Voiding Mode Button
        if (shouldShowVoidingModeButton()) {
            builder.widget(new ImageCycleButtonWidget(173, 161, 18, 18, GuiTextures.BUTTON_VOID_MULTIBLOCK,
                    4, this::getVoidingMode, this::setVoidingMode)
                            .setTooltipHoverString(MultiblockWithDisplayBase::getVoidingModeTooltip));
        } else {
            builder.widget(new ImageWidget(173, 161, 18, 18, GuiTextures.BUTTON_VOID_NONE)
                    .setTooltip("gregtech.gui.multiblock_voiding_not_supported"));
        }

        builder.widget(new ImageWidget(173, 143, 18, 18, GuiTextures.BUTTON_NO_DISTINCT_BUSES)
                .setTooltip("gregtech.multiblock.universal.distinct_not_supported"));

        // Imprint slot
        builder.widget(new SlotWidget(controllerSlot, 0, 173, 161)
                .setBackgroundTexture(GuiTextures.SLOT));

        // Flex Button
        builder.widget(getFlexButton(173, 125, 18, 18));

        builder.bindPlayerInventory(entityPlayer.inventory, 125);

        return builder.build(getHolder(), entityPlayer);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (getRecipeMapIndex() == 0) {
            if (imprintStack.isEmpty()) {
                textList.add(new TextComponentTranslation("zbgt.machine.cal.no_imprint"));
            } else {
                textList.add(new TextComponentTranslation("zbgt.machine.cal.imprint",
                        new TextComponentTranslation(imprintStack.getTranslationKey() + ".name")));
            }
        }
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
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        ItemStack controllerStack = controllerSlot.getStackInSlot(0);
        if (!controllerStack.isEmpty()) {
            itemBuffer.add(controllerStack);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        NBTTagCompound tag = super.writeToNBT(data);
        GTUtility.writeItems(controllerSlot, "imprint", data);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        GTUtility.readItems(controllerSlot, "imprint", data);
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }

    private static class CALRecipeLogic extends MultiblockRecipeLogic {

        public CALRecipeLogic(MTECircuitAssemblyLine tileEntity) {
            super(tileEntity, true);
        }

        @Override
        public long getMaxVoltage() {
            if (((MTECircuitAssemblyLine) metaTileEntity).getRecipeMapIndex() == 0) {
                return super.getMaxVoltage();
            } else {
                IEnergyContainer energyContainer = getEnergyContainer();
                int tier = GTUtility.getTierByVoltage(energyContainer.getInputVoltage());
                return GTValues.V[tier - 1];
            }
        }

        // Ugly copy but oh well not my problem
        @Override
        protected boolean setupAndConsumeRecipeInputs(@NotNull Recipe recipe,
                                                      @NotNull IItemHandlerModifiable importInventory,
                                                      @NotNull IMultipleTankHandler importFluids) {
            this.overclockResults = calculateOverclock(recipe);

            modifyOverclockPost(overclockResults, recipe.getRecipePropertyStorage());

            if (!hasEnoughPower(overclockResults)) return false;

            IItemHandlerModifiable exportInventory = getOutputInventory();
            IMultipleTankHandler exportFluids = getOutputTank();

            // We have already trimmed outputs and chanced outputs at this time
            // Attempt to merge all outputs + chanced outputs into the output bus, to prevent voiding chanced outputs
            if (!metaTileEntity.canVoidRecipeItemOutputs() &&
                    !GTTransferUtils.addItemsToItemHandler(exportInventory, true, recipe.getAllItemOutputs())) {
                this.isOutputsFull = true;
                return false;
            }

            // We have already trimmed fluid outputs at this time
            if (!metaTileEntity.canVoidRecipeFluidOutputs() &&
                    !GTTransferUtils.addFluidsToFluidHandler(exportFluids, true, recipe.getAllFluidOutputs())) {
                this.isOutputsFull = true;
                return false;
            }

            this.isOutputsFull = false;
            if (recipe.matches(false, importInventory, importFluids)) {
                this.consumeInputs(recipe);
                this.metaTileEntity.addNotifiedInput(importInventory);
                return true;
            }

            return false;
        }

        private void consumeInputs(Recipe recipe) {
            if (!ConfigHolder.machines.orderedAssembly) {
                recipe.matches(true, metaTileEntity.getImportItems(), metaTileEntity.getImportFluids());
                return;
            }

            List<GTRecipeInput> ingredients = recipe.getInputs();
            List<IItemHandlerModifiable> buses = ((RecipeMapMultiblockController) metaTileEntity)
                    .getAbilities(MultiblockAbility.IMPORT_ITEMS);
            for (int i = 0; i < Math.min(ingredients.size(), buses.size()); i++) {
                IItemHandlerModifiable bus = buses.get(i);
                GTRecipeInput ingredient = ingredients.get(i);
                int amount = ingredient.getAmount();
                for (int j = 0; j < bus.getSlots(); j++) {
                    ItemStack stack = bus.getStackInSlot(j);
                    if (ingredient.acceptsStack(stack)) {
                        amount -= bus.extractItem(j, amount, false).getCount();
                    }
                    if (amount == 0) break;
                }
            }

            IMultipleTankHandler hatches = getInputTank();
            ingredients = recipe.getFluidInputs();
            for (int i = 0; i < ingredients.size(); i++) {
                GTRecipeInput ingredient = ingredients.get(i);
                int amount = ingredient.getAmount();
                for (int j = 0; j < hatches.getTanks(); j++) {
                    FluidStack stack = hatches.getTankAt(i).getFluid();
                    if (ingredient.acceptsFluid(stack)) {
                        FluidStack drain = hatches.getTankAt(i).drain(amount, true);
                        if (drain != null) amount -= drain.amount;
                    }
                    if (amount == 0) break;
                }
            }
        }
    }
}
