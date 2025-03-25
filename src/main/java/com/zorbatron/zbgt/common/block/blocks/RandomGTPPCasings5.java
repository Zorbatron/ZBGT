package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class RandomGTPPCasings5 extends VariantBlock<RandomGTPPCasings5.CasingType> {

    public RandomGTPPCasings5() {
        super(Material.IRON);
        setTranslationKey("gtpp_casing_5");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(RandomGTPPCasings5.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        MODULATOR_4("modulator_4"),
        STRONG_BRONZE_MACHINE_CASING("strong_bronze_machine_casing"),
        STURDY_ALUMINUM_MACHINE_CASING("sturdy_aluminum_machine_casing"),
        VIGOROUS_LAURENIUM_MACHINE_CASING("vigorous_laurenium_machine_casing"),
        RUGGED_BOTMIUM_MACHINE_CASING("rugged_botmium_machine_casing");

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
