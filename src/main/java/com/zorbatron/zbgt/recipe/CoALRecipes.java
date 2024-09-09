package com.zorbatron.zbgt.recipe;

import static com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps.CoAL_RECIPES;
import static com.zorbatron.zbgt.recipe.CoALRecipes.coalRecipeType.*;
import static com.zorbatron.zbgt.recipe.helpers.RecipeAssists.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.QUANTUM_EYE;
import static gregtech.common.items.MetaItems.QUANTUM_STAR;

import com.filostorm.ulvcovers.items.ULVCoverMetaItems;
import com.zorbatron.zbgt.api.recipes.builders.CoALRecipeBuilder;
import com.zorbatron.zbgt.api.util.ZBGTMods;

import gregtech.api.recipes.RecipeBuilder;

public class CoALRecipes {

    public static void init() {
        if (ZBGTMods.ULV_COVERS.isModLoaded()) {
            ulv();
        }
        lvToEV();
        luvToUV();
    }

    private static void specialRecipes(int tier, coalRecipeType type) {
        if (tier == LV && type == MOTOR) {
            CoAL_RECIPES.recipeBuilder()
                    .outputs(getMotorByTier(LV).getStackForm(64))
                    .input(stickLong, Steel, 48)
                    .input(cableGtHex, getCableByTier(LV), 6)
                    .input(wireGtHex, getFineWireByTier(LV), 12)
                    .input(stickLong, SteelMagnetic, 24)
                    .EUt(VA[ULV]).duration(600)
                    .CasingTier(LV)
                    .CWUt(getCWUt(LV))
                    .circuitMeta(MOTOR.ordinal() + 1)
                    .buildAndRegister();
        }
    }

    private static void ulv() {
        CoAL_RECIPES.recipeBuilder()
                .output(ULVCoverMetaItems.ELECTRIC_MOTOR_ULV, 64)
                .input(stickLong, Bronze, 48)
                .input(cableGtHex, getCableByTier(ULV), 48)
                .input(wireGtHex, getFineWireByTier(ULV), 12)
                .input(stickLong, IronMagnetic, 24)
                .EUt(VA[ULV]).duration(600)
                .buildAndRegister();

        CoAL_RECIPES.recipeBuilder()
                .output(ULVCoverMetaItems.ELECTRIC_PISTON_ULV, 64)
                .input(gear, Bronze, 12)
                .input(stickLong, Bronze, 48)
                .input(ULVCoverMetaItems.ELECTRIC_MOTOR_ULV, 48)
                .input(cableGtHex, getCableByTier(ULV), 6)
                .input(plateDense, Bronze, 16)
                .EUt(VA[ULV]).duration(600)
                .buildAndRegister();

        CoAL_RECIPES.recipeBuilder()
                .output(ULVCoverMetaItems.ELECTRIC_PUMP_ULV, 64)
                .input(screw, Bronze, 48)
                .input(rotor, Bronze, 48)
                .input(pipeNormalFluid, Bronze, 48)
                .input(cableGtHex, getCableByTier(ULV), 3)
                .input(ULVCoverMetaItems.ELECTRIC_MOTOR_ULV, 48)
                .fluidInputs(Rubber.getFluid(L * 24))
                .EUt(VA[ULV]).duration(600)
                .buildAndRegister();

        CoAL_RECIPES.recipeBuilder()
                .output(ULVCoverMetaItems.ROBOT_ARM_ULV, 64)
                .input(circuit, getMarkerMaterialByTier(ULV), 48)
                .input(stickLong, Bronze, 48)
                .input(cableGtHex, getCableByTier(ULV), 9)
                .input(ULVCoverMetaItems.ELECTRIC_PISTON_ULV, 64)
                .input(ULVCoverMetaItems.ELECTRIC_MOTOR_ULV, 96)
                .EUt(VA[ULV]).duration(600)
                .buildAndRegister();

        CoAL_RECIPES.recipeBuilder()
                .output(ULVCoverMetaItems.CONVEYOR_MODULE_ULV, 64)
                .input(ULVCoverMetaItems.ELECTRIC_MOTOR_ULV, 96)
                .input(cableGtHex, getCableByTier(ULV), 3)
                .fluidInputs(Rubber.getFluid(L * 288))
                .EUt(VA[ULV]).duration(600)
                .buildAndRegister();
    }

    private static void lvToEV() {
        for (int tier = LV; tier <= IV; tier++) {
            for (coalRecipeType type : coalRecipeType.values()) {
                if (type == PUMP) {
                    if (tier < IV) {
                        getCoALLowTierRecipe(tier, type).fluidInputs(Rubber.getFluid(L * 24))
                                .buildAndRegister();
                    }

                    getCoALLowTierRecipe(tier, type)
                            .fluidInputs(StyreneButadieneRubber.getFluid(L * 24))
                            .buildAndRegister();
                    getCoALLowTierRecipe(tier, type)
                            .fluidInputs(SiliconeRubber.getFluid(L * 24))
                            .buildAndRegister();
                } else if (type == CONVEYOR) {
                    if (tier < IV) {
                        getCoALLowTierRecipe(tier, type)
                                .fluidInputs(Rubber.getFluid(L * 288))
                                .buildAndRegister();
                    }

                    getCoALLowTierRecipe(tier, type)
                            .fluidInputs(StyreneButadieneRubber.getFluid(L * 288))
                            .buildAndRegister();
                    getCoALLowTierRecipe(tier, type)
                            .fluidInputs(SiliconeRubber.getFluid(L * 288))
                            .buildAndRegister();
                } else if (type == EMITTER || type == SENSOR) {
                    if (tier < EV) {
                        getCoALLowTierRecipe(tier, type)
                                .input(tier == MV ? gemFlawless : gem, getLowEmitterSensorStarMaterial(tier), 48)
                                .buildAndRegister();
                        getCoALLowTierRecipe(tier, type)
                                .input(tier == MV ? gemFlawless : gem, getLowEmitterSensorStarMaterial(tier), 48)
                                .buildAndRegister();
                    } else {
                        getCoALLowTierRecipe(tier, type)
                                .input(getStarByTier(tier), 48)
                                .buildAndRegister();
                        getCoALLowTierRecipe(tier, type)
                                .input(getStarByTier(tier), 48)
                                .buildAndRegister();
                    }
                } else if (type == FIELD_GEN) {
                    switch (tier) {
                        case LV -> getCoALLowTierRecipe(tier, type)
                                .input(gem, EnderPearl, 48).buildAndRegister();
                        case MV -> getCoALLowTierRecipe(tier, type)
                                .input(gem, EnderEye, 48)
                                .buildAndRegister();
                        case HV -> getCoALLowTierRecipe(tier, type)
                                .inputs(QUANTUM_EYE.getStackForm(48))
                                .buildAndRegister();
                        case EV -> getCoALLowTierRecipe(tier, type)
                                .input(gem, NetherStar, 48)
                                .buildAndRegister();
                        case IV -> getCoALLowTierRecipe(tier, type)
                                .inputs(QUANTUM_STAR.getStackForm(48))
                                .buildAndRegister();
                    }
                } else {
                    getCoALLowTierRecipe(tier, type).buildAndRegister();
                    specialRecipes(tier, type);
                }
            }
        }
    }

    private static void luvToUV() {
        for (int tier = LuV; tier < UHV; tier++) {
            for (coalRecipeType type : coalRecipeType.values()) {
                getCoALHighTierRecipe(tier, type).buildAndRegister();
                specialRecipes(tier, type);
            }
        }
    }

    private static RecipeBuilder<CoALRecipeBuilder> getCoALLowTierRecipe(int tier, coalRecipeType type) {
        CoALRecipeBuilder builder = CoAL_RECIPES.recipeBuilder().EUt(VA[tier - 1]).duration(600)
                .CasingTier(tier)
                .CWUt(getCWUt(tier))
                .circuitMeta(type.ordinal() + 1);

        return switch (type) {
            case MOTOR -> builder
                    .outputs(getMotorByTier(tier).getStackForm(64))
                    .input(stickLong, tier == 1 ? Iron : getMaterialByTier(tier), 48)
                    .input(cableGtHex, getCableByTier(tier), tier > 2 ? 12 : 6)
                    .input(wireGtHex, getFineWireByTier(tier), tier > 1 ? 24 * (tier == 5 ? 2 : 1) : 12)
                    .input(stickLong, getMagneticMaterialByTier(tier), 24);

            case PISTON -> builder
                    .outputs(getPistonByTier(tier).getStackForm(64))
                    .input(gear, getMaterialByTier(tier), 12)
                    .input(stickLong, getMaterialByTier(tier), 48)
                    .inputs(getMotorByTier(tier).getStackForm(48))
                    .input(cableGtHex, getCableByTier(tier), 6)
                    .input(plateDense, getMaterialByTier(tier), 16);

            case PUMP -> builder
                    .outputs(getPumpByTier(tier).getStackForm(64))
                    .input(screw, getFluidPipeMaterialByTier(tier == 5 ? tier : tier - 1), 48)
                    .input(rotor, getFluidPipeMaterialByTier(tier == 5 ? tier : tier - 1), 48)
                    .input(pipeNormalFluid, getFluidPipeMaterialByTier(tier), 48)
                    .input(cableGtHex, getCableByTier(tier), 3)
                    .inputs(getMotorByTier(tier).getStackForm(48));

            case ROBOT_ARM -> builder
                    .outputs(getRobotArmByTier(tier).getStackForm(64))
                    .input(circuit, getMarkerMaterialByTier(tier), 48)
                    .input(stickLong, getMaterialByTier(tier), 48)
                    .input(cableGtHex, getCableByTier(tier), 9)
                    .inputs(getPistonByTier(tier).getStackForm(48))
                    .inputs(getMotorByTier(tier).getStackForm(96));

            case CONVEYOR -> builder
                    .outputs(getConveyorByTier(tier).getStackForm(64))
                    .inputs(getMotorByTier(tier).getStackForm(96))
                    .input(cableGtHex, getCableByTier(tier), 3);

            case EMITTER -> builder
                    .outputs(getEmitterByTier(tier).getStackForm(64))
                    .input(circuit, getMarkerMaterialByTier(tier), 96)
                    .input(cableGtHex, getCableByTier(tier), 6)
                    .fluidInputs(getLowEmitterSensorRodMaterial(tier).getFluid(13824));

            case SENSOR -> builder
                    .outputs(getSensorByTier(tier).getStackForm(64))
                    .input(plateDense, getMaterialByTier(tier), 21)
                    .input(stickLong, getLowEmitterSensorRodMaterial(tier), 24)
                    .input(circuit, getMarkerMaterialByTier(tier), 48);

            case FIELD_GEN -> builder
                    .outputs(getFieldGeneratorByTier(tier).getStackForm(64))
                    .input(circuit, getMarkerMaterialByTier(tier), 96)
                    .input(plateDouble, getMaterialByTier(tier), tier > HV ? 96 : 48)
                    .fluidInputs(getSuperconductorByTier(tier).getFluid(384 * L));
        };
    }

    private static RecipeBuilder<CoALRecipeBuilder> getCoALHighTierRecipe(int tier, coalRecipeType type) {
        CoALRecipeBuilder builder = CoAL_RECIPES.recipeBuilder().EUt(VA[tier - 1])
                .duration(28800)
                .CasingTier(tier)
                .CWUt(getCWUt(tier))
                .circuitMeta(type.ordinal() + 1);

        return switch (type) {
            case MOTOR -> builder
                    .outputs(getMotorByTier(tier).getStackForm(64))
                    .input(stickLong, getMagneticMaterialByTier(tier), 24)
                    .input(cableGtHex, getCableByTier(tier), 6)
                    .fluidInputs(SolderingAlloy.getFluid((int) (48 * L * Math.pow(2, tier - LuV))))
                    .fluidInputs(Lubricant.getFluid((int) (48 * 250 * Math.pow(2, tier - LuV))))
                    .fluidInputs(getMainComponentMaterialByTier(tier).getFluid((tier == LuV ? 900 : 1800) * 48))
                    .fluidInputs(getFineWireByTier(tier).getFluid(L * 48 * 8 * (tier - 4)))
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid(L * 4 * 48) : null);

            case PISTON -> builder
                    .outputs(getPistonByTier(tier).getStackForm(64))
                    .input(plateDense, getMainComponentMaterialByTier(tier), 21)
                    .inputs(getMotorByTier(tier).getStackForm(48))
                    .input(cableGtHex, getCableByTier(tier), 6)
                    .fluidInputs(SolderingAlloy.getFluid((int) (48 * L * Math.pow(2, tier - LuV))))
                    .fluidInputs(Lubricant.getFluid((int) (48 * 250 * Math.pow(2, tier - LuV))))
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid(L * 4 * 48) : null);

            case PUMP -> builder
                    .outputs(getPumpByTier(tier).getStackForm(64))
                    .input(cableGtHex, getCableByTier(tier), 6)
                    .input(plateDense, getMainComponentMaterialByTier(tier), 10)
                    .inputs(getMotorByTier(tier).getStackForm(48))
                    .fluidInputs(SolderingAlloy.getFluid((int) (48 * L * Math.pow(2, tier - LuV))))
                    .fluidInputs(Lubricant.getFluid((int) (48 * 250 * Math.pow(2, tier - LuV))))
                    .fluidInputs(getFluidPipeMaterialByTier(tier).getFluid(getFluidPipeAsFluidAmountByTier(tier) * 48))
                    .fluidInputs(getMainComponentMaterialByTier(tier).getFluid((tier < UV ? 5 : 1) * L * 48))
                    .fluidInputs(tier >= UV ? getSecondaryComponentMaterialByTier(tier).getFluid(L * 4 * 48) : null)
                    .fluidInputs(SiliconeRubber.getFluid((int) Math.pow(2, tier - LuV) * 48 * L))
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid(L * 4 * 48) : null);

            case ROBOT_ARM -> builder.outputs(getRobotArmByTier(tier).getStackForm(64))
                    .input(circuit, getMarkerMaterialByTier(tier), 48)
                    .input(circuit, getMarkerMaterialByTier(tier - 1), 96)
                    .input(circuit, getMarkerMaterialByTier(tier - 2), 192)
                    .input(cableGtHex, getCableByTier(tier), 18)
                    .inputs(getPistonByTier(tier).getStackForm(48))
                    .inputs(getMotorByTier(tier).getStackForm(96))
                    .fluidInputs(SolderingAlloy.getFluid((int) (48 * L * Math.pow(2, tier - LuV))))
                    .fluidInputs(Lubricant.getFluid((int) (48 * 250 * Math.pow(2, tier - LuV))))
                    .fluidInputs(getMainComponentMaterialByTier(tier).getFluid(L * 11 * 48))
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid(L * 4 * 48) : null);

            case CONVEYOR -> builder
                    .outputs(getConveyorByTier(tier).getStackForm(64))
                    .input(plateDense, getMainComponentMaterialByTier(tier), 10)
                    .input(cableGtHex, getCableByTier(tier), 6)
                    .inputs(getMotorByTier(tier).getStackForm(96))
                    .fluidInputs(SolderingAlloy.getFluid((int) (48 * L * Math.pow(2, tier - LuV))))
                    .fluidInputs(Lubricant.getFluid((int) (48 * 250 * Math.pow(2, tier - LuV))))
                    .fluidInputs(getMainComponentMaterialByTier(tier).getFluid(424 * 48))
                    .fluidInputs(StyreneButadieneRubber.getFluid(L * 48 * 8 * (tier - 5)))
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid(L * 4 * 48) : null);

            case EMITTER -> builder
                    .outputs(getEmitterByTier(tier).getStackForm(64))
                    .input(cableGtHex, getCableByTier(tier), 24)
                    .inputs(getMotorByTier(tier).getStackForm(48))
                    .input(getStarByTier(tier), tier == ZPM ? 2 : 1)
                    .input(frameGt, tier == ZPM ? NaquadahAlloy : getMainComponentMaterialByTier(tier), 48)
                    .input(circuit, getMarkerMaterialByTier(tier), 96)
                    .fluidInputs(SolderingAlloy.getFluid((int) (48 * L * Math.pow(2, tier - LuV))))
                    .fluidInputs(getSensorEmitterFoil(tier).getFluid(L * 48 * 24))
                    .fluidInputs(getSensorEmitterPlateRod(tier).getFluid(L * 48 * 4))
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid(L * 4 * 48) : null);

            case SENSOR -> builder
                    .outputs(getSensorByTier(tier).getStackForm(64))
                    .input(cableGtHex, getCableByTier(tier), 24)
                    .inputs(getMotorByTier(tier).getStackForm(48))
                    .input(getStarByTier(tier), tier == ZPM ? 2 : 1)
                    .input(frameGt, tier == ZPM ? NaquadahAlloy : getMainComponentMaterialByTier(tier), 48)
                    .input(circuit, getMarkerMaterialByTier(tier), 96)
                    .fluidInputs(SolderingAlloy.getFluid((int) (48 * L * Math.pow(2, tier - LuV))))
                    .fluidInputs(getSensorEmitterFoil(tier).getFluid(L * 48 * 24))
                    .fluidInputs(getSensorEmitterPlateRod(tier).getFluid(L * 48 * 4))
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid(L * 4 * 48) : null);

            case FIELD_GEN -> builder
                    .outputs(getFieldGeneratorByTier(tier).getStackForm(64))
                    .input(getStarByTier(tier), 48)
                    .input(frameGt, tier == ZPM ? NaquadahAlloy : getMainComponentMaterialByTier(tier), 48)
                    .input(plateDense, tier == ZPM ? NaquadahAlloy : getMainComponentMaterialByTier(tier), 32)
                    .input(cableGtHex, getCableByTier(tier), 48)
                    .inputs(getEmitterByTier(tier).getStackForm(96))
                    .input(circuit, getMarkerMaterialByTier(tier), 384)
                    .fluidInputs(SolderingAlloy.getFluid(L * 48 * 4 * (tier - 5)))
                    .fluidInputs(getSuperconductorByTier(tier).getFluid(L * 16 * 48))
                    .fluidInputs(tier > ZPM ? Naquadria.getFluid(L * 4 * 48) : null);
        };
    }

    public enum coalRecipeType {
        MOTOR,
        PISTON,
        PUMP,
        ROBOT_ARM,
        CONVEYOR,
        EMITTER,
        SENSOR,
        FIELD_GEN
    }
}
