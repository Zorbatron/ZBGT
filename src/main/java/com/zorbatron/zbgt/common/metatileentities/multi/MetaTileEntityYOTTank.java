package com.zorbatron.zbgt.common.metatileentities.multi;

import static gregtech.api.capability.GregtechDataCodes.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.api.util.ZBGTUtility;
import com.zorbatron.zbgt.common.ZBGTConfig;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;
import com.zorbatron.zbgt.common.block.blocks.YOTTankCell;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.MetaTileEntityYOTTankMEHatch;
import com.zorbatron.zbgt.core.sound.ZBGTSoundEvents;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;

public class MetaTileEntityYOTTank extends MultiblockWithDisplayBase implements IControllable {

    private boolean isWorkingEnabled;
    private boolean isFluidLocked;
    private boolean shouldImport;
    private boolean shouldExport;

    private BigInteger capacity;
    private BigInteger stored;
    private FluidStack fluid;
    private FluidStack lockedFluid;

    private int tickRate;
    private boolean voiding;

    private MetaTileEntityYOTTankMEHatch MEHatch;

    private static final String YOTTANK_CELL_HEADER = "YOTTANK_CELL_";

    public MetaTileEntityYOTTank(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.isWorkingEnabled = true;
        this.isFluidLocked = false;
        this.shouldImport = false;
        this.shouldExport = false;

        this.capacity = BigInteger.ZERO;
        this.stored = BigInteger.ZERO;

        this.tickRate = 20;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityYOTTank(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote && isWorkingEnabled() && getOffsetTimer() % tickRate == 0) {
            if (shouldImport) {
                importFluids();
            }

            if (shouldExport) {
                exportFluids();
            }
        }
    }

    private void importFluids() {
        long totalDrained = 0;
        long drainUpTo;
        if (isVoiding()) {
            drainUpTo = Long.MAX_VALUE;
        } else {
            BigInteger bigDrainUpTo = capacity.subtract(stored);
            if (bigDrainUpTo.compareTo(ZBGTUtility.BIGINT_MAXLONG) >= 1) {
                drainUpTo = Long.MAX_VALUE;
            } else {
                drainUpTo = bigDrainUpTo.longValueExact();
            }
        }

        for (IMultipleTankHandler.MultiFluidTankEntry tank : this.importFluids.getFluidTanks()) {
            int drainUpToInt = (int) Math.min(Integer.MAX_VALUE, drainUpTo);

            FluidStack tankFluid = tank.getFluid();
            if (tankFluid == null) continue;

            if (isFluidLocked) {
                if (lockedFluid != null) {
                    if (!lockedFluid.isFluidEqual(tankFluid)) continue;
                } else {
                    if (fluid != null) {
                        lockedFluid = fluid.copy();
                    } else {
                        lockedFluid = tankFluid.copy();
                        lockedFluid.amount = 1;
                    }
                }
            }

            if (this.fluid == null || tankFluid.isFluidEqual(this.fluid)) {
                if (this.fluid == null) {
                    setFluid(tankFluid.copy());
                }

                FluidStack drainedFluid = tank.drain(drainUpToInt, true);
                if (drainedFluid != null) totalDrained += drainedFluid.amount;
            }
        }

        this.stored = stored.add(BigInteger.valueOf(totalDrained));

        if (this.stored.compareTo(BigInteger.ZERO) <= 0) {
            this.fluid = null;
        }
    }

    private void exportFluids() {
        if (this.fluid != null) {
            int outputAmount;
            if (this.stored.compareTo(ZBGTUtility.BIGINT_MAXINT) >= 0) {
                outputAmount = Integer.MAX_VALUE;
            } else {
                outputAmount = this.stored.intValueExact();
            }

            final int originalOutputAmount = outputAmount;

            for (IMultipleTankHandler.MultiFluidTankEntry tank : this.exportFluids.getFluidTanks()) {
                final FluidStack fluidInHatch = tank.getFluid();

                final int remainingHatchSpace;

                if (fluidInHatch != null) {
                    if (fluidInHatch.isFluidEqual(this.fluid)) {
                        remainingHatchSpace = tank.getCapacity() - fluidInHatch.amount;
                    } else {
                        continue;
                    }
                } else {
                    remainingHatchSpace = tank.getCapacity();
                }

                final int amountToFillHatch = Math.min(remainingHatchSpace, outputAmount);
                if (amountToFillHatch <= 0) {
                    continue;
                }

                final FluidStack fillStack = this.fluid.copy();
                fillStack.amount = amountToFillHatch;
                final int transferredAmount = tank.fill(fillStack, true);
                outputAmount -= transferredAmount;
            }

            final int totalDrainedAmount = originalOutputAmount - outputAmount;
            if (totalDrainedAmount > 0) {
                this.stored = this.stored.subtract(BigInteger.valueOf(totalDrainedAmount));
                if (this.stored.signum() < 0) {
                    throw new IllegalStateException(
                            "YOTTank drained beyond its fluid amount, indicating logic bug: " + this.stored);
                }
            }
        }
    }

    public void setFluid(FluidStack fluid) {
        this.fluid = fluid;
        this.fluid.amount = 1;

        if (this.lockedFluid == null && this.isFluidLocked) {
            this.lockedFluid = this.fluid.copy();
        }
    }

    public FluidStack getFluid() {
        return this.fluid;
    }

    public FluidStack getLockedFluid() {
        return this.lockedFluid;
    }

    public BigInteger getCapacity() {
        return this.capacity;
    }

    public void setCapacity(BigInteger capacity) {
        this.capacity = capacity;
    }

    public BigInteger getStored() {
        return this.stored;
    }

    /**
     * Attempts to put {@code amount} of fluid into the tank if possible, fails if there's not enough space for all of
     * it.
     *
     * @param amount The millibucket amount of the fluid to insert
     * @param doFill Whether to actually fill, or just simulate a fill
     * @return True if successfully added the given amount of fluid to the tank, false if failed.
     */
    public boolean addFluid(long amount, boolean doFill) {
        final BigInteger bigAmount = BigInteger.valueOf(amount);
        final BigInteger newTotal = this.stored.add(bigAmount);

        if (newTotal.compareTo(this.capacity) > 0) {
            return false;
        } else {
            if (doFill) {
                this.stored = newTotal;
            }

            return true;
        }
    }

    /**
     * Attempts to remove {@code amount} of fluid from the tank if possible, does not do partial removals.
     *
     * @param amount The millibucket amount of the fluid to remove
     */
    public void reduceFluid(long amount) {
        final BigInteger bigAmount = BigInteger.valueOf(amount);

        if (this.stored.compareTo(bigAmount) >= 0) {
            this.stored = this.stored.subtract(bigAmount);
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();

        this.shouldImport = this.importFluids.getTanks() > 0;
        this.shouldExport = this.exportFluids.getTanks() > 0;

        List<YOTTankCell.CasingType> cells = new ArrayList<>();
        for (Map.Entry<String, Object> cell : context.entrySet()) {
            if (cell.getKey().startsWith(YOTTANK_CELL_HEADER) && cell.getValue() instanceof CellMatchWrapper wrapper) {
                for (int i = 0; i < wrapper.amount; i++) {
                    cells.add(wrapper.casingType);
                }
            }
        }

        cells.forEach(casingType -> this.capacity = this.capacity.add(casingType.getCapacity()));

        for (IMultiblockPart multiblockPart : this.getMultiblockParts()) {
            if (multiblockPart instanceof MetaTileEntityYOTTankMEHatch meHatch) {
                MEHatch = meHatch;
                break;
            }
        }

        resetMEHatch();
    }

    protected void initializeAbilities() {
        this.importFluids = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.exportFluids = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    @Override
    public void invalidateStructure() {
        this.capacity = BigInteger.ZERO;
        resetTileAbilities();
        resetMEHatch();

        super.invalidateStructure();
    }

    private void resetTileAbilities() {
        this.importFluids = new FluidTankList(true);
        this.exportFluids = new FluidTankList(true);
    }

    private void resetMEHatch() {
        if (MEHatch != null) MEHatch.notifyME();
    }

    @Override
    @NotNull
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.UP)
                .aisle("#####", "#OOO#", "#OOO#", "#OOO#", "#####")
                .aisle("MMSMM", "MXXXM", "MXXXM", "MXXXM", "MMMMM")
                .aisle("GGGGG", "GCCCG", "GCCCG", "GCCCG", "GGGGG").setRepeatable(1, 15)
                .aisle("XXXXX", "XIIIX", "XIIIX", "XIIIX", "XXXXX")
                .aisle("FFFFF", "F###F", "F###F", "F###F", "FFFFF")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()))
                .where('M', autoAbilities(true, false)
                        .or(states(getCasingState())))
                .where('C', CELL_PREDICATE.get())
                .where('O', abilities(MultiblockAbility.EXPORT_FLUIDS).setPreviewCount(1)
                        .or(states(getCasingState()))
                        .or(getYOTTankMEHatch()))
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1)
                        .or(states(getCasingState())))
                .where('F', frames(Materials.Steel))
                .where('G', states(getGlassState()))
                .where('#', any())
                .build();
    }

    protected IBlockState getCasingState() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.YOTTANK_CASING);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    protected TraceabilityPredicate getYOTTankMEHatch() {
        return metaTileEntities(ZBGTMetaTileEntities.YOTTANK_ME_HATCH).setMaxGlobalLimited(1);
    }

    protected void setVoiding(boolean isVoiding) {
        this.voiding = isVoiding;
        if (!getWorld().isRemote) {
            writeCustomData(UPDATE_IS_VOIDING, buf -> buf.writeBoolean(this.voiding));
            markDirty();
        }
    }

    public boolean isVoiding() {
        return voiding;
    }

    protected void setLocked(boolean locked) {
        this.isFluidLocked = locked;
        if (!getWorld().isRemote) {
            writeCustomData(UPDATE_LOCKED_STATE, buf -> buf.writeBoolean(this.isFluidLocked));

            if (!locked && lockedFluid != null) {
                lockedFluid = null;
            } else if (fluid != null) {
                lockedFluid = fluid.copy();
            }

            markDirty();
        }
    }

    protected boolean isLocked() {
        return isFluidLocked;
    }

    protected void setTickRate(String tickRate) {
        int newTickRate;

        try {
            newTickRate = Integer.parseInt(tickRate);
        } catch (Exception ignored) {
            newTickRate = 20;
        }

        this.tickRate = newTickRate;
        markDirty();
    }

    protected String getTickRate() {
        return String.valueOf(this.tickRate);
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 198, 208);
        builder.image(4, 4, 190, 117, GuiTextures.DISPLAY);

        builder.label(9, 9, getMetaFullName(), 0xFFFFFF);
        builder.widget(new AdvancedTextWidget(9, 20, this::addDisplayText, 0xFFFFFF)
                .setMaxWidthLimit(181)
                .setClickHandler(this::handleDisplayClick));

        builder.widget(new ImageCycleButtonWidget(173, 183, 18, 18, GuiTextures.BUTTON_POWER,
                this::isWorkingEnabled, this::setWorkingEnabled));
        builder.widget(new ImageWidget(173, 201, 18, 6, GuiTextures.BUTTON_POWER_DETAIL));

        builder.widget(new ToggleButtonWidget(173, 161, 18, 18, GuiTextures.BUTTON_FLUID_VOID,
                this::isVoiding, this::setVoiding)
                        .setTooltipText("gregtech.gui.fluid_voiding.tooltip")
                        .shouldUseBaseBackground());

        builder.widget(new ToggleButtonWidget(173, 143, 18, 18, GuiTextures.BUTTON_LOCK,
                this::isLocked, this::setLocked)
                        .setTooltipText("gregtech.gui.fluid_lock.tooltip")
                        .shouldUseBaseBackground());

        // Flex Button
        builder.widget(getFlexButton(173, 125, 18, 18));

        builder.widget(new AdvancedTextWidget(9, 108,
                (tl) -> tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE,
                        I18n.format("zbgt.machine.yottank.tick_rate"))),
                0xFFFFFF));
        builder.widget(new TextFieldWidget2(9 + 50, 108, 25, 18, this::getTickRate, this::setTickRate)
                .setNumbersOnly(1, 100));

        builder.bindPlayerInventory(entityPlayer.inventory, 125);
        return builder;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isWorkingEnabled())
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE,
                            "zbgt.machine.yottank.max_capacity",
                            TextFormattingUtil.formatNumbers(this.capacity)));

                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE,
                            "zbgt.machine.yottank.current_capacity",
                            TextFormattingUtil.formatNumbers(this.stored)));

                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE,
                            "zbgt.machine.yottank.fluid",
                            this.fluid == null ? I18n.format("zbgt.machine.yottank.none") :
                                    this.fluid.getLocalizedName()));

                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.WHITE,
                            "zbgt.machine.yottank.locked_fluid",
                            I18n.format(getLockedFluidName())));
                })
                .addWorkingStatusLine();
    }

    protected String getLockedFluidName() {
        if (!isFluidLocked) return "zbgt.machine.yottank.none";
        if (lockedFluid == null) return "zbgt.machine.yottank.next";
        return lockedFluid.getLocalizedName();
    }

    protected static final Supplier<TraceabilityPredicate> CELL_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ZBGTAPI.YOTTANK_CELLS.containsKey(blockState)) {
                    YOTTankCell.CasingType casing = ZBGTAPI.YOTTANK_CELLS.get(blockState);

                    String key = YOTTANK_CELL_HEADER + casing.getCellName();
                    CellMatchWrapper wrapper = blockWorldState.getMatchContext().get(key);
                    if (wrapper == null) wrapper = new CellMatchWrapper(casing);
                    blockWorldState.getMatchContext().set(key, wrapper.increment());

                    return true;
                }

                return false;
            }, () -> ZBGTAPI.YOTTANK_CELLS.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().ordinal()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new))
                            .addTooltips("zbgt.multiblock.pattern.error.yottank_cells");

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.YOTTANK_CASING;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISPLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == UPDATE_ACTIVE) {
            this.isWorkingEnabled = buf.readBoolean();
        } else if (dataId == UPDATE_IS_VOIDING) {
            this.voiding = buf.readBoolean();
        } else if (dataId == UPDATE_LOCKED_STATE) {
            this.isFluidLocked = buf.readBoolean();
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
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setString("StorageCurrent", this.stored.toString());
        data.setBoolean("IsFluidLocked", this.isFluidLocked);
        data.setBoolean("IsVoiding", this.voiding);
        data.setInteger("TickRate", this.tickRate);
        data.setBoolean("IsWorkingEnabled", this.isWorkingEnabled);

        if (fluid != null) data.setTag("FluidTag", fluid.writeToNBT(new NBTTagCompound()));
        if (lockedFluid != null) data.setTag("LockedFluidTag", lockedFluid.writeToNBT(new NBTTagCompound()));

        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        String amountCurrent = data.getString("StorageCurrent");
        this.stored = amountCurrent.isEmpty() ? BigInteger.ZERO : new BigInteger(amountCurrent);
        this.isFluidLocked = data.getBoolean("IsFluidLocked");
        this.voiding = data.getBoolean("IsVoiding");
        this.tickRate = data.getInteger("TickRate");
        this.isWorkingEnabled = !data.hasKey("IsWorkingEnabled") || data.getBoolean("IsWorkingEnabled");

        // Moved to using "FluidTag" when I changed how the fluid was stored to NBT.
        // Gotta check for legacy tanks so players don't end up with empty YOTTanks!
        if (data.hasKey("FluidTag")) {
            this.fluid = FluidStack.loadFluidStackFromNBT(data.getCompoundTag("FluidTag"));
            this.lockedFluid = FluidStack.loadFluidStackFromNBT(data.getCompoundTag("LockedFluidTag"));
        } else {
            this.fluid = FluidRegistry.getFluidStack(data.getString("Fluid"), 1);
            this.lockedFluid = FluidRegistry.getFluidStack(data.getString("LockedFluid"), 1);
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);

        buf.writeBoolean(voiding);
        buf.writeBoolean(isFluidLocked);
        buf.writeInt(tickRate);
        buf.writeBoolean(isWorkingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);

        this.voiding = buf.readBoolean();
        this.isFluidLocked = buf.readBoolean();
        this.tickRate = buf.readInt();
        this.isWorkingEnabled = buf.readBoolean();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public SoundEvent getSound() {
        return ZBGTConfig.multiblockSettings.yottankSound ? ZBGTSoundEvents.FX_LOW_FREQ : null;
    }

    @Override
    public boolean isActive() {
        return isWorkingEnabled() && isStructureFormed();
    }

    private static class CellMatchWrapper {

        private final YOTTankCell.CasingType casingType;
        private int amount;

        public CellMatchWrapper(YOTTankCell.CasingType casingType) {
            this.casingType = casingType;
        }

        public CellMatchWrapper increment() {
            this.amount++;
            return this;
        }
    }

    @Override
    public boolean isInCreativeTab(CreativeTabs creativeTab) {
        return creativeTab == CreativeTabs.SEARCH || creativeTab == ZBGTAPI.TAB_ZBGT;
    }
}
