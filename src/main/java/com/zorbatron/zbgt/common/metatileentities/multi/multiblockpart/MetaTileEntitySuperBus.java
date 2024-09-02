package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.Position;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntitySuperBus extends MetaTileEntityMultiblockNotifiablePart
                                    implements IMultiblockAbilityPart<IItemHandlerModifiable> {

    public MetaTileEntitySuperBus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.HV, false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySuperBus(metaTileEntityId);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 16, null, false) {

            @Override
            public int getSlotLimit(int slot) {
                return Integer.MAX_VALUE;
            }

            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                return getSlotLimit(slot);
            }

            @NotNull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (amount == 0) return ItemStack.EMPTY;

                validateSlotIndex(slot);

                ItemStack existing = this.stacks.get(slot);

                if (existing.isEmpty()) return ItemStack.EMPTY;

                if (existing.getCount() <= amount) {
                    if (!simulate) {
                        this.stacks.set(slot, ItemStack.EMPTY);
                        onContentsChanged(slot);
                    }

                    return existing;
                } else {
                    if (!simulate) {
                        this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(
                                existing, existing.getCount() - amount));
                        onContentsChanged(slot);
                    }

                    return ItemHandlerHelper.copyStackWithSize(existing, amount);
                }
            }
        };
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.defaultBuilder().label(6, 6, getMetaFullName());

        WidgetGroup slots = new WidgetGroup(new Position(6, 18));

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                int index = y * 4 + x;

            }
        }

        return builder.bindPlayerInventory(entityPlayer.inventory)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.add(this.importItems);
    }
}
