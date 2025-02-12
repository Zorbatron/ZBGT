package com.zorbatron.zbgt.api.metatileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.common.ZBGTConfig;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.utils.TooltipHelper;

public abstract class LaserCapableGCYMRecipeMapMultiblockController extends GCYMRecipeMapMultiblockController {

    private final boolean allowSubstationHatches;

    public LaserCapableGCYMRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMap) {
        this(metaTileEntityId, recipeMap, true);
    }

    public LaserCapableGCYMRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        this(metaTileEntityId, recipeMap, true);
    }

    public LaserCapableGCYMRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMap,
                                                         boolean allowSubstationHatches) {
        super(metaTileEntityId, recipeMap);
        this.allowSubstationHatches = allowSubstationHatches;
    }

    public LaserCapableGCYMRecipeMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap,
                                                         boolean allowSubstationHatches) {
        super(metaTileEntityId, recipeMap);
        this.allowSubstationHatches = allowSubstationHatches;
    }

    public boolean allowsSubstationHatches() {
        return this.allowSubstationHatches && ZBGTConfig.multiblockSettings.allowSubstationHatches;
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();

        List<IEnergyContainer> list = new ArrayList<>();
        list.addAll(getAbilities(MultiblockAbility.INPUT_ENERGY));
        list.addAll(getAbilities(MultiblockAbility.INPUT_LASER));
        if (allowsSubstationHatches()) {
            list.addAll(getAbilities(MultiblockAbility.SUBSTATION_INPUT_ENERGY));
        }

        this.energyContainer = new EnergyContainerList(list);
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn,
                                               boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut,
                                               boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(false, checkMaintenance, checkItemIn, checkItemOut,
                checkFluidIn, checkFluidOut, checkMuffler);

        if (checkEnergyIn) {
            predicate = predicate.or(autoEnergyInputsMega());
        }

        return predicate;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format(I18n.format("zbgt.laser_enabled.1") +
                TooltipHelper.RAINBOW + I18n.format("zbgt.laser_enabled.2")) +
                (allowsSubstationHatches() ? I18n.format("zbgt.substation_enabled") : ""));
    }

    protected TraceabilityPredicate autoEnergyInputsMega() {
        return TraceabilityPredicates.autoEnergyInputs(1, 8, 2, true, allowsSubstationHatches(), true);
    }
}
