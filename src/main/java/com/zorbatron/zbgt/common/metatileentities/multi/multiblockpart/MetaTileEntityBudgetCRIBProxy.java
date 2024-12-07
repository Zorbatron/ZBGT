package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.api.util.ZBGTUtility;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IDataStickIntractable;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntityBudgetCRIBProxy extends MetaTileEntityMultiblockNotifiablePart
                                           implements IMultiblockAbilityPart<IItemHandlerModifiable>,
                                           IDataStickIntractable {

    private MetaTileEntityBudgetCRIB main;
    private BlockPos mainPos;
    private boolean checkForMain = true;
    private final ProxyItemHandler proxyItemHandler = new ProxyItemHandler();

    public MetaTileEntityBudgetCRIBProxy(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.LuV, false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBudgetCRIBProxy(metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();

        if (!getWorld().isRemote && getOffsetTimer() % 100 == 0 && checkForMain && !hasMain()) {
            tryToSetMain();
        }
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.add(new ProxyItemHandler());
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);

        if (hasMain()) {
            ZBGTUtility.addNotifiableToMTE(getMain().getPatternItems(), controllerBase, this, false);
        }
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        super.removeFromMultiBlock(controllerBase);

        if (hasMain()) {
            ZBGTUtility.removeNotifiableFromMTE(getMain().getPatternItems(), controllerBase);
        }
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return getMain() != null;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return getMain().createUI(entityPlayer);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        ZBGTTextures.CRIB_PROXY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    private void tryToSetMain() {
        if (getWorld() == null || mainPos == null) return;

        TileEntity tileEntity = getWorld().getTileEntity(mainPos);
        if (!(tileEntity instanceof IGregTechTileEntity iGregTechTileEntity)) {
            this.checkForMain = true;
            return;
        }

        MetaTileEntity metaTileEntity = iGregTechTileEntity.getMetaTileEntity();
        if (!(metaTileEntity instanceof MetaTileEntityBudgetCRIB budgetCRIB)) {
            this.checkForMain = true;
            return;
        }

        this.main = budgetCRIB;
        this.checkForMain = false;

        MultiblockControllerBase controllerBase = getController();
        if (controllerBase != null) {
            NotifiableItemStackHandler mainCRIBPatternItems = getMain().getPatternItems();
            ZBGTUtility.addNotifiableToMTE(mainCRIBPatternItems, controllerBase, this, false);
            controllerBase.addNotifiedInput(mainCRIBPatternItems);
        }
    }

    private MetaTileEntityBudgetCRIB getMain() {
        return main;
    }

    public boolean hasMain() {
        return main != null && main.isValid();
    }

    @Override
    public void onDataStickLeftClick(EntityPlayer player, ItemStack dataStick) {}

    @Override
    public boolean onDataStickRightClick(EntityPlayer player, ItemStack dataStick) {
        NBTTagCompound tag = dataStick.getTagCompound();
        if (tag == null || !tag.hasKey("BudgetCRIB")) return false;

        readLocationFromTag(tag.getCompoundTag("BudgetCRIB"));
        player.sendStatusMessage(new TextComponentTranslation("zbgt.machine.budget_crib_proxy.data_stick_use",
                TextFormattingUtil.formatNumbers(mainPos.getX()),
                TextFormattingUtil.formatNumbers(mainPos.getY()),
                TextFormattingUtil.formatNumbers(mainPos.getZ())), true);

        tryToSetMain();

        return true;
    }

    private void readLocationFromTag(NBTTagCompound tag) {
        this.mainPos = new BlockPos(tag.getInteger("MainX"), tag.getInteger("MainY"), tag.getInteger("MainZ"));
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        if (data.getBoolean("HasMain")) {
            readLocationFromTag(data);
        }

        tryToSetMain();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        if (hasMain()) {
            data.setBoolean("HasMain", true);
            data.setInteger("MainX", mainPos.getX());
            data.setInteger("MainY", mainPos.getY());
            data.setInteger("MainZ", mainPos.getZ());
        } else {
            data.setBoolean("HasMain", false);
        }

        return super.writeToNBT(data);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);

        if (main != null) {
            buf.writeBoolean(true);
            buf.writeBlockPos(mainPos);
        } else {
            buf.writeBoolean(false);
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);

        if (buf.readBoolean()) {
            mainPos = buf.readBlockPos();

            tryToSetMain();
        }
    }

    // To act as a middleman when not linked to a main crib
    @SuppressWarnings("InnerClassMayBeStatic")
    private class ProxyItemHandler implements IItemHandlerModifiable {

        public ProxyItemHandler() {/**/}

        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            // I only have to worry about extracting :p
        }

        @Override
        public int getSlots() {
            return 18; // 16 pattern slots + catalyst slot + circuit
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return hasMain() ? getMain().getImportItems().getStackInSlot(slot) : ItemStack.EMPTY;
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return hasMain() ? getMain().getImportItems().extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return Integer.MAX_VALUE;
        }
    }
}
