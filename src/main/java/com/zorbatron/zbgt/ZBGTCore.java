package com.zorbatron.zbgt;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.zorbatron.zbgt.api.util.ZBGTLog;
import com.zorbatron.zbgt.common.CommonProxy;
import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;

import gregtech.GTInternalTags;

@Mod(modid = ZBGTCore.MODID,
     version = ZBGTCore.VERSION,
     name = ZBGTCore.NAME,
     acceptedMinecraftVersions = "[1.12.2]",
     dependencies = GTInternalTags.DEP_VERSION_STRING)
public class ZBGTCore {

    public static final String MODID = Tags.MODID;
    public static final String NAME = Tags.MODNAME;
    public static final String VERSION = Tags.VERSION;

    @SidedProxy(modId = MODID,
                clientSide = "com.zorbatron.zbgt.client.ClientProxy",
                serverSide = "com.zorbatron.zbgt.common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc. (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        // register to the event bus so that we can listen to events
        MinecraftForge.EVENT_BUS.register(this);

        ZBGTLog.init(event.getModLog());

        ZBGTLog.logger.info("I am " + Tags.MODNAME + " + at version " + Tags.VERSION);

        ZBGTMetaTileEntities.init();
        ZBGTMetaBlocks.init();

        proxy.preInit();
    }
}
