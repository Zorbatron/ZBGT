package com.zorbatron.zbgt.common.covers;

import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;
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
    private Pattern regex = ZBGTAPI.EMPTY_PATTERN;
    private final Object2ObjectOpenCustomHashMap<FluidStack, Boolean> matchCache = new Object2ObjectOpenCustomHashMap<>(
            FluidStackHashStrategy.builder().compareFluid().build());

    private final FilterTestFluidSlot[] testSlots = new FilterTestFluidSlot[5];

    @Override
    public boolean testFluid(@NotNull FluidStack fluidStack) {
        return ZBGTUtility.computeIfAbsentDiffKey(matchCache, fluidStack, fluidStack::copy,
                stack -> regex.matcher(stack.getUnlocalizedName()).matches());
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
        widgetGroup.accept(new TextFieldWidget2(14 - 22, 26 + 5, 150, 14, this::getPattern, this::setRegex));
    }

    private void setRegex(String newRegex) {
        if (!newRegex.equals(getPattern())) {
            matchCache.clear();

            try {
                regex = Pattern.compile(newRegex);
            } catch (PatternSyntaxException e) {
                regex = ZBGTAPI.EMPTY_PATTERN;
            }

            for (FilterTestFluidSlot testSlot : testSlots) {
                if (testSlot == null) continue;
                testSlot.update();
            }
        }
    }

    @NotNull
    private String getPattern() {
        return regex.pattern();
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setString("Filter", getPattern());
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
