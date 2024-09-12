package com.zorbatron.zbgt.api.pattern;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.abilities;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Supplier;

import net.minecraft.block.state.IBlockState;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.BlockInfo;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

public class TraceabilityPredicates {

    private static final Supplier<TraceabilityPredicate> CoAL_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ZBGTAPI.CoAL_CASINGS.containsKey(blockState)) {
                    CoALCasing.CasingType tier = ZBGTAPI.CoAL_CASINGS.get(blockState);
                    Object casing = blockWorldState.getMatchContext().getOrPut("CoALTier", tier);

                    if (!casing.equals(tier)) {
                        blockWorldState.setError(
                                new PatternStringError("gregtech.multiblock.pattern.error.coal_tier"));
                        return false;
                    }

                    blockWorldState.getMatchContext().getOrPut("VBlock", new LinkedList<>())
                            .add(blockWorldState.getPos());

                    return true;
                }

                return false;
            }, () -> ZBGTAPI.CoAL_CASINGS.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().ordinal()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new))
                            .addTooltips("zbgt.multiblock.pattern.coal_tier");

    public static TraceabilityPredicate coALCasings() {
        return CoAL_PREDICATE.get();
    }

    private static final Supplier<TraceabilityPredicate> PRECISE_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ZBGTAPI.PRECISE_CASINGS.containsKey(blockState)) {
                    PreciseCasing.CasingType tier = ZBGTAPI.PRECISE_CASINGS.get(blockState);
                    Object casing = blockWorldState.getMatchContext().getOrPut("PreciseTier", tier);

                    if (!casing.equals(tier)) {
                        blockWorldState.setError(
                                new PatternStringError("gregtech.multiblock.pattern.error.precise_tier"));
                        return false;
                    }

                    blockWorldState.getMatchContext().getOrPut("VBlock", new LinkedList<>())
                            .add(blockWorldState.getPos());

                    return true;
                }

                return false;
            }, () -> ZBGTAPI.PRECISE_CASINGS.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().ordinal()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new))
                            .addTooltips("zbgt.multiblock.pattern.precise_tier");

    public static TraceabilityPredicate preciseCasings() {
        return PRECISE_PREDICATE.get();
    }

    private static final Supplier<TraceabilityPredicate> MACHINE_CASING_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ZBGTAPI.MACHINE_CASINGS.containsKey(blockState)) {
                    BlockMachineCasing.MachineCasingType tier = ZBGTAPI.MACHINE_CASINGS.get(blockState);
                    Object casing = blockWorldState.getMatchContext().getOrPut("MachineCasingTier", tier);

                    if (!casing.equals(tier)) {
                        blockWorldState.setError(
                                new PatternStringError("gregtech.multiblock.pattern.error.machine_casing_tier"));
                        return false;
                    }

                    blockWorldState.getMatchContext().getOrPut("VBlock", new LinkedList<>())
                            .add(blockWorldState.getPos());

                    return true;
                }

                return false;
            }, () -> ZBGTAPI.MACHINE_CASINGS.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().ordinal()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new)).addTooltip("zbgt.multiblock.pattern.error.machine_casings");

    public static TraceabilityPredicate machineCasings() {
        return MACHINE_CASING_PREDICATE.get();
    }

    public static TraceabilityPredicate AIR_BLOCKS_COUNTED = new TraceabilityPredicate(
            blockWorldState -> {
                boolean isAirBlock = blockWorldState.getBlockState().getBlock().isAir(blockWorldState.getBlockState(),
                        blockWorldState.getWorld(), blockWorldState.getPos());

                if (isAirBlock) {
                    blockWorldState.getMatchContext().getOrPut("AirBlocks", new LinkedList<>())
                            .add(blockWorldState.getPos());
                    return true;
                }

                return false;
            });

    public static TraceabilityPredicate airBlockWithCount() {
        return AIR_BLOCKS_COUNTED;
    }

    public static TraceabilityPredicate autoBusesAndHatches(RecipeMap<?>[] recipeMaps) {
        boolean checkedItemIn = false, checkedItemOut = false, checkedFluidIn = false, checkedFluidOut = false;
        TraceabilityPredicate predicate = new TraceabilityPredicate();

        for (RecipeMap<?> recipeMap : recipeMaps) {
            if (!checkedItemIn) {
                if (recipeMap.getMaxInputs() > 0) {
                    checkedItemIn = true;
                    predicate = predicate.or(abilities(MultiblockAbility.IMPORT_ITEMS).setPreviewCount(1));
                }
            }
            if (!checkedItemOut) {
                if (recipeMap.getMaxOutputs() > 0) {
                    checkedItemOut = true;
                    predicate = predicate.or(abilities(MultiblockAbility.EXPORT_ITEMS).setPreviewCount(1));
                }
            }
            if (!checkedFluidIn) {
                if (recipeMap.getMaxFluidInputs() > 0) {
                    checkedFluidIn = true;
                    predicate = predicate.or(abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1));
                }
            }
            if (!checkedFluidOut) {
                if (recipeMap.getMaxFluidOutputs() > 0) {
                    checkedFluidOut = true;
                    predicate = predicate.or(abilities(MultiblockAbility.EXPORT_FLUIDS).setPreviewCount(1));
                }
            }
        }

        return predicate;
    }

    public static TraceabilityPredicate autoBusesAndHatches(RecipeMap<?> recipeMap) {
        return autoBusesAndHatches(new RecipeMap<?>[] { recipeMap });
    }

    public static Supplier<?> getMaintenanceHatchMTE() {
        return () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH :
                MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF);
    }
}
