package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class RandomGTPPCasings3 extends VariantBlock<RandomGTPPCasings3.CasingType> {

    public RandomGTPPCasings3() {
        super(Material.IRON);
        setTranslationKey("gtpp_casing_3");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(RandomGTPPCasings3.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        SPACETIME_BENDING_CORE("spacetime_bending_core"),
        TURBINE_SHAFT("turbine_shaft"),
        REINFORCED_STEAM_TURBINE_CASING("reinforced_steam_turbine_casing"),
        REINFORCED_HP_STEAM_TURBINE_CASING("reinforced_hp_steam_turbine_casing"),
        REINFORCED_SC_STEAM_TURBINE_CASING("reinforced_sc_steam_turbine_casing");

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
