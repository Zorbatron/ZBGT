package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregicality.multiblocks.common.metatileentities.multiblockpart.MetaTileEntityParallelHatch;
import gregtech.api.GTValues;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.TextFormattingUtil;

// I need to do all of this shit just so I can have more than 1,048,576 as a parallel amount
// It's marginally easier to extend and override than make a class from scratch (not sure about that anymore...)
public class MTELargeParallelHatch extends MetaTileEntityParallelHatch {

    private final int maxParallelUpper;
    private int currentParallelUpper;

    public MTELargeParallelHatch(ResourceLocation metaTileEntityId, int tier, int maxParallel) {
        super(metaTileEntityId, tier);
        this.maxParallelUpper = maxParallel;
        this.currentParallelUpper = maxParallel;
    }

    public MTELargeParallelHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.maxParallelUpper = (int) Math.pow(4, tier - GTValues.EV);
        this.currentParallelUpper = maxParallelUpper;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MTELargeParallelHatch(metaTileEntityId, getTier(), getMaxParallel());
    }

    @Override
    protected ModularUI createUI(@NotNull EntityPlayer entityPlayer) {
        ServerWidgetGroup parallelAmountGroup = new ServerWidgetGroup(() -> true);
        parallelAmountGroup.addWidget(new ImageWidget(62 - 10, 36, 53 + 20, 20, GuiTextures.DISPLAY)
                .setTooltip("gcym.machine.parallel_hatch.display"));

        parallelAmountGroup
                .addWidget(new IncrementButtonWidget(118 + 10, 36, 30, 20, 1, 4, 16, 64, this::setCurrentParallel)
                        .setDefaultTooltip()
                        .setShouldClientCallback(false));
        parallelAmountGroup
                .addWidget(new IncrementButtonWidget(29 - 10, 36, 30, 20, -1, -4, -16, -64, this::setCurrentParallel)
                        .setDefaultTooltip()
                        .setShouldClientCallback(false));

        parallelAmountGroup
                .addWidget(new TextFieldWidget2(63 - 5, 42, 51 + 10, 20, this::getParallelAmountToString, val -> {
                    if (val != null && !val.isEmpty()) {
                        setCurrentParallelUpper(Integer.parseInt(val));
                    }
                })
                        .setCentered(true)
                        .setNumbersOnly(1, getMaxParallel())
                        .setMaxLength(10) // I need extend the normal parallel hatch just so I can override this number.
                                          // DecoderException :fire:
                        .setValidator(getTextFieldValidator(this::getMaxParallel)));

        return ModularUI.defaultBuilder()
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .widget(parallelAmountGroup)
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0)
                .build(getHolder(), entityPlayer);
    }

    @Override
    public String getParallelAmountToString() {
        return Integer.toString(this.currentParallelUpper);
    }

    public int getMaxParallel() {
        return this.maxParallelUpper;
    }

    @Override
    public int getCurrentParallel() {
        return this.currentParallelUpper;
    }

    protected void setCurrentParallelUpper(int currentParallelUpper) {
        this.currentParallelUpper = currentParallelUpper;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(
                I18n.format("gcym.machine.parallel_hatch.tooltip", TextFormattingUtil.formatNumbers(getMaxParallel())));
        tooltip.add(I18n.format("gregtech.universal.disabled"));
    }

    @Override
    public NBTTagCompound writeToNBT(@NotNull NBTTagCompound data) {
        data.setInteger("currentParallelUpper", this.currentParallelUpper);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.currentParallelUpper = data.getInteger("currentParallelUpper");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.currentParallelUpper);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.currentParallelUpper = buf.readInt();
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
