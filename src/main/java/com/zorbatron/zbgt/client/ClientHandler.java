package com.zorbatron.zbgt.client;

import gregtech.api.gui.resources.TextureArea;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class ClientHandler {

    public static SimpleOverlayRenderer WATER_OVERLAY_INFINITY;
    public static SimpleOverlayRenderer ITEM_OVERLAY_INFINITY;

    public static TextureArea ITEM_FLUID_OVERLAY;
    public static TextureArea AUTO_PULL;

    public static OrientedOverlayRenderer GTPP_MACHINE_OVERLAY;

    public static void preInit() {
        WATER_OVERLAY_INFINITY = new SimpleOverlayRenderer("overlay/machine/overlay_water_infinity");
        ITEM_OVERLAY_INFINITY = new SimpleOverlayRenderer("overlay/machine/overlay_item_infinity");

        ITEM_FLUID_OVERLAY = TextureArea.fullImage("textures/gui/widget/item_fluid.png");
        AUTO_PULL = TextureArea.fullImage("textures/gui/widget/auto_pull.png");

        GTPP_MACHINE_OVERLAY = new OrientedOverlayRenderer("multiblock/mega_abs");
    }
}
