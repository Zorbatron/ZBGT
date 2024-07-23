package com.zorbatron.zbgt.common.items;

import gregtech.api.items.metaitem.MetaItem;

public class ZBGTMetaItems {

    public static MetaItem<?>.MetaValueItem DUAL_COVER_LV;
    public static MetaItem<?>.MetaValueItem DUAL_COVER_MV;
    public static MetaItem<?>.MetaValueItem DUAL_COVER_HV;
    public static MetaItem<?>.MetaValueItem DUAL_COVER_EV;
    public static MetaItem<?>.MetaValueItem DUAL_COVER_IV;
    public static MetaItem<?>.MetaValueItem DUAL_COVER_LuV;
    public static MetaItem<?>.MetaValueItem DUAL_COVER_ZPM;
    public static MetaItem<?>.MetaValueItem DUAL_COVER_UV;

    public static void init() {
        ZBGTMetaItem item = new ZBGTMetaItem();
        item.setRegistryName("zbgt_meta_item");
    }
}
