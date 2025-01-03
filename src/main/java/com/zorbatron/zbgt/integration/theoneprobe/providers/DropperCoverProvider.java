package com.zorbatron.zbgt.integration.theoneprobe.providers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.ZBGTCore;
import com.zorbatron.zbgt.common.covers.CoverDropper;

import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.cover.Cover;
import gregtech.api.cover.CoverHolder;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;

public class DropperCoverProvider extends CapabilityInfoProvider<CoverHolder> {

    @Override
    protected @NotNull Capability<CoverHolder> getCapability() {
        return GregtechTileCapabilities.CAPABILITY_COVER_HOLDER;
    }

    @Override
    protected void addProbeInfo(CoverHolder capability, IProbeInfo probeInfo, EntityPlayer player,
                                TileEntity tileEntity, IProbeHitData data) {
        Cover cover = capability.getCoverAtSide(data.getSideHit());
        if (cover instanceof CoverDropper coverDropper) {
            int updateRate = coverDropper.getUpdateRate();
            probeInfo.text(updateRate == 1 ? "{*cover.cover_dropper.update_rate.3*}" :
                    "{*cover.cover_dropper.update_rate.1*}" + updateRate + "{*cover.cover_dropper.update_rate.2*}");
        }
    }

    @Override
    public String getID() {
        return ZBGTCore.MODID + ":dropper_cover_provider";
    }
}
