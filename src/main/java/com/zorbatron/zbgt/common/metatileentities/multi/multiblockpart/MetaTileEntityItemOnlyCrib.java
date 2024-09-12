package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import org.jetbrains.annotations.NotNull;

import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntityItemOnlyCrib extends MetaTileEntityMultiblockNotifiablePart
                                        implements IMultiblockAbilityPart<IItemHandlerModifiable> {

    private GhostCircuitItemStackHandler circuit;
    private IItemHandlerModifiable sharedItems;

    public MetaTileEntityItemOnlyCrib(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier, false);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
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
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {}

    public class PatternSlot implements IItemHandlerModifiable {

        private final ItemStack pattern;
        private final ICraftingPatternDetails patternDetails;
        private final List<ItemStack> stacks;

        public PatternSlot(ItemStack pattern, World world) {
            this.pattern = pattern;
            this.patternDetails = ((ICraftingPatternItem) Objects.requireNonNull(pattern.getItem()))
                    .getPatternForItem(pattern, world);
            this.stacks = new ArrayList<>();
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
