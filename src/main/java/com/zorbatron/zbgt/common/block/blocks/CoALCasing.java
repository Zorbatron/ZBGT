package com.zorbatron.zbgt.common.block.blocks;

import static gregtech.api.GTValues.*;

import javax.annotation.Nonnull;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.block.ICoALTier;

import gregtech.api.block.VariantBlock;

public class CoALCasing extends VariantBlock<CoALCasing.CasingType> {

    public CoALCasing() {
        super(Material.IRON);
        setTranslationKey("coal_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(CasingType.values()[0]));
    }

    @Override
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos,
                                    @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum CasingType implements IStringSerializable, ICoALTier {

        CASING_LV("lv"),
        CASING_MV("mv"),
        CASING_HV("hv"),
        CASING_EV("ev"),
        CASING_IV("iv"),
        CASING_LuV("luv"),
        CASING_ZPM("zpm"),
        CASING_UV("uv"),
        CASING_UHV("uhv"),
        CASING_UEV("uev"),
        CASING_UIV("uiv"),
        CASING_UXV("uxv"),
        CASING_OpV("opv"),
        CASING_MAX("max");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @NotNull
        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getTier() {
            return this.ordinal();
        }

        public static CasingType getCasingByTier(int tier) {
            return switch (tier) {
                case (MV) -> CASING_MV;
                case (HV) -> CASING_HV;
                case (EV) -> CASING_EV;
                case (IV) -> CASING_IV;
                case (LuV) -> CASING_LuV;
                case (ZPM) -> CASING_ZPM;
                case (UV) -> CASING_UV;
                case (UHV) -> CASING_UHV;
                case (UEV) -> CASING_UEV;
                case (UIV) -> CASING_UIV;
                case (UXV) -> CASING_UXV;
                case (OpV) -> CASING_OpV;
                case (MAX) -> CASING_MAX;
                default -> CASING_LV;
            };
        }
    }
}
