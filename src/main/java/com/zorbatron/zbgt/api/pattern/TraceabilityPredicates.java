package com.zorbatron.zbgt.api.pattern;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Supplier;

import net.minecraft.block.state.IBlockState;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.block.IComponentALTier;

import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;

public class TraceabilityPredicates {

    private static final Supplier<TraceabilityPredicate> COMPONENT_AL_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ZBGTAPI.COMPONENT_AL_CASINGS.containsKey(blockState)) {
                    IComponentALTier tier = ZBGTAPI.COMPONENT_AL_CASINGS.get(blockState);
                    Object casing = blockWorldState.getMatchContext().getOrPut("ComponentALTier", tier);
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
            }, () -> ZBGTAPI.COMPONENT_AL_CASINGS.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().getTier()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new))
                            .addTooltips("gcyl.multiblock.pattern.error.component_al_casings");

    public static TraceabilityPredicate componentALCasings() {
        return COMPONENT_AL_PREDICATE.get();
    }
}
