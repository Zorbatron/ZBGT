package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class QFTCasings extends VariantBlock<QFTCasings.CasingType> {

    public QFTCasings() {
        super(Material.IRON);
        setTranslationKey("qft_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        NEUTRON_PULSE_MANIPULATOR("neutron_pulse_manipulator"),
        COSMIC_FABRIC_MANIPULATOR("cosmic_fabric_manipulator"),
        INFINITY_INFUSED_MANIPULATOR("infinity_infused_manipulator"),
        SPACETIME_CONTINUUM_RIPPER("spacetime_continuum_ripper"),
        NEUTRON_SHIELDING_CORE("neutron_shielding_core"),
        COSMIC_FABRIC_SHIELDING_CORE("cosmic_fabric_shielding_core"),
        INFINITY_INFUSED_SHIELDING_CORE("infinity_infused_shielding_core"),
        SPACETIME_BENDING_CORE("spacetime_bending_core"),
        QUANTUM_FORCE_TRANSFORMER_COIL_CASINGS("quantum_force_transformer_coil_casings");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getName() {
            return name;
        }
    }
}
