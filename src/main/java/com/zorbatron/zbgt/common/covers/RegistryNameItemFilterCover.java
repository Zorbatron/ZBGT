package com.zorbatron.zbgt.common.covers;

import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.util.ZBGTUtility;
import com.zorbatron.zbgt.client.widgets.FilterTestSlot;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.api.util.ItemStackHashStrategy;
import gregtech.common.covers.filter.ItemFilter;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;

public class RegistryNameItemFilterCover extends ItemFilter {

    @NotNull
    private Pattern regex = ZBGTAPI.EMPTY_PATTERN;
    private final Object2ObjectOpenCustomHashMap<ItemStack, Boolean> matchCache = new Object2ObjectOpenCustomHashMap<>(
            ItemStackHashStrategy.builder().compareItem(true).build());

    private final FilterTestSlot[] testSlots = new FilterTestSlot[5];

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
        if (regex == ZBGTAPI.EMPTY_PATTERN) return false;

        return ZBGTUtility.computeIfAbsentDiffKey(matchCache, itemStack, itemStack::copy, stack -> {
            ResourceLocation resloc = Item.REGISTRY.getNameForObject(itemStack.getItem());
            if (resloc == null) return false;
            String name = resloc.toString();
            return regex.matcher(name).matches();
        });
    }

    @Override
    public int getTotalOccupiedHeight() {
        return 0;
    }

    @Override
    public void initUI(Consumer<Widget> widgetGroup) {
        for (int x = 0; x < 5; x++) {
            FilterTestSlot testSlot = new FilterTestSlot(20 + 22 * x, 0, this::matchesItemStack);
            testSlots[x] = testSlot;
            widgetGroup.accept(testSlot);
        }

        widgetGroup.accept(new ImageWidget(10, 22, 154, 18, GuiTextures.DISPLAY));
        widgetGroup.accept(new TextFieldWidget2(14, 26, 150, 14, this::getPattern, this::setRegex));
    }

    private void setRegex(String newRegex) {
        if (!newRegex.equals(getPattern())) {
            matchCache.clear();

            try {
                regex = Pattern.compile(newRegex);
            } catch (PatternSyntaxException e) {
                regex = ZBGTAPI.EMPTY_PATTERN;
            }

            for (FilterTestSlot testSlot : testSlots) {
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
}
