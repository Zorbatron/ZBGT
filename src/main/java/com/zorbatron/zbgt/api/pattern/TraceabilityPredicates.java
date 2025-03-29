package com.zorbatron.zbgt.api.pattern;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.abilities;

import java.util.*;
import java.util.function.Supplier;

import net.minecraft.block.state.IBlockState;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.BlockInfo;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.metatileentities.MetaTileEntities;

@SuppressWarnings("unused")
public final class TraceabilityPredicates {

    private static final List<MetaTileEntity> ENERGY_HATCHES = new ArrayList<>();
    private static final List<MetaTileEntity> DYNAMO_HATCHES = new ArrayList<>();

    private static final List<MetaTileEntity> LASERS_INPUT = new ArrayList<>();
    private static final List<MetaTileEntity> LASERS_OUTPUT = new ArrayList<>();

    private static final List<MetaTileEntity> ALL_ENERGY_INPUTS = new ArrayList<>();
    private static final List<MetaTileEntity> ALL_ENERGY_OUTPUTS = new ArrayList<>();

    static {
        ENERGY_HATCHES.addAll(Arrays.asList(MetaTileEntities.ENERGY_INPUT_HATCH));
        ENERGY_HATCHES.addAll(Arrays.asList(MetaTileEntities.ENERGY_INPUT_HATCH_4A));
        ENERGY_HATCHES.addAll(Arrays.asList(MetaTileEntities.ENERGY_INPUT_HATCH_16A));

        DYNAMO_HATCHES.addAll(Arrays.asList(MetaTileEntities.ENERGY_OUTPUT_HATCH));
        DYNAMO_HATCHES.addAll(Arrays.asList(MetaTileEntities.ENERGY_OUTPUT_HATCH_4A));
        DYNAMO_HATCHES.addAll(Arrays.asList(MetaTileEntities.ENERGY_OUTPUT_HATCH_16A));

        LASERS_INPUT.addAll(Arrays.asList(MetaTileEntities.LASER_INPUT_HATCH_256));
        LASERS_INPUT.addAll(Arrays.asList(MetaTileEntities.LASER_INPUT_HATCH_1024));
        LASERS_INPUT.addAll(Arrays.asList(MetaTileEntities.LASER_INPUT_HATCH_4096));

        LASERS_OUTPUT.addAll(Arrays.asList(MetaTileEntities.LASER_INPUT_HATCH_256));
        LASERS_OUTPUT.addAll(Arrays.asList(MetaTileEntities.LASER_INPUT_HATCH_1024));
        LASERS_OUTPUT.addAll(Arrays.asList(MetaTileEntities.LASER_INPUT_HATCH_4096));

        ALL_ENERGY_INPUTS.addAll(ENERGY_HATCHES);
        ALL_ENERGY_INPUTS.addAll(Arrays.asList(MetaTileEntities.SUBSTATION_ENERGY_INPUT_HATCH));
        ALL_ENERGY_INPUTS.addAll(LASERS_INPUT);

        ALL_ENERGY_OUTPUTS.addAll(DYNAMO_HATCHES);
        DYNAMO_HATCHES.addAll(Arrays.asList(MetaTileEntities.SUBSTATION_ENERGY_OUTPUT_HATCH));
        ALL_ENERGY_OUTPUTS.addAll(LASERS_OUTPUT);
    }

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

    private static final TraceabilityPredicate AIR_BLOCKS_COUNTED = new TraceabilityPredicate(
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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public static TraceabilityPredicate autoBusesAndHatches(RecipeMap<?> recipeMap) {
        return autoBusesAndHatches(new RecipeMap<?>[] { recipeMap });
    }

    public static Supplier<?> getMaintenanceHatchMTE(IBlockState defaultCasing) {
        return () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH : defaultCasing;
    }

    public static TraceabilityPredicate autoEnergyInputs(int min, int max, int previewCount, boolean allowEnergyHatches,
                                                         boolean allowSubstationHatches, boolean allowLaserHatches) {
        List<MultiblockAbility<?>> allowedAbilities = new ArrayList<>();

        if (allowEnergyHatches) allowedAbilities.add(MultiblockAbility.INPUT_ENERGY);
        if (allowSubstationHatches) allowedAbilities.add(MultiblockAbility.SUBSTATION_INPUT_ENERGY);
        if (allowLaserHatches) allowedAbilities.add(MultiblockAbility.INPUT_LASER);

        return new TraceabilityPredicate(abilities(allowedAbilities.stream().toArray(MultiblockAbility<?>[]::new))
                .setMinGlobalLimited(min)
                .setMaxGlobalLimited(max)
                .setPreviewCount(previewCount));
    }

    public static TraceabilityPredicate autoEnergyInputs(int min, int max, boolean allowEnergyHatches,
                                                         boolean allowSubstationHatches, boolean allowLaserHatches) {
        return autoEnergyInputs(min, max, 2, allowEnergyHatches, allowSubstationHatches, allowLaserHatches);
    }

    public static TraceabilityPredicate autoEnergyInputs(int min, int max, int previewCount) {
        return autoEnergyInputs(min, max, previewCount, true, false, false);
    }

    public static TraceabilityPredicate autoEnergyInputs(int min, int max) {
        return autoEnergyInputs(min, max, 2);
    }

    public static TraceabilityPredicate autoEnergyInputs() {
        return autoEnergyInputs(1, 3);
    }

    public static TraceabilityPredicate inputBusesNormal() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.ITEM_IMPORT_BUS);
    }

    public static TraceabilityPredicate outputBusesNormal() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.ITEM_EXPORT_BUS);
    }

    public static TraceabilityPredicate inputHatchesNormal() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.FLUID_IMPORT_HATCH);
    }

    public static TraceabilityPredicate outputHatchesNormal() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.FLUID_EXPORT_HATCH);
    }

    public static TraceabilityPredicate energyHatchesNormal() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.ENERGY_INPUT_HATCH);
    }

    public static TraceabilityPredicate dynamoHatchesNormal() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.ENERGY_OUTPUT_HATCH);
    }

    public static TraceabilityPredicate energyHatches4a() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.ENERGY_INPUT_HATCH_4A);
    }

    public static TraceabilityPredicate dynamoHatches4a() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.ENERGY_OUTPUT_HATCH_4A);
    }

    public static TraceabilityPredicate energyHatches16a() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.ENERGY_INPUT_HATCH_16A);
    }

    public static TraceabilityPredicate dynamoHatches16a() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.ENERGY_OUTPUT_HATCH_16A);
    }

    public static TraceabilityPredicate energyHatchesSubstation() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.SUBSTATION_ENERGY_INPUT_HATCH);
    }

    public static TraceabilityPredicate dynamoHatchesSubstation() {
        return MultiblockControllerBase.metaTileEntities(MetaTileEntities.SUBSTATION_ENERGY_OUTPUT_HATCH);
    }

    public static TraceabilityPredicate energyHatchesLaser() {
        return MultiblockControllerBase.metaTileEntities(LASERS_INPUT.toArray(new MetaTileEntity[0]));
    }

    public static TraceabilityPredicate dynamoHatchesLaser() {
        return MultiblockControllerBase.metaTileEntities(LASERS_OUTPUT.toArray(new MetaTileEntity[0]));
    }

    public static TraceabilityPredicate allEnergyHatches() {
        return MultiblockControllerBase.metaTileEntities(ENERGY_HATCHES.toArray(new MetaTileEntity[0]));
    }

    public static TraceabilityPredicate allDynamoHatches() {
        return MultiblockControllerBase.metaTileEntities(DYNAMO_HATCHES.toArray(new MetaTileEntity[0]));
    }

    /**
     * <i>all</i> energy input hatches
     */
    public static TraceabilityPredicate allEnergyInputHatches() {
        return MultiblockControllerBase.metaTileEntities(ALL_ENERGY_INPUTS.toArray(new MetaTileEntity[0]));
    }

    /**
     * <i>all</i> energy output hatches
     */
    public static TraceabilityPredicate allEnergyOutputHatches() {
        return MultiblockControllerBase.metaTileEntities(ALL_ENERGY_OUTPUTS.toArray(new MetaTileEntity[0]));
    }
}
