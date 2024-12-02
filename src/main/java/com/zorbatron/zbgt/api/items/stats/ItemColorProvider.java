package com.zorbatron.zbgt.api.items.stats;

import gregtech.api.items.metaitem.stats.IItemComponent;

public class ItemColorProvider implements IItemComponent {

    private final int color;

    public ItemColorProvider() {
        this.color = 0x0;
    }

    public ItemColorProvider(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }
}
