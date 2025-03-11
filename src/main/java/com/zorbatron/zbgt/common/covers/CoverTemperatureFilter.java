package com.zorbatron.zbgt.common.covers;

import java.util.function.Consumer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.util.FluidStackHashStrategy;
import com.zorbatron.zbgt.api.util.ZBGTUtility;
import com.zorbatron.zbgt.client.widgets.FilterTestFluidSlot;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.common.covers.filter.FluidFilter;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenCustomHashMap;

public class CoverTemperatureFilter extends FluidFilter {

    private static final String NBT_TEMP = "Temp";
    private static final String NBT_MODE = "Mode";

    private int temperature = 0;
    @NotNull
    private CompareMethod compareMethod = CompareMethod.LESS_THAN;
    @NotNull
    private final Object2BooleanOpenCustomHashMap<FluidStack> matchCache = new Object2BooleanOpenCustomHashMap<>(
            FluidStackHashStrategy.compareFluid);

    private final FilterTestFluidSlot[] testSlots = new FilterTestFluidSlot[5];

    @Override
    public boolean testFluid(FluidStack fluidStack) {
        return ZBGTUtility.computeIfAbsentDiffKey(matchCache, fluidStack, fluidStack::copy,
                stack -> {
                    int temp = stack.getFluid().getTemperature();
                    switch (compareMethod) {
                        case LESS_THAN -> {
                            return temp < temperature;
                        }
                        case LESS_THAN_OR_EQUAL -> {
                            return temp <= temperature;
                        }
                        case EQUAL -> {
                            return temp == temperature;
                        }
                        case GREATER_THAN_OR_EQUAL -> {
                            return temp >= temperature;
                        }
                        case GREATER_THAN -> {
                            return temp > temperature;
                        }
                        default -> throw new IllegalStateException("How?????");
                    }
                });
    }

    @Override
    public int getFluidTransferLimit(FluidStack fluidStack) {
        return 0;
    }

    @Override
    public int getMaxOccupiedHeight() {
        return 0;
    }

    @Override
    public void initUI(Consumer<Widget> widgetGroup) {
        FilterTestFluidSlot.createTestSlotArray(widgetGroup, testSlots, this::testFluid);

        widgetGroup.accept(new CycleButtonWidget(-12, 0, 18, 18, CompareMethod.class,
                this::getCompareMethod, this::setCompareMethod));

        widgetGroup.accept(new ImageWidget(10 - 22, 22 + 5, 154, 18, GuiTextures.DISPLAY));
        widgetGroup.accept(new TextFieldWidget2(14 - 22, 26 + 5, 150, 14, this::getTemperature, this::setTemperature));
    }

    @NotNull
    private CompareMethod getCompareMethod() {
        return compareMethod;
    }

    private void setCompareMethod(@NotNull CompareMethod newMethod) {
        compareMethod = newMethod;
    }

    private String getTemperature() {
        return String.valueOf(temperature);
    }

    private void setTemperature(String newTemperatureString) {
        int newTemperature = 0;

        try {
            newTemperature = Integer.parseInt(newTemperatureString);
        } catch (NumberFormatException ignored) {
            // :p
        }

        if (newTemperature != temperature) {
            matchCache.clear();

            temperature = newTemperature;

            for (FilterTestFluidSlot testSlot : testSlots) {
                if (testSlot == null) continue;
                testSlot.update();
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger(NBT_TEMP, temperature);
        tagCompound.setByte(NBT_MODE, (byte) compareMethod.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        temperature = tagCompound.getInteger(NBT_TEMP);
        compareMethod = CompareMethod.values()[tagCompound.getByte(NBT_MODE)];
    }

    @Override
    public void configureFilterTanks(int amount) {}

    @Override
    public void setMaxConfigurableFluidSize(int maxStackSize) {}

    private enum CompareMethod implements IStringSerializable {

        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL("<="),
        EQUAL("="),
        GREATER_THAN_OR_EQUAL(">="),
        GREATER_THAN(">");

        private final String name;

        CompareMethod(String name) {
            this.name = name;
        }

        @NotNull
        @Override
        public String getName() {
            return this.name;
        }
    }
}
