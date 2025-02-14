package com.zorbatron.zbgt.common.covers;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.util.FluidStackHashStrategy;
import com.zorbatron.zbgt.api.util.ZBGTUtility;
import com.zorbatron.zbgt.client.widgets.FilterTestFluidSlot;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.common.covers.filter.FluidFilter;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;

public class LocalNameFluidFilterCover extends FluidFilter {

    @NotNull
    private String regex = "";
    private final Object2ObjectOpenCustomHashMap<FluidStack, Boolean> matchCache = new Object2ObjectOpenCustomHashMap<>(
            FluidStackHashStrategy.builder().compareFluid().build());

    private final FilterTestFluidSlot[] testSlots = new FilterTestFluidSlot[5];

    @Override
    public boolean testFluid(@NotNull FluidStack fluidStack) {
        return ZBGTUtility.computeIfAbsentDiffKey(matchCache, fluidStack, fluidStack::copy,
                stack -> Pattern.matches(regex, stack.getUnlocalizedName()));
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
        for (int x = 0; x < 5; x++) {
            FilterTestFluidSlot testSlot = new FilterTestFluidSlot(10 + 22 * x, 0, this::testFluid);
            testSlots[x] = testSlot;
            widgetGroup.accept(testSlot);
        }

        widgetGroup.accept(new ImageWidget(10 - 22, 22 + 5, 154, 18, GuiTextures.DISPLAY));
        widgetGroup.accept(new TextFieldWidget2(14 - 22, 26 + 5, 150, 14, this::getRegex, this::setRegex));
    }

    private void setRegex(String newRegex) {
        if (!regex.equals(newRegex)) {
            regex = newRegex;
            matchCache.clear();
            for (FilterTestFluidSlot testSlot : testSlots) {
                if (testSlot == null) continue;
                testSlot.update();
            }
        }
    }

    @NotNull
    private String getRegex() {
        return regex;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setString("Filter", getRegex());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        setRegex(tagCompound.getString("Filter"));
    }

    @Override
    public void configureFilterTanks(int amount) {}

    @Override
    public void setMaxConfigurableFluidSize(int maxStackSize) {}
}
