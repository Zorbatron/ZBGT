package com.zorbatron.zbgt.common;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.util.ZBGTMods;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.zorbatron.zbgt.ZBGTCore;
import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.recipes.properties.CoALProperty;
import com.zorbatron.zbgt.api.unification.material.ZBGTMaterials;
import com.zorbatron.zbgt.api.util.ZBGTLog;
import com.zorbatron.zbgt.api.worldgen.CustomOreVeins;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.covers.ZBGTCovers;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.integration.theoneprobe.ZBGTTOPModule;
import com.zorbatron.zbgt.recipe.ZBGTRecipes;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.VariantItemBlock;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.MaterialRegistryEvent;

@Mod.EventBusSubscriber(modid = ZBGTCore.MODID)
public class CommonProxy {

    public void preInit() {
        ZBGTMetaItems.init();
    }

    public void init() {
        ZBGTTOPModule.init();
    }

    public void postInit() throws IOException {
        if (ZBGTConfig.worldGenerationSettings.enableOreGeneration) {
            CustomOreVeins.init();
        }

        ZBGTAPI.pyrotheum = ZBGTMods.THERMAL_FOUNDATION.isModLoaded() ?
                FluidRegistry.getFluidStack("pyrotheum", 1) :
                ZBGTMaterials.Pyrotheum.getFluid(1);
        ZBGTAPI.cryotheum = ZBGTMods.THERMAL_FOUNDATION.isModLoaded() ?
                FluidRegistry.getFluidStack("cryotheum", 1) :
                ZBGTMaterials.Cryotheum.getFluid(1);
    }

    @SubscribeEvent
    public static void registerCovers(GregTechAPI.RegisterEvent<CoverDefinition> event) {
        ZBGTCovers.init();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        ZBGTLog.logger.info("Registering recipes...");

        for (int i = 1; i < GTValues.VN.length; i++) {
            CoALProperty.registerCasingTier(i, GTValues.VN[i]);
        }

        ZBGTRecipeMaps.modifyMaps();

        ZBGTRecipes.init();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ZBGTLog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();

        ZBGTMetaBlocks.ALL_CASINGS.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ZBGTLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        ZBGTMetaBlocks.ALL_CASINGS.forEach(casing -> registry.register(createItemBlock(casing, VariantItemBlock::new)));
    }

    @SubscribeEvent
    public static void registerMaterials(MaterialEvent event) {
        ZBGTLog.logger.info("Registering materials and material modifications...");
        ZBGTMaterials.init();
    }

    @SubscribeEvent
    public static void createMaterialRegistry(MaterialRegistryEvent event) {
        GregTechAPI.materialManager.createRegistry(ZBGTCore.MODID);
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent
    public static void syncConfigValues(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ZBGTCore.MODID)) {
            ConfigManager.sync(ZBGTCore.MODID, Config.Type.INSTANCE);
        }
    }
}
