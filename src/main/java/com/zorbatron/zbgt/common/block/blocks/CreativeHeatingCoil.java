package com.zorbatron.zbgt.common.block.blocks;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.common.ZBGTConfig;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.block.VariantBlock;
import gregtech.api.unification.material.Material;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityMultiSmelter;

public class CreativeHeatingCoil extends VariantBlock<CreativeHeatingCoil.CoilType> {

    public CreativeHeatingCoil() {
        super(net.minecraft.block.material.Material.IRON);
        setTranslationKey("creative_heating_coil");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    @NotNull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack itemStack, @Nullable World worldIn, List<String> lines,
                               @Nonnull ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        lines.add(I18n.format("tile.wire_coil.tooltip_heat", CoilType.CREATIVE_COIL.getCoilTemperature()));

        if (TooltipHelper.isShiftDown()) {
            int coilTier = CoilType.CREATIVE_COIL.getTier();
            lines.add(I18n.format("tile.wire_coil.tooltip_smelter"));
            lines.add(I18n.format("tile.wire_coil.tooltip_parallel_smelter", CoilType.CREATIVE_COIL.getLevel() * 32));
            int EUt = MetaTileEntityMultiSmelter.getEUtForParallel(
                    MetaTileEntityMultiSmelter.getMaxParallel(CoilType.CREATIVE_COIL.getLevel()),
                    CoilType.CREATIVE_COIL.getEnergyDiscount());
            lines.add(I18n.format("tile.wire_coil.tooltip_energy_smelter", EUt));
            lines.add(I18n.format("tile.wire_coil.tooltip_pyro"));
            lines.add(I18n.format("tile.wire_coil.tooltip_speed_pyro", coilTier == 0 ? 75 : 50 * (coilTier + 1)));
            lines.add(I18n.format("tile.wire_coil.tooltip_cracking"));
            lines.add(I18n.format("tile.wire_coil.tooltip_energy_cracking", 100 - 10 * coilTier));
        } else {
            lines.add(I18n.format("tile.wire_coil.tooltip_extended_info"));
        }
    }

    @Override
    public boolean canCreatureSpawn(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos,
                                    @Nonnull EntityLiving.SpawnPlacementType type) {
        return false;
    }

    public enum CoilType implements IStringSerializable, IHeatingCoilBlockStats {

        CREATIVE_COIL("creative_heating_coil",
                ZBGTConfig.creativeCoilSettings.temperature,
                ZBGTConfig.creativeCoilSettings.level,
                ZBGTConfig.creativeCoilSettings.energyDiscount);

        private final String name;
        private final int coilTemperature;
        private final int level;
        private final int energyDiscount;

        CoilType(String name, int coilTemperature, int level, int energyDiscount) {
            this.name = name;
            this.coilTemperature = coilTemperature;
            this.level = level;
            this.energyDiscount = energyDiscount;
        }

        @Override
        public int getCoilTemperature() {
            return this.coilTemperature;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getEnergyDiscount() {
            return this.energyDiscount;
        }

        @Override
        public int getTier() {
            return this.level;
        }

        @Override
        @Nullable
        public Material getMaterial() {
            return null;
        }

        @Override
        @NotNull
        public String getName() {
            return this.name;
        }
    }
}
