package com.zorbatron.zbgt.common.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.zorbatron.zbgt.common.block.blocks.*;

import gregtech.api.block.VariantBlock;
import gregtech.common.blocks.MetaBlocks;

public class ZBGTMetaBlocks {

    private ZBGTMetaBlocks() {}

    public static MaterialCasing MATERIAL_CASINGS;
    public static CoALCasing CoAL_CASING;
    public static PreciseCasing PRECISE_CASING;
    public static YOTTankCell YOTTANK_CELL;
    public static MiscCasing MISC_CASING;
    public static IntegralCasing INTEGRAL_CASING;
    public static RandomGTPPCasings0 GTPP_CASING_0;
    public static RandomGTPPCasings1 GTPP_CASING_1;
    public static RandomGTPPCasings2 GTPP_CASING_2;
    public static RandomGTPPCasings3 GTPP_CASING_3;
    public static RandomGTPPCasings4 GTPP_CASING_4;
    public static RandomGTPPCasings5 GTPP_CASING_5;
    public static TransparentBlock TRANSPARENT_BLOCK;

    public static CreativeHeatingCoil CREATIVE_HEATING_COIL;

    public static List<VariantBlock<?>> ALL_CASINGS = new ArrayList<>();

    public static void init() {
        MATERIAL_CASINGS = new MaterialCasing();
        MATERIAL_CASINGS.setRegistryName("multiblock_casing");

        CoAL_CASING = new CoALCasing();
        CoAL_CASING.setRegistryName("coal_casing");

        PRECISE_CASING = new PreciseCasing();
        PRECISE_CASING.setRegistryName("precise_casing");

        YOTTANK_CELL = new YOTTankCell();
        YOTTANK_CELL.setRegistryName("yottank_cell");

        MISC_CASING = new MiscCasing();
        MISC_CASING.setRegistryName("misc_casing");

        INTEGRAL_CASING = new IntegralCasing();
        INTEGRAL_CASING.setRegistryName("integral_casing");

        GTPP_CASING_0 = new RandomGTPPCasings0();
        GTPP_CASING_0.setRegistryName("gtpp_casing_0");
        GTPP_CASING_1 = new RandomGTPPCasings1();
        GTPP_CASING_1.setRegistryName("gtpp_casing_1");
        GTPP_CASING_2 = new RandomGTPPCasings2();
        GTPP_CASING_2.setRegistryName("gtpp_casing_2");
        GTPP_CASING_3 = new RandomGTPPCasings3();
        GTPP_CASING_3.setRegistryName("gtpp_casing_3");
        GTPP_CASING_4 = new RandomGTPPCasings4();
        GTPP_CASING_4.setRegistryName("gtpp_casing_4");
        GTPP_CASING_5 = new RandomGTPPCasings5();
        GTPP_CASING_5.setRegistryName("gtpp_casing_5");

        TRANSPARENT_BLOCK = new TransparentBlock();
        TRANSPARENT_BLOCK.setRegistryName("transparent_block");

        CREATIVE_HEATING_COIL = new CreativeHeatingCoil();
        CREATIVE_HEATING_COIL.setRegistryName("creative_heating_coil");

        ALL_CASINGS.addAll(Arrays.asList(MATERIAL_CASINGS, CoAL_CASING, PRECISE_CASING, YOTTANK_CELL, MISC_CASING,
                INTEGRAL_CASING, GTPP_CASING_0, GTPP_CASING_1, GTPP_CASING_2, GTPP_CASING_3, GTPP_CASING_4,
                GTPP_CASING_5, TRANSPARENT_BLOCK, CREATIVE_HEATING_COIL));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        ALL_CASINGS.forEach(ZBGTMetaBlocks::registerItemModel);
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
