package com.zorbatron.zbgt.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.ZBGTBlockMultiblockCasing;

import gregtech.common.blocks.MetaBlocks;

public class ZBGTMetaBlocks {

    private ZBGTMetaBlocks() {}

    public static ZBGTBlockMultiblockCasing MULTIBLOCK_CASING;
    public static CoALCasing CoAL_CASING;

    public static void init() {
        MULTIBLOCK_CASING = new ZBGTBlockMultiblockCasing();
        MULTIBLOCK_CASING.setRegistryName("multiblock_casing");

        CoAL_CASING = new CoALCasing();
        CoAL_CASING.setRegistryName("coal_casing");
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerItemModel(MULTIBLOCK_CASING);
        registerItemModel(CoAL_CASING);
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(@NotNull Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            ResourceLocation location = block.getRegistryName();
            String stateProperties = MetaBlocks.statePropertiesToString(state.getProperties());

            Item item = Item.getItemFromBlock(block);
            int metaData = block.getMetaFromState(state);
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location, stateProperties);

            // noinspection ConstantConditions
            ModelLoader.setCustomModelResourceLocation(item, metaData, modelResourceLocation);
        }
    }

    @SuppressWarnings({ "unused", "unchecked" })
    private static <T extends Comparable<T>> @NotNull String getPropertyName(@NotNull IProperty<T> property,
                                                                             Comparable<?> value) {
        return property.getName((T) value);
    }
}
