package com.zorbatron.zbgt.common.items;

import static com.zorbatron.zbgt.common.items.ZBGTCatalystItems.*;

import java.util.Map;
import java.util.Objects;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zorbatron.zbgt.api.items.stats.ItemColorProvider;
import com.zorbatron.zbgt.api.unification.material.info.ZBGTMaterialIconType;
import com.zorbatron.zbgt.api.util.ZBGTUtility;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.items.metaitem.stats.IItemComponent;
import gregtech.api.unification.material.info.MaterialIconSet;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

public class ZBGTCatalystItem extends StandardMetaItem {

    public ZBGTCatalystItem() {
        super();
    }

    @Override
    public ResourceLocation createItemModelPath(MetaItem<?>.MetaValueItem metaValueItem, String postfix) {
        return ZBGTUtility.zbgtId(this.formatModelPath(metaValueItem) + postfix);
    }

    @Override
    public void registerSubItems() {
        EMPTY_CATALYST = addItem(0, "empty_catalyst");

        PINK_CATALYST = addItem(1, "pink_catalyst")
                .addComponents(new ItemColorProvider(0xE05ED5));
        BLUE_CATALYST = addItem(2, "blue_catalyst")
                .addComponents(new ItemColorProvider(0x1F7FB0));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels() {
        Map<Short, ModelResourceLocation> alreadyRegistered = new Short2ObjectOpenHashMap<>();
        for (short metaItem : metaItems.keySet()) {
            short registrationKey = (short) ZBGTMaterialIconType.catalystCarrier.id;
            if (!alreadyRegistered.containsKey(registrationKey)) {
                ResourceLocation resourceLocation = ZBGTMaterialIconType.catalystCarrier
                        .getItemModelPath(MaterialIconSet.DULL);
                ModelBakery.registerItemVariants(this, resourceLocation);
                alreadyRegistered.put(registrationKey, new ModelResourceLocation(resourceLocation, "inventory"));
            }
            ModelResourceLocation resourceLocation = alreadyRegistered.get(registrationKey);
            metaItemsModels.put(metaItem, resourceLocation);
        }
    }

    @Override
    protected int getColorForItemStack(ItemStack stack, int tintIndex) {
        if (tintIndex % 2 == 0) {
            for (IItemComponent stat : Objects.requireNonNull(((MetaItem<?>) stack.getItem()).getItem(stack))
                    .getAllStats()) {
                if (stat instanceof ItemColorProvider colorProvider) {
                    return colorProvider.color();
                }
            }
        }

        return 0xFFFFFF;
    }
}
