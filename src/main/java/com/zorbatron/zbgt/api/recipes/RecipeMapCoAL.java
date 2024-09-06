package com.zorbatron.zbgt.api.recipes;

import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.render.ZBGTTextures;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.core.sound.GTSoundEvents;

public class RecipeMapCoAL<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapCoAL(@NotNull String unlocalizedName, @NotNull R defaultRecipeBuilder) {
        super(unlocalizedName, 12, 1, 12, 1, defaultRecipeBuilder, false);
        setSound(GTSoundEvents.ASSEMBLER);
    }

    @Override
    @NotNull
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 176)
                .widget(new ProgressWidget(100, 62, 0, 72, 20, ZBGTTextures.PROGRESS_BAR_COMPONENT_AL,
                        ProgressWidget.MoveType.HORIZONTAL));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        return builder;
    }

    @Override
    protected void addInventorySlotGroup(@NotNull ModularUI.Builder builder,
                                         @NotNull IItemHandlerModifiable itemHandler,
                                         @NotNull FluidTankList fluidHandler, boolean isOutputs, int yOffset) {
        int startInputsX = 80 - 4 * 18;
        int startInputsY = 37 - 2 * 18;

        if (!isOutputs) {
            // item input slots
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    int slotIndex = i * 3 + j;
                    addSlot(builder, startInputsX + 18 * j, startInputsY + 18 * i, slotIndex, itemHandler, fluidHandler,
                            false, false);
                }
            }

            int startFluidX = startInputsX + 18 * 4;
            int startFluidY = startInputsY + 18;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    int slotIndex = i * 4 + j;
                    addSlot(builder, startFluidX + 18 * j, startFluidY + 18 * i, slotIndex, itemHandler, fluidHandler,
                            true, false);
                }
            }

        } else {
            // output slot
            addSlot(builder, startInputsX + 18 * 7, 1, 0, itemHandler, fluidHandler, false, true);
        }
    }
}
