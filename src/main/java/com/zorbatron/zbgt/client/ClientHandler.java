package com.zorbatron.zbgt.client;

import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class ClientHandler {

    public static SimpleOverlayRenderer WATER_OVERLAY_INFINITY;

    public static void preInit() {
        WATER_OVERLAY_INFINITY = new SimpleOverlayRenderer("overlay/machine/overlay_water_infinity");
    }
}
