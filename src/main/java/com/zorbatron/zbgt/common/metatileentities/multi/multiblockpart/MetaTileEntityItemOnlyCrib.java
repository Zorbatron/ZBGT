package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.*;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.me.helpers.AENetworkProxy;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntityItemOnlyCrib extends MetaTileEntityMultiblockNotifiablePart
                                        implements IMultiblockAbilityPart<IItemHandlerModifiable> {

    private static final int MAX_PATTERN_COUNT = 4 * 9;

    private GhostCircuitItemStackHandler ghostCircuit;
    private IItemHandlerModifiable sharedItems;
    private final ItemHandlerList[] actualImportItems;
    private final PatternSlot[] internalInventory = new PatternSlot[MAX_PATTERN_COUNT];
    private final Map<ICraftingPatternDetails, PatternSlot> patternDetailsPatternSlotMap;

    private boolean needPatternSync = true;
    private boolean justHadNewItems = false;

    private @Nullable AENetworkProxy proxy;
    private String customName = null;
    private boolean additionalConnection = false;

    public MetaTileEntityItemOnlyCrib(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier, false);
        this.actualImportItems = new ItemHandlerList[MAX_PATTERN_COUNT];
        this.patternDetailsPatternSlotMap = new HashMap<>(MAX_PATTERN_COUNT);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();

        this.sharedItems = new NotifiableItemStackHandler(this, 9, null, false);
        this.ghostCircuit = new GhostCircuitItemStackHandler(this);

        for (int i = 0; i < MAX_PATTERN_COUNT; i++) {
            this.internalInventory[i] = new PatternSlot();
            this.actualImportItems[i] = new ItemHandlerList(
                    Arrays.asList(internalInventory[i], sharedItems, ghostCircuit));
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityItemOnlyCrib(metaTileEntityId, getTier());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.addAll(Arrays.asList(actualImportItems));
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class PatternSlot implements IItemHandlerModifiable {

        private ItemStack pattern;
        private ICraftingPatternDetails patternDetails;
        private final List<ItemStack> stacks;

        public PatternSlot() {
            this.stacks = new ArrayList<>();
        }

        public ICraftingPatternDetails getPatternDetails() {
            return this.patternDetails;
        }

        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            validateSlotIndex(slot);
            this.stacks.set(slot, stack);
        }

        @Override
        public int getSlots() {
            return stacks.size();
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return stacks.get(slot);
        }

        @Override
        @Nonnull
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (stack.isEmpty())
                return ItemStack.EMPTY;

            validateSlotIndex(slot);

            ItemStack existing = this.stacks.get(slot);

            int limit = Integer.MAX_VALUE;

            if (!existing.isEmpty()) {
                if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                    return stack;

                limit -= existing.getCount();
            }

            if (limit <= 0)
                return stack;

            boolean reachedLimit = stack.getCount() > limit;

            if (!simulate) {
                if (existing.isEmpty()) {
                    this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
                } else {
                    existing.grow(reachedLimit ? limit : stack.getCount());
                }
            }

            return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) :
                    ItemStack.EMPTY;
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (amount == 0) return ItemStack.EMPTY;

            validateSlotIndex(slot);

            ItemStack existing = this.stacks.get(slot);

            if (existing.isEmpty()) return ItemStack.EMPTY;

            if (existing.getCount() <= amount) {
                if (!simulate) this.stacks.set(slot, ItemStack.EMPTY);

                return existing;
            } else {
                if (!simulate) this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(
                        existing, existing.getCount() - amount));

                return ItemHandlerHelper.copyStackWithSize(existing, amount);
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return Integer.MAX_VALUE;
        }

        protected void validateSlotIndex(int slot) {
            if (slot < 0 || slot >= stacks.size()) {
                throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
            }
        }
    }
}
