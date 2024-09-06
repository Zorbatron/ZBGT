package com.zorbatron.zbgt.api.pattern;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.abilities;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Supplier;

import net.minecraft.block.state.IBlockState;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.block.ICoALTier;
import com.zorbatron.zbgt.api.block.IPreciseTier;

import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregicality.multiblocks.api.metatileentity.GCYMMultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.BlockInfo;

public class TraceabilityPredicates {

    private static final Supplier<TraceabilityPredicate> CoAL_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ZBGTAPI.CoAL_CASINGS.containsKey(blockState)) {
                    ICoALTier tier = ZBGTAPI.CoAL_CASINGS.get(blockState);
                    Object casing = blockWorldState.getMatchContext().getOrPut("CoALTier", tier);
                    if (!casing.equals(tier)) {
                        blockWorldState.setError(
                                new PatternStringError("gregtech.multiblock.pattern.error.component_al_tier"));
                        return false;
                    }
                    blockWorldState.getMatchContext().getOrPut("VBlock", new LinkedList<>())
                            .add(blockWorldState.getPos());
                    return true;
                }
                return false;
            }, () -> ZBGTAPI.CoAL_CASINGS.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new))
                            .addTooltips("gcyl.multiblock.pattern.error.component_al_casings");

    public static TraceabilityPredicate coALCasings() {
        return CoAL_PREDICATE.get();
    }

    private static final Supplier<TraceabilityPredicate> PRECISE_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ZBGTAPI.PRECISE_CASINGS.containsKey(blockState)) {
                    IPreciseTier tier = ZBGTAPI.PRECISE_CASINGS.get(blockState);
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
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new)).addTooltips("gcyl.multiblock.pattern.error.precise_casings");

    public static TraceabilityPredicate preciseCasings() {
        return PRECISE_PREDICATE.get();
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

    public static TraceabilityPredicate maintenanceHatch(MultiblockControllerBase controller) {
        TraceabilityPredicate predicate = new TraceabilityPredicate(
                abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1));

        if (controller instanceof IParallelMultiblock) {
            predicate = predicate
                    .or(abilities(GCYMMultiblockAbility.PARALLEL_HATCH).setMaxGlobalLimited(1).setPreviewCount(1));
        }

        return predicate;
    }
}
