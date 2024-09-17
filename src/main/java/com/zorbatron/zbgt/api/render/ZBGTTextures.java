package com.zorbatron.zbgt.api.render;

import gregtech.api.gui.resources.TextureArea;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SidedCubeRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class ZBGTTextures {

    // Multiblock part overlays
    public static SimpleOverlayRenderer WATER_OVERLAY_INFINITY;
    public static SimpleOverlayRenderer ITEM_OVERLAY_INFINITY;
    public static SimpleOverlayRenderer SWIRLY_INFINITY;
    public static SimpleOverlayRenderer YOTTANK_ME_HATCH;

    // GUI overlays
    public static TextureArea ITEM_FLUID_OVERLAY;
    public static TextureArea AUTO_PULL;
    public static TextureArea PME;
    public static TextureArea AE2_RW_STATES;
    public static TextureArea SLIME_BALL;

    public static TextureArea PROGRESS_BAR_CoAL;

    // Multiblock controller overlays
    public static OrientedOverlayRenderer GTPP_MACHINE_OVERLAY;

    // Casings
    public static SimpleOverlayRenderer PRECISE_CASING_0;
    public static SimpleOverlayRenderer PRECISE_CASING_1;
    public static SimpleOverlayRenderer PRECISE_CASING_2;
    public static SimpleOverlayRenderer PRECISE_CASING_3;
    public static SimpleOverlayRenderer PRECISE_CASING_4;

    public static SimpleOverlayRenderer IRIDIUM_CASING;
    public static ICubeRenderer YOTTANK_CASING;

    public static void preInit() {
        WATER_OVERLAY_INFINITY = new SimpleOverlayRenderer("overlay/machine/water_infinity");
        ITEM_OVERLAY_INFINITY = new SimpleOverlayRenderer("overlay/machine/item_infinity");
        SWIRLY_INFINITY = new SimpleOverlayRenderer("overlay/machine/swirly_infinity");
        YOTTANK_ME_HATCH = new SimpleOverlayRenderer("overlay/machine/yottank_me_hatch");

        ITEM_FLUID_OVERLAY = TextureArea.fullImage("textures/gui/widget/item_fluid.png");
        AUTO_PULL = TextureArea.fullImage("textures/gui/widget/auto_pull.png");
        PME = TextureArea.fullImage("textures/gui/widget/pme.png");
        AE2_RW_STATES = TextureArea.fullImage("textures/gui/widget/ae2_rw_states.png");
        SLIME_BALL = TextureArea.fullImage("textures/gui/widget/slimeball.png");

        PROGRESS_BAR_CoAL = TextureArea.fullImage("textures/gui/progress_bar/progress_bar_component_al.png");

        GTPP_MACHINE_OVERLAY = new OrientedOverlayRenderer("multiblock/gtpp");

        PRECISE_CASING_0 = new SimpleOverlayRenderer("casings/precise/precise_0");
        PRECISE_CASING_1 = new SimpleOverlayRenderer("casings/precise/precise_1");
        PRECISE_CASING_2 = new SimpleOverlayRenderer("casings/precise/precise_2");
        PRECISE_CASING_3 = new SimpleOverlayRenderer("casings/precise/precise_3");
        PRECISE_CASING_4 = new SimpleOverlayRenderer("casings/precise/precise_4");

        IRIDIUM_CASING = new SimpleOverlayRenderer("casings/material/iridium");
        YOTTANK_CASING = new SidedCubeRenderer("casings/yottank");
    }

    public static SimpleOverlayRenderer getPrassTextureByTier(int tier) {
        return switch (tier) {
            case (1) -> PRECISE_CASING_1;
            case (2) -> PRECISE_CASING_2;
            case (3) -> PRECISE_CASING_3;
            case (4) -> PRECISE_CASING_4;
            default -> PRECISE_CASING_0;
        };
    }
}
