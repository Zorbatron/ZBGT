package com.zorbatron.zbgt;

import static com.zorbatron.zbgt.api.ZBGTAPI.*;
import static com.zorbatron.zbgt.common.block.ZBGTMetaBlocks.*;
import static gregtech.api.GregTechAPI.HEATING_COILS;

import java.io.IOException;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.zorbatron.zbgt.api.util.ZBGTLog;
import com.zorbatron.zbgt.common.CommonProxy;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.CoALCasing;
import com.zorbatron.zbgt.common.block.blocks.CreativeHeatingCoil;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;
import com.zorbatron.zbgt.common.block.blocks.YOTTankCell;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.core.sound.ZBGTSoundEvents;

import gregtech.GTInternalTags;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;

@Mod(modid = ZBGTCore.MODID,
     version = ZBGTCore.VERSION,
     name = ZBGTCore.NAME,
     acceptedMinecraftVersions = "[1.12.2]",
     dependencies = GTInternalTags.DEP_VERSION_STRING +
             "required-after:gcym@[1.2.11,);" +
             "required-after:appliedenergistics2;")
public class ZBGTCore {

    public static final String MODID = Tags.MODID;
    public static final String NAME = Tags.MODNAME;
    public static final String VERSION = Tags.VERSION;

    @SidedProxy(modId = MODID,
                clientSide = "com.zorbatron.zbgt.client.ClientProxy",
                serverSide = "com.zorbatron.zbgt.common.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // register to the event bus so that we can listen to events
        MinecraftForge.EVENT_BUS.register(this);

        ZBGTLog.logger.info("I am " + Tags.MODNAME + " + at version " + Tags.VERSION);

        ZBGTMetaTileEntities.init();
        ZBGTMetaBlocks.init();

        for (CoALCasing.CasingType type : CoALCasing.CasingType.values()) {
            CoAL_CASINGS.put(CoAL_CASING.getState(type), type);
        }

        for (PreciseCasing.CasingType type : PreciseCasing.CasingType.values()) {
            PRECISE_CASINGS.put(PRECISE_CASING.getState(type), type);
        }

        for (BlockMachineCasing.MachineCasingType type : BlockMachineCasing.MachineCasingType.values()) {
            if (type.ordinal() > (GregTechAPI.isHighTier() ? GTValues.MAX : GTValues.UHV)) continue;

            MACHINE_CASINGS.put(MetaBlocks.MACHINE_CASING.getState(type), type);
        }

        for (YOTTankCell.CasingType type : YOTTankCell.CasingType.values()) {
            YOTTANK_CELLS.put(YOTTANK_CELL.getState(type), type);
        }

        HEATING_COILS.put(CREATIVE_HEATING_COIL.getState(CreativeHeatingCoil.CoilType.CREATIVE_COIL),
                CreativeHeatingCoil.CoilType.CREATIVE_COIL);

        ZBGTSoundEvents.register();

        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) throws IOException {
        proxy.postInit();
    }
}
