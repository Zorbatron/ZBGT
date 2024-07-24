package com.zorbatron.zbgt.client.widgets;

import java.util.function.BooleanSupplier;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ServerWidgetGroup;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.util.IDirtyNotifiable;
import gregtech.common.covers.filter.ItemFilterContainer;

public class HideableItemFilterContainer extends ItemFilterContainer {

    public HideableItemFilterContainer(IDirtyNotifiable dirtyNotifiable) {
        super(dirtyNotifiable);
    }

    public void initUI(int y, ServerWidgetGroup serverWidgetGroup, BooleanSupplier isVisibleGetter) {
        serverWidgetGroup.addWidget(new LabelWidget(10, y, "cover.conveyor.item_filter.title"));

        SlotWidget filterHolder = new HideableSlotWidget(getFilterInventory(), 0, 10, y + 15, isVisibleGetter)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.FILTER_SLOT_OVERLAY);
        serverWidgetGroup.addWidget(filterHolder);

        this.getFilterWrapper().initUI(y + 38, serverWidgetGroup::addWidget);
        this.getFilterWrapper().blacklistUI(y + 38, serverWidgetGroup::addWidget, () -> true);
    }
}
