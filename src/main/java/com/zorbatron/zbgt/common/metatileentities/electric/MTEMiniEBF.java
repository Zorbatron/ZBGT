package com.zorbatron.zbgt.common.metatileentities.electric;

import java.util.function.Supplier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockWireCoil;

public class MTEMiniEBF extends SimpleMachineMetaTileEntity {

    private final GTItemStackHandler coilSlot;

    public MTEMiniEBF(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, RecipeMaps.BLAST_RECIPES, Textures.MULTIBLOCK_WORKABLE_OVERLAY, tier, true);
        coilSlot = new GTItemStackHandler(this, 1);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTEMiniEBF(metaTileEntityId, getTier());
    }

    @Override
    protected AbstractRecipeLogic createWorkable(RecipeMap<?> recipeMap) {
        return new MiniEBFLogic(this, recipeMap, () -> energyContainer);
    }

    @Override
    public void update() {
        super.update();

        if (getWorld().isRemote && isActive()) {
            makeSmoke();
        }
    }

    @Override
    protected ModularUI.Builder createGuiTemplate(EntityPlayer player) {
        ModularUI.Builder builder = super.createGuiTemplate(player);

        builder.slot(coilSlot, 0, 79, 62 - 16 - 2, GuiTextures.SLOT);

        return builder;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.MUFFLER_OVERLAY.renderSided(EnumFacing.UP, renderState, translation, pipeline);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        GTUtility.writeItems(coilSlot, "coilSlot", data);

        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        GTUtility.readItems(coilSlot, "coilSlot", data);
    }

    @SideOnly(Side.CLIENT)
    private void makeSmoke() {
        if (getWorld() == null || getPos() == null) return;

        BlockPos pos = getPos();
        float xPos = EnumFacing.UP.getXOffset() * 0.76F + pos.getX() + 0.25F;
        float yPos = EnumFacing.UP.getYOffset() * 0.76F + pos.getY() + 0.25F;
        float zPos = EnumFacing.UP.getZOffset() * 0.76F + pos.getZ() + 0.25F;

        float ySpd = EnumFacing.UP.getYOffset() * 0.1F + 0.2F + 0.1F * GTValues.RNG.nextFloat();
        float xSpd = EnumFacing.UP.getXOffset() * (0.1F + 0.2F * GTValues.RNG.nextFloat());
        float zSpd = EnumFacing.UP.getZOffset() * (0.1F + 0.2F * GTValues.RNG.nextFloat());

        xPos += GTValues.RNG.nextFloat() * 0.5F;
        yPos += GTValues.RNG.nextFloat() * 0.5F;
        zPos += GTValues.RNG.nextFloat() * 0.5F;

        getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, xPos, yPos, zPos, xSpd, ySpd, zSpd);
    }

    private class MiniEBFLogic extends RecipeLogicEnergy {

        public MiniEBFLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap,
                            Supplier<IEnergyContainer> energyContainer) {
            super(tileEntity, recipeMap, energyContainer);
        }

        @Override
        public boolean checkRecipe(@NotNull Recipe recipe) {
            int temperature = 0;

            ItemStack stack = coilSlot.getStackInSlot(0);
            if (stack.getItem() instanceof ItemBlock block) {
                if (block.getBlock() instanceof BlockWireCoil wireCoil) {
                    temperature = wireCoil.getState(stack).getCoilTemperature();
                }
            }

            return super.checkRecipe(recipe) && temperature > recipe.getProperty(TemperatureProperty.getInstance(), 0);
        }
    }
}
