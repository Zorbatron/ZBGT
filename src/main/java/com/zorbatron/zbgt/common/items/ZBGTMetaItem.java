package com.zorbatron.zbgt.common.items;

import static com.zorbatron.zbgt.common.items.ZBGTMetaItems.*;
import static gregtech.api.GTValues.*;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.util.ZBGTUtility;
import com.zorbatron.zbgt.recipe.helpers.RecipeAssists;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.behaviors.TooltipBehavior;

public class ZBGTMetaItem extends StandardMetaItem {

    public ZBGTMetaItem() {
        super();
    }

    @Override
    public ResourceLocation createItemModelPath(MetaItem<?>.MetaValueItem metaValueItem, String postfix) {
        return ZBGTUtility.zbgtId(this.formatModelPath(metaValueItem) + postfix);
    }

    @Override
    public void registerSubItems() {
        // Dual Covers: 0-12
        DUAL_COVER_LV = addItem(0, "cover.dual_cover.lv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 8, 1280 / 20));
        }));
        DUAL_COVER_MV = addItem(1, "cover.dual_cover.mv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 32, 1280 * 4 / 20));
        }));
        DUAL_COVER_HV = addItem(2, "cover.dual_cover.hv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 64, 1280 * 16 / 20));
        }));
        DUAL_COVER_EV = addItem(3, "cover.dual_cover.ev").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 3, 1280 * 64 / 20));
        }));
        DUAL_COVER_IV = addItem(4, "cover.dual_cover.iv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 8, 1280 * 64 * 4 / 20));
        }));
        DUAL_COVER_LuV = addItem(5, "cover.dual_cover.luv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 16 / 20));
        }));
        DUAL_COVER_ZPM = addItem(6, "cover.dual_cover.zpm").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 / 20));
        }));
        DUAL_COVER_UV = addItem(7, "cover.dual_cover.uv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        }));
        DUAL_COVER_UHV = addItem(8, "cover.dual_cover.uhv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());
        DUAL_COVER_UEV = addItem(9, "cover.dual_cover.uev").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());
        DUAL_COVER_UIV = addItem(10, "cover.dual_cover.uiv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());
        DUAL_COVER_UXV = addItem(11, "cover.dual_cover.uxv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());
        DUAL_COVER_OpV = addItem(12, "cover.dual_cover.opv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        })).setInvisibleIf(!GregTechAPI.isHighTier());

        // Precise Dual Covers: 13-25
        PRECISE_DUAL_COVER_LV = addItem(13, "cover.precise_dual_cover.lv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 8, 1280 / 20));
        }));
        PRECISE_DUAL_COVER_MV = addItem(14, "cover.precise_dual_cover.mv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 32, 1280 * 4 / 20));
        }));
        PRECISE_DUAL_COVER_HV = addItem(15, "cover.precise_dual_cover.hv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate", 64, 1280 * 16 / 20));
        }));
        PRECISE_DUAL_COVER_EV = addItem(16, "cover.precise_dual_cover.ev").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 3, 1280 * 64 / 20));
        }));
        PRECISE_DUAL_COVER_IV = addItem(17, "cover.precise_dual_cover.iv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 8, 1280 * 64 * 4 / 20));
        }));
        PRECISE_DUAL_COVER_LuV = addItem(18, "cover.precise_dual_cover.luv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 16 / 20));
                }));
        PRECISE_DUAL_COVER_ZPM = addItem(19, "cover.precise_dual_cover.zpm")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 / 20));
                }));
        PRECISE_DUAL_COVER_UV = addItem(20, "cover.precise_dual_cover.uv").addComponents(new TooltipBehavior(lines -> {
            lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
            lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16, 1280 * 64 * 64 * 4 / 20));
        }));
        PRECISE_DUAL_COVER_UHV = addItem(21, "cover.precise_dual_cover.uhv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
        PRECISE_DUAL_COVER_UEV = addItem(22, "cover.precise_dual_cover.uev")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
        PRECISE_DUAL_COVER_UIV = addItem(23, "cover.precise_dual_cover.uiv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
        PRECISE_DUAL_COVER_UXV = addItem(24, "cover.precise_dual_cover.uxv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());
        PRECISE_DUAL_COVER_OpV = addItem(25, "cover.precise_dual_cover.opv")
                .addComponents(new TooltipBehavior(lines -> {
                    lines.add(I18n.format("metaitem.cover.precise_dual_cover.tooltip"));
                    lines.add(I18n.format("metaitem.cover.dual.tooltip.transfer_rate.stacks", 16,
                            1280 * 64 * 64 * 4 / 20));
                })).setInvisibleIf(!GregTechAPI.isHighTier());

        // Generic Circuits: 26-40
        GENERIC_CIRCUIT_ULV = addItem(26, "generic_circuit.ulv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(ULV));
        GENERIC_CIRCUIT_LV = addItem(27, "generic_circuit.lv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(LV));
        GENERIC_CIRCUIT_MV = addItem(28, "generic_circuit.mv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(MV));
        GENERIC_CIRCUIT_HV = addItem(29, "generic_circuit.hv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(HV));
        GENERIC_CIRCUIT_EV = addItem(30, "generic_circuit.ev")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(EV));
        GENERIC_CIRCUIT_IV = addItem(31, "generic_circuit.iv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(IV));
        GENERIC_CIRCUIT_LuV = addItem(32, "generic_circuit.luv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(LuV));
        GENERIC_CIRCUIT_ZPM = addItem(33, "generic_circuit.zpm")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(ZPM));
        GENERIC_CIRCUIT_UV = addItem(34, "generic_circuit.uv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(UV));
        GENERIC_CIRCUIT_UHV = addItem(35, "generic_circuit.uhv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(UHV));
        GENERIC_CIRCUIT_UEV = addItem(36, "generic_circuit.uev")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(UEV))
                .setInvisibleIf(!GregTechAPI.isHighTier());
        GENERIC_CIRCUIT_UIV = addItem(37, "generic_circuit.uiv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(UIV))
                .setInvisibleIf(!GregTechAPI.isHighTier());
        GENERIC_CIRCUIT_UXV = addItem(38, "generic_circuit.uxv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(UXV))
                .setInvisibleIf(!GregTechAPI.isHighTier());
        GENERIC_CIRCUIT_OpV = addItem(39, "generic_circuit.opv")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(OpV))
                .setInvisibleIf(!GregTechAPI.isHighTier());
        GENERIC_CIRCUIT_MAX = addItem(40, "generic_circuit.max")
                .setUnificationData(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(MAX))
                .setInvisibleIf(!GregTechAPI.isHighTier());

        // GoodGenerator Circuits: 41-45
        GG_CIRCUIT_1 = addItem(41, "gg_circuit_1");
        GG_CIRCUIT_2 = addItem(42, "gg_circuit_2");
        GG_CIRCUIT_3 = addItem(43, "gg_circuit_3");
        GG_CIRCUIT_4 = addItem(44, "gg_circuit_4");
        GG_CIRCUIT_5 = addItem(45, "gg_circuit_5");

        // Misc chips: 46-49
        ENGRAVED_GOLD_CHIP = addItem(46, "engraved_gold_chip");
        ENGRAVED_DIAMOND_CHIP = addItem(47, "engraved_diamond_chip");
        ENGRAVED_ENERGY_CHIP = addItem(48, "engraved_energy_chip");
        ENGRAVED_MANYULLYN_CHIP = addItem(49, "engraved_manyullyn_chip");

        SPECIAL_CERAMICS_PLATE = addItem(50, "special_ceramics_plate");
        QUARTZ_WAFER = addItem(51, "quartz_wafer");
        MICRO_HEATER = addItem(52, "micro_heater");
        QUARTZ_CRYSTAL_RESONATOR = addItem(53, "quartz_crystal_resonator");
        HIGH_ENERGY_MIXTURE = addItem(54, "high_energy_mixture")
                .setInvisibleIf(ZBGTAPI.nomiLabsCompat);
        RADIATION_PROTECTION_PLATE = addItem(55, "radiation_protection_plate");
        ADVANCED_RADIATION_PROTECTION_PLATE = addItem(56, "advanced_radiation_protection_plate");

        // Wrapped circuits 57-71
        WRAPPED_CIRCUIT_ULV = addItem(57, "wrapped.circuit.ulv");
        WRAPPED_CIRCUIT_LV = addItem(58, "wrapped.circuit.lv");
        WRAPPED_CIRCUIT_MV = addItem(59, "wrapped.circuit.mv");
        WRAPPED_CIRCUIT_HV = addItem(60, "wrapped.circuit.hv");
        WRAPPED_CIRCUIT_EV = addItem(61, "wrapped.circuit.ev");
        WRAPPED_CIRCUIT_IV = addItem(62, "wrapped.circuit.iv");
        WRAPPED_CIRCUIT_LuV = addItem(63, "wrapped.circuit.luv");
        WRAPPED_CIRCUIT_ZPM = addItem(64, "wrapped.circuit.zpm");
        WRAPPED_CIRCUIT_UV = addItem(65, "wrapped.circuit.uv");
        WRAPPED_CIRCUIT_UHV = addItem(66, "wrapped.circuit.uhv");
        WRAPPED_CIRCUIT_UEV = addItem(67, "wrapped.circuit.uev")
                .setInvisibleIf(!GregTechAPI.isHighTier());
        WRAPPED_CIRCUIT_UIV = addItem(68, "wrapped.circuit.uiv")
                .setInvisibleIf(!GregTechAPI.isHighTier());
        WRAPPED_CIRCUIT_UXV = addItem(69, "wrapped.circuit.uxv")
                .setInvisibleIf(!GregTechAPI.isHighTier());
        WRAPPED_CIRCUIT_OpV = addItem(70, "wrapped.circuit.opv")
                .setInvisibleIf(!GregTechAPI.isHighTier());
        WRAPPED_CIRCUIT_MAX = addItem(71, "wrapped.circuit.max")
                .setInvisibleIf(!GregTechAPI.isHighTier());

        // Wrapped SMDs 72-81
        WRAPPED_SMD_CAPACITOR = addItem(72, "wrapped.smd.capacitor");
        WRAPPED_SMD_DIODE = addItem(73, "wrapped.smd.diode");
        WRAPPED_SMD_INDUCTOR = addItem(74, "wrapped.smd.inductor");
        WRAPPED_SMD_RESISTOR = addItem(75, "wrapped.smd.resistor");
        WRAPPED_SMD_TRANSISTOR = addItem(76, "wrapped.smd.transistor");

        WRAPPED_ADVANCED_SMD_CAPACITOR = addItem(77, "wrapped.smd.advanced_capacitor");
        WRAPPED_ADVANCED_SMD_DIODE = addItem(78, "wrapped.smd.advanced_diode");
        WRAPPED_ADVANCED_SMD_INDUCTOR = addItem(79, "wrapped.smd.advanced_inductor");
        WRAPPED_ADVANCED_SMD_RESISTOR = addItem(80, "wrapped.smd.advanced_resistor");
        WRAPPED_ADVANCED_SMD_TRANSISTOR = addItem(81, "wrapped.smd.advanced_transistor");

        // Wrapped boards 82-88
        WRAPPED_BOARD_COATED = addItem(82, "wrapped.board.coated");
        WRAPPED_BOARD_PHENOLIC = addItem(83, "wrapped.board.phenolic");
        WRAPPED_BOARD_PLASTIC = addItem(84, "wrapped.board.plastic");
        WRAPPED_BOARD_EPOXY = addItem(85, "wrapped.board.epoxy");
        WRAPPED_BOARD_FIBER_REINFORCED = addItem(86, "wrapped.board.fiber_reinforced");
        WRAPPED_BOARD_MULTILAYER_FIBER_REINFORCED = addItem(87, "wrapped.board.multilayer_fiber_reinforced");
        WRAPPED_BOARD_WETWARE = addItem(88, "wrapped.board.wetware");

        // Wrapped circuit boards 89-95
        WRAPPED_CIRCUIT_BOARD_BASIC = addItem(89, "wrapped.circuit_board.basic");
        WRAPPED_CIRCUIT_BOARD_GOOD = addItem(90, "wrapped.circuit_board.good");
        WRAPPED_CIRCUIT_BOARD_PLASTIC = addItem(91, "wrapped.circuit_board.plastic");
        WRAPPED_CIRCUIT_BOARD_ADVANCED = addItem(92, "wrapped.circuit_board.advanced");
        WRAPPED_CIRCUIT_BOARD_ELITE = addItem(93, "wrapped.circuit_board.elite");
        WRAPPED_CIRCUIT_BOARD_EXTREME = addItem(94, "wrapped.circuit_board.extreme");
        WRAPPED_CIRCUIT_BOARD_WETWARE = addItem(95, "wrapped.circuit_board.wetware");

        // Wrapped chips 96-111
        WRAPPED_CHIP_SOC_SIMPLE = addItem(96, "wrapped.chip.soc_simple");
        WRAPPED_CHIP_SOC = addItem(97, "wrapped.chip.soc");
        WRAPPED_CHIP_SOC_ADVANCED = addItem(98, "wrapped.chip.soc_advanced");
        WRAPPED_CHIP_SOC_HIGHLY_ADVANCED = addItem(99, "wrapped.chip.soc_highly_advanced");

        WRAPPED_CHIP_CPU = addItem(100, "wrapped.chip.cpu");
        WRAPPED_CHIP_CPU_NANO = addItem(101, "wrapped.chip.cpu_nano");
        WRAPPED_CHIP_CPU_QUBIT = addItem(102, "wrapped.chip.cpu_qubit");

        WRAPPED_CHIP_PIC_ULTRA_LOW = addItem(103, "wrapped.chip.pic_ultra_low");
        WRAPPED_CHIP_PIC_LOW = addItem(104, "wrapped.chip.pic_low");
        WRAPPED_CHIP_PIC = addItem(105, "wrapped.chip.pic");
        WRAPPED_CHIP_PIC_HIGH = addItem(106, "wrapped.chip.pic_high");
        WRAPPED_CHIP_PIC_ULTRA_HIGH = addItem(107, "wrapped.chip.pic_ultra_high");

        WRAPPED_CHIP_RAM = addItem(108, "wrapped.chip.ram");
        WRAPPED_CHIP_NOR = addItem(109, "wrapped.chip.nor");
        WRAPPED_CHIP_NAND = addItem(110, "wrapped.chip.nand");
        WRAPPED_CHIP_INTEGRATED_LOGIC = addItem(111, "wrapped.chip.integrated_logic");

        WRAPPED_NEURO_PROCESSOR = addItem(112, "wrapped.misc.neuro_processor");
    }
}
