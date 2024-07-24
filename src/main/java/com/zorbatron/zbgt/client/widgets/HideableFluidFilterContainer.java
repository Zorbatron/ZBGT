package com.zorbatron.zbgt.client.widgets;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ServerWidgetGroup;
import gregtech.api.util.IDirtyNotifiable;
import gregtech.common.covers.filter.FluidFilterContainer;

public class HideableFluidFilterContainer extends FluidFilterContainer {

    public HideableFluidFilterContainer(IDirtyNotifiable dirtyNotifiable, Supplier<Boolean> showTip) {
        super(dirtyNotifiable, showTip);
    }

    public HideableFluidFilterContainer(IDirtyNotifiable dirtyNotifiable, Supplier<Boolean> showTip, int maxSize) {
        super(dirtyNotifiable, showTip, maxSize);
    }

    public void initUI(int y, ServerWidgetGroup serverWidgetGroup, BooleanSupplier isVisibleGetter) {
        serverWidgetGroup.addWidget(new LabelWidget(10, y, "cover.pump.fluid_filter.title"));
        serverWidgetGroup.addWidget(new HideableSlotWidget(getFilterInventory(), 0, 10, y + 15, isVisibleGetter)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.FILTER_SLOT_OVERLAY));

        this.getFilterWrapper().initUI(y + 15, serverWidgetGroup::addWidget);
        this.getFilterWrapper().blacklistUI(y + 15, serverWidgetGroup::addWidget, () -> true);
    }
}
