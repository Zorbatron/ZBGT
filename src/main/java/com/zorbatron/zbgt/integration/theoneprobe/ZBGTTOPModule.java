package com.zorbatron.zbgt.integration.theoneprobe;

import com.zorbatron.zbgt.api.util.ZBGTLog;
import com.zorbatron.zbgt.integration.theoneprobe.providers.DropperCoverProvider;
import com.zorbatron.zbgt.integration.theoneprobe.providers.YOTTankProvider;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class ZBGTTOPModule {

    public static void init() {
        ZBGTLog.logger.info("Registering TOP providers");

        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

        oneProbe.registerProvider(new DropperCoverProvider());
        oneProbe.registerProvider(new YOTTankProvider());
    }
}
