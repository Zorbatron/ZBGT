package com.zorbatron.zbgt.api.recipes.builders;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.recipe.helpers.RecipeAssists;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

public class CALRecipeBuilder extends RecipeBuilder<CALRecipeBuilder> {

    public CALRecipeBuilder() {}

    public CALRecipeBuilder(Recipe recipe, RecipeMap<CALRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public CALRecipeBuilder(RecipeBuilder<CALRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public CALRecipeBuilder copy() {
        return new CALRecipeBuilder(this);
    }

    public CALRecipeBuilder wire(@NotNull Material material) {
        input(OrePrefix.wireGtHex, material);
        return this;
    }

    public CALRecipeBuilder wire(@NotNull Material material, int baseAmount) {
        input(OrePrefix.wireGtHex, material, baseAmount);
        return this;
    }

    public CALRecipeBuilder fineWire(@NotNull Material material) {
        input(OrePrefix.wireGtOctal, material);
        return this;
    }

    public CALRecipeBuilder fineWire(@NotNull Material material, int baseAmount) {
        input(OrePrefix.wireGtQuadruple, material, baseAmount);
        return this;
    }

    public CALRecipeBuilder bolt(@NotNull Material material) {
        input(OrePrefix.bolt, material, 16);
        return this;
    }

    public CALRecipeBuilder bolt(@NotNull Material material, int baseAmount) {
        if (baseAmount <= 4) {
            input(OrePrefix.bolt, material, baseAmount * 16);
        } else {
            input(OrePrefix.stick, material, baseAmount * 4);
        }

        return this;
    }

    public CALRecipeBuilder frameBox(@NotNull Material material) {
        input(OrePrefix.frameGt, material, 16);
        return this;
    }

    public CALRecipeBuilder frameBox(@NotNull Material material, int baseAmount) {
        input(OrePrefix.frameGt, material, 16 * baseAmount);
        return this;
    }

    public CALRecipeBuilder anyTierCircuit(int tier, int amount) {
        input(OrePrefix.circuit, RecipeAssists.getMarkerMaterialByTier(tier), amount);
        return this;
    }

    public CALRecipeBuilder board(@NotNull BoardType board) {
        input(board.boardItem);
        return this;
    }

    public CALRecipeBuilder board(@NotNull BoardType board, int amount) {
        input(board.boardItem, amount);
        return this;
    }

    public enum BoardType {

        BASIC(ZBGTMetaItems.WRAPPED_CIRCUIT_BOARD_BASIC),
        GOOD(ZBGTMetaItems.WRAPPED_CIRCUIT_BOARD_GOOD),
        PLASTIC(ZBGTMetaItems.WRAPPED_CIRCUIT_BOARD_PLASTIC),
        ADVANCED(ZBGTMetaItems.WRAPPED_CIRCUIT_BOARD_ADVANCED),
        ELITE(ZBGTMetaItems.WRAPPED_CIRCUIT_BOARD_ELITE),
        EXTREME(ZBGTMetaItems.WRAPPED_CIRCUIT_BOARD_EXTREME),
        MASTER(ZBGTMetaItems.WRAPPED_CIRCUIT_BOARD_MASTER);

        @NotNull
        private final MetaItem<?>.MetaValueItem boardItem;

        BoardType(@NotNull MetaItem<?>.MetaValueItem boardItem) {
            this.boardItem = boardItem;
        }

        public @NotNull MetaItem<?>.MetaValueItem getBoardItem() {
            return boardItem;
        }
    }

    public CALRecipeBuilder component(@NotNull ComponentType component) {
        input(component.componentItem);
        return this;
    }

    public CALRecipeBuilder component(@NotNull ComponentType component, int amount) {
        input(component.componentItem, amount);
        return this;
    }

    public enum ComponentType {

        CAPACITOR(ZBGTMetaItems.WRAPPED_SMD_CAPACITOR),
        DIODE(ZBGTMetaItems.WRAPPED_SMD_DIODE),
        INDUCTOR(ZBGTMetaItems.WRAPPED_SMD_INDUCTOR),
        RESISTOR(ZBGTMetaItems.WRAPPED_SMD_RESISTOR),
        TRANSISTOR(ZBGTMetaItems.WRAPPED_SMD_TRANSISTOR),
        ADVANCED_CAPACITOR(ZBGTMetaItems.WRAPPED_ADVANCED_SMD_CAPACITOR),
        ADVANCED_DIODE(ZBGTMetaItems.WRAPPED_ADVANCED_SMD_DIODE),
        ADVANCED_INDUCTOR(ZBGTMetaItems.WRAPPED_ADVANCED_SMD_INDUCTOR),
        ADVANCED_RESISTOR(ZBGTMetaItems.WRAPPED_ADVANCED_SMD_RESISTOR),
        ADVANCED_TRANSISTOR(ZBGTMetaItems.WRAPPED_ADVANCED_SMD_TRANSISTOR);

        @NotNull
        private final MetaItem<?>.MetaValueItem componentItem;

        ComponentType(@NotNull MetaItem<?>.MetaValueItem componentItem) {
            this.componentItem = componentItem;
        }

        public @NotNull MetaItem<?>.MetaValueItem getComponentItem() {
            return componentItem;
        }
    }

    public CALRecipeBuilder cpu(@NotNull CPUType cpu) {
        input(cpu.cpuItem);
        return this;
    }

    public CALRecipeBuilder cpu(@NotNull CPUType cpu, int amount) {
        input(cpu.cpuItem, amount);
        return this;
    }

    public enum CPUType {

        NORMAL(ZBGTMetaItems.WRAPPED_CHIP_CPU),
        NANO(ZBGTMetaItems.WRAPPED_CHIP_CPU_NANO),
        QUBIT(ZBGTMetaItems.WRAPPED_CHIP_CPU_QUBIT),
        CRYSTAL(ZBGTMetaItems.WRAPPED_CRYSTAL_CPU),
        NEURO(ZBGTMetaItems.WRAPPED_NEURO_PROCESSOR);

        @NotNull
        private final MetaItem<?>.MetaValueItem cpuItem;

        CPUType(@NotNull MetaItem<?>.MetaValueItem cpuItem) {
            this.cpuItem = cpuItem;
        }

        public @NotNull MetaItem<?>.MetaValueItem getCPUItem() {
            return cpuItem;
        }
    }

    public CALRecipeBuilder soc(@NotNull SOCType soc) {
        input(soc.socItem);
        return this;
    }

    public CALRecipeBuilder soc(@NotNull SOCType soc, int amount) {
        input(soc.socItem, amount);
        return this;
    }

    public enum SOCType {

        SIMPLE(ZBGTMetaItems.WRAPPED_CHIP_SOC_SIMPLE),
        NORMAL(ZBGTMetaItems.WRAPPED_CHIP_SOC),
        ADVANCED(ZBGTMetaItems.WRAPPED_CHIP_SOC_ADVANCED),
        HIGHLY_ADVANCED(ZBGTMetaItems.WRAPPED_CHIP_SOC_HIGHLY_ADVANCED),
        CRYSTAL(ZBGTMetaItems.WRAPPED_CRYSTAL_SOC);

        @NotNull
        private final MetaItem<?>.MetaValueItem socItem;

        SOCType(@NotNull MetaItem<?>.MetaValueItem socItem) {
            this.socItem = socItem;
        }

        public @NotNull MetaItem<?>.MetaValueItem getSOCItem() {
            return socItem;
        }
    }

    public CALRecipeBuilder pic(@NotNull PICType pic) {
        input(pic.picItem);
        return this;
    }

    public CALRecipeBuilder pic(@NotNull PICType pic, int amount) {
        input(pic.picItem, amount);
        return this;
    }

    public enum PICType {

        ULTRA_LOW(ZBGTMetaItems.WRAPPED_CHIP_PIC_ULTRA_LOW),
        LOW(ZBGTMetaItems.WRAPPED_CHIP_PIC_LOW),
        NORMAL(ZBGTMetaItems.WRAPPED_CHIP_PIC),
        HIGH(ZBGTMetaItems.WRAPPED_CHIP_PIC_HIGH),
        ULTRA_HIGH(ZBGTMetaItems.WRAPPED_CHIP_PIC_ULTRA_HIGH);

        @NotNull
        private final MetaItem<?>.MetaValueItem picItem;

        PICType(@NotNull MetaItem<?>.MetaValueItem picItem) {
            this.picItem = picItem;
        }

        public @NotNull MetaItem<?>.MetaValueItem getPICItem() {
            return picItem;
        }
    }

    public CALRecipeBuilder chip(@NotNull ChipType chip) {
        input(chip.chipItem);
        return this;
    }

    public CALRecipeBuilder chip(@NotNull ChipType chip, int amount) {
        input(chip.chipItem, amount);
        return this;
    }

    public enum ChipType {

        RAM(ZBGTMetaItems.WRAPPED_CHIP_RAM),
        NOR(ZBGTMetaItems.WRAPPED_CHIP_NOR),
        NAND(ZBGTMetaItems.WRAPPED_CHIP_NAND),
        INTEGRATED_LOGIC(ZBGTMetaItems.WRAPPED_CHIP_INTEGRATED_LOGIC);

        @NotNull
        private final MetaItem<?>.MetaValueItem chipItem;

        ChipType(@NotNull MetaItem<?>.MetaValueItem chipItem) {
            this.chipItem = chipItem;
        }

        public @NotNull MetaItem<?>.MetaValueItem getChipItem() {
            return chipItem;
        }
    }
}
