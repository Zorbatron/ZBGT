package com.zorbatron.zbgt;

import static com.zorbatron.zbgt.api.ZBGTAPI.CoAL_CASINGS;
import static com.zorbatron.zbgt.api.ZBGTAPI.PRECISE_CASINGS;
import static com.zorbatron.zbgt.common.block.ZBGTMetaBlocks.CoAL_CASING;
import static com.zorbatron.zbgt.common.block.ZBGTMetaBlocks.PRECISE_CASING;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zorbatron.zbgt.common.CommonProxy;
import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;

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

    public static final Logger LOGGER = LogManager.getLogger(Tags.MODID);

    @SidedProxy(modId = MODID,
                clientSide = "com.zorbatron.zbgt.client.ClientProxy",
                serverSide = "com.zorbatron.zbgt.common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc. (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        // register to the event bus so that we can listen to events
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("I am " + Tags.MODNAME + " + at version " + Tags.VERSION);

        ZBGTMetaTileEntities.init();
        ZBGTMetaBlocks.init();

        for (CoALCasing.CasingType type : CoALCasing.CasingType.values()) {
            CoAL_CASINGS.put(CoAL_CASING.getState(type), type);
        }

        for (PreciseCasing.CasingType type : PreciseCasing.CasingType.values()) {
            PRECISE_CASINGS.put(PRECISE_CASING.getState(type), type);
        }

        proxy.preInit();
    }
}
