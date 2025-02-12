package com.zorbatron.zbgt.common.covers;

import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;

import com.zorbatron.zbgt.api.util.ZBGTLog;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;

import gregtech.api.GTValues;
import gregtech.common.covers.CoverBehaviors;

public final class ZBGTCovers {

    public static void init() {
        ZBGTLog.logger.info("Registering cover behaviors...");

        CoverBehaviors.registerBehavior(zbgtId("dual_cover.lv"), ZBGTMetaItems.DUAL_COVER_LV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.LV, 8, 1280));
        CoverBehaviors.registerBehavior(zbgtId("dual_cover.mv"), ZBGTMetaItems.DUAL_COVER_MV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.MV, 32, 1280 * 4));
        CoverBehaviors.registerBehavior(zbgtId("dual_cover.hv"), ZBGTMetaItems.DUAL_COVER_HV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.HV, 64, 1280 * 16));
        CoverBehaviors.registerBehavior(zbgtId("dual_cover.ev"), ZBGTMetaItems.DUAL_COVER_EV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.EV, 3 * 64, 1280 * 64));
        CoverBehaviors.registerBehavior(zbgtId("dual_cover.iv"), ZBGTMetaItems.DUAL_COVER_IV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.IV, 8 * 64, 1280 * 64 * 4));
        CoverBehaviors.registerBehavior(zbgtId("dual_cover.luv"), ZBGTMetaItems.DUAL_COVER_LuV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.LuV, 16 * 64, 1280 * 64 * 16));
        CoverBehaviors.registerBehavior(zbgtId("dual_cover.zpm"), ZBGTMetaItems.DUAL_COVER_ZPM,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.ZPM, 16 * 64, 1280 * 64 * 64));
        CoverBehaviors.registerBehavior(zbgtId("dual_cover.uv"), ZBGTMetaItems.DUAL_COVER_UV,
                (def, tile, side) -> new CoverDualCover(def, tile, side, GTValues.UV, 16 * 64, 1280 * 64 * 64 * 4));

        CoverBehaviors.registerBehavior(zbgtId("precise_dual_cover.lv"), ZBGTMetaItems.PRECISE_DUAL_COVER_LV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.LV, 8, 1280));
        CoverBehaviors.registerBehavior(zbgtId("precise_dual_cover.mv"), ZBGTMetaItems.PRECISE_DUAL_COVER_MV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.MV, 32, 1280 * 4));
        CoverBehaviors.registerBehavior(zbgtId("precise_dual_cover.hv"), ZBGTMetaItems.PRECISE_DUAL_COVER_HV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.HV, 64, 1280 * 16));
        CoverBehaviors.registerBehavior(zbgtId("precise_dual_cover.ev"), ZBGTMetaItems.PRECISE_DUAL_COVER_EV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.EV, 3 * 64, 1280 * 64));
        CoverBehaviors.registerBehavior(zbgtId("precise_dual_cover.iv"), ZBGTMetaItems.PRECISE_DUAL_COVER_IV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.IV, 8 * 64, 1280 * 64 * 4));
        CoverBehaviors.registerBehavior(zbgtId("precise_dual_cover.luv"), ZBGTMetaItems.PRECISE_DUAL_COVER_LuV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.LuV, 16 * 64, 1280 * 64 * 16));
        CoverBehaviors.registerBehavior(zbgtId("precise_dual_cover.zpm"), ZBGTMetaItems.PRECISE_DUAL_COVER_ZPM,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.ZPM, 16 * 64, 1280 * 64 * 64));
        CoverBehaviors.registerBehavior(zbgtId("precise_dual_cover.uv"), ZBGTMetaItems.PRECISE_DUAL_COVER_UV,
                (def, tile, side) -> new CoverPreciseDualCover(def, tile, side, GTValues.UV, 16 * 64,
                        1280 * 64 * 64 * 4));

        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.lv"), ZBGTMetaItems.DROPPER_COVER_LV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.mv"), ZBGTMetaItems.DROPPER_COVER_MV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 2));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.hv"), ZBGTMetaItems.DROPPER_COVER_HV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 4));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.ev"), ZBGTMetaItems.DROPPER_COVER_EV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 8));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.iv"), ZBGTMetaItems.DROPPER_COVER_IV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 16));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.luv"), ZBGTMetaItems.DROPPER_COVER_LuV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 32));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.zpm"), ZBGTMetaItems.DROPPER_COVER_ZPM,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 64));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.uv"), ZBGTMetaItems.DROPPER_COVER_UV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 128));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.uhv"), ZBGTMetaItems.DROPPER_COVER_UHV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 256));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.uev"), ZBGTMetaItems.DROPPER_COVER_UEV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 512));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.uiv"), ZBGTMetaItems.DROPPER_COVER_UIV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 1024));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.uxv"), ZBGTMetaItems.DROPPER_COVER_UXV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 2048));
        CoverBehaviors.registerBehavior(zbgtId("cover_dropper.opv"), ZBGTMetaItems.DROPPER_COVER_OpV,
                (def, tile, side) -> new CoverDropper(def, tile, side, 8 * 4096));
    }
}
