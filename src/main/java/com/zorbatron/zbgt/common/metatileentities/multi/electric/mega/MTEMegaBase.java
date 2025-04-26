package com.zorbatron.zbgt.common.metatileentities.multi.electric.mega;

import static gregicality.multiblocks.api.metatileentity.GCYMMultiblockAbility.PARALLEL_HATCH;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregicality.multiblocks.api.capability.IParallelHatch;
import gregicality.multiblocks.api.capability.IParallelMultiblock;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.utils.TooltipHelper;

public abstract class MTEMegaBase extends RecipeMapMultiblockController implements IParallelMultiblock {

    public MTEMegaBase(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap) {
        this(metaTileEntityId, recipeMap, false);
    }

    public MTEMegaBase(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, boolean hasPerfectOC) {
        super(metaTileEntityId, recipeMap);
        this.recipeMapWorkable = new MegaRecipeLogic(this, hasPerfectOC);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();

        List<IEnergyContainer> list = new ArrayList<>();
        list.addAll(getAbilities(MultiblockAbility.INPUT_ENERGY));
        list.addAll(getAbilities(MultiblockAbility.INPUT_LASER));
        list.addAll(getAbilities(MultiblockAbility.SUBSTATION_INPUT_ENERGY));

        this.energyContainer = new EnergyContainerList(list);
    }

    @Override
    public boolean isParallel() {
        return true;
    }

    @Override
    public int getMaxParallel() {
        int baseParallel = 256;

        List<IParallelHatch> parallelHatches = getAbilities(PARALLEL_HATCH);
        if (!parallelHatches.isEmpty()) {
            baseParallel = Math.min(Integer.MAX_VALUE, baseParallel * parallelHatches.get(0).getCurrentParallel());
        }

        return baseParallel;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format(I18n.format("zbgt.laser_enabled.1") +
                TooltipHelper.RAINBOW + I18n.format("zbgt.laser_enabled.2")) +
                I18n.format("zbgt.substation_enabled"));
        tooltip.add(I18n.format("zbgt.mega_parallel.1"));
        tooltip.add(I18n.format("zbgt.mega_parallel.2"));
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkEnergyIn, boolean checkMaintenance, boolean checkItemIn,
                                               boolean checkItemOut, boolean checkFluidIn, boolean checkFluidOut,
                                               boolean checkMuffler) {
        TraceabilityPredicate predicate = super.autoAbilities(checkEnergyIn, checkMaintenance, checkItemIn,
                checkItemOut, checkFluidIn, checkFluidOut, checkMuffler);

        return predicate.or(abilities(PARALLEL_HATCH)
                .setMaxGlobalLimited(1, 1));
    }

    protected TraceabilityPredicate autoEnergyInputs() {
        return abilities(MultiblockAbility.INPUT_ENERGY)
                .setMaxGlobalLimited(8, 8)
                .or(abilities(MultiblockAbility.SUBSTATION_INPUT_ENERGY)
                        .setMaxGlobalLimited(1))
                .or(abilities(MultiblockAbility.INPUT_LASER)
                        .setMaxGlobalLimited(1));
    }

    public static class MegaRecipeLogic extends MultiblockRecipeLogic {

        public MegaRecipeLogic(MTEMegaBase tileEntity) {
            super(tileEntity);
        }

        public MegaRecipeLogic(MTEMegaBase tileEntity, boolean hasPerfectOC) {
            super(tileEntity, hasPerfectOC);
        }

        @Override
        public @NotNull MTEMegaBase getMetaTileEntity() {
            return (MTEMegaBase) super.getMetaTileEntity();
        }

        @Override
        public int getParallelLimit() {
            return getMetaTileEntity().getMaxParallel();
        }
    }
}
