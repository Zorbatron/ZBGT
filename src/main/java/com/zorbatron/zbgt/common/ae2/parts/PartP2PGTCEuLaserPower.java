package com.zorbatron.zbgt.common.ae2.parts;

import java.util.ArrayDeque;
import java.util.Queue;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.Nullable;

import appeng.me.GridAccessException;
import appeng.parts.p2p.PartP2PTunnel;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.ILaserContainer;

public class PartP2PGTCEuLaserPower extends PartP2PTunnel<PartP2PGTCEuLaserPower> {

    private final ILaserContainer NULL_ENERGY_STORAGE = new NullEnergyStorage();
    private final ILaserContainer inputHandler = new InputEnergyStorage();
    private final Queue<PartP2PGTCEuLaserPower> outputs = new ArrayDeque<>();

    public PartP2PGTCEuLaserPower(ItemStack is) {
        super(is);
    }

    @Override
    public void onTunnelNetworkChange() {
        getHost().notifyNeighbors();
    }

    private ILaserContainer getAttachedEnergyStorage() {
        if (isActive()) {
            TileEntity self = getTile();
            TileEntity te = self.getWorld().getTileEntity(self.getPos().offset(getSide().getFacing()));

            if (te != null &&
                    te.hasCapability(GregtechTileCapabilities.CAPABILITY_LASER, getSide().getOpposite().getFacing())) {
                return te.getCapability(GregtechTileCapabilities.CAPABILITY_LASER, getSide().getOpposite().getFacing());
            }
        }

        return NULL_ENERGY_STORAGE;
    }

    @Override
    public boolean hasCapability(Capability<?> capability) {
        if (!isOutput()) {
            if (capability == GregtechTileCapabilities.CAPABILITY_LASER) {
                return true;
            }
        }

        return super.hasCapability(capability);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == GregtechTileCapabilities.CAPABILITY_LASER) {
            if (isOutput()) {
                return null;
            } else return (T) inputHandler;
        }

        return super.getCapability(capability);
    }

    private class InputEnergyStorage implements ILaserContainer {

        @Override
        public long getEnergyCanBeInserted() {
            long canInsert = 0;

            if (outputs.isEmpty()) {
                try {
                    for (PartP2PGTCEuLaserPower out : getOutputs()) {
                        outputs.add(out);
                    }
                } catch (GridAccessException e) {
                    e.printStackTrace();
                }
            }

            while (!outputs.isEmpty()) {
                var target = outputs.poll();
                ILaserContainer out = target.getAttachedEnergyStorage();

                if (out == this) {
                    return 0;
                }

                if (out == null || out.getEnergyCanBeInserted() <= 0) {
                    continue;
                }

                canInsert += out.getEnergyCanBeInserted();
            }

            return canInsert;
        }

        @Override
        public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
            long amperesUsed = 0L;

            if (outputs.isEmpty()) {
                try {
                    for (PartP2PGTCEuLaserPower out : getOutputs()) {
                        outputs.add(out);
                    }
                } catch (GridAccessException e) {
                    e.printStackTrace();
                }
            }

            // voltage = (long) (voltage * 0.95);

            if (voltage > 0) {
                while (!outputs.isEmpty()) {
                    var target = outputs.poll();
                    ILaserContainer output = target.getAttachedEnergyStorage();

                    if (output == null || !output.inputsEnergy(target.getSide().getFacing().getOpposite()) ||
                            output.getEnergyCanBeInserted() <= 0) {
                        continue;
                    }

                    amperesUsed += output.acceptEnergyFromNetwork(target.getSide().getFacing().getOpposite(), voltage,
                            amperage - amperesUsed);

                    if (amperesUsed == amperage) {
                        outputs.clear();
                        break;
                    }
                }
            }

            return amperesUsed;
        }

        @Override
        public boolean inputsEnergy(EnumFacing side) {
            return true;
        }

        @Override
        public long changeEnergy(long differenceAmount) {
            return 0;
        }

        @Override
        public long getEnergyStored() {
            return 0;
        }

        @Override
        public long getEnergyCapacity() {
            return 0;
        }

        @Override
        public long getInputAmperage() {
            return 0;
        }

        @Override
        public long getInputVoltage() {
            return 0;
        }
    }

    private static class NullEnergyStorage implements ILaserContainer {

        @Override
        public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
            return 0;
        }

        @Override
        public boolean inputsEnergy(EnumFacing side) {
            return false;
        }

        @Override
        public long changeEnergy(long differenceAmount) {
            return 0;
        }

        @Override
        public long getEnergyStored() {
            return 0;
        }

        @Override
        public long getEnergyCapacity() {
            return 0;
        }

        @Override
        public long getInputAmperage() {
            return 0;
        }

        @Override
        public long getInputVoltage() {
            return 0;
        }
    }
}
