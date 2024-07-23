package com.zorbatron.zbgt.common.items;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;

import gregtech.common.items.behaviors.TooltipBehavior;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.zorbatron.zbgt.ZBUtility;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;

public class ZBGTMetaItem extends StandardMetaItem {

    public ZBGTMetaItem() {
        super();
    }

    @Override
    public ResourceLocation createItemModelPath(MetaItem<?>.MetaValueItem metaValueItem, String postfix) {
        return ZBUtility.zbgtId(this.formatModelPath(metaValueItem) + postfix);
    }

    @Override
    public void registerSubItems() {
        //Dual Covers: 0-12
        DUAL_COVER_LV = addItem(0, "cover.dual_cover.lv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip.transfer_rate", 8, 1280 / 20));
        }));
        DUAL_COVER_MV = addItem(1, "cover.dual_cover.mv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip.transfer_rate", 32, 1280 * 4 / 20));
        }));
        DUAL_COVER_HV = addItem(2, "cover.dual_cover.hv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip.transfer_rate", 64, 1280 * 16 / 20));
        }));
        DUAL_COVER_EV = addItem(3, "cover.dual_cover.ev").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip.transfer_rate.stacks", 3, 1280 * 64 / 20));
        }));
        DUAL_COVER_IV = addItem(4, "cover.dual_cover.iv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip.transfer_rate.stacks", 8, 1280 * 64 * 4 / 20));
        }));
        DUAL_COVER_LuV = addItem(5, "cover.dual_cover.luv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 16 / 20));
        }));
        DUAL_COVER_ZPM = addItem(6, "cover.dual_cover.zpm").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 / 20));
        }));
        DUAL_COVER_UV = addItem(7, "cover.dual_cover.uv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        }));
        //8-12 reserved for higher tier dual covers.

        //Precise Dual Covers: 13-25
    }
}
