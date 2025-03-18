package com.zorbatron.zbgt.api.util;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.ZBGTCore;

import gregtech.api.capability.INotifiableHandler;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;

@SuppressWarnings("unused")
public final class ZBGTUtility {

    public static @NotNull ResourceLocation zbgtId(@NotNull String path) {
        return new ResourceLocation(ZBGTCore.MODID, path);
    }

    public static final int[] intV = { 8, 32, 128, 512, 2048, 8192, 32768, 131072, 524288, 2097152, 8388608, 33554432,
            134217728, 536870912, Integer.MAX_VALUE };

    public static void getCircuitSlotTooltip(@NotNull SlotWidget widget,
                                             GhostCircuitItemStackHandler circuitItemStackHandler) {
        String configString;
        if (circuitItemStackHandler.getCircuitValue() == GhostCircuitItemStackHandler.NO_CONFIG) {
            configString = new TextComponentTranslation("gregtech.gui.configurator_slot.no_value").getFormattedText();
        } else {
            configString = String.valueOf(circuitItemStackHandler.getCircuitValue());
        }

        widget.setTooltipText("gregtech.gui.configurator_slot.tooltip", configString);
    }

    public static boolean isInventoryEmpty(IItemHandler inventory) {
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty()) return false;
        }

        return true;
    }

    public static void addNotifiableToMTE(ItemHandlerList itemHandlerList, MultiblockControllerBase controllerBase,
                                          MetaTileEntity sourceMTE, boolean isExport) {
        for (IItemHandler handler : itemHandlerList.getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiableHandler) {
                notifiableHandler.addNotifiableMetaTileEntity(controllerBase);
                notifiableHandler.addToNotifiedList(sourceMTE, handler, isExport);
            }
        }
    }

    public static void removeNotifiableFromMTE(ItemHandlerList itemHandlerList,
                                               MultiblockControllerBase controllerBase) {
        for (IItemHandler handler : itemHandlerList.getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiableHandler) {
                notifiableHandler.removeNotifiableMetaTileEntity(controllerBase);
            }
        }
    }

    public static void addNotifiableToMTE(INotifiableHandler notifiableHandler, MultiblockControllerBase controllerBase,
                                          MetaTileEntity sourceMTE, boolean isExport) {
        notifiableHandler.addNotifiableMetaTileEntity(controllerBase);
        notifiableHandler.addToNotifiedList(sourceMTE, notifiableHandler, isExport);
    }

    public static void removeNotifiableFromMTE(INotifiableHandler notifiableHandler,
                                               MultiblockControllerBase controllerBase) {
        notifiableHandler.removeNotifiableMetaTileEntity(controllerBase);
    }

    public static <K, V> V computeIfAbsentDiffKey(Map<K, V> map, K key, Supplier<K> keyToPut,
                                                  Function<? super K, ? extends V> mappingFunction) {
        V v;
        if ((v = map.get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                map.put(keyToPut.get(), newValue);
                return newValue;
            }
        }

        return v;
    }

    public static int combineRGB(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
}
