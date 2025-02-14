package com.zorbatron.zbgt.client.widgets;

import java.util.function.Function;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.PhantomSlotWidget;
import gregtech.api.gui.widgets.WidgetGroup;

public class FilterTestSlot extends WidgetGroup {

    private final ImageWidget match;
    private final ImageWidget noMatch;

    private final IItemHandlerModifiable handler;

    @NotNull
    private final Function<ItemStack, Boolean> itemMatcher;

    public FilterTestSlot(int x, int y, @NotNull Function<ItemStack, Boolean> itemMatcher) {
        super(x, y, 18, 18);
        this.match = new ImageWidget(18 - 5, -3, 9, 6, GuiTextures.ORE_FILTER_MATCH);
        this.noMatch = new ImageWidget(18 - 5, -3, 7, 7, GuiTextures.ORE_FILTER_NO_MATCH);
        this.handler = new ItemStackHandler(1);
        PhantomSlotWidget slotWidget = new PhantomSlotWidget(handler, 0, 0, 0);
        slotWidget.setBackgroundTexture(GuiTextures.SLOT);
        slotWidget.setChangeListener(this::update);

        this.itemMatcher = itemMatcher;

        addWidget(slotWidget);
        addWidget(this.match);
        addWidget(this.noMatch);
    }

    public void update() {
        boolean itemMatches = itemMatcher.apply(handler.getStackInSlot(0));
        match.setVisible(itemMatches);
        noMatch.setVisible(!itemMatches);
    }
}
