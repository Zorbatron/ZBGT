package com.zorbatron.zbgt.common.metatileentities.multi.electric.mega;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.capability.impl.ZBGT_GCYMMultiblockRecipeLogic;
import com.zorbatron.zbgt.api.metatileentity.LaserCapableGCYMRecipeMapMultiblockController;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.render.ZBGTTextures;

import gregicality.multiblocks.api.metatileentity.GCYMMultiblockAbility;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.*;

public class MetaTileEntityMegaLCR extends LaserCapableGCYMRecipeMapMultiblockController {

    public MetaTileEntityMegaLCR(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.LARGE_CHEMICAL_RECIPES);
        this.recipeMapWorkable = new ZBGT_GCYMMultiblockRecipeLogic(this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMegaLCR(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXX", "XEEEX", "XEEEX", "XEEEX", "XXXXX")
                .aisle("HPXPH", "#GGG#", "#GFG#", "#GGG#", "HPXPH")
                .aisle("HPXPH", "#GGG#", "#GFG#", "#GGG#", "HPXPH")
                .aisle("HPXPH", "#GGG#", "#GFG#", "#GGG#", "HPXPH")
                .aisle("HPXPH", "#GGG#", "#GFG#", "#GGG#", "HPXPH")
                .aisle("HPXPH", "#GGG#", "#GFG#", "#GGG#", "HPXPH")
                .aisle("HPXPH", "#GGG#", "#GFG#", "#GGG#", "HPXPH")
                .aisle("HPXPH", "#GGG#", "#GFG#", "#GGG#", "HPXPH")
                .aisle("XXXXX", "XGGGX", "XGSGX", "XGGGX", "XXXXX")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()))
                .where('P', states(getPipeCasingState()))
                .where('#', air())
                .where('G', states(getGlassState()))
                .where('F', states(getCoilState()))
                .where('E', states(getCasingState())
                        .or(autoEnergyInputsMega())
                        .or(abilities(GCYMMultiblockAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                        .or(maintenancePredicate()))
                .where('H', states(getCasingState())
                        .or(TraceabilityPredicates.autoBusesAndHatches(getRecipeMap())))
                .build();
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING);
    }

    protected IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.POLYTETRAFLUOROETHYLENE_PIPE);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    protected IBlockState getCoilState() {
        return MetaBlocks.FUSION_CASING.getState(BlockFusionCasing.CasingType.FUSION_COIL);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.INERT_PTFE_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc"));
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return ZBGTTextures.GTPP_MACHINE_OVERLAY;
    }
}
