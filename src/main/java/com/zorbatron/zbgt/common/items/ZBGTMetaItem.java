package com.zorbatron.zbgt.common.items;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.zorbatron.zbgt.ZBUtility;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.common.items.behaviors.TooltipBehavior;

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
        // Dual Covers: 0-12
        DUAL_COVER_LV = addItem(0, "cover.dual_cover.lv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 8, 1280 / 20));
        }));
        DUAL_COVER_MV = addItem(1, "cover.dual_cover.mv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 32, 1280 * 4 / 20));
        }));
        DUAL_COVER_HV = addItem(2, "cover.dual_cover.hv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 64, 1280 * 16 / 20));
        }));
        DUAL_COVER_EV = addItem(3, "cover.dual_cover.ev").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 3, 1280 * 64 / 20));
        }));
        DUAL_COVER_IV = addItem(4, "cover.dual_cover.iv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 8, 1280 * 64 * 4 / 20));
        }));
        DUAL_COVER_LuV = addItem(5, "cover.dual_cover.luv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 16 / 20));
        }));
        DUAL_COVER_ZPM = addItem(6, "cover.dual_cover.zpm").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 / 20));
        }));
        DUAL_COVER_UV = addItem(7, "cover.dual_cover.uv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        }));
        DUAL_COVER_UHV = addItem(8, "cover.dual_cover.uhv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());
        DUAL_COVER_UEV = addItem(9, "cover.dual_cover.uev").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());
        DUAL_COVER_UIV = addItem(10, "cover.dual_cover.uiv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());
        DUAL_COVER_UXV = addItem(11, "cover.dual_cover.uxv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());
        DUAL_COVER_OpV = addItem(12, "cover.dual_cover.opv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());

        // Precise Dual Covers: 13-25
        PRECISE_DUAL_COVER_LV = addItem(13, "cover.precise_dual_cover.lv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 8, 1280 / 20));
        }));
        PRECISE_DUAL_COVER_MV = addItem(14, "cover.precise_dual_cover.mv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 32, 1280 * 4 / 20));
        }));
        PRECISE_DUAL_COVER_HV = addItem(15, "cover.precise_dual_cover.hv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 64, 1280 * 16 / 20));
        }));
        PRECISE_DUAL_COVER_EV = addItem(16, "cover.precise_dual_cover.ev").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 3, 1280 * 64 / 20));
        }));
        PRECISE_DUAL_COVER_IV = addItem(17, "cover.precise_dual_cover.iv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 8, 1280 * 64 * 4 / 20));
        }));
        PRECISE_DUAL_COVER_LuV = addItem(18, "cover.precise_dual_cover.luv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 16 / 20));
                }));
        PRECISE_DUAL_COVER_ZPM = addItem(19, "cover.precise_dual_cover.zpm")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 / 20));
                }));
        PRECISE_DUAL_COVER_UV = addItem(20, "cover.precise_dual_cover.uv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        }));
        PRECISE_DUAL_COVER_UHV = addItem(21, "cover.precise_dual_cover.uhv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
        PRECISE_DUAL_COVER_UEV = addItem(22, "cover.precise_dual_cover.uev")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
        PRECISE_DUAL_COVER_UIV = addItem(23, "cover.precise_dual_cover.uiv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
        PRECISE_DUAL_COVER_UXV = addItem(24, "cover.precise_dual_cover.uxv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
        PRECISE_DUAL_COVER_OpV = addItem(25, "cover.precise_dual_cover.opv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
    }
}
