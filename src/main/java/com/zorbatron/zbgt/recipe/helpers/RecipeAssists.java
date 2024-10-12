package com.zorbatron.zbgt.recipe.helpers;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.items.MetaItems.*;

import net.minecraft.item.ItemStack;

import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;

@SuppressWarnings("unused")
public class RecipeAssists {

    public static int getCWUt(int tier) {
        return (int) Math.max(EV, Math.pow(MV, tier - MV));
    }

    public static Material getMarkerMaterialByTier(int tier) {
        return switch (tier) {
            case (LV)  -> MarkerMaterials.Tier.LV;
            case (MV)  -> MarkerMaterials.Tier.MV;
            case (HV)  -> MarkerMaterials.Tier.HV;
            case (EV)  -> MarkerMaterials.Tier.EV;
            case (IV)  -> MarkerMaterials.Tier.IV;
            case (LuV) -> MarkerMaterials.Tier.LuV;
            case (ZPM) -> MarkerMaterials.Tier.ZPM;
            case (UV)  -> MarkerMaterials.Tier.UV;
            case (UHV) -> MarkerMaterials.Tier.UHV;
            case (UEV) -> MarkerMaterials.Tier.UEV;
            case (UIV) -> MarkerMaterials.Tier.UIV;
            case (UXV) -> MarkerMaterials.Tier.UXV;
            case (OpV) -> MarkerMaterials.Tier.OpV;
            case (MAX) -> MarkerMaterials.Tier.MAX;
            default -> MarkerMaterials.Tier.ULV;
        };
    }

    public static MetaItem<?>.MetaValueItem getGenericCircuitByTier(int tier) {
        return switch (tier) {
            case (1) -> GENERIC_CIRCUIT_LV;
            case (2) -> GENERIC_CIRCUIT_MV;
            case (3) -> GENERIC_CIRCUIT_HV;
            case (4) -> GENERIC_CIRCUIT_EV;
            case (5) -> GENERIC_CIRCUIT_IV;
            case (6) -> GENERIC_CIRCUIT_LuV;
            case (7) -> GENERIC_CIRCUIT_ZPM;
            case (8) -> GENERIC_CIRCUIT_UV;
            case (9) -> GENERIC_CIRCUIT_UHV;
            case (10) -> GENERIC_CIRCUIT_UEV;
            case (11) -> GENERIC_CIRCUIT_UIV;
            case (12) -> GENERIC_CIRCUIT_UXV;
            case (13) -> GENERIC_CIRCUIT_OpV;
            case (14) -> GENERIC_CIRCUIT_MAX;
            default -> GENERIC_CIRCUIT_ULV;
        };
    }

    public static Material getMaterialByTier(int tier) {
        return switch (tier) {
            case (LV)  -> Steel;
            case (MV)  -> Aluminium;
            case (HV)  -> StainlessSteel;
            case (EV)  -> Titanium;
            case (IV)  -> TungstenSteel;
            case (LuV) -> RhodiumPlatedPalladium;
            case (ZPM) -> NaquadahAlloy;
            case (UV)  -> Darmstadtium;
            case (UHV) -> Neutronium;
            default    -> WroughtIron;
        };
    }

    public static Material getCableByTier(int tier) {
        return switch (tier) {
            case (LV)  -> Tin;
            case (MV)  -> Copper;
            case (HV)  -> Silver;
            case (EV)  -> Aluminium;
            case (IV)  -> Tungsten;
            case (LuV) -> NiobiumTitanium;
            case (ZPM) -> VanadiumGallium;
            case (UV)  -> YttriumBariumCuprate;
            default -> Lead;
        };
    }

    public static Material getFineWireByTier(int tier) {
        return switch (tier) {
            case (LV)  -> Copper;
            case (MV)  -> Cupronickel;
            case (HV)  -> Electrum;
            case (EV)  -> Kanthal;
            case (IV)  -> Graphene;
            case (LuV) -> Ruridit;
            case (ZPM) -> Europium;
            case (UV)  -> Americium;
            default    -> Tin;
        };
    }

    public static MetaItem<?>.MetaValueItem getMotorByTier(int tier) {
        return switch (tier) {
            case (MV)  -> ELECTRIC_MOTOR_MV;
            case (HV)  -> ELECTRIC_MOTOR_HV;
            case (EV)  -> ELECTRIC_MOTOR_EV;
            case (IV)  -> ELECTRIC_MOTOR_IV;
            case (LuV) -> ELECTRIC_MOTOR_LuV;
            case (ZPM) -> ELECTRIC_MOTOR_ZPM;
            case (UV)  -> ELECTRIC_MOTOR_UV;
            case (UHV) -> ELECTRIC_MOTOR_UHV;
            case (UEV) -> ELECTRIC_MOTOR_UEV;
            case (UIV) -> ELECTRIC_MOTOR_UIV;
            case (UXV) -> ELECTRIC_MOTOR_UXV;
            case (OpV) -> ELECTRIC_MOTOR_OpV;
            default    -> ELECTRIC_MOTOR_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getPistonByTier(int tier) {
        return switch (tier) {
            case (MV)  -> ELECTRIC_PISTON_MV;
            case (HV)  -> ELECTRIC_PISTON_HV;
            case (EV)  -> ELECTRIC_PISTON_EV;
            case (IV)  -> ELECTRIC_PISTON_IV;
            case (LuV) -> ELECTRIC_PISTON_LUV;
            case (ZPM) -> ELECTRIC_PISTON_ZPM;
            case (UV)  -> ELECTRIC_PISTON_UV;
            case (UHV) -> ELECTRIC_PISTON_UHV;
            case (UEV) -> ELECTRIC_PISTON_UEV;
            case (UIV) -> ELECTRIC_PISTON_UIV;
            case (UXV) -> ELECTRIC_PISTON_UXV;
            case (OpV) -> ELECTRIC_PISTON_OpV;
            default    -> ELECTRIC_PISTON_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getPumpByTier(int tier) {
        return switch (tier) {
            case (MV)  -> ELECTRIC_PUMP_MV;
            case (HV)  -> ELECTRIC_PUMP_HV;
            case (EV)  -> ELECTRIC_PUMP_EV;
            case (IV)  -> ELECTRIC_PUMP_IV;
            case (LuV) -> ELECTRIC_PUMP_LuV;
            case (ZPM) -> ELECTRIC_PUMP_ZPM;
            case (UV)  -> ELECTRIC_PUMP_UV;
            case (UHV) -> ELECTRIC_PUMP_UHV;
            case (UEV) -> ELECTRIC_PUMP_UEV;
            case (UIV) -> ELECTRIC_PUMP_UIV;
            case (UXV) -> ELECTRIC_PUMP_UXV;
            case (OpV) -> ELECTRIC_PUMP_OpV;
            default    -> ELECTRIC_PUMP_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getRobotArmByTier(int tier) {
        return switch (tier) {
            case (MV)  -> ROBOT_ARM_MV;
            case (HV)  -> ROBOT_ARM_HV;
            case (EV)  -> ROBOT_ARM_EV;
            case (IV)  -> ROBOT_ARM_IV;
            case (LuV) -> ROBOT_ARM_LuV;
            case (ZPM) -> ROBOT_ARM_ZPM;
            case (UV)  -> ROBOT_ARM_UV;
            case (UHV) -> ROBOT_ARM_UHV;
            case (UEV) -> ROBOT_ARM_UEV;
            case (UIV) -> ROBOT_ARM_UIV;
            case (UXV) -> ROBOT_ARM_UXV;
            case (OpV) -> ROBOT_ARM_OpV;
            default    -> ROBOT_ARM_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getConveyorByTier(int tier) {
        return switch (tier) {
            case (MV)  -> CONVEYOR_MODULE_MV;
            case (HV)  -> CONVEYOR_MODULE_HV;
            case (EV)  -> CONVEYOR_MODULE_EV;
            case (IV)  -> CONVEYOR_MODULE_IV;
            case (LuV) -> CONVEYOR_MODULE_LuV;
            case (ZPM) -> CONVEYOR_MODULE_ZPM;
            case (UV)  -> CONVEYOR_MODULE_UV;
            case (UHV) -> CONVEYOR_MODULE_UHV;
            case (UEV) -> CONVEYOR_MODULE_UEV;
            case (UIV) -> CONVEYOR_MODULE_UIV;
            case (UXV) -> CONVEYOR_MODULE_UXV;
            case (OpV) -> CONVEYOR_MODULE_OpV;
            default    -> CONVEYOR_MODULE_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getFluidRegulatorByTier(int tier) {
        return switch (tier) {
            case (MV)  -> FLUID_REGULATOR_MV;
            case (HV)  -> FLUID_REGULATOR_HV;
            case (EV)  -> FLUID_REGULATOR_EV;
            case (IV)  -> FLUID_REGULATOR_IV;
            case (LuV) -> FLUID_REGULATOR_LUV;
            case (ZPM) -> FLUID_REGULATOR_ZPM;
            case (UV)  -> FLUID_REGULATOR_UV;
            default    -> FLUID_REGULATOR_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getEmitterByTier(int tier) {
        return switch (tier) {
            case (MV)  -> EMITTER_MV;
            case (HV)  -> EMITTER_HV;
            case (EV)  -> EMITTER_EV;
            case (IV)  -> EMITTER_IV;
            case (LuV) -> EMITTER_LuV;
            case (ZPM) -> EMITTER_ZPM;
            case (UV)  -> EMITTER_UV;
            case (UHV) -> EMITTER_UHV;
            case (UEV) -> EMITTER_UEV;
            case (UIV) -> EMITTER_UIV;
            case (UXV) -> EMITTER_UXV;
            case (OpV) -> EMITTER_OpV;
            default    -> EMITTER_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getSensorByTier(int tier) {
        return switch (tier) {
            case (MV)  -> SENSOR_MV;
            case (HV)  -> SENSOR_HV;
            case (EV)  -> SENSOR_EV;
            case (IV)  -> SENSOR_IV;
            case (LuV) -> SENSOR_LuV;
            case (ZPM) -> SENSOR_ZPM;
            case (UV)  -> SENSOR_UV;
            case (UHV) -> SENSOR_UHV;
            case (UEV) -> SENSOR_UEV;
            case (UIV) -> SENSOR_UIV;
            case (UXV) -> SENSOR_UXV;
            case (OpV) -> SENSOR_OpV;
            default    -> SENSOR_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getFieldGeneratorByTier(int tier) {
        return switch (tier) {
            case (MV)  -> FIELD_GENERATOR_MV;
            case (HV)  -> FIELD_GENERATOR_HV;
            case (EV)  -> FIELD_GENERATOR_EV;
            case (IV)  -> FIELD_GENERATOR_IV;
            case (LuV) -> FIELD_GENERATOR_LuV;
            case (ZPM) -> FIELD_GENERATOR_ZPM;
            case (UV)  -> FIELD_GENERATOR_UV;
            case (UHV) -> FIELD_GENERATOR_UHV;
            case (UEV) -> FIELD_GENERATOR_UEV;
            case (UIV) -> FIELD_GENERATOR_UIV;
            case (UXV) -> FIELD_GENERATOR_UXV;
            case (OpV) -> FIELD_GENERATOR_OpV;
            default    -> FIELD_GENERATOR_LV;
        };
    }

    public static Material getMagneticMaterialByTier(int tier) {
        if (tier < MV) return IronMagnetic;
        if (tier < EV) return SteelMagnetic;
        if (tier < LuV) return NeodymiumMagnetic;
        else return SamariumMagnetic;
    }

    public static Material getFluidPipeMaterialByTier(int tier) {
        return switch (tier) {
            case (LV)  -> Bronze;
            case (MV)  -> Steel;
            case (HV)  -> StainlessSteel;
            case (EV)  -> Titanium;
            case (IV)  -> TungstenSteel;
            case (LuV) -> NiobiumTitanium;
            case (ZPM) -> Polybenzimidazole;
            case (UV)  -> Naquadah;
            default -> Tin;
        };
    }

    public static int getFluidPipeAsFluidAmountByTier(int tier) {
        return switch (tier) {
            case (ZPM) -> L * 3;
            case (UV)  -> L * 6;
            default    -> L;
        };
    }

    public static Material getLowEmitterSensorRodMaterial(int tier) {
        return switch (tier) {
            case (MV) -> Electrum;
            case (HV) -> Chrome;
            case (EV) -> Platinum;
            case (IV) -> Iridium;
            default   -> Brass;
        };
    }

    public static Material getSuperconductorByTier(int tier) {
        return switch (tier) {
            case (MV)  -> MagnesiumDiboride;
            case (HV)  -> MercuryBariumCalciumCuprate;
            case (EV)  -> UraniumTriplatinum;
            case (IV)  -> SamariumIronArsenicOxide;
            case (LuV) -> IndiumTinBariumTitaniumCuprate;
            case (ZPM) -> UraniumRhodiumDinaquadide;
            case (UV)  -> EnrichedNaquadahTriniumEuropiumDuranide;
            case (UHV) -> RutheniumTriniumAmericiumNeutronate;
            default    -> ManganesePhosphide;
        };
    }

    public static MetaItem<?>.MetaValueItem getStarByTier(int tier) {
        if (tier == EV) return QUANTUM_EYE;
        if (tier < UV) return QUANTUM_STAR;
        else return GRAVI_STAR;
    }

    public static Material getLowEmitterSensorStarMaterial(int tier) {
        return switch (tier) {
            case (MV) -> Emerald;
            case (HV) -> EnderEye;
            default -> Quartzite;
        };
    }

    public static Material getMainComponentMaterialByTier(int tier) {
        return switch (tier) {
            case (ZPM) -> Osmiridium;
            case (UV)  -> Tritanium;
            default    -> HSSS;
        };
    }

    public static Material getSecondaryComponentMaterialByTier(int tier) {
        if (tier == UV) return NaquadahAlloy;
        return getMainComponentMaterialByTier(tier);
    }

    public static Material getSensorEmitterFoilByTier(int tier) {
        return switch (tier) {
            case (LuV) -> Palladium;
            case (ZPM) -> Trinium;
            case (UV)  -> Naquadria;
            default    -> getMainComponentMaterialByTier(tier);
        };
    }

    public static Material getSensorEmitterPlateRodByTier(int tier) {
        return switch (tier) {
            case (LuV) -> Ruridit;
            case (ZPM) -> Osmiridium;
            default    -> getMainComponentMaterialByTier(tier);
        };
    }

    public static ItemStack getMachineCasingByTier(int tier) {
        return MetaBlocks.MACHINE_CASING.getItemVariant(getMachineCasingTypeByTier(tier));
    }

    public static ItemStack getMachineCasingByTier(int tier, int amount) {
        ItemStack casing = getMachineCasingByTier(tier);
        casing.setCount(amount);
        return casing;
    }

    public static BlockMachineCasing.MachineCasingType getMachineCasingTypeByTier(int tier) {
        return switch (tier) {
            case (LV) -> BlockMachineCasing.MachineCasingType.LV;
            case (MV) -> BlockMachineCasing.MachineCasingType.MV;
            case (HV) -> BlockMachineCasing.MachineCasingType.HV;
            case (EV) -> BlockMachineCasing.MachineCasingType.EV;
            case (IV) -> BlockMachineCasing.MachineCasingType.IV;
            case (LuV) -> BlockMachineCasing.MachineCasingType.LuV;
            case (ZPM) -> BlockMachineCasing.MachineCasingType.ZPM;
            case (UV) -> BlockMachineCasing.MachineCasingType.UV;
            case (UHV) -> BlockMachineCasing.MachineCasingType.UHV;
            default -> BlockMachineCasing.MachineCasingType.ULV;
        };
    }

    public static ItemStack getPreciseCasingByTier(int tier) {
        return ZBGTMetaBlocks.PRECISE_CASING.getItemVariant(getPreciseCasingTypeByTier(tier));
    }

    public static PreciseCasing.CasingType getPreciseCasingTypeByTier(int tier) {
        return switch (tier) {
            case (1) -> PreciseCasing.CasingType.PRECISE_CASING_1;
            case (2) -> PreciseCasing.CasingType.PRECISE_CASING_2;
            case (3) -> PreciseCasing.CasingType.PRECISE_CASING_3;
            case (4) -> PreciseCasing.CasingType.PRECISE_CASING_4;
            default -> PreciseCasing.CasingType.PRECISE_CASING_0;
        };
    }

    public static MetaItem<?>.MetaValueItem getDualCoverByTier(int tier) {
        return switch (tier) {
            case (MV)   -> DUAL_COVER_MV;
            case (HV)   -> DUAL_COVER_HV;
            case (EV)   -> DUAL_COVER_EV;
            case (IV)   -> DUAL_COVER_IV;
            case (LuV)  -> DUAL_COVER_LuV;
            case (ZPM)  -> DUAL_COVER_ZPM;
            case (UV)   -> DUAL_COVER_UV;
            case (UHV)  -> DUAL_COVER_UHV;
            case (UEV)  -> DUAL_COVER_UEV;
            case (UIV)  -> DUAL_COVER_UIV;
            case (UXV)  -> DUAL_COVER_UXV;
            case (OpV)  -> DUAL_COVER_OpV;
            default     -> DUAL_COVER_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getPreciseDualCoverByTier(int tier) {
        return switch (tier) {
            case (MV)   -> PRECISE_DUAL_COVER_MV;
            case (HV)   -> PRECISE_DUAL_COVER_HV;
            case (EV)   -> PRECISE_DUAL_COVER_EV;
            case (IV)   -> PRECISE_DUAL_COVER_IV;
            case (LuV)  -> PRECISE_DUAL_COVER_LuV;
            case (ZPM)  -> PRECISE_DUAL_COVER_ZPM;
            case (UV)   -> PRECISE_DUAL_COVER_UV;
            case (UHV)  -> PRECISE_DUAL_COVER_UHV;
            case (UEV)  -> PRECISE_DUAL_COVER_UEV;
            case (UIV)  -> PRECISE_DUAL_COVER_UIV;
            case (UXV)  -> PRECISE_DUAL_COVER_UXV;
            case (OpV)  -> PRECISE_DUAL_COVER_OpV;
            default     -> PRECISE_DUAL_COVER_LV;
        };
    }
}
