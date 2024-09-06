package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.jetbrains.annotations.NotNull;

import gregtech.api.block.VariantBlock;

public class ZBGTBlockMultiblockCasing extends VariantBlock<ZBGTBlockMultiblockCasing.CasingType> {

    public ZBGTBlockMultiblockCasing() {
        super(Material.IRON);
        setTranslationKey("multiblock_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(CasingType.values()[0]));
    }

    @Override
    public boolean canCreatureSpawn(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos,
                                    EntityLiving.@NotNull SpawnPlacementType type) {
        return false;
    }

    public enum CasingType implements IStringSerializable {

        PRECISE_CASING_1("precise_1"),
        PRECISE_CASING_2("precise_2"),
        PRECISE_CASING_3("precise_3"),
        IRIDIUM_CASING("iridium");

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
