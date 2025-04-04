package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class RandomGTPPCasings2 extends VariantBlock<RandomGTPPCasings2.CasingType> {

    public RandomGTPPCasings2() {
        super(Material.IRON);
        setTranslationKey("gtpp_casing_2");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(RandomGTPPCasings2.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        VACUUM_CASING("vacuum_casing"),
        TURBODYNE_CASING("turbodyne_casing"),
        ISAMILL_EXTERIOR_CASING("isamill_exterior_casing"),
        ISAMILL_PIPING("isamill_piping"),
        ISAMILL_GEARBOX("isamill_gearbox"),
        ELEMENTAL_CONFINEMENT_SHELL("elemental_confinement_shell"),
        SPARGE_TOWER_EXTERIOR_CASING("sparge_tower_exterior_casing"),
        STURDY_PRINTER_CASING("sturdy_printer_casing"),
        FORGE_CASING("forge_casing"),
        NEUTRON_PULSE_MANIPULATOR("neutron_pulse_manipulator"),
        COSMIC_FABRIC_MANIPULATOR("cosmic_fabric_manipulator"),
        INFINITY_INFUSED_MANIPULATOR("infinity_infused_manipulator"),
        SPACETIME_CONTINUUM_RIPPER("spacetime_continuum_ripper"),
        NEUTRON_SHIELDING_CORE("neutron_shielding_core"),
        COSMIC_FABRIC_SHIELDING_CORE("cosmic_fabric_shielding_core"),
        INFINITY_INFUSED_SHIELDING_CORE("infinity_infused_shielding_core");

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
