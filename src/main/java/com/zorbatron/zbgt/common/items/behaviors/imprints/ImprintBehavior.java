package com.zorbatron.zbgt.common.items.behaviors.imprints;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import org.jetbrains.annotations.NotNull;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;

@SuppressWarnings("ClassCanBeRecord")
public class ImprintBehavior implements IItemBehaviour {

    @NotNull
    private final String translationKey;

    public ImprintBehavior(@NotNull String translationKey) {
        this.translationKey = translationKey;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        ItemStack circuitStack = getImprintedCircuit(itemStack);
        if (!circuitStack.isEmpty()) {
            lines.add(I18n.format(translationKey, circuitStack.getDisplayName()));
        }
    }

    public static @NotNull ItemStack getImprintedCircuit(@NotNull ItemStack imprintedStack) {
        NBTTagCompound tag = GTUtility.getOrCreateNbtCompound(imprintedStack);
        return tag.hasKey("imprint", Constants.NBT.TAG_COMPOUND) ? new ItemStack(tag.getCompoundTag("imprint")) :
                ItemStack.EMPTY;
    }

    public static void setImprintedCircuit(@NotNull ItemStack stackToImprint,
                                           @NotNull MetaItem<?>.MetaValueItem circuitToImprint) {
        setImprintedCircuit(stackToImprint, circuitToImprint.getStackForm());
    }

    public static void setImprintedCircuit(@NotNull ItemStack imprintedStack,
                                           @NotNull ItemStack circuitToImprint) {
        GTUtility.getOrCreateNbtCompound(imprintedStack).setTag("imprint", circuitToImprint.serializeNBT());
    }
}
