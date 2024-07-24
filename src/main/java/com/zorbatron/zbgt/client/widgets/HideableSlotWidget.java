package com.zorbatron.zbgt.client.widgets;

import java.util.function.BooleanSupplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.gui.widgets.SlotWidget;

public class HideableSlotWidget extends SlotWidget {

    private final BooleanSupplier isGroupVisible;
    private boolean lastIsGroupVisible;

    public HideableSlotWidget(IItemHandlerModifiable itemHandler, int slotIndex, int xPosition, int yPosition,
                              BooleanSupplier isVisibleGetter) {
        super(itemHandler, slotIndex, xPosition, yPosition);
        isGroupVisible = isVisibleGetter;
    }

    @Override
    public void detectAndSendChanges() {
        boolean isGroupVisible = this.isGroupVisible.getAsBoolean();
        if (isGroupVisible != lastIsGroupVisible) {
            this.lastIsGroupVisible = isGroupVisible;
            writeUpdateInfo(3, packetBuffer -> packetBuffer.writeBoolean(lastIsGroupVisible));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 3) {
            setVisible(buffer.readBoolean());
        }
    }
}
