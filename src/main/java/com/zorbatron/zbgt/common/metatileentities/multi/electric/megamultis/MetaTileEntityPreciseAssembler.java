package com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis;

import static com.zorbatron.zbgt.api.capability.ZBGTDataCodes.MULTIBLOCK_TIER_CHANGE_1;
import static com.zorbatron.zbgt.api.capability.ZBGTDataCodes.MULTIBLOCK_TIER_CHANGE_2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.metatileentity.LaserCapableMultiMapMultiblockController;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.recipes.ZBGTRecipeMaps;
import com.zorbatron.zbgt.api.recipes.properties.PreciseAssemblerProperty;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.ZBGTMetaTileEntities;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.PreciseCasing;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

public class MetaTileEntityPreciseAssembler extends LaserCapableMultiMapMultiblockController {

    private int preciseCasingTier = -1;
    private int machineCasingTier = -1;

    public MetaTileEntityPreciseAssembler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new gregtech.api.recipes.RecipeMap[] { RecipeMaps.ASSEMBLER_RECIPES,
                ZBGTRecipeMaps.PRECISE_ASSEMBLER_RECIPES });
        this.recipeMapWorkable = new PreciseAssemblerRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPreciseAssembler(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "X#######X", "X#######X", "X#######X", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXXSXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .where('S', selfPredicate())
                .where('H', getMachineHull())
                .where('X', getCasing().or(autoAbilities()))
                .where('F', frames(Materials.TungstenSteel))
                .where('G', states(getGlassState()))
                .where('#', air())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();

        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XXXXXXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "X#######X", "X#######X", "X#######X", "XXXXXXXXX")
                .aisle("XHHHHHHHX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXESMXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .where('S', ZBGTMetaTileEntities.PRASS, EnumFacing.SOUTH)
                .where('G', MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS))
                .where('F', MetaBlocks.FRAMES.get(Materials.TungstenSteel).getBlock(Materials.TungstenSteel))
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV], EnumFacing.SOUTH)
                .where('#', Blocks.AIR.getDefaultState());

        ZBGTAPI.MACHINE_CASINGS.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().ordinal()))
                .forEach(entry -> {
                    IBlockState preciseCasingState = ZBGTMetaBlocks.PRECISE_CASING
                            .getState(PreciseCasing.CasingType.getCasingByTier(entry.getValue().ordinal()));
                    shapeInfo.add(builder
                            .where('H', entry.getKey())
                            .where('X', preciseCasingState)
                            .where('M', TraceabilityPredicates.getMaintenanceHatchMTE(preciseCasingState),
                                    EnumFacing.SOUTH)
                            .build());
                });

        return shapeInfo;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                .addEnergyUsageLine(getEnergyContainer())
                .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                .addParallelsLine(recipeMapWorkable.getParallelLimit())
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        int preciseCasingTier = getPreciseCasingTier();
                        ITextComponent preciseCasingText;

                        if (preciseCasingTier == -1) {
                            preciseCasingText = TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "zbgt.machine.precise_assembler.precise_casing.tier.error");
                        } else {
                            preciseCasingText = TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "zbgt.machine.precise_assembler.precise_casing.tier",
                                    I18n.format(PreciseCasing.CasingType
                                            .getUntranslatedShortNameByTier(preciseCasingTier)));
                        }

                        ITextComponent preciseCasingHoverText = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "zbgt.machine.precise_assembler.precise_casing.tier.hover");

                        tl.add(TextComponentUtil.setHover(preciseCasingText, preciseCasingHoverText));

                        int machineCasingTier = getMachineCasingTier();
                        ITextComponent machineCasingText;

                        if (machineCasingTier == -1) {
                            machineCasingText = TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "zbgt.machine.precise_assembler.machine_casing.tier.error");
                        } else {
                            machineCasingText = TextComponentUtil.translationWithColor(
                                    TextFormatting.GRAY,
                                    "zbgt.machine.precise_assembler.machine_casing.tier",
                                    GTValues.VNF[machineCasingTier]);
                        }

                        ITextComponent machineCasingHoverText = TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "zbgt.machine.precise_assembler.machine_casing.tier.hover");

                        tl.add(TextComponentUtil.setHover(machineCasingText, machineCasingHoverText));
                    }
                })
                .addWorkingStatusLine()
                .addProgressLine(recipeMapWorkable.getProgressPercent());
    }

    protected TraceabilityPredicate getCasing() {
        return TraceabilityPredicates.preciseCasings();
    }

    protected TraceabilityPredicate getMachineHull() {
        return TraceabilityPredicates.machineCasings();
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISPLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return getPreciseCasingTier() == -1 ? ZBGTTextures.PRECISE_CASING_1 :
                ZBGTTextures.getPrassTextureByTier(getPreciseCasingTier());
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        Object preciseType = context.get("PreciseTier");
        if (preciseType instanceof PreciseCasing.CasingType preciseCasingType) {
            this.preciseCasingTier = preciseCasingType.ordinal();
        } else {
            this.preciseCasingTier = -1;
        }

        Object hullType = context.get("MachineCasingTier");
        if (hullType instanceof BlockMachineCasing.MachineCasingType machineCasingType) {
            this.machineCasingTier = machineCasingType.ordinal();
        }

        writeCustomData(MULTIBLOCK_TIER_CHANGE_1, buf -> buf.writeInt(this.preciseCasingTier));
        writeCustomData(MULTIBLOCK_TIER_CHANGE_2, buf -> buf.writeInt(this.machineCasingTier));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.preciseCasingTier = -1;
        this.machineCasingTier = -1;
    }

    public int getPreciseCasingTier() {
        return this.preciseCasingTier;
    }

    public int getMachineCasingTier() {
        return this.machineCasingTier;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == MULTIBLOCK_TIER_CHANGE_1) {
            this.preciseCasingTier = buf.readInt();
        } else if (dataId == MULTIBLOCK_TIER_CHANGE_2) {
            this.machineCasingTier = buf.readInt();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.preciseCasingTier);
        buf.writeInt(this.machineCasingTier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.preciseCasingTier = buf.readInt();
        this.machineCasingTier = buf.readInt();
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        int supposedRecipeMap = recipe.getRecipeCategory().getRecipeMap() == RecipeMaps.ASSEMBLER_RECIPES ? 0 : 1;
        return getRecipeMapIndex() == supposedRecipeMap;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(I18n.format("zbgt.machine.precise_assembler.tooltip.1"));
        tooltip.add(I18n.format("zbgt.machine.precise_assembler.tooltip.2"));
        tooltip.add(I18n.format("zbgt.machine.precise_assembler.tooltip.3"));
        tooltip.add(I18n.format("zbgt.machine.precise_assembler.tooltip.4"));
        tooltip.add(I18n.format("zbgt.machine.precise_assembler.tooltip.5"));
    }

    protected class PreciseAssemblerRecipeLogic extends MultiblockRecipeLogic {

        public PreciseAssemblerRecipeLogic(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public long getMaxVoltage() {
            int casingTier = getMachineCasingTier() == -1 ? GTValues.ULV : getMachineCasingTier();
            long maxVoltage = super.getMaxVoltage();

            return casingTier >= GTValues.UHV ? maxVoltage : Math.min(maxVoltage, GTValues.V[casingTier]);
        }

        @Override
        protected void modifyOverclockPre(int @NotNull [] values, @NotNull IRecipePropertyStorage storage) {
            if (getRecipeMapIndex() == 0) {
                values[1] = (int) (values[1] * 0.5);
            }

            super.modifyOverclockPre(values, storage);
        }

        @Override
        public int getParallelLimit() {
            return (int) (16 * Math.pow(2, getPreciseCasingTier()));
        }

        @Override
        public boolean checkRecipe(@NotNull Recipe recipe) {
            return getRecipeMapIndex() == 0 ? super.checkRecipe(recipe) :
                    super.checkRecipe(recipe) &&
                            recipe.getProperty(PreciseAssemblerProperty.getInstance(), 0) <= getPreciseCasingTier();
        }
    }
}
