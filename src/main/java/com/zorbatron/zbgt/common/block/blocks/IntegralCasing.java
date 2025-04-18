package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class IntegralCasing extends VariantBlock<IntegralCasing.CasingType> {

    public IntegralCasing() {
        super(Material.IRON);
        setTranslationKey("integral_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        INTEGRAL_ENCASEMENT_1("integral_encasement_1"),
        INTEGRAL_ENCASEMENT_2("integral_encasement_2"),
        INTEGRAL_ENCASEMENT_3("integral_encasement_3"),
        INTEGRAL_ENCASEMENT_4("integral_encasement_4"),
        INTEGRAL_ENCASEMENT_5("integral_encasement_5"),
        INTEGRAL_FRAMEWORK_1("integral_framework_1"),
        INTEGRAL_FRAMEWORK_2("integral_framework_2"),
        INTEGRAL_FRAMEWORK_3("integral_framework_3"),
        INTEGRAL_FRAMEWORK_4("integral_framework_4"),
        INTEGRAL_FRAMEWORK_5("integral_framework_5");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getName() {
            return this.name;
        }
    }
}
