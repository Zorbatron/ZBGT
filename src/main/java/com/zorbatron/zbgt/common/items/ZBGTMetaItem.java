package com.zorbatron.zbgt.common.items;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;

import net.minecraft.util.ResourceLocation;

import com.zorbatron.zbgt.ZBUtility;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;

public class ZBGTMetaItem extends StandardMetaItem {

    public int id = 0;

    public ZBGTMetaItem() {
        super();
    }

    @Override
    public ResourceLocation createItemModelPath(MetaItem<?>.MetaValueItem metaValueItem, String postfix) {
        return ZBUtility.zbgtId(this.formatModelPath(metaValueItem) + postfix);
    }

    @Override
    public void registerSubItems() {
        DUAL_COVER_LV = addItem(id, "cover.dual_cover.lv");
        DUAL_COVER_MV = addItem(++id, "cover.dual_cover.mv");
        DUAL_COVER_HV = addItem(++id, "cover.dual_cover.hv");
        DUAL_COVER_EV = addItem(++id, "cover.dual_cover.ev");
        DUAL_COVER_IV = addItem(++id, "cover.dual_cover.iv");
        DUAL_COVER_LuV = addItem(++id, "cover.dual_cover.luv");
        DUAL_COVER_ZPM = addItem(++id, "cover.dual_cover.zpm");
        DUAL_COVER_UV = addItem(++id, "cover.dual_cover.uv");
    }
}
