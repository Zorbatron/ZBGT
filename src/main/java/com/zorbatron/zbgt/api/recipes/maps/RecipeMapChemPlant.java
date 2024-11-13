package com.zorbatron.zbgt.api.recipes.maps;

import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;

public class RecipeMapChemPlant<R extends RecipeBuilder<R>> extends RecipeMap<R> {

    public RecipeMapChemPlant(@NotNull String unlocalizedName, @NotNull R defaultRecipeBuilder) {
        super(unlocalizedName, 4, 6, 4, 3, defaultRecipeBuilder, false);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.defaultBuilder(yOffset);
        builder.widget(new RecipeProgressWidget(200, 18 * 5 - 2, 7 + 18 + yOffset + 2, 20, 20,
                GuiTextures.PROGRESS_BAR_MIXER, ProgressWidget.MoveType.CIRCULAR, this));

        for (int x = 0; x < 4; x++) {
            builder.widget(new SlotWidget(importItems, x, 7 + x * 18, yOffset + 10)
                    .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.MOLECULAR_OVERLAY_1));
        }

        for (int x = 0; x < 4; x++) {
            builder.widget(new TankWidget(importFluids.getTankAt(x), 7 + x * 18, yOffset + 10 + 36, 18, 18)
                    .setBackgroundTexture(GuiTextures.FLUID_SLOT, GuiTextures.MOLECULAR_OVERLAY_4));
        }

        for (int x = 0; x < 3; x++) {
            builder.widget(new SlotWidget(exportItems, x * 2, 7 + 6 * 18, yOffset + 10 + 18 * x)
                    .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.VIAL_OVERLAY_1));
            builder.widget(new SlotWidget(exportItems, x * 2 + 1, 7 + 7 * 18, yOffset + 10 + 18 * x)
                    .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.VIAL_OVERLAY_1));
            builder.widget(new TankWidget(exportFluids.getTankAt(x), 7 + 8 * 18, yOffset + 10 + 18 * x, 18, 18)
                    .setBackgroundTexture(GuiTextures.FLUID_SLOT, GuiTextures.VIAL_OVERLAY_2));
        }

        return builder;
    }
}
