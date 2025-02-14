package com.zorbatron.zbgt.client.widgets;

import java.util.function.Function;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.PhantomFluidWidget;
import gregtech.api.gui.widgets.WidgetGroup;

public class FilterTestFluidSlot extends WidgetGroup {

    private final ImageWidget match;
    private final ImageWidget noMatch;

    @Nullable
    private FluidStack testStack;

    @NotNull
    private final Function<FluidStack, Boolean> fluidMatcher;

    public FilterTestFluidSlot(int x, int y, @NotNull Function<FluidStack, Boolean> fluidMatcher) {
        super(x, y, 18, 18);
        this.match = new ImageWidget(18 - 5, -3, 9, 6, GuiTextures.ORE_FILTER_MATCH);
        this.noMatch = new ImageWidget(18 - 5, -3, 7, 7, GuiTextures.ORE_FILTER_NO_MATCH);
        PhantomFluidWidget tankWidget = new PhantomFluidWidget(0, 0, 18, 18, () -> testStack,
                fluidStack -> {
                    testStack = fluidStack;
                    update();
                });
        tankWidget.setBackgroundTexture(GuiTextures.FLUID_SLOT);

        this.fluidMatcher = fluidMatcher;

        addWidget(tankWidget);
        addWidget(match);
        addWidget(noMatch);
    }

    public void update() {
        boolean fluidMatches;
        if (testStack != null) {
            fluidMatches = fluidMatcher.apply(testStack);
        } else fluidMatches = false;
        match.setVisible(fluidMatches);
        noMatch.setVisible(!fluidMatches);
    }
}
