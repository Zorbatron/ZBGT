package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class RandomGTPPCasings1 extends VariantBlock<RandomGTPPCasings1.CasingType> {

    public RandomGTPPCasings1() {
        super(Material.IRON);
        setTranslationKey("gtpp_casing_1");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(RandomGTPPCasings1.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        WASH_PLANT_CASING("wash_plant_casing"),
        INDUSTRIAL_SIEVE_CASING("industrial_sieve_casing"),
        INDUSTRIAL_SIEVE_GRATE("industrial_sieve_grate"),
        CYCLOTRON_COIL("cyclotron_coil"),
        CYCLOTRON_OUTER_CASING("cyclotron_outer_casing"),
        THERMAL_CONTAINMENT_CASING("thermal_containment_casing"),
        BULK_PRODUCTION_FRAME("bulk_production_frame"),
        CUTTING_FACTORY_FRAME("cutting_factory_frame"),
        STERILE_FARM_CASING("sterile_farm_casing"),
        AQUATIC_CASING("aquatic_casing"),
        INCONEL_REINFORCED_CASING("inconel_reinforced_casing"),
        MULTI_USE_CASING("multi_use_casing"),
        SUPPLY_DEPOT_CASING("supply_depot_casing"),
        TEMPERED_ARC_FURNACE_CASING("tempered_arc_furnace_casing");

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
