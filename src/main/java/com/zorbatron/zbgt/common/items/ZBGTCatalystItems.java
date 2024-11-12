package com.zorbatron.zbgt.common.items;

import gregtech.api.items.metaitem.MetaItem;

public class ZBGTCatalystItems {

    public static MetaItem<?>.MetaValueItem EMPTY_CATALYST;

    public static MetaItem<?>.MetaValueItem PINK_CATALYST;
    public static MetaItem<?>.MetaValueItem BLUE_CATALYST;

    public static void init() {
        ZBGTCatalystItem catalystItem = new ZBGTCatalystItem();
        catalystItem.setRegistryName("zbgt_catalyst_item");
    }
}
