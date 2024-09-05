package com.zorbatron.zbgt.api.metatileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.ZBGTConfig;
import com.zorbatron.zbgt.client.ClientHandler;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;
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

        this.energyContainer = new EnergyContainerList(Collections.unmodifiableList(list));
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn,
                                               boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut,
                                               boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(false, checkMaintenance, checkItemIn, checkItemOut,
                checkFluidIn, checkFluidOut, checkMuffler);

        if (checkEnergyIn) {
            predicate = predicate.or(autoEnergyInputs());
        }

        return predicate;
    }

    public TraceabilityPredicate busesAndHatches() {
        boolean checkedItemIn = false, checkedItemOut = false, checkedFluidIn = false, checkedFluidOut = false;
        TraceabilityPredicate predicate = new TraceabilityPredicate();

        for (RecipeMap<?> recipeMap : getAvailableRecipeMaps()) {
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

    public TraceabilityPredicate autoEnergyInputs(int min, int max, int previewCount) {
        if (allowsSubstationHatches()) {
            return new TraceabilityPredicate(abilities(MultiblockAbility.INPUT_ENERGY, MultiblockAbility.INPUT_LASER,
                    MultiblockAbility.SUBSTATION_INPUT_ENERGY)
                            .setMinGlobalLimited(min).setMaxGlobalLimited(max).setPreviewCount(previewCount));
        } else {
            return new TraceabilityPredicate(abilities(MultiblockAbility.INPUT_ENERGY, MultiblockAbility.INPUT_LASER)
                    .setMinGlobalLimited(min).setMaxGlobalLimited(max).setPreviewCount(previewCount));
        }
    }

    public TraceabilityPredicate autoEnergyInputs(int min, int max) {
        return autoEnergyInputs(min, max, 2);
    }

    public TraceabilityPredicate autoEnergyInputs() {
        return autoEnergyInputs(1, 3);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format(I18n.format("zbgt.laser_enabled.1") +
                TooltipHelper.RAINBOW + I18n.format("zbgt.laser_enabled.2")) +
                (allowsSubstationHatches() ? I18n.format("zbgt.substation_enabled") : ""));
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return ClientHandler.GTPP_MACHINE_OVERLAY;
    }
}
