package com.zorbatron.zbgt.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static net.minecraft.init.Blocks.COBBLESTONE;

import net.minecraft.item.ItemStack;

import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;

import appeng.api.AEApi;
import appeng.api.definitions.IMaterials;
import appeng.api.definitions.IParts;
import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.metatileentities.MetaTileEntities;

public class AE2Recipes {

    protected static void init() {
        final IMaterials ae2Materials = AEApi.instance().definitions().materials();
        final IParts ae2Parts = AEApi.instance().definitions().parts();

        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .input(COBBLESTONE, 256_000)
                .outputs(ae2Materials.singularity().maybeStack(1).orElse(ItemStack.EMPTY))
                .EUt(GTValues.VA[GTValues.HV]).duration(20 * 60)
                .buildAndRegister();

        RecipeMaps.COMPRESSOR_RECIPES.recipeBuilder()
                .fluidInputs(Water.getFluid(256_000_000))
                .outputs(ae2Materials.singularity().maybeStack(1).orElse(ItemStack.EMPTY))
                .EUt(GTValues.VA[GTValues.HV]).duration(20 * 60)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .input(MetaTileEntities.FLUID_IMPORT_HATCH[IV])
                .input(MetaTileEntities.FLUID_EXPORT_HATCH[IV])
                .inputs(ae2Parts.fluidIface().maybeStack(1).orElse(ItemStack.EMPTY))
                .inputs(ae2Parts.fluidStorageBus().maybeStack(1).orElse(ItemStack.EMPTY))
                .input(OrePrefix.screw, CertusQuartz, 8)
                .inputs(ae2Materials.cardSpeed().maybeStack(16).orElse(ItemStack.EMPTY))
                .fluidInputs(Polyethylene.getFluid(L))
                .circuitMeta(1)
                .output(ZBGTMetaTileEntities.YOTTANK_ME_HATCH)
                .EUt(VA[EV]).duration(20 * 10)
                .buildAndRegister();
    }
}
