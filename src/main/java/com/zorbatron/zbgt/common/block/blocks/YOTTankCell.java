package com.zorbatron.zbgt.common.block.blocks;

import java.math.BigInteger;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.jetbrains.annotations.NotNull;

import gregtech.api.block.VariantBlock;

public class YOTTankCell extends VariantBlock<YOTTankCell.CasingType> {

    public YOTTankCell() {
        super(Material.IRON);
        setTranslationKey("yottank_cell");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(YOTTankCell.CasingType.values()[0]));
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos,
                                    EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum CasingType implements IStringSerializable {

        YOTTANK_CELL_1("yottank_cell_1", 6),
        YOTTANK_CELL_2("yottank_cell_2", 8),
        YOTTANK_CELL_3("yottank_cell_3", 10),
        YOTTANK_CELL_4("yottank_cell_4", 12),
        YOTTANK_CELL_5("yottank_cell_5", 14),
        YOTTANK_CELL_6("yottank_cell_6", 16),
        YOTTANK_CELL_7("yottank_cell_7", 18),
        YOTTANK_CELL_8("yottank_cell_8", 20),
        YOTTANK_CELL_9("yottank_cell_9", 22),
        YOTTANK_CELL_10("yottank_cell_10", 24);

        private final String name;
        private final int zeros;

        CasingType(String name, int zeros) {
            this.name = name;
            this.zeros = zeros;
        }

        @NotNull
        @Override
        public String getName() {
            return this.name;
        }

        public BigInteger getCapacity() {
            return BigInteger.TEN.pow(this.zeros);
        }

        public YOTTankCell.CasingType getCasingByTier(int tier) {
            return switch (tier) {
                case (2) -> YOTTANK_CELL_2;
                case (3) -> YOTTANK_CELL_3;
                case (4) -> YOTTANK_CELL_4;
                case (5) -> YOTTANK_CELL_5;
                case (6) -> YOTTANK_CELL_6;
                case (7) -> YOTTANK_CELL_7;
                case (8) -> YOTTANK_CELL_8;
                case (9) -> YOTTANK_CELL_9;
                case (10) -> YOTTANK_CELL_10;
                default -> YOTTANK_CELL_1;
            };
        }
    }
}
