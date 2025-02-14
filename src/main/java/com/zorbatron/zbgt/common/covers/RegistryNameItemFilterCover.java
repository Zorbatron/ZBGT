package com.zorbatron.zbgt.common.covers;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.api.util.ItemStackHashStrategy;
import gregtech.common.covers.filter.ItemFilter;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;

public class RegistryNameItemFilterCover extends ItemFilter {

    @NotNull
    private String expression = "";
    private final Object2ObjectOpenCustomHashMap<ItemStack, Boolean> matchCache = new Object2ObjectOpenCustomHashMap<>(
            ItemStackHashStrategy.builder().compareItem(true).build());

    @Override
    public boolean showGlobalTransferLimitSlider() {
        return false;
    }

    @Override
    public int getSlotTransferLimit(Object matchSlot, int globalTransferLimit) {
        return 0;
    }

    @Override
    public Object matchItemStack(ItemStack itemStack) {
        return matchesItemStack(itemStack) ? "lmao 2.8.10's codebase is wack asf" : null;
    }

    public boolean matchesItemStack(@NotNull ItemStack itemStack) {
        return matchCache.computeIfAbsent(itemStack, stack -> {
            ResourceLocation resloc = Item.REGISTRY.getNameForObject(itemStack.getItem());
            if (resloc == null) return false;
            String name = resloc.toString();
            return Pattern.matches(expression, name);
        });
    }

    @Override
    public int getTotalOccupiedHeight() {
        return 0;
    }

    @Override
    public void initUI(Consumer<Widget> widgetGroup) {
        widgetGroup.accept(new ImageWidget(10, 24, 154, 16, GuiTextures.DISPLAY));
        widgetGroup.accept(new TextFieldWidget2(14, 26, 150, 14, this::getPattern, this::setPattern));
    }

    private void setPattern(String pattern) {
        this.expression = pattern;
        matchCache.clear();
    }

    private String getPattern() {
        return expression;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setString("Filter", getPattern());
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        setPattern(tagCompound.getString("Filter"));
    }
}
