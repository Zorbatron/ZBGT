package com.zorbatron.zbgt.client.widgets;

import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.PhantomFluidWidget;
import gregtech.api.gui.widgets.WidgetGroup;

public class FilterTestFluidSlot extends WidgetGroup {

    private boolean fluidMatches = false;

    @Nullable
    private FluidStack testStack;

    @NotNull
    private final Function<FluidStack, Boolean> fluidMatcher;

    public static void createTestSlotArray(int startX, int startY, Consumer<Widget> widgetGroup,
                                           FilterTestFluidSlot[] testSlots, Function<FluidStack, Boolean> testMethod) {
        for (int x = 0; x < 5; x++) {
            FilterTestFluidSlot testSlot = new FilterTestFluidSlot(startX + startY * x, 0, testMethod);
            testSlots[x] = testSlot;
            widgetGroup.accept(testSlot);
        }
    }

    public static void createTestSlotArray(Consumer<Widget> widgetGroup, FilterTestFluidSlot[] testSlots,
                                           Function<FluidStack, Boolean> testMethod) {
        createTestSlotArray(10, 22, widgetGroup, testSlots, testMethod);
    }

    public FilterTestFluidSlot(int x, int y, @NotNull Function<FluidStack, Boolean> fluidMatcher) {
        super(x, y, 18, 18);
        ImageWidget match = new ImageWidget(18 - 5, -3, 9, 6, GuiTextures.ORE_FILTER_MATCH);
        ImageWidget noMatch = new ImageWidget(18 - 5, -3, 7, 7, GuiTextures.ORE_FILTER_NO_MATCH);
        match.setPredicate(() -> fluidMatches);
        noMatch.setPredicate(() -> !fluidMatches);

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
        if (testStack != null) {
            fluidMatches = fluidMatcher.apply(testStack);
        } else fluidMatches = false;
    }
}
