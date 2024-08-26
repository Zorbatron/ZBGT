package com.zorbatron.zbgt;

import static com.zorbatron.zbgt.ZBGTCore.MODID;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.zorbatron.zbgt.common.covers.ZBGTCovers;
import com.zorbatron.zbgt.common.items.ZBGTMetaItems;
import com.zorbatron.zbgt.loaders.recipe.ZBGTRecipes;

import gregtech.api.GregTechAPI;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.metatileentity.registry.MTEManager;

@Mod.EventBusSubscriber(modid = MODID)
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
        ZBGTRecipes.init();
    }

    @SubscribeEvent
    public void registerMTERegistry(MTEManager.MTERegistryEvent event) {
        GregTechAPI.mteManager.createRegistry(MODID);
    }
}
