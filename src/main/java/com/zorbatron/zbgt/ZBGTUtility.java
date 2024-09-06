package com.zorbatron.zbgt;

import static gregicality.multiblocks.api.unification.GCYMMaterials.Zeron100;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Iridium;
import static gregtech.common.items.MetaItems.*;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.INotifiableHandler;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;

public class ZBGTUtility {

    public static @NotNull ResourceLocation zbgtId(@NotNull String path) {
        return new ResourceLocation("zbgt", path);
    }

    public static void getCircuitSlotTooltip(@NotNull SlotWidget widget,
                                             GhostCircuitItemStackHandler circuitItemStackHandler) {
        String configString;
        if (circuitItemStackHandler.getCircuitValue() == GhostCircuitItemStackHandler.NO_CONFIG) {
            configString = new TextComponentTranslation("gregtech.gui.configurator_slot.no_value").getFormattedText();
        } else {
            configString = String.valueOf(circuitItemStackHandler.getCircuitValue());
        }

        widget.setTooltipText("gregtech.gui.configurator_slot.tooltip", configString);
    }

    public static boolean isInventoryEmpty(IItemHandler inventory) {
        for (int slot = 0; slot < inventory.getSlots(); slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty()) return false;
        }

        return true;
    }

    public static void addNotifiableToMTE(ItemHandlerList itemHandlerList, MultiblockControllerBase controllerBase,
                                          MetaTileEntity sourceMTE, boolean isExport) {
        for (IItemHandler handler : itemHandlerList.getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiableHandler) {
                notifiableHandler.addNotifiableMetaTileEntity(controllerBase);
                notifiableHandler.addToNotifiedList(sourceMTE, handler, isExport);
            }
        }
    }

    public static void removeNotifiableFromMTE(ItemHandlerList itemHandlerList,
                                               MultiblockControllerBase controllerBase) {
        for (IItemHandler handler : itemHandlerList.getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiableHandler) {
                notifiableHandler.removeNotifiableMetaTileEntity(controllerBase);
            }
        }
    }

    public static void addNotifiableToMTE(INotifiableHandler notifiableHandler, MultiblockControllerBase controllerBase,
                                          MetaTileEntity sourceMTE, boolean isExport) {
        notifiableHandler.addNotifiableMetaTileEntity(controllerBase);
        notifiableHandler.addToNotifiedList(sourceMTE, notifiableHandler, isExport);
    }

    public static void removeNotifiableFromMTE(INotifiableHandler notifiableHandler,
                                               MultiblockControllerBase controllerBase) {
        notifiableHandler.removeNotifiableMetaTileEntity(controllerBase);
    }

    public static int getCWUt(int tier) {
        return (int) Math.max(4, Math.pow(2, tier - 2));
    }

    public static MetaItem<?>.MetaValueItem getMotorByTier(int tier) {
        return switch (tier) {
            case (2) -> ELECTRIC_MOTOR_MV;
            case (3) -> ELECTRIC_MOTOR_HV;
            case (4) -> ELECTRIC_MOTOR_EV;
            case (5) -> ELECTRIC_MOTOR_IV;
            case (6) -> ELECTRIC_MOTOR_LuV;
            case (7) -> ELECTRIC_MOTOR_ZPM;
            case (8) -> ELECTRIC_MOTOR_UV;
            case (9) -> ELECTRIC_MOTOR_UHV;
            case (10) -> ELECTRIC_MOTOR_UEV;
            case (11) -> ELECTRIC_MOTOR_UIV;
            case (12) -> ELECTRIC_MOTOR_UXV;
            case (13) -> ELECTRIC_MOTOR_OpV;
            default -> ELECTRIC_MOTOR_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getConveyorByTier(int tier) {
        return switch (tier) {
            case (2) -> CONVEYOR_MODULE_MV;
            case (3) -> CONVEYOR_MODULE_HV;
            case (4) -> CONVEYOR_MODULE_EV;
            case (5) -> CONVEYOR_MODULE_IV;
            case (6) -> CONVEYOR_MODULE_LuV;
            case (7) -> CONVEYOR_MODULE_ZPM;
            case (8) -> CONVEYOR_MODULE_UV;
            case (9) -> CONVEYOR_MODULE_UHV;
            case (10) -> CONVEYOR_MODULE_UEV;
            case (11) -> CONVEYOR_MODULE_UIV;
            case (12) -> CONVEYOR_MODULE_UXV;
            case (13) -> CONVEYOR_MODULE_OpV;
            default -> CONVEYOR_MODULE_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getPistonByTier(int tier) {
        return switch (tier) {
            case (2) -> ELECTRIC_PISTON_MV;
            case (3) -> ELECTRIC_PISTON_HV;
            case (4) -> ELECTRIC_PISTON_EV;
            case (5) -> ELECTRIC_PISTON_IV;
            case (6) -> ELECTRIC_PISTON_LUV;
            case (7) -> ELECTRIC_PISTON_ZPM;
            case (8) -> ELECTRIC_PISTON_UV;
            case (9) -> ELECTRIC_PISTON_UHV;
            case (10) -> ELECTRIC_PISTON_UEV;
            case (11) -> ELECTRIC_PISTON_UIV;
            case (12) -> ELECTRIC_PISTON_UXV;
            case (13) -> ELECTRIC_PISTON_OpV;
            default -> ELECTRIC_PISTON_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getRobotArmByTier(int tier) {
        return switch (tier) {
            case (2) -> ROBOT_ARM_MV;
            case (3) -> ROBOT_ARM_HV;
            case (4) -> ROBOT_ARM_EV;
            case (5) -> ROBOT_ARM_IV;
            case (6) -> ROBOT_ARM_LuV;
            case (7) -> ROBOT_ARM_ZPM;
            case (8) -> ROBOT_ARM_UV;
            case (9) -> ROBOT_ARM_UHV;
            case (10) -> ROBOT_ARM_UEV;
            case (11) -> ROBOT_ARM_UIV;
            case (12) -> ROBOT_ARM_UXV;
            case (13) -> ROBOT_ARM_OpV;
            default -> ROBOT_ARM_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getEmitterByTier(int tier) {
        return switch (tier) {
            case (2) -> EMITTER_MV;
            case (3) -> EMITTER_HV;
            case (4) -> EMITTER_EV;
            case (5) -> EMITTER_IV;
            case (6) -> EMITTER_LuV;
            case (7) -> EMITTER_ZPM;
            case (8) -> EMITTER_UV;
            case (9) -> EMITTER_UHV;
            case (10) -> EMITTER_UEV;
            case (11) -> EMITTER_UIV;
            case (12) -> EMITTER_UXV;
            case (13) -> EMITTER_OpV;
            default -> EMITTER_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getPumpByTier(int tier) {
        return switch (tier) {
            case (2) -> ELECTRIC_PUMP_MV;
            case (3) -> ELECTRIC_PUMP_HV;
            case (4) -> ELECTRIC_PUMP_EV;
            case (5) -> ELECTRIC_PUMP_IV;
            case (6) -> ELECTRIC_PUMP_LuV;
            case (7) -> ELECTRIC_PUMP_ZPM;
            case (8) -> ELECTRIC_PUMP_UV;
            case (9) -> ELECTRIC_PUMP_UHV;
            case (10) -> ELECTRIC_PUMP_UEV;
            case (11) -> ELECTRIC_PUMP_UIV;
            case (12) -> ELECTRIC_PUMP_UXV;
            case (13) -> ELECTRIC_PUMP_OpV;
            default -> ELECTRIC_PUMP_LV;
        };
    }

    public static MetaItem<?>.MetaValueItem getStarByTier(int tier) {
        if (tier == 4)
            return QUANTUM_EYE;
        if (tier < 8)
            return QUANTUM_STAR;
        else
            return GRAVI_STAR;
    }

    public static Material getLowEmitterSensorStarMaterial(int tier) {
        return switch (tier) {
            case (1) -> Quartzite;
            case (2) -> Emerald;
            case (3) -> EnderEye;
            default -> null;
        };
    }

    public static Material getLowEmitterSensorRodMaterial(int tier) {
        return switch (tier) {
            case (1) -> Brass;
            case (2) -> Electrum;
            case (3) -> Chrome;
            case (4) -> Platinum;
            case (5) -> Iridium;
            default -> null;
        };
    }

    public static Material getFineWireByTier(int tier) {
        return switch (tier) {
            case (1) -> Copper;
            case (2) -> Cupronickel;
            case (3) -> Electrum;
            case (4) -> Kanthal;
            case (8) -> Duranium;
            default -> Lead;
        };
    }

    public static Material getMaterialByTier(int tier) {
        return switch (tier) {
            case (1) -> Steel;
            case (2) -> Aluminium;
            case (3) -> StainlessSteel;
            case (4) -> Titanium;
            case (5) -> TungstenSteel;
            case (6) -> RhodiumPlatedPalladium;
            case (7) -> Duranium;
            case (8) -> Tritanium;
            case (9) -> Seaborgium;
            case (10) -> Bohrium;
            case (14) -> Neutronium;
            default -> Lead;
        };
    }

    public static Material getCableByTier(int tier) {
        return switch (tier) {
            case (1) -> Tin;
            case (2) -> Copper;
            case (3) -> Silver;
            case (4) -> Aluminium;
            case (5) -> Tungsten;
            case (6) -> YttriumBariumCuprate;
            case (7) -> Naquadah;
            case (8) -> Duranium;
            case (13) -> Neutronium;
            default -> Lead;
        };
    }

    public static Material getFluidPipeMaterialByTier(int tier) {
        return switch (tier) {
            case (1) -> Bronze;
            case (2) -> Steel;
            case (3) -> StainlessSteel;
            case (4) -> Titanium;
            case (5) -> TungstenSteel;
            case (9) -> Zeron100;
            case (13) -> Neutronium;
            default -> Tin;
        };
    }

    public static MetaItem<?>.MetaValueItem getFieldGeneratorByTier(int tier) {
        if (tier < 1)
            return FIELD_GENERATOR_LV;

        return switch (tier) {
            case (1) -> FIELD_GENERATOR_LV;
            case (2) -> FIELD_GENERATOR_MV;
            case (3) -> FIELD_GENERATOR_HV;
            case (4) -> FIELD_GENERATOR_EV;
            case (5) -> FIELD_GENERATOR_IV;
            case (6) -> FIELD_GENERATOR_LuV;
            case (7) -> FIELD_GENERATOR_ZPM;
            case (8) -> FIELD_GENERATOR_UV;
            case (9) -> FIELD_GENERATOR_UHV;
            case (10) -> FIELD_GENERATOR_UEV;
            case (11) -> FIELD_GENERATOR_UIV;
            case (12) -> FIELD_GENERATOR_UXV;
            default -> FIELD_GENERATOR_OpV;
        };
    }

    public static MetaItem<?>.MetaValueItem getSensorByTier(int tier) {
        if (tier < 1)
            return SENSOR_LV;

        return switch (tier) {
            case (1) -> SENSOR_LV;
            case (2) -> SENSOR_MV;
            case (3) -> SENSOR_HV;
            case (4) -> SENSOR_EV;
            case (5) -> SENSOR_IV;
            case (6) -> SENSOR_LuV;
            case (7) -> SENSOR_ZPM;
            case (8) -> SENSOR_UV;
            case (9) -> SENSOR_UHV;
            case (10) -> SENSOR_UEV;
            case (11) -> SENSOR_UIV;
            case (12) -> SENSOR_UXV;
            default -> SENSOR_OpV;
        };
    }

    public static Material getMarkerMaterialByTier(int tier) {
        if (tier < 0)
            return MarkerMaterials.Tier.ULV;

        return switch (tier) {
            case (0) -> MarkerMaterials.Tier.ULV;
            case (1) -> MarkerMaterials.Tier.LV;
            case (2) -> MarkerMaterials.Tier.MV;
            case (3) -> MarkerMaterials.Tier.HV;
            case (4) -> MarkerMaterials.Tier.EV;
            case (5) -> MarkerMaterials.Tier.IV;
            case (6) -> MarkerMaterials.Tier.LuV;
            case (7) -> MarkerMaterials.Tier.ZPM;
            case (8) -> MarkerMaterials.Tier.UV;
            case (9) -> MarkerMaterials.Tier.UHV;
            case (10) -> MarkerMaterials.Tier.UEV;
            case (11) -> MarkerMaterials.Tier.UIV;
            case (12) -> MarkerMaterials.Tier.UXV;
            case (13) -> MarkerMaterials.Tier.OpV;
            default -> MarkerMaterials.Tier.MAX;
        };
    }

    public static Material getSuperconductorByTier(int tier) {
        if (tier < 1)
            return ManganesePhosphide;

        return switch (tier) {
            case (1) -> ManganesePhosphide;
            case (2) -> MagnesiumDiboride;
            case (3) -> MercuryBariumCalciumCuprate;
            case (4) -> UraniumTriplatinum;
            case (5) -> SamariumIronArsenicOxide;
            case (6) -> IndiumTinBariumTitaniumCuprate;
            case (7) -> UraniumRhodiumDinaquadide;
            case (8) -> EnrichedNaquadahTriniumEuropiumDuranide;
            default -> RutheniumTriniumAmericiumNeutronate;
        };
    }

    public static Material getMagneticMaterialFluidByTier(int tier) {
        if (tier == 1)
            return IronMagnetic;
        if (tier < 4)
            return SteelMagnetic;
        if (tier < 10) {
            return NeodymiumMagnetic;
        } else
            return SamariumMagnetic;
    }

    public static Material getMainComponentMaterialByTier(int tier) {
        return switch (tier) {
            case (6) -> HSSS;
            case (7) -> HSSE;
            case (8) -> Tritanium;
            case (13) -> Neutronium;
            default -> Tin;
        };
    }

    public static Material getFluidMaterialByTier(int tier) {
        return switch (tier) {
            case (9) -> Seaborgium;
            case (10) -> Bohrium;
            case (14) -> Neutronium;
            default -> Naquadria;
        };
    }

    public static Material getPolymerByTier(int tier) {
        return switch (tier) {
            case (8) -> Polybenzimidazole;
            default -> Polyethylene;
        };
    }

    public static Material getSolderingAlloyByTier(int tier) {
        return SolderingAlloy;
    }

    public static Material getGemByTier(int tier) {
        return switch (tier) {
            case (6) -> Ruby;
            case (7) -> Emerald;
            case (8) -> Diamond;
            case (9) -> Diamond;
            case (10) -> Diamond;
            case (11) -> Diamond;
            case (12) -> Diamond;
            default -> Diamond;
        };
    }

    public static Material getSensorEmitterFoil(int tier) {
        return switch (tier) {
            case (6) -> Palladium;
            case (7) -> Platinum;
            case (8) -> Osmiridium;
            default -> getMainComponentMaterialByTier(tier);
        };
    }
}
