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

import com.zorbatron.zbgt.api.capability.impl.InfiniteEnergyContainerHandler;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
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

    protected InfiniteEnergyContainerHandler energyContainer;

    private int setTier = 0;
    private long voltage;
    private long amps;
    private final boolean isExportHatch;

    public MetaTileEntityCreativeEnergyHatch(ResourceLocation metaTileEntityId, boolean isExportHatch) {
        super(metaTileEntityId, GTValues.MAX);
        this.voltage = 8;
        this.amps = 1;
        this.isExportHatch = isExportHatch;
        setEnergyConfiguration();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCreativeEnergyHatch(metaTileEntityId, isExportHatch);
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
        tooltip.add(I18n.format("zbgt.machine.creative_energy.warning.1"));
        tooltip.add(I18n.format("zbgt.machine.creative_energy.warning.2"));
    }

    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    public long getAmps() {
        return this.amps;
    }

    public long getVoltage() {
        return this.voltage;
    }

    @NotNull
    protected SimpleOverlayRenderer getOverlay() {
        return isExportHatch ? Textures.ENERGY_OUT_MULTI : Textures.ENERGY_IN_MULTI;
    }

    @Override
    public MultiblockAbility<IEnergyContainer> getAbility() {
        return isExportHatch ? MultiblockAbility.OUTPUT_ENERGY : MultiblockAbility.INPUT_ENERGY;
    }

    @Override
    public void registerAbilities(List<IEnergyContainer> abilityList) {
        abilityList.add(energyContainer);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int yOffset = 10;
        // Voltage selector
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166 + yOffset)
                .widget(new CycleButtonWidget(7, 7 + yOffset, 30, 20, GTValues.VNF, () -> setTier, tier -> {
                    setTier = tier;
                    voltage = GTValues.V[setTier];
                }));
        builder.label(10, 5, getMetaFullName());

        builder.label(7, 32 + yOffset, "gregtech.creative.energy.voltage");
        builder.widget(new ImageWidget(7, 44 + yOffset, 156, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(9, 50 + yOffset, 152, 16, () -> String.valueOf(voltage), value -> {
            if (!value.isEmpty()) {
                voltage = Long.parseLong(value);
                setTier = GTUtility.getTierByVoltage(voltage);
            }
        }).setAllowedChars(TextFieldWidget2.NATURAL_NUMS).setMaxLength(19).setValidator(getTextFieldValidator()));

        builder.label(7, 74 + yOffset, "gregtech.creative.energy.amperage");
        builder.widget(new ClickButtonWidget(7, 87 + yOffset, 20, 20, "-", data -> {
            if (amps > 0) {
                amps--;
            }
        }));
        builder.widget(new ClickButtonWidget(7, 111 + yOffset, 20, 20, "÷4", clickData -> {
            if (amps / 4 > 0) {
                amps = amps / 4;
            } else {
                amps = 1;
            }
        }));
        builder.widget(new ImageWidget(29, 87 + yOffset, 118, 20, GuiTextures.DISPLAY));
        builder.widget(new TextFieldWidget2(31, 93 + yOffset, 114, 16, () -> String.valueOf(amps), value -> {
            if (!value.isEmpty()) {
                amps = Integer.parseInt(value);
            }
        }).setMaxLength(10).setNumbersOnly(0, Integer.MAX_VALUE));
        builder.widget(new ClickButtonWidget(149, 87 + yOffset, 20, 20, "+", data -> {
            if (amps < Integer.MAX_VALUE) {
                amps++;
            }
        }));
        builder.widget(new ClickButtonWidget(149, 111 + yOffset, 20, 20, "x4", data -> {
            if (amps * 4 <= Integer.MAX_VALUE) {
                amps = amps * 4;
            }
        }));

        builder.widget(new ClickButtonWidget(7, 139 + yOffset, 80, 20,
                I18n.format("zbgt.machine.creative_energy.apply_button"),
                (clickData) -> setEnergyConfiguration()));

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

    protected void setEnergyConfiguration() {
        this.energyContainer = new InfiniteEnergyContainerHandler(this,
                isExportHatch ? 0L : getVoltage(),
                isExportHatch ? 0L : getAmps(),
                isExportHatch ? getVoltage() : 0L,
                isExportHatch ? getAmps() : 0L,
                isExportHatch, this::getController);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setLong("Voltage", this.voltage);
        data.setLong("Amps", this.amps);
        data.setByte("Tier", (byte) this.setTier);
        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        this.voltage = data.getLong("Voltage");
        this.amps = data.getLong("Amps");
        this.setTier = data.getByte("Tier");
        super.readFromNBT(data);
        setEnergyConfiguration();
    }
}
