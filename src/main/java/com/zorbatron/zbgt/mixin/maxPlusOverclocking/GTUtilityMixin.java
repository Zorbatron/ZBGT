package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import static com.zorbatron.zbgt.api.ZBGTValues.MAX_TRUE;
import static com.zorbatron.zbgt.api.ZBGTValues.VOC;
import static com.zorbatron.zbgt.api.util.ZBGTUtility.getOCTierByVoltage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import gregtech.api.GTValues;
import gregtech.api.util.GTUtility;

@Mixin(value = GTUtility.class, remap = false)
public class GTUtilityMixin {

    /**
     * @author Zorbatron
     * @reason Yeah!
     */
    @Overwrite
    public static byte getTierByVoltage(long voltage) {
        if (voltage >= GTValues.V[GTValues.MAX]) {
            return GTValues.MAX;
        }
        return getOCTierByVoltage(voltage);
    }

    /**
     * @author Zorbatron
     * @reason Yeah!
     */
    @Overwrite
    public static byte getFloorTierByVoltage(long voltage) {
        if (voltage < GTValues.V[GTValues.LV]) {
            return GTValues.ULV;
        }
        if (voltage == VOC[MAX_TRUE]) {
            return MAX_TRUE;
        }

        return (byte) ((60 - Long.numberOfLeadingZeros(voltage)) >> 1);
    }
}
