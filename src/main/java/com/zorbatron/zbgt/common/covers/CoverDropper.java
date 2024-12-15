package com.zorbatron.zbgt.common.covers;

import gregtech.api.capability.GregtechTileCapabilities;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IControllable;
import gregtech.api.cover.CoverBase;
import gregtech.api.cover.CoverDefinition;
import gregtech.api.cover.CoverableView;
import gregtech.client.renderer.texture.Textures;
import org.jetbrains.annotations.Nullable;

public class CoverDropper extends CoverBase implements ITickable, IControllable {

    private boolean isWorkingEnabled = true;
    private int itemsLeftToTransferLastSecond;
    private int updateRate;
    private final int maxTransfer;

    public CoverDropper(@NotNull CoverDefinition definition, @NotNull CoverableView coverableView,
                        @NotNull EnumFacing attachedSide, int maxTransfer) {
        super(definition, coverableView, attachedSide);
        this.maxTransfer = maxTransfer;
        this.updateRate = 20;
    }

    @Override
    public boolean isWorkingEnabled() {
        return isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        isWorkingEnabled = isWorkingAllowed;

        if (!getWorld().isRemote) {
            writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(isWorkingAllowed));
        }
    }

    @Override
    public boolean canAttach(@NotNull CoverableView coverable, @NotNull EnumFacing side) {
        return coverable.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
    }

    @Override
    public void renderCover(@NotNull CCRenderState renderState, @NotNull Matrix4 translation,
                            @NotNull IVertexOperation[] pipeline, @NotNull Cuboid6 plateBox,
                            @NotNull BlockRenderLayer layer) {
        Textures.CONVEYOR_OVERLAY.renderSided(getAttachedSide(), plateBox, renderState, pipeline, translation);
    }

    @Override
    public void update() {
        CoverableView coverable = getCoverableView();
        long timer = coverable.getOffsetTimer();

        if (timer % updateRate == 0 && itemsLeftToTransferLastSecond > 0) {
            IItemHandler extractFrom = coverable.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    getAttachedSide());

            if (extractFrom != null) {
                int totalTransferred = dropItems(extractFrom, itemsLeftToTransferLastSecond);
                this.itemsLeftToTransferLastSecond -= totalTransferred;
            }
        }

        if (timer % 20 == 0) {
            this.itemsLeftToTransferLastSecond = maxTransfer;
        }
    }

    private int dropItems(IItemHandler extractFrom, int maxTransferAmount) {
        int itemsLeftToTransfer = maxTransferAmount;

        for (int slot = 0; slot < extractFrom.getSlots(); slot++) {
            ItemStack stack = extractFrom.extractItem(slot, itemsLeftToTransfer, true);
            if (stack.isEmpty()) continue;

            stack = extractFrom.extractItem(slot, itemsLeftToTransfer, false);
            itemsLeftToTransfer -= stack.getCount();

            actuallyDropItems(stack);
        }

        return itemsLeftToTransfer;
    }

    private void actuallyDropItems(ItemStack stack) {
        EnumFacing facing = getAttachedSide();

        BehaviorDefaultDispenseItem.doDispense(getWorld(), stack, 6, facing,
                getDropPosition(getPos(), facing));
    }

    private IPosition getDropPosition(BlockPos blockPos, EnumFacing facing) {
        double d0 = blockPos.getX() + 0.7D * (double) facing.getXOffset() + 0.5D;
        double d1 = blockPos.getY() + 0.7D * (double) facing.getYOffset() + 0.5D;
        double d2 = blockPos.getZ() + 0.7D * (double) facing.getZOffset() + 0.5D;

        return new PositionImpl(d0, d1, d2);
    }

    @Override
    public void readCustomData(int discriminator, @NotNull PacketBuffer buf) {
        super.readCustomData(discriminator, buf);

        if (discriminator == GregtechDataCodes.WORKING_ENABLED) {
            isWorkingEnabled = buf.readBoolean();
        }
    }

    @Override
    public void writeInitialSyncData(@NotNull PacketBuffer packetBuffer) {
        super.writeInitialSyncData(packetBuffer);
        packetBuffer.writeBoolean(isWorkingEnabled);
    }

    @Override
    public void readInitialSyncData(@NotNull PacketBuffer packetBuffer) {
        super.readInitialSyncData(packetBuffer);
        isWorkingEnabled = packetBuffer.readBoolean();
    }

    @Override
    public <T> @Nullable T getCapability(@NotNull Capability<T> capability, @Nullable T defaultValue) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }

        return super.getCapability(capability, defaultValue);
    }

    @Override
    public void writeToNBT(@NotNull NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("IsWorkingEnabled", isWorkingEnabled);
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        isWorkingEnabled = nbt.getBoolean("IsWorkingEnabled");
    }
}
