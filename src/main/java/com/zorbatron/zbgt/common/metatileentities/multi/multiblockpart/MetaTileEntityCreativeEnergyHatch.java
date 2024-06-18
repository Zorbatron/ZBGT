package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;
import java.util.function.Function;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.CycleButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.TextFieldWidget2;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import gregtech.client.utils.PipelineUtil;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

public class MetaTileEntityCreativeEnergyHatch extends MetaTileEntityMultiblockPart implements
                                               IMultiblockAbilityPart<IEnergyContainer> {

    protected IEnergyContainer energyContainer;

    private long voltage;
    private long amps;
    private boolean workingEnabled;

    private int setTier = 0;

    public MetaTileEntityCreativeEnergyHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTValues.MAX);
        this.voltage = 8;
        this.amps = 1;
        this.workingEnabled = true;
        updateEnergyData();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCreativeEnergyHatch(metaTileEntityId);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (shouldRenderOverlay()) {
            getOverlay().renderSided(getFrontFacing(), renderState, translation,
                    PipelineUtil.color(pipeline, GTValues.VC[getTier()]));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.creative_tooltip.1") + TooltipHelper.RAINBOW +
                I18n.format("gregtech.creative_tooltip.2") + I18n.format("gregtech.creative_tooltip.3"));
        tooltip.add(I18n.format("gregtech.universal.enabled"));
    }

    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    @Override
    public void update() {
        super.update();

        if (workingEnabled) {
            long fillAmount = energyContainer.getEnergyCapacity() - energyContainer.getEnergyStored();
            if (fillAmount > 0) {
                energyContainer.addEnergy(fillAmount);
            }
        }
    }

    @NotNull
    private SimpleOverlayRenderer getOverlay() {
        return Textures.ENERGY_IN_MULTI;
    }

    @Override
    public MultiblockAbility<IEnergyContainer> getAbility() {
        return MultiblockAbility.INPUT_ENERGY;
    }

    @Override
    public void registerAbilities(List<IEnergyContainer> abilityList) {
        abilityList.add(energyContainer);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        // Voltage selector
        ModularUI.Builder builder = ModularUI.defaultBuilder()
                .widget(new CycleButtonWidget(7, 7, 30, 20, GTValues.VNF, () -> setTier, tier -> {
                    setTier = tier;
                    voltage = GTValues.V[setTier];
                    updateEnergyData();
                }));
        builder.label(7, 32, "gregtech.creative.energy.voltage");
        builder.widget(new ImageWidget(7, 44, 156, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(9, 50, 152, 16, () -> String.valueOf(voltage), value -> {
            if (!value.isEmpty()) {
                voltage = Long.parseLong(value);
                setTier = GTUtility.getTierByVoltage(voltage);
                updateEnergyData();
            }
        }).setAllowedChars(TextFieldWidget2.NATURAL_NUMS).setMaxLength(19).setValidator(getTextFieldValidator()));

        builder.label(7, 74, "gregtech.creative.energy.amperage");
        builder.widget(new ClickButtonWidget(7, 87, 20, 20, "-", data -> {
            if (amps > 0) {
                amps--;
            }
            updateEnergyData();
        }));
        builder.widget(new ClickButtonWidget(7, 111, 20, 20, "÷4", clickData -> {
            if (amps / 4 > 0) {
                amps = amps / 4;
            } else {
                amps = 1;
            }
            updateEnergyData();
        }));
        builder.widget(new ImageWidget(29, 87, 118, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(31, 93, 114, 16, () -> String.valueOf(amps), value -> {
            if (!value.isEmpty()) {
                amps = Integer.parseInt(value);
            }
            updateEnergyData();
        }).setMaxLength(10).setNumbersOnly(0, Integer.MAX_VALUE));
        builder.widget(new ClickButtonWidget(149, 87, 20, 20, "+", data -> {
            if (amps < Integer.MAX_VALUE) {
                amps++;
            }
            updateEnergyData();
        }));
        builder.widget(new ClickButtonWidget(149, 111, 20, 20, "x4", data -> {
            if (amps * 4 <= Integer.MAX_VALUE) {
                amps = amps * 4;
            }
            updateEnergyData();
        }));

        builder.widget(new CycleButtonWidget(7, 139, 77, 20, () -> this.workingEnabled, this::setworkingEnabled,
                "gregtech.creative.activity.off", "gregtech.creative.activity.on"));

        return builder.build(getHolder(), entityPlayer);
    }

    public static Function<String, String> getTextFieldValidator() {
        return val -> {
            if (val.isEmpty()) {
                return "0";
            }
            long num;
            try {
                num = Long.parseLong(val);
            } catch (NumberFormatException ignored) {
                return "0";
            }
            if (num < 0) {
                return "0";
            }
            return val;
        };
    }

    private void updateEnergyData() {
        this.energyContainer = EnergyContainerHandler.receiverContainer(this, voltage * amps * 16L,
                voltage, amps);
    }

    public void setworkingEnabled(boolean workingEnabled) {
        this.workingEnabled = workingEnabled;
        if (!this.getWorld().isRemote) {
            this.writeCustomData(GregtechDataCodes.UPDATE_ACTIVE, (buf) -> buf.writeBoolean(workingEnabled));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setLong("Voltage", voltage);
        data.setLong("Amps", amps);
        data.setByte("Tier", (byte) setTier);
        data.setBoolean("workingEnabled", workingEnabled);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        voltage = data.getLong("Voltage");
        amps = data.getLong("Amps");
        setTier = data.getByte("Tier");
        workingEnabled = data.getBoolean("workingEnabled");
        super.readFromNBT(data);
        updateEnergyData();
    }
}
