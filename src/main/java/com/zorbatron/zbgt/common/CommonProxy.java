package com.zorbatron.zbgt.common;

import java.util.Objects;
import java.util.function.Function;

import com.zorbatron.zbgt.api.recipes.properties.ComponentALProperty;
import gregtech.api.GTValues;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.zorbatron.zbgt.ZBGTCore;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.covers.ZBGTCovers;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.loaders.recipe.ZBGTRecipes;
import com.zorbatron.zbgt.materials.ZBGTMaterialOverride;

import gregicality.multiblocks.api.utils.GCYMLog;
import gregtech.api.GregTechAPI;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.unification.material.event.MaterialEvent;

@Mod.EventBusSubscriber(modid = ZBGTCore.MODID)
public class CommonProxy {

    public void preInit() {
        ZBGTMetaItems.init();
    }

    @SubscribeEvent
    public static void registerCovers(GregTechAPI.RegisterEvent<CoverDefinition> event) {
        ZBGTCovers.init();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        ZBGTCore.LOGGER.info("Registering recipes...");

        for(int i = 1; i < GTValues.VN.length; i++) {
            ComponentALProperty.registerCasingTier(i, GTValues.VN[i]);
        }

        ZBGTRecipes.init();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GCYMLog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(ZBGTMetaBlocks.MULTIBLOCK_CASING);
        registry.register(ZBGTMetaBlocks.CoAL_CASING);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        GCYMLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(createItemBlock(ZBGTMetaBlocks.MULTIBLOCK_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(ZBGTMetaBlocks.CoAL_CASING, VariantItemBlock::new));
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void registerMaterials(MaterialEvent event) {
        ZBGTMaterialOverride.init();
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }
}
