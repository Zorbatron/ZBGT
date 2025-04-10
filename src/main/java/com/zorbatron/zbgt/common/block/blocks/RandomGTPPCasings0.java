package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class RandomGTPPCasings0 extends VariantBlock<RandomGTPPCasings0.CasingType> {

    public RandomGTPPCasings0() {
        super(Material.IRON);
        setTranslationKey("gtpp_casing_0");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(RandomGTPPCasings0.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        CENTRIFUGE_CASING("centrifuge_casing"),
        STRUCTURAL_COKE_OVEN_CASING("structural_coke_oven_casing"),
        HEAT_RESISTANT_COKE_OVEN_CASING("heat_resistant_coke_oven_casing"),
        HEAT_PROOF_COKE_OVEN_CASING("heat_proof_coke_oven_casing"),
        MATERIAL_PRESS_MACHINE_CASING("material_press_machine_casing"),
        ELECTROLYZER_CASING("electrolyzer_casing"),
        WIRE_FACTORY_CASING("wire_factory_casing"),
        MACERATION_STACK_CASING("maceration_stack_casing"),
        MATTER_GENERATION_COIL("matter_generation_coil"),
        MATTER_FABRICATION_CASING("matter_fabrication_casing"),
        HASTELLOY_N_REACTOR_CASING("hastelloy_n_reactor_casing"),
        ZERON_100_REACTOR_SHIELDING("zeron_100_reactor_shielding"),
        THERMAL_PROCESSING_CASING("thermal_processing_casing"),
        HASTELLOY_N_SEALANT_BLOCK("hastelloy_n_sealant_block"),
        HASTELLOY_X_STRUCTURAL_BLOCK("hastelloy_x_structural_block"),
        INCOLOY_DS_FLUID_CONTAINMENT_BLOCK("incoloy_ds_fluid_containment_block");

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
