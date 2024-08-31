package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.IItemHandlerModifiable;

import com.zorbatron.zbgt.client.ClientHandler;
import com.zorbatron.zbgt.client.widgets.ItemSlotTinyAmountTextWidget;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IDataStickIntractable;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.Position;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;

public class MetaTileEntityBudgetCRIBProxy extends MetaTileEntityMultiblockNotifiablePart
                                           implements IMultiblockAbilityPart<IItemHandlerModifiable>,
                                           IDataStickIntractable {

    MetaTileEntityBudgetCRIB main;
    int mainX, mainY, mainZ;

    public MetaTileEntityBudgetCRIBProxy(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier, false);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityBudgetCRIBProxy(metaTileEntityId, getTier());
    }

    @Override
    public IItemHandlerModifiable getImportItems() {
        if (main == null) return new GTItemStackHandler(this, 0);

        return main.getPatternItems();
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return MultiblockAbility.IMPORT_ITEMS;
    }

    @Override
    public void registerAbilities(List<IItemHandlerModifiable> abilityList) {
        if (main == null) return;

        abilityList.add(main.getPatternItems());
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        if (main == null) return null;

        IItemHandlerModifiable patternItems = main.getPatternItems();

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 166 + 18 * 2)
                .label(7, 7, getMetaFullName());

        // Item slots
        WidgetGroup slots = new WidgetGroup(new Position((int) (7 + 18 * 2.5), 20));
        for (int y = 0; y <= 3; y++) {
            for (int x = 0; x <= 3; x++) {
                int index = y * 4 + x;
                slots.addWidget(new ItemSlotTinyAmountTextWidget(patternItems, index, 18 * x, 18 * y,
                        false, false) {

                    @Override
                    public void drawInForeground(int mouseX, int mouseY) {
                        ItemStack item = patternItems.getStackInSlot(index);

                        if (isMouseOverElement(mouseX, mouseY) && !item.isEmpty()) {
                            List<String> tooltip = getItemToolTip(item);

                            tooltip.add(TextFormatting.GRAY + I18n.format("zbgt.machine.budget_crib.amount_tooltip",
                                    TextFormattingUtil.formatNumbers(item.getCount())));

                            drawHoveringText(item, tooltip, -1, mouseX, mouseY);
                        }
                    }
                }.setBackgroundTexture(GuiTextures.SLOT));
            }
        }

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * 5 + 12);
        return builder.widget(slots).build(getHolder(), entityPlayer);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        ClientHandler.CRIB_PROXY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public void onDataStickLeftClick(EntityPlayer player, ItemStack dataStick) {}

    @Override
    public boolean onDataStickRightClick(EntityPlayer player, ItemStack dataStick) {
        NBTTagCompound tag = dataStick.getTagCompound();
        if (tag == null || !tag.hasKey("BudgetCRIB")) return false;

        readLocationFromTag(tag.getCompoundTag("BudgetCRIB"));
        player.sendStatusMessage(new TextComponentTranslation("zbgt.machine.budget_crib_proxy.data_stick_use",
                TextFormattingUtil.formatNumbers(this.mainX),
                TextFormattingUtil.formatNumbers(this.mainY),
                TextFormattingUtil.formatNumbers(this.mainZ)), true);

        return true;
    }

    private void readLocationFromTag(NBTTagCompound tag) {
        this.mainX = tag.getInteger("MainX");
        this.mainY = tag.getInteger("MainY");
        this.mainZ = tag.getInteger("MainZ");

        trySetMain();
    }

    private void trySetMain() {
        if (getWorld() == null) return;

        TileEntity tileEntity = getWorld().getTileEntity(new BlockPos(this.mainX, this.mainY, this.mainZ));
        if (!(tileEntity instanceof IGregTechTileEntity iGregTechTileEntity)) return;

        MetaTileEntity metaTileEntity = iGregTechTileEntity.getMetaTileEntity();
        if (!(metaTileEntity instanceof MetaTileEntityBudgetCRIB budgetCRIB)) return;

        this.main = budgetCRIB;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        this.mainX = data.getInteger("MainX");
        this.mainY = data.getInteger("MainY");
        this.mainZ = data.getInteger("MainZ");

        trySetMain();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("MainX", this.mainX);
        data.setInteger("MainY", this.mainY);
        data.setInteger("MainZ", this.mainZ);

        return super.writeToNBT(data);
    }
}
