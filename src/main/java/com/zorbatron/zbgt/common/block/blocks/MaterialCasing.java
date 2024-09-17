package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.jetbrains.annotations.NotNull;

import gregtech.api.block.VariantBlock;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

public class MaterialCasing extends VariantBlock<MaterialCasing.CasingType> {

    public MaterialCasing() {
        super(net.minecraft.block.material.Material.IRON);
        setTranslationKey("material_casing");
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

        IRIDIUM_CASING("iridium", Materials.Iridium);

        private final String name;
        private final Material material;

        CasingType(String name, Material material) {
            this.name = name;
            this.material = material;
        }

        @NotNull
        @Override
        public String getName() {
            return this.name;
        }

        public Material getMaterial() {
            return this.material;
        }
    }
}
