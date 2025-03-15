package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class MiscCasing extends VariantBlock<MiscCasing.CasingType> {

    public MiscCasing() {
        super(Material.IRON);
        setTranslationKey("misc_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(MiscCasing.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        YOTTANK_CASING("yottank_casing"),
        AMELIORATED_SUPERCONDUCTOR_COIL("ameliorated_superconductor_coil"),
        COMPACT_FUSION_COIL_1("compact_fusion_coil_1"),
        COMPACT_FUSION_COIL_2("compact_fusion_coil_2"),
        COMPACT_FUSION_COIL_3("compact_fusion_coil_3"),
        COMPACT_FUSION_COIL_4("compact_fusion_coil_4"),
        VOLCANUS_CASING("volcanus_casing"),
        CRYOGENIC_CASING("cryogenic_casing");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @NotNull
        @Override
        public String getName() {
            return this.name;
        }
    }
}
