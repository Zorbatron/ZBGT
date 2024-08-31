package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import static gregtech.api.capability.GregtechDataCodes.UPDATE_ACTIVE;
import static gregtech.api.capability.GregtechDataCodes.UPDATE_ONLINE_STATUS;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.ZBGTCore;
import com.zorbatron.zbgt.client.ClientHandler;
import com.zorbatron.zbgt.client.widgets.ItemSlotTinyAmountTextWidget;

import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.implementations.IPowerChannelState;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProvider;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.events.MENetworkCraftingPatternChange;
import appeng.api.util.AECableType;
import appeng.api.util.AEPartLocation;
import appeng.api.util.DimensionalCoord;
import appeng.items.misc.ItemEncodedPattern;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.GhostCircuitSlotWidget;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.util.GTUtility;
import gregtech.common.ConfigHolder;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntityBudgetCRIB extends MetaTileEntityMultiblockNotifiablePart
                                      implements IMultiblockAbilityPart<IItemHandlerModifiable>, ICraftingProvider,
                                      IGridProxyable, IPowerChannelState, IGhostSlotConfigurable, IControllable {

    @Nullable
    protected GhostCircuitItemStackHandler circuitInventory;
    private ItemStackHandler patternSlot;
    private ICraftingPatternDetails patternDetails;
    private IItemHandlerModifiable patternItems;
    private IItemHandlerModifiable actualImportItems;
    private boolean needPatternSync = true;
    // Controls blocking
    private boolean isWorkingEnabled = true;

    private @Nullable AENetworkProxy aeProxy;
    private boolean isOnline;

    public MetaTileEntityBudgetCRIB(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier, false);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();

        this.patternSlot = new ItemStackHandler(1) {

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof ICraftingPatternItem;
            }

            @Override
            protected void onContentsChanged(int slot) {
                if (!getWorld().isRemote) {
                    if (stacks.get(0).getItem() instanceof ICraftingPatternItem) {
                        setPatternDetails();
                    } else {
                        ZBGTCore.LOGGER.warn(String.format("Item in Budget CRIB at X: %d, Y: %d, Z: %d not a pattern!",
                                getPos().getX(), getPos().getZ(), getPos().getZ()));
                    }
                }
            }
        };

        this.circuitInventory = new GhostCircuitItemStackHandler(this);
        this.circuitInventory.addNotifiableMetaTileEntity(this);

        this.patternItems = new NotifiableItemStackHandler(this, 16, getController(), false) {

             @Override
             public int getSlotLimit(int slot) {
             return Integer.MAX_VALUE;
             }

             @Override
             protected int getStackLimit(int slot, @NotNull ItemStack stack) {
             return getSlotLimit(slot);
             }
        };

        this.actualImportItems = new ItemHandlerList(Arrays.asList(this.patternItems, this.circuitInventory));
    }

    @Override
    public IItemHandlerModifiable getImportItems() {
        return this.actualImportItems;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBudgetCRIB(metaTileEntityId, getTier());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166 + 18 * 2)
                .label(7, 7, getMetaFullName());

        // Item slots
        int startX = 7;
        int startY = 20;
        for (int y = 0; y <= 3; y++) {
            for (int x = 0; x <= 3; x++) {
                int index = y * 4 + x;
                builder.widget(new ItemSlotTinyAmountTextWidget(patternItems, index, startX + 18 * x, startY + 18 * y,
                        false, false)
                                .setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        // Pattern slot
        builder.widget(new SlotWidget(patternSlot, 0, startX + 18 * 5, startY)
                .setBackgroundTexture(GuiTextures.SLOT, ClientHandler.ME_PATTERN_OVERLAY));

        // Circuit slot
        builder.widget(new GhostCircuitSlotWidget(circuitInventory, 0, startX + 18 * 5, startY + 18 * 3)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.INT_CIRCUIT_OVERLAY)
                .setConsumer(this::getCircuitSlotTooltip));

        // Blocking toggle button
        builder.widget(new ImageCycleButtonWidget(startX + 18 * 5, startY + 18 + 9, 18, 18, GuiTextures.BUTTON_POWER,
                this::isWorkingEnabled,
                this::setWorkingEnabled).setTooltipHoverString("zbgt.machine.budget_crib.blocking_button"));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * 5 + 12);
        return builder.build(getHolder(), entityPlayer);
    }

    protected void getCircuitSlotTooltip(@NotNull SlotWidget widget) {
        String configString;
        if (circuitInventory == null || circuitInventory.getCircuitValue() == GhostCircuitItemStackHandler.NO_CONFIG) {
            configString = new TextComponentTranslation("gregtech.gui.configurator_slot.no_value").getFormattedText();
        } else {
            configString = String.valueOf(circuitInventory.getCircuitValue());
        }

        widget.setTooltipText("gregtech.gui.configurator_slot.tooltip", configString);
    }

    @Override
    public boolean hasGhostCircuitInventory() {
        return this.circuitInventory != null;
    }

    @Override
    public void setGhostCircuitConfig(int config) {
        if (this.circuitInventory == null || this.circuitInventory.getCircuitValue() == config) {
            return;
        }
        this.circuitInventory.setCircuitValue(config);
        if (!getWorld().isRemote) {
            markDirty();
        }
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (isOnline) {
            ClientHandler.CRIB_ACTIVE.renderSided(getFrontFacing(), renderState, translation, pipeline);
        } else {
            ClientHandler.CRIB_INACTIVE.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    public void update() {
        super.update();

        if (!getWorld().isRemote) {
            updateMEStatus();

            if (needPatternSync && getOffsetTimer() % 10 == 0) {
                needPatternSync = !MEPatternChange();
            }
        }
    }

    private boolean MEPatternChange() {
        // don't post until it's active
        if (getProxy() == null && !getProxy().isActive()) return false;
        try {
            getProxy().getGrid()
                    .postEvent(new MENetworkCraftingPatternChange(this, getProxy().getNode()));
        } catch (GridAccessException ignored) {
            return false;
        }
        return true;
    }

    public boolean updateMEStatus() {
        boolean isOnline = this.aeProxy != null && this.aeProxy.isActive() && this.aeProxy.isPowered();
        if (this.isOnline != isOnline) {
            writeCustomData(UPDATE_ONLINE_STATUS, buf -> buf.writeBoolean(isOnline));
            this.isOnline = isOnline;
        }
        return this.isOnline;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == UPDATE_ONLINE_STATUS) {
            boolean isOnline = buf.readBoolean();
            if (this.isOnline != isOnline) {
                this.isOnline = isOnline;
                scheduleRenderUpdate();
            } else if (dataId == UPDATE_ACTIVE) {
                this.isWorkingEnabled = buf.readBoolean();
            }
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        if (this.aeProxy != null) {
            buf.writeBoolean(true);
            NBTTagCompound proxy = new NBTTagCompound();
            this.aeProxy.writeToNBT(proxy);
            buf.writeCompoundTag(proxy);
        } else {
            buf.writeBoolean(false);
        }
        buf.writeBoolean(this.isOnline);
        buf.writeBoolean(this.isWorkingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        if (buf.readBoolean()) {
            NBTTagCompound nbtTagCompound;
            try {
                nbtTagCompound = buf.readCompoundTag();
            } catch (IOException ignored) {
                nbtTagCompound = null;
            }

            if (this.aeProxy != null && nbtTagCompound != null) {
                this.aeProxy.readFromNBT(nbtTagCompound);
            }
        }
        this.isOnline = buf.readBoolean();
        this.isWorkingEnabled = buf.readBoolean();
    }

    @Nullable
    @Override
    public IGridNode getGridNode(@NotNull AEPartLocation aePartLocation) {
        return getProxy().getNode();
    }

    @NotNull
    @Override
    public AECableType getCableConnectionType(@NotNull AEPartLocation part) {
        if (part.getFacing() != this.frontFacing) {
            return AECableType.NONE;
        }
        return AECableType.SMART;
    }

    @Nullable
    @Override
    public AENetworkProxy getProxy() {
        if (this.aeProxy == null) {
            return this.aeProxy = this.createProxy();
        }
        if (!this.aeProxy.isReady() && this.getWorld() != null) {
            this.aeProxy.onReady();
        }
        return this.aeProxy;
    }

    @Nullable
    private AENetworkProxy createProxy() {
        AENetworkProxy proxy = new AENetworkProxy(this, "mte_proxy", this.getStackForm(), true);
        proxy.setFlags(GridFlags.REQUIRE_CHANNEL);
        proxy.setIdlePowerUsage(ConfigHolder.compat.ae2.meHatchEnergyUsage);
        proxy.setValidSides(EnumSet.of(this.getFrontFacing()));
        return proxy;
    }

    @Override
    public void setFrontFacing(EnumFacing frontFacing) {
        super.setFrontFacing(frontFacing);
        if (this.aeProxy != null) {
            this.aeProxy.setValidSides(EnumSet.of(this.getFrontFacing()));
        }
    }

    @Override
    public void securityBreak() {}

    @Override
    public void provideCrafting(ICraftingProviderHelper iCraftingProviderHelper) {
        if (!isActive() || patternSlot.getStackInSlot(0).isEmpty() || patternDetails == null) return;
        iCraftingProviderHelper.addCraftingOption(this, patternDetails);
    }

    private void setPatternDetails() {
        if (!(patternSlot.getStackInSlot(0).getItem() instanceof ItemEncodedPattern) &&
                !patternSlot.getStackInSlot(0).isEmpty())
            return;

        if (patternSlot.getStackInSlot(0).equals(ItemStack.EMPTY)) {
            this.needPatternSync = true;
            return;
        }

        ICraftingPatternDetails newPatternDetails = ((ICraftingPatternItem) Objects
                .requireNonNull(patternSlot.getStackInSlot(0).getItem()))
                        .getPatternForItem(patternSlot.getStackInSlot(0), getWorld());

        if (newPatternDetails.equals(this.patternDetails)) {
            return;
        }

        this.patternDetails = newPatternDetails;
        this.needPatternSync = true;
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails iCraftingPatternDetails, InventoryCrafting inventoryCrafting) {
        if (!isActive()) return false;

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            if (patternItems.insertItem(i, itemStack, true) != ItemStack.EMPTY) return false;
        }

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); ++i) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            patternItems.insertItem(i, itemStack, false);
        }

        return true;
    }

    @Override
    public boolean isBusy() {
        if (!isWorkingEnabled) return false;

        for (int i = 0; i < patternItems.getSlots(); i++) {
            if (!patternItems.getStackInSlot(i).isEmpty()) return true;
        }

        return false;
    }

    @Override
    public void gridChanged() {
        needPatternSync = true;
    }

    @Override
    public DimensionalCoord getLocation() {
        return new DimensionalCoord(getWorld(), getPos());
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        abilityList.add(this.actualImportItems);
    }

    @Override
    public boolean isPowered() {
        return getProxy() != null && getProxy().isPowered();
    }

    @Override
    public boolean isActive() {
        return getProxy() != null && getProxy().isActive();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        data.setTag("Pattern", this.patternSlot.serializeNBT());
        GTUtility.writeItems(this.patternItems, "PatternItems", data);

        if (this.circuitInventory != null) {
            this.circuitInventory.write(data);
        }

        data.setBoolean("BlockingEnabled", this.isWorkingEnabled);

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        this.patternSlot.deserializeNBT(data.getCompoundTag("Pattern"));
        GTUtility.readItems(this.patternItems, "PatternItems", data);

        circuitInventory.read(data);

        this.isWorkingEnabled = data.getBoolean("BlockingEnabled");

        setPatternDetails();
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);

        for (IItemHandler handler : ((ItemHandlerList) this.actualImportItems).getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiable) {
                notifiable.addNotifiableMetaTileEntity(controllerBase);
                notifiable.addToNotifiedList(this, handler, false);
            }
        }
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        super.removeFromMultiBlock(controllerBase);

        for (IItemHandler handler : ((ItemHandlerList) this.actualImportItems).getBackingHandlers()) {
            if (handler instanceof INotifiableHandler notifiable) {
                notifiable.removeNotifiableMetaTileEntity(controllerBase);
            }
        }
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        this.isWorkingEnabled = isWorkingAllowed;
        if (!getWorld().isRemote) {
            writeCustomData(GregtechDataCodes.UPDATE_ACTIVE, buf -> buf.writeBoolean(isWorkingAllowed));
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        super.clearMachineInventory(itemBuffer);
        clearInventory(itemBuffer, this.patternSlot);
    }
}
