package com.zorbatron.zbgt.api.metatileentity;

import static com.zorbatron.zbgt.api.pattern.TraceabilityPredicates.autoEnergyInputs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.common.ZBGTConfig;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.multiblock.MultiMapMultiblockController;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.utils.TooltipHelper;

public abstract class LaserCapableMultiMapMultiblockController extends MultiMapMultiblockController {

    private final boolean allowSubstationHatches;

    public LaserCapableMultiMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps) {
        this(metaTileEntityId, recipeMaps, true);
    }

    public LaserCapableMultiMapMultiblockController(ResourceLocation metaTileEntityId, RecipeMap<?>[] recipeMaps,
                                                    boolean allowSubstationHatches) {
        super(metaTileEntityId, recipeMaps);
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
            predicate = predicate.or(autoEnergyInputs(1, 3, true, false, true));
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
}
