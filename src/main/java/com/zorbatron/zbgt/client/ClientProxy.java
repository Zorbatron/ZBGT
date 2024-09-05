package com.zorbatron.zbgt.client;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.CommonProxy;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        super.preInit();
        ZBGTTextures.preInit();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ZBGTMetaBlocks.registerItemModels();
    }
}
