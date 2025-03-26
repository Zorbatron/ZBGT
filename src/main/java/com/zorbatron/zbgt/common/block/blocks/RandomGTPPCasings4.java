package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class RandomGTPPCasings4 extends VariantBlock<RandomGTPPCasings4.CasingType> {

    public RandomGTPPCasings4() {
        super(Material.IRON);
        setTranslationKey("gtpp_casing_4");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(RandomGTPPCasings4.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        REINFORCED_GAS_TURBINE_CASING("reinforced_gas_turbine_casing"),
        REINFORCED_PLASMA_TURBINE_CASING("reinforced_plasma_turbine_casing"),
        STRUCTURAL_SOLAR_CASING("structural_solar_casing"),
        SALT_CONTAINMENT_CASING("salt_containment_casing"),
        THERMALLY_INSULATED_CASING("thermally_insulated_casing"),
        FLOTATION_CELL_CASINGS("flotation_cell_casings"),
        MOLECULAR_CONTAINMENT_CASING("molecular_containment_casing"),
        HIGH_VOLTAGE_CURRENT_CAPACITOR("high_voltage_current_capacitor"),
        RESONANCE_CHAMBER_1("resonance_chamber_1"),
        RESONANCE_CHAMBER_2("resonance_chamber_2"),
        RESONANCE_CHAMBER_3("resonance_chamber_3"),
        RESONANCE_CHAMBER_4("resonance_chamber_4"),
        MODULATOR_1("modulator_1"),
        MODULATOR_2("modulator_2"),
        MODULATOR_3("modulator_3");

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
