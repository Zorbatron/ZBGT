package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;

import gregicality.multiblocks.api.metatileentity.GCYMRecipeMapMultiblockController;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.GhostCircuitSlotWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;

public class MetaTileEntityFishingPort extends GCYMRecipeMapMultiblockController {

    private GhostCircuitItemStackHandler circuitSlot;

    public MetaTileEntityFishingPort(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, ZBGTRecipeMaps.FISHING_PORT_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityFishingPort(metaTileEntityId);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();

        circuitSlot = new GhostCircuitItemStackHandler(this);
        circuitSlot.addNotifiableMetaTileEntity(this);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();

        this.inputInventory = circuitSlot;
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXXXX", "XXXXXXXXX", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "XWWWWWWWX", "XWWWWWWWX").setRepeatable(7, 7)
                .aisle("XXXXXXXXX", "XXXXSXXXX", "XXXXXXXXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()).setMinGlobalLimited(64)
                        .or(autoAbilities(true, true, false, true, false, false, false)))
                .where('W', any())
                .build();
    }

    protected IBlockState getCasingState() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.AQUATIC_CASING);
    }

    @Override
    protected @NotNull Widget getFlexButton(int x, int y, int width, int height) {
        return new GhostCircuitSlotWidget(circuitSlot, 0, x, y)
                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.INT_CIRCUIT_OVERLAY)
                .setConsumer(slotWidget -> slotWidget.setTooltipText("gregtech.gui.configurator_slot.tooltip",
                        circuitSlot.getCircuitValue() == GhostCircuitItemStackHandler.NO_CONFIG ?
                                new TextComponentTranslation("gregtech.gui.configurator_slot.no_value")
                                        .getFormattedText() :
                                String.valueOf(circuitSlot.getCircuitValue())));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.AQUATIC_CASING;
    }

    @Override
    public boolean allowsExtendedFacing() {
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        circuitSlot.write(data);

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        circuitSlot.read(data);
    }
}
