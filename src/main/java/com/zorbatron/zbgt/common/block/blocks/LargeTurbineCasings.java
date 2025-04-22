package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class LargeTurbineCasings extends VariantBlock<LargeTurbineCasings.CasingType> {

    public LargeTurbineCasings() {
        super(Material.IRON);
        setTranslationKey("large_turbine_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(LargeTurbineCasings.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        TURBINE_SHAFT("turbine_shaft"),
        REINFORCED_STEAM_TURBINE_CASING("reinforced_steam_turbine_casing"),
        REINFORCED_HP_STEAM_TURBINE_CASING("reinforced_hp_steam_turbine_casing"),
        REINFORCED_SC_STEAM_TURBINE_CASING("reinforced_sc_steam_turbine_casing"),
        REINFORCED_GAS_TURBINE_CASING("reinforced_gas_turbine_casing"),
        REINFORCED_PLASMA_TURBINE_CASING("reinforced_plasma_turbine_casing");

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
