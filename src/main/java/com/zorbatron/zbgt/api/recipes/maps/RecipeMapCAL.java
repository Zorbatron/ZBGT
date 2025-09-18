package com.zorbatron.zbgt.api.recipes.maps;

import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.ModularUI;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;

public class RecipeMapCAL<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapCAL(@NotNull String unlocalizedName, @NotNull R defaultRecipeBuilder) {
        super(unlocalizedName, 7, 1, 1, 0, defaultRecipeBuilder, false);
    }

    @Override
    protected void addInventorySlotGroup(ModularUI.Builder builder, IItemHandlerModifiable itemHandler,
                                         FluidTankList fluidHandler, boolean isOutputs, int yOffset) {
        int itemInputsCount = itemHandler.getSlots();
        int fluidInputsCount = fluidHandler.getTanks();

        boolean invertFluids = false;
        if (itemInputsCount == 0) {
            int tmp = itemInputsCount;
            itemInputsCount = fluidInputsCount;
            fluidInputsCount = tmp;
            invertFluids = true;
        }

        int itemSlotsToLeft = 3;
        int itemSlotsToDown = 2;
        int startInputsX = isOutputs ? 106 : 70 - itemSlotsToLeft * 18;
        int startInputsY = 33 - (int) (itemSlotsToDown / 2.0 * 18) + yOffset;

        boolean wasGroup = itemHandler.getSlots() + fluidHandler.getTanks() == 12;
        if (wasGroup) {
            startInputsY -= 9;
        } else {
            if (itemHandler.getSlots() >= 6 && fluidHandler.getTanks() >= 2 && !isOutputs) startInputsY -= 9;
        }

        for (int i = 0; i < itemSlotsToDown; i++) {
            for (int j = 0; j < itemSlotsToLeft; j++) {
                int slotIndex = i * itemSlotsToLeft + j;
                if (slotIndex >= itemInputsCount) break;
                int x = startInputsX + 18 * j;
                int y = startInputsY + 18 * i;
                addSlot(builder, x, y, slotIndex, itemHandler, fluidHandler, invertFluids, isOutputs);
            }
        }

        if (wasGroup) startInputsY += 2;
        if (fluidInputsCount > 0 || invertFluids) {
            int startSpecY = startInputsY + itemSlotsToDown * 18;
            for (int i = 0; i < fluidInputsCount; i++) {
                int x = isOutputs ? startInputsX + 18 * (i % 3) :
                        startInputsX + itemSlotsToLeft * 18 - 18 - 18 * (i % 3);
                int y = startSpecY + (i / 3) * 18;
                addSlot(builder, x, y, i, itemHandler, fluidHandler, true, isOutputs);
            }
        }

        if (!isOutputs) {
            addSlot(builder, 18 * 6 - 2, yOffset + 16 * 3 + 3, 6, itemHandler, fluidHandler, false, false);
        }
    }
}
