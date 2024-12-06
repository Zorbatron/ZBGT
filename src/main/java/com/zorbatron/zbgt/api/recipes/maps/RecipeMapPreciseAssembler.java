package com.zorbatron.zbgt.api.recipes.maps;

import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.core.sound.GTSoundEvents;

public class RecipeMapPreciseAssembler<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapPreciseAssembler(@NotNull String unlocalizedName, @NotNull R defaultRecipeBuilder) {
        super(unlocalizedName, 4, 1, 4, 0, defaultRecipeBuilder, false);
        setSound(GTSoundEvents.ASSEMBLER);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 176)
                .widget(new ProgressWidget(100, 85, 30, 20, 20, GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE,
                        ProgressWidget.MoveType.HORIZONTAL));

        // :clueless:
        addSlot(builder, 8, 14, 0, importItems, importFluids, false, false);
        addSlot(builder, 8 + 18, 14, 1, importItems, importFluids, false, false);
        addSlot(builder, 8 + 18 * 2, 14, 2, importItems, importFluids, false, false);
        addSlot(builder, 8 + 18 * 3, 14, 3, importItems, importFluids, false, false);

        addSlot(builder, 8, 11 + 20 + 18, 0, importItems, importFluids, true, false);
        addSlot(builder, 8 + 18, 11 + 20 + 18, 1, importItems, importFluids, true, false);
        addSlot(builder, 8 + 18 * 2, 11 + 20 + 18, 2, importItems, importFluids, true, false);
        addSlot(builder, 8 + 18 * 3, 11 + 20 + 18, 3, importItems, importFluids, true, false);

        addSlot(builder, 85 + 18 + 12, 31, 0, exportItems, exportFluids, false, true);

        return builder;
    }
}
