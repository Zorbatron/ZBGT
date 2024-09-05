package com.zorbatron.zbgt.api.render;

import gregtech.api.gui.resources.TextureArea;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class ZBGTTextures {

    // Multiblock part overlays
    public static SimpleOverlayRenderer WATER_OVERLAY_INFINITY;
    public static SimpleOverlayRenderer ITEM_OVERLAY_INFINITY;

    // GUI overlays
    public static TextureArea ITEM_FLUID_OVERLAY;
    public static TextureArea AUTO_PULL;

    // Multiblock controller overlays
    public static OrientedOverlayRenderer GTPP_MACHINE_OVERLAY;

    // Casings
    public static SimpleOverlayRenderer PRECISE_CASING_0;
    public static SimpleOverlayRenderer PRECISE_CASING_1;
    public static SimpleOverlayRenderer PRECISE_CASING_2;
    public static SimpleOverlayRenderer PRECISE_CASING_3;
    public static SimpleOverlayRenderer PRECISE_CASING_4;

    public static void preInit() {
        WATER_OVERLAY_INFINITY = new SimpleOverlayRenderer("overlay/machine/overlay_water_infinity");
        ITEM_OVERLAY_INFINITY = new SimpleOverlayRenderer("overlay/machine/overlay_item_infinity");

        ITEM_FLUID_OVERLAY = TextureArea.fullImage("textures/gui/widget/item_fluid.png");
        AUTO_PULL = TextureArea.fullImage("textures/gui/widget/auto_pull.png");

        GTPP_MACHINE_OVERLAY = new OrientedOverlayRenderer("multiblock/mega_abs");

        PRECISE_CASING_0 = new SimpleOverlayRenderer("multiblock_casing/precise_casing_0");
        PRECISE_CASING_1 = new SimpleOverlayRenderer("multiblock_casing/precise_casing_1");
        PRECISE_CASING_2 = new SimpleOverlayRenderer("multiblock_casing/precise_casing_2");
        PRECISE_CASING_3 = new SimpleOverlayRenderer("multiblock_casing/precise_casing_3");
        PRECISE_CASING_4 = new SimpleOverlayRenderer("multiblock_casing/precise_casing_4");
    }
}
