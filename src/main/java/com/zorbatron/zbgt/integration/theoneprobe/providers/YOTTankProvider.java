package com.zorbatron.zbgt.integration.theoneprobe.providers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.ZBGTCore;
import com.zorbatron.zbgt.common.metatileentities.multi.MetaTileEntityYOTTank;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IMultiblockController;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.TextFormattingUtil;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;

public class YOTTankProvider extends CapabilityInfoProvider<IMultiblockController> {

    @Override
    protected @NotNull Capability<IMultiblockController> getCapability() {
        return GregtechCapabilities.CAPABILITY_MULTIBLOCK_CONTROLLER;
    }

    @Override
    protected void addProbeInfo(IMultiblockController capability, IProbeInfo probeInfo, EntityPlayer player,
                                TileEntity tileEntity, IProbeHitData data) {
        if (tileEntity instanceof IGregTechTileEntity gregTechTileEntity) {
            if (gregTechTileEntity.getMetaTileEntity() instanceof MetaTileEntityYOTTank yotTank) {
                probeInfo.text("{*zbgt.machine.yottank.fluid_no_format*} " + "{*" + (yotTank.getFluid() == null ?
                        "zbgt.machine.yottank.none" : yotTank.getFluid().getUnlocalizedName()) + "*}");
                probeInfo.text("{*zbgt.machine.yottank.current_capacity_no_format*} " +
                        TextFormattingUtil.formatNumbers(yotTank.getStored()) + "L");
            }
        }
    }

    @Override
    public String getID() {
        return ZBGTCore.MODID + ":yottank_provider";
    }
}
