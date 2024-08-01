package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.GasCollectorDimensionProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.XSTR;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;

public class MetaTileEntityAirIntakeHatch extends MetaTileEntityMultiblockNotifiablePart
                                          implements IMultiblockAbilityPart<IFluidTank> {

    private final FluidTank fluidTank;
    protected final static XSTR floatGen = new XSTR();
    private boolean isWorkingEnabled;

    private final int tankCapacity;
    private final int fillAmount;
    private Fluid fillFluid;

    public MetaTileEntityAirIntakeHatch(ResourceLocation metaTileEntityId, int tier, int tankCapacity, int fillAmount) {
        super(metaTileEntityId, tier, false);
        this.fluidTank = new NotifiableFluidTank(tankCapacity, this, false);

        this.tankCapacity = tankCapacity;
        this.fillAmount = fillAmount;

        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityAirIntakeHatch(metaTileEntityId, getTier(), tankCapacity, fillAmount);
    }

    @Override
    public void update() {
        super.update();

        if (isFirstTick() && !getWorld().isRemote) {
            Collection<Recipe> collectorRecipes = RecipeMaps.GAS_COLLECTOR_RECIPES.getRecipeList();

            for (Recipe recipe : collectorRecipes) {
                if (!recipe.hasProperty(GasCollectorDimensionProperty.getInstance())) continue;

                IntList dimensionProperty = recipe.getProperty(GasCollectorDimensionProperty.getInstance(),
                        IntLists.EMPTY_LIST);

                if (dimensionProperty.get(0) == getWorld().provider.getDimension()) {
                    this.fillFluid = recipe.getFluidOutputs().get(0).getFluid();
                    break;
                }
            }

            if (this.fillFluid == null) {
                this.fillFluid = Materials.Air.getFluid();
            }
        }

        final EnumFacing facing = getFrontFacing();
        final BlockPos blockFacingPos = new BlockPos(getPos().getX() + facing.getXOffset(),
                getPos().getY() + facing.getYOffset(), getPos().getZ() + facing.getZOffset());

        if (getOffsetTimer() % 5 == 0 && getWorld().isAirBlock(blockFacingPos)) {
            if (!getWorld().isRemote) {
                int fillAmount = fluidTank.fill(new FluidStack(fillFluid, this.fillAmount), true);

                if (fillAmount == 0 && isWorkingEnabled) {
                    isWorkingEnabled = false;
                    writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(isWorkingEnabled));
                } else if (fillAmount > 0 && !isWorkingEnabled) {
                    isWorkingEnabled = true;
                    writeCustomData(GregtechDataCodes.WORKING_ENABLED, buf -> buf.writeBoolean(isWorkingEnabled));
                }
            }

            if (getWorld().isRemote && isWorkingEnabled) {
                generateParticles();
            }
        }

        fillContainerFromInternalTank(fluidTank);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);

        if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.isWorkingEnabled = buf.readBoolean();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);

        buf.writeBoolean(isWorkingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);

        this.isWorkingEnabled = buf.readBoolean();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createTankUI(fluidTank, getMetaFullName(), entityPlayer).build(getHolder(), entityPlayer);
    }

    public ModularUI.Builder createTankUI(IFluidTank fluidTank, String title, EntityPlayer entityPlayer) {
        // Create base builder/widget references
        ModularUI.Builder builder = ModularUI.defaultBuilder();
        TankWidget tankWidget;

        // Add input/output-specific widgets
        tankWidget = new TankWidget(fluidTank, 69, 52, 18, 18)
                .setAlwaysShowFull(true).setDrawHoveringText(false).setContainerClicking(true, false);

        builder.image(7, 16, 81, 55, GuiTextures.DISPLAY);

        // Add general widgets
        return builder.label(6, 6, title)
                .label(11, 20, "gregtech.gui.fluid_amount", 0xFFFFFF)
                .widget(new AdvancedTextWidget(11, 30, getFluidAmountText(tankWidget), 0xFFFFFF))
                .widget(new AdvancedTextWidget(11, 40, getFluidNameText(tankWidget), 0xFFFFFF))
                .widget(tankWidget)
                .bindPlayerInventory(entityPlayer.inventory);
    }

    private Consumer<List<ITextComponent>> getFluidNameText(TankWidget tankWidget) {
        return (list) -> {
            TextComponentTranslation translation = tankWidget.getFluidTextComponent();
            if (translation != null) {
                list.add(translation);
            }
        };
    }

    private Consumer<List<ITextComponent>> getFluidAmountText(TankWidget tankWidget) {
        return (list) -> {
            String fluidAmount = tankWidget.getFormattedFluidAmount();
            if (!fluidAmount.isEmpty()) {
                list.add(new TextComponentString(fluidAmount));
            }
        };
    }

    @Override
    public MultiblockAbility<IFluidTank> getAbility() {
        return MultiblockAbility.IMPORT_FLUIDS;
    }

    @Override
    public void registerAbilities(List<IFluidTank> abilityList) {
        abilityList.add(fluidTank);
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        Textures.PIPE_IN_OVERLAY.renderSided(this.getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, fluidTank);
    }

    // Black magic from GT++
    private void generateParticles() {
        final EnumParticleTypes particle = EnumParticleTypes.CLOUD;

        final float ran1 = floatGen.nextFloat();
        float ran2 = floatGen.nextFloat();
        float ran3 = floatGen.nextFloat();

        final BlockPos position = this.getPos();
        final EnumFacing direction = getFrontFacing();

        final float xPos = position.getX() + 0.25f + direction.getXOffset() * 0.76f;
        float yPos = position.getY() + 0.65f + direction.getYOffset() * 0.76f;
        final float zPos = position.getZ() + 0.25f + direction.getZOffset() * 0.76f;
        float ySpd = direction.getYOffset() * 0.1f + 0.2f + 0.1f * floatGen.nextFloat();
        float xSpd;
        float zSpd;

        if (direction.getYOffset() == -1) {
            final float temp = (float) (floatGen.nextFloat() * 2.0f * Math.PI);
            xSpd = (float) Math.sin(temp) * 0.1f;
            zSpd = (float) Math.cos(temp) * 0.1f;
            ySpd = -ySpd;
            yPos = yPos - 0.8f;
        } else {
            xSpd = -(direction.getXOffset() * (0.1f + 0.2f * floatGen.nextFloat()));
            zSpd = -(direction.getZOffset() * (0.1f + 0.2f * floatGen.nextFloat()));
        }

        getWorld().spawnParticle(
                particle,
                xPos + ran1 * 0.5f,
                yPos + floatGen.nextFloat() * 0.5f,
                zPos + floatGen.nextFloat() * 0.5f,
                xSpd,
                -ySpd,
                zSpd);
        getWorld().spawnParticle(
                particle,
                (xPos + ran2 * 0.5f),
                (yPos + floatGen.nextFloat() * 0.5f),
                (zPos + floatGen.nextFloat() * 0.5f),
                xSpd,
                -ySpd,
                zSpd);
        getWorld().spawnParticle(
                particle,
                (xPos + ran3 * 0.5f),
                (yPos + floatGen.nextFloat() * 0.5f),
                (zPos + floatGen.nextFloat() * 0.5f),
                xSpd,
                -ySpd,
                zSpd);
    }
}
