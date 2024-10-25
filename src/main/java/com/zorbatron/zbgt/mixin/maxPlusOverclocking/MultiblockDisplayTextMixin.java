package com.zorbatron.zbgt.mixin.maxPlusOverclocking;

import java.util.List;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.zorbatron.zbgt.api.ZBGTValues;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;

// I think I have to overwrite it all because if GTValues.VNF[foo] gets run with a max+ tier, it'll result in an Array
// Out of Bounds. ModifyArg and ModifyArgs seem to both execute the original line and provide it, which is bad.
@Mixin(value = MultiblockDisplayText.Builder.class, remap = false)
public class MultiblockDisplayTextMixin {

    @Shadow
    @Final
    private boolean isStructureFormed;

    @Shadow
    @Final
    private List<ITextComponent> textList;

    /**
     * @author Zorbatron
     * @reason I have to >:(
     */
    @Overwrite
    public MultiblockDisplayText.Builder addEnergyUsageLine(IEnergyContainer energyContainer) {
        if (!isStructureFormed) return (MultiblockDisplayText.Builder) (Object) this;
        if (energyContainer != null && energyContainer.getEnergyCapacity() > 0) {
            long maxVoltage = Math.max(energyContainer.getInputVoltage(), energyContainer.getOutputVoltage());

            String energyFormatted = TextFormattingUtil.formatNumbers(maxVoltage);
            // wrap in text component to keep it from being formatted
            ITextComponent voltageName = new TextComponentString(
                    ZBGTValues.VOCNF[GTUtility.getFloorTierByVoltage(maxVoltage)]);

            ITextComponent bodyText = TextComponentUtil.translationWithColor(
                    TextFormatting.GRAY,
                    "gregtech.multiblock.max_energy_per_tick",
                    energyFormatted, voltageName);
            ITextComponent hoverText = TextComponentUtil.translationWithColor(TextFormatting.GRAY,
                    "gregtech.multiblock.max_energy_per_tick_hover");
            textList.add(TextComponentUtil.setHover(bodyText, hoverText));
        }
        return (MultiblockDisplayText.Builder) (Object) this;
    }

    /**
     * @author Zorbatron
     * @reason I have to >:(
     */
    @Overwrite
    public MultiblockDisplayText.Builder addEnergyUsageExactLine(long energyUsage) {
        if (!isStructureFormed) return (MultiblockDisplayText.Builder) (Object) this;
        if (energyUsage > 0) {
            String energyFormatted = TextFormattingUtil.formatNumbers(energyUsage);
            // wrap in text component to keep it from being formatted
            ITextComponent voltageName = new TextComponentString(
                    ZBGTValues.VOCNF[GTUtility.getFloorTierByVoltage(energyUsage)]);

            textList.add(TextComponentUtil.translationWithColor(
                    TextFormatting.GRAY,
                    "gregtech.multiblock.energy_consumption",
                    energyFormatted, voltageName));
        }
        return (MultiblockDisplayText.Builder) (Object) this;
    }

    /**
     * @author Zorbatron
     * @reason I have to >:(
     */
    @Overwrite
    public MultiblockDisplayText.Builder addEnergyProductionLine(long maxVoltage, long recipeEUt) {
        if (!isStructureFormed) return (MultiblockDisplayText.Builder) (Object) this;
        if (maxVoltage != 0 && maxVoltage >= -recipeEUt) {
            String energyFormatted = TextFormattingUtil.formatNumbers(maxVoltage);
            // wrap in text component to keep it from being formatted
            ITextComponent voltageName = new TextComponentString(
                    ZBGTValues.VOCNF[GTUtility.getFloorTierByVoltage(maxVoltage)]);

            textList.add(TextComponentUtil.translationWithColor(
                    TextFormatting.GRAY,
                    "gregtech.multiblock.max_energy_per_tick",
                    energyFormatted, voltageName));
        }
        return (MultiblockDisplayText.Builder) (Object) this;
    }

    /**
     * @author Zorbatron
     * @reason I have to >:(
     */
    @Overwrite
    public MultiblockDisplayText.Builder addEnergyProductionAmpsLine(long maxVoltage, int amperage) {
        if (!isStructureFormed) return (MultiblockDisplayText.Builder) (Object) this;
        if (maxVoltage != 0 && amperage != 0) {
            String energyFormatted = TextFormattingUtil.formatNumbers(maxVoltage);
            // wrap in text component to keep it from being formatted
            ITextComponent voltageName = new TextComponentString(
                    ZBGTValues.VOCNF[GTUtility.getFloorTierByVoltage(maxVoltage)]);

            textList.add(TextComponentUtil.translationWithColor(
                    TextFormatting.GRAY,
                    "gregtech.multiblock.max_energy_per_tick_amps",
                    energyFormatted, amperage, voltageName));
        }
        return (MultiblockDisplayText.Builder) (Object) this;
    }
}
