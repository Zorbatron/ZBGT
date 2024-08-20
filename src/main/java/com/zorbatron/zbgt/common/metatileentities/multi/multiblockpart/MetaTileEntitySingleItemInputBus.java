package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityItemBus;

public class MetaTileEntitySingleItemInputBus extends MetaTileEntityItemBus {

    private final int stackSizeLimit = 1;
    private final int slotSize = 1;

    public MetaTileEntitySingleItemInputBus(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.ULV, false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySingleItemInputBus(metaTileEntityId);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, slotSize, getController(), false) {

            // only allow a single item, no stack size
            @Override
            public int getSlotLimit(int slot) {
                return stackSizeLimit;
            }
        };
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gregtech.machine.item_bus.import.tooltip"));
        tooltip.add(I18n.format("gregtech.universal.tooltip.item_storage_capacity", slotSize));
        tooltip.add(I18n.format("zbgt.machine.single_item_input_bus.stack_size", stackSizeLimit));
        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }
}
