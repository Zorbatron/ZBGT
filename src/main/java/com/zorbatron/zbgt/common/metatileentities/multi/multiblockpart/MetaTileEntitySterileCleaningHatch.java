package com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart;

import static gregtech.api.metatileentity.multiblock.CleanroomType.CLEANROOM;
import static gregtech.api.metatileentity.multiblock.CleanroomType.STERILE_CLEANROOM;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityAutoMaintenanceHatch;

public class MetaTileEntitySterileCleaningHatch extends MetaTileEntityAutoMaintenanceHatch {

    private static ICleanroomProvider DUMMY_CLEANROOM;

    public MetaTileEntitySterileCleaningHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);

        DUMMY_CLEANROOM = DummyCleanroom.createForAllTypes();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitySterileCleaningHatch(metaTileEntityId);
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);
        if (controllerBase instanceof ICleanroomReceiver &&
                ((ICleanroomReceiver) controllerBase).getCleanroom() == null) {
            ((ICleanroomReceiver) controllerBase).setCleanroom(DUMMY_CLEANROOM);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.maintenance_hatch_cleanroom_auto.tooltip.1"));
        tooltip.add(I18n.format("gregtech.machine.maintenance_hatch.cleanroom_auto.tooltip.2"));
        tooltip.add(String.format("  %s%s", TextFormatting.GREEN, I18n.format(CLEANROOM.getTranslationKey())));
        tooltip.add(String.format("  %s%s", TextFormatting.GREEN, I18n.format(STERILE_CLEANROOM.getTranslationKey())));
    }
}
