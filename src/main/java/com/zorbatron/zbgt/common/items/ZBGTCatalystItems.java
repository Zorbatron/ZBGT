package com.zorbatron.zbgt.common.items;

import gregtech.api.items.metaitem.MetaItem;

public class ZBGTCatalystItems {

    public static MetaItem<?>.MetaValueItem EMPTY_CATALYST;

    public static MetaItem<?>.MetaValueItem GREEN_CATALYST;
    public static MetaItem<?>.MetaValueItem RED_CATALYST;
    public static MetaItem<?>.MetaValueItem YELLOW_CATALYST;
    public static MetaItem<?>.MetaValueItem BLUE_CATALYST;
    public static MetaItem<?>.MetaValueItem ORANGE_CATALYST;
    public static MetaItem<?>.MetaValueItem PURPLE_CATALYST;
    public static MetaItem<?>.MetaValueItem BROWN_CATALYST;
    public static MetaItem<?>.MetaValueItem PINK_CATALYST;

    public static void init() {
        ZBGTCatalystItem catalystItem = new ZBGTCatalystItem();
        catalystItem.setRegistryName("zbgt_catalyst_item");
    }
}
