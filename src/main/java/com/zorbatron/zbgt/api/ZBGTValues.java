package com.zorbatron.zbgt.api;

import static net.minecraft.util.text.TextFormatting.*;

public class ZBGTValues {

    /**
     * The Voltage Tiers extended all the way to max Long value for overclocking
     */
    public static final long[] VOC = { 8, 32, 128, 512, 2048, 8192, 32768, 131072, 524288, 2097152, 8388608, 33554432,
            134217728, 536870912, 2147483648L, 8589934592L, 34359738368L, 137438953472L, 549755813888L,
            2199023255552L, 8796093022208L, 35184372088832L, 140737488355328L, 562949953421312L, 2251799813685248L,
            9007199254740992L, 36028797018963968L, 144115188075855872L, 576460752303423488L, 2305843009213693952L,
            Long.MAX_VALUE };

    public static final String[] VOCN = new String[] { "ULV", "LV", "MV", "HV", "EV", "IV", "LuV", "ZPM", "UV", "UHV",
            "UEV", "UIV", "UXV", "OpV", "MAX", "MAX+1", "MAX+2", "MAX+3", "MAX+4", "MAX+5", "MAX+6", "MAX+7", "MAX+8",
            "MAX+9", "MAX+10", "MAX+11", "MAX+12", "MAX+13", "MAX+14", "MAX+15", "MAX+16",
    };

    public static final int MAX_TRUE = 30;

    public static final String MAX_PLUS = RED.toString() + BOLD + "M" + YELLOW + BOLD + "A" + GREEN + BOLD + "X" +
            AQUA + BOLD + "+" + LIGHT_PURPLE + BOLD;

    public static final String[] VOCNF = new String[] {
            DARK_GRAY + "ULV", GRAY + "LV", AQUA + "MV",
            GOLD + "HV", DARK_PURPLE + "EV", DARK_BLUE + "IV",
            LIGHT_PURPLE + "LuV", RED + "ZPM", DARK_AQUA + "UV",
            DARK_RED + "UHV", GREEN + "UEV", DARK_GREEN + "UIV",
            YELLOW + "UXV", BLUE + "OpV", RED.toString() + BOLD + "MAX",
            MAX_PLUS + "1", MAX_PLUS + "2", MAX_PLUS + "3", MAX_PLUS + "4",
            MAX_PLUS + "5", MAX_PLUS + "6", MAX_PLUS + "7", MAX_PLUS + "8",
            MAX_PLUS + "9", MAX_PLUS + "10", MAX_PLUS + "11", MAX_PLUS + "12",
            MAX_PLUS + "13", MAX_PLUS + "14", MAX_PLUS + "15", MAX_PLUS + "16" };
}
