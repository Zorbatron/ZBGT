package com.zorbatron.zbgt.common.metatileentities.multi.electric;

import static com.zorbatron.zbgt.api.capability.ZBGTDataCodes.*;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.capability.ICreativePart;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.metatileentities.ZBGTMetaTileEntities;

import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.*;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockBoilerCasing;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

public class MetaTileEntityChemicalPlant extends RecipeMapMultiblockController {

    private int casingTier = -1;
    private int machineCasingTier = -1;
    private int pipeCasingTier = -1;
    private int coilTier = -1;

    private static final List<IBlockState> casings = new ArrayList<>();
    private static final List<IBlockState> pipes = new ArrayList<>();

    static {
        casings.add(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.BRONZE_BRICKS));
        casings.add(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID));
        casings.add(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF));
        casings.add(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN));
        casings.add(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TITANIUM_STABLE));
        casings.add(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST));

        pipes.add(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.BRONZE_PIPE));
        pipes.add(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE));
        pipes.add(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TITANIUM_PIPE));
        pipes.add(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.TUNGSTENSTEEL_PIPE));
    }

    public MetaTileEntityChemicalPlant(ResourceLocation metaTileEntityId) {
        // TODO: recipe map!!1!11!
        super(metaTileEntityId, RecipeMaps.LARGE_CHEMICAL_RECIPES);
        this.recipeMapWorkable = new ChemicalPlantRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityChemicalPlant(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.DOWN)
                .aisle("XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX")
                .aisle("X     X", " MMMMM ", " MHHHM ", " MHHHM ", " MHHHM ", " MMMMM ", "X     X")
                .aisle("X     X", "       ", "  PPP  ", "  PPP  ", "  PPP  ", "       ", "X     X")
                .aisle("X     X", "       ", "  HHH  ", "  HHH  ", "  HHH  ", "       ", "X     X")
                .aisle("X     X", "       ", "  PPP  ", "  PPP  ", "  PPP  ", "       ", "X     X")
                .aisle("X     X", " MMMMM ", " MHHHM ", " MHHHM ", " MHHHM ", " MMMMM ", "X     X")
                .aisle("CCCSCCC", "CMMMMMC", "CMMMMMC", "CMMMMMC", "CMMMMMC", "CMMMMMC", "CCCCCCC")
                .where('S', selfPredicate())
                .where('X', casingPredicate)
                .where('C', casingPredicate
                        .or(autoAbilities())
                        .or(metaTileEntities(ZBGTMetaTileEntities.CATALYST_HATCH).setPreviewCount(1)))
                .where('M', TraceabilityPredicates.machineCasings())
                .where('H', heatingCoils())
                .where('P', pipePredicate)
                .build();
    }

    protected static TraceabilityPredicate casingPredicate = new TraceabilityPredicate(blockWorldState -> {
        IBlockState blockState = blockWorldState.getBlockState();
        if (casings.contains(blockState)) {
            int tier = casings.indexOf(blockState);
            int casing = blockWorldState.getMatchContext().getOrPut("CasingTier", tier);

            if (casing != tier) {
                blockWorldState.setError(
                        new PatternStringError("gregtech.multiblock.pattern.error.chem_plant_casing"));
                return false;
            }

            return true;
        }

        return false;
    }, () -> casings.stream()
            .map(entry -> new BlockInfo(entry, null))
            .toArray(BlockInfo[]::new)).addTooltips("gregtech.multiblock.pattern.error.chem_plant_casing");

    protected static TraceabilityPredicate pipePredicate = new TraceabilityPredicate(blockWorldState -> {
        IBlockState blockState = blockWorldState.getBlockState();
        if (pipes.contains(blockState)) {
            int tier = pipes.indexOf(blockState);
            int casing = blockWorldState.getMatchContext().getOrPut("PipeTier", tier);

            if (casing != tier) {
                blockWorldState.setError(
                        new PatternStringError("gregtech.multiblock.pattern.error.chem_plant_pipe"));
                return false;
            }

            return true;
        }

        return false;
    }, () -> pipes.stream()
            .map(entry -> new BlockInfo(entry, null))
            .toArray(BlockInfo[]::new)).addTooltip("gregtech.multiblock.pattern.error.chem_plant_pipe");

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        Object contextMatch = context.get("CasingTier");
        if (contextMatch instanceof Integer tier) {
            this.casingTier = tier;
        }

        contextMatch = context.get("MachineCasingTier");
        if (contextMatch instanceof BlockMachineCasing.MachineCasingType machineCasingType) {
            this.machineCasingTier = machineCasingType.ordinal();
        }

        contextMatch = context.get("PipeTier");
        if (contextMatch instanceof Integer tier) {
            this.pipeCasingTier = tier;
        }

        contextMatch = context.get("CoilType");
        if (contextMatch instanceof IHeatingCoilBlockStats heatingCoilBlockStats) {
            this.coilTier = heatingCoilBlockStats.getTier();
        }

        for (IMultiblockPart part : getMultiblockParts()) {
            if (part instanceof MetaTileEntityMultiblockPart multiblockPart && !(part instanceof ICreativePart)) {
                if (multiblockPart.getTier() > machineCasingTier) {
                    invalidateStructure();
                    return;
                }
            }
        }

        writeCustomData(MULTIBLOCK_TIER_CHANGE_1, buf -> buf.writeInt(casingTier));
        writeCustomData(MULTIBLOCK_TIER_CHANGE_2, buf -> buf.writeInt(machineCasingTier));
        writeCustomData(MULTIBLOCK_TIER_CHANGE_3, buf -> buf.writeInt(pipeCasingTier));
        writeCustomData(MULTIBLOCK_TIER_CHANGE_4, buf -> buf.writeInt(coilTier));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();

        casingTier = 0;
        machineCasingTier = 0;
        pipeCasingTier = 0;
        coilTier = 0;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);

        if (dataId == MULTIBLOCK_TIER_CHANGE_1) {
            this.casingTier = buf.readInt();
        } else if (dataId == MULTIBLOCK_TIER_CHANGE_2) {
            this.machineCasingTier = buf.readInt();
        } else if (dataId == MULTIBLOCK_TIER_CHANGE_3) {
            this.pipeCasingTier = buf.readInt();
        } else if (dataId == MULTIBLOCK_TIER_CHANGE_4) {
            this.coilTier = buf.readInt();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);

        buf.writeInt(casingTier);
        buf.writeInt(machineCasingTier);
        buf.writeInt(pipeCasingTier);
        buf.writeInt(coilTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);

        this.casingTier = buf.readInt();
        this.machineCasingTier = buf.readInt();
        this.pipeCasingTier = buf.readInt();
        this.coilTier = buf.readInt();
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(recipeMapWorkable.getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addCustom(text -> {
                    text.add(TextComponentUtil.stringWithColor(TextFormatting.GRAY,
                            I18n.format("zbgt.machine.chem_plant.casing",
                                    I18n.format(String.format("zbgt.machine.chem_plant.casing.%d", casingTier)))));
                    text.add(TextComponentUtil.stringWithColor(TextFormatting.GRAY,
                            I18n.format("zbgt.machine.chem_plant.machine_casing",
                                    GTValues.VNF[machineCasingTier])));
                    text.add(TextComponentUtil.stringWithColor(TextFormatting.GRAY,
                            I18n.format("zbgt.machine.chem_plant.pipe",
                                    I18n.format(String.format("zbgt.machine.chem_plant.pipe.%d", pipeCasingTier)))));
                    text.add(TextComponentUtil.stringWithColor(TextFormatting.GRAY,
                            I18n.format("zbgt.machine.chem_plant.coil",
                                    TextFormattingUtil.formatNumbers(coilTier))));
                })
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return switch (casingTier) {
            case (2) -> Textures.SOLID_STEEL_CASING;
            case (3) -> Textures.FROST_PROOF_CASING;
            case (4) -> Textures.CLEAN_STAINLESS_STEEL_CASING;
            case (5) -> Textures.STABLE_TITANIUM_CASING;
            case (6) -> Textures.ROBUST_TUNGSTENSTEEL_CASING;
            default -> Textures.BRONZE_PLATED_BRICKS;
        };
    }

    @SideOnly(Side.CLIENT)
    @Override
    @NotNull
    protected ICubeRenderer getFrontOverlay() {
        return ZBGTTextures.GTPP_MACHINE_OVERLAY;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class ChemicalPlantRecipeLogic extends MultiblockRecipeLogic {

        public ChemicalPlantRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }
    }
}
