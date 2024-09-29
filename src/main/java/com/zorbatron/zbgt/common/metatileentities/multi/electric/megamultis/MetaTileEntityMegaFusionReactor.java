package com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

public class MetaTileEntityMegaFusionReactor extends RecipeMapMultiblockController {

    private EnergyContainerList inputEnergyContainers;
    private final int tier;
    private long heat;

    private long previouslyStoredEnergy;

    public MetaTileEntityMegaFusionReactor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
        this.recipeMapWorkable = new MegaFusionRecipeLogic(this);
        this.recipeMapWorkable.setParallelLimit(64 * (tier - GTValues.IV));
        this.tier = tier;
        this.heat = 0;
        this.previouslyStoredEnergy = 0;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMegaFusionReactor(metaTileEntityId, tier);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)
                .aisle(Layer0)
                .aisle(Layer1)
                .aisle(Layer2)
                .aisle(Layer3)
                .aisle(Layer2)
                .aisle(Layer1)
                .aisle(Layer0)
                .where('S', selfPredicate())
                .where('C', states(getCasingState()))
                .where('H', states(getCoilState()))
                .where('B', states(getGlassState()))
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1)
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1))
                        .or(states(getGlassState())))
                .where('E', metaTileEntities(Arrays
                        .stream(MetaTileEntities.ENERGY_INPUT_HATCH)
                        .filter(mte -> mte != null && tier <= mte.getTier())
                        .toArray(MetaTileEntity[]::new))
                                .setMinGlobalLimited(1).setPreviewCount(32)
                                .or(states(getCasingState())))
                .where('F', frames(getFrameMaterial()))
                .where('M', autoAbilities(true, false)
                        .or(states(getCasingState())))
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState() {
        BlockFusionCasing.CasingType casingType;
        casingType = switch (tier) {
            case (GTValues.ZPM) -> BlockFusionCasing.CasingType.FUSION_CASING_MK2;
            case (GTValues.UV) -> BlockFusionCasing.CasingType.FUSION_CASING_MK3;
            default -> BlockFusionCasing.CasingType.FUSION_CASING;
        };

        return MetaBlocks.FUSION_CASING.getState(casingType);
    }

    protected IBlockState getCoilState() {
        MiscCasing.CasingType casingType = switch (tier) {
            case (GTValues.ZPM) -> MiscCasing.CasingType.COMPACT_FUSION_COIL_1;
            case (GTValues.UV) -> MiscCasing.CasingType.COMPACT_FUSION_COIL_2;
            case (GTValues.UHV) -> MiscCasing.CasingType.COMPACT_FUSION_COIL_3;
            case (GTValues.UEV) -> MiscCasing.CasingType.COMPACT_FUSION_COIL_4;
            default -> MiscCasing.CasingType.AMELIORATED_SUPERCONDUCTOR_COIL;
        };

        return ZBGTMetaBlocks.MISC_CASING.getState(casingType);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    protected Material getFrameMaterial() {
        return switch (tier) {
            case (GTValues.ZPM) -> Materials.Duranium;
            case (GTValues.UV) -> Materials.Neutronium;
            default -> Materials.NaquadahAlloy;
        };
    }

    protected static final String[] Layer0 = {
            "                                               ",
            "                                               ",
            "                    FCCCCCF                    ",
            "                    FCIBICF                    ",
            "                    FCCCCCF                    ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "  FFF                                     FFF  ",
            "  CCC                                     CCC  ",
            "  CIC                                     CIC  ",
            "  CBC                                     CBC  ",
            "  CIC                                     CIC  ",
            "  CCC                                     CCC  ",
            "  FFF                                     FFF  ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                                               ",
            "                    FCCCCCF                    ",
            "                    FCIBICF                    ",
            "                    FCCCCCF                    ",
            "                                               ",
            "                                               " };

    protected static final String[] Layer1 = {
            "                                               ",
            "                    FCBBBCF                    ",
            "                   CC#####CC                   ",
            "                CCCCC#####CCCCC                ",
            "              CCCCCCC#####CCCCCCC              ",
            "            CCCCCCC FCBBBCF CCCCCCC            ",
            "           CCCCC               CCCCC           ",
            "          CCCC                   CCCC          ",
            "         CCC                       CCC         ",
            "        CCC                         CCC        ",
            "       CCC                           CCC       ",
            "      CCC                             CCC      ",
            "     CCC                               CCC     ",
            "     CCC                               CCC     ",
            "    CCC                                 CCC    ",
            "    CCC                                 CCC    ",
            "   CCC                                   CCC   ",
            "   CCC                                   CCC   ",
            "   CCC                                   CCC   ",
            "  CCC                                     CCC  ",
            " FCCCF                                   FCCCF ",
            " C###C                                   C###C ",
            " B###B                                   B###B ",
            " B###B                                   B###B ",
            " B###B                                   B###B ",
            " C###C                                   C###C ",
            " FCCCF                                   FCCCF ",
            "  CCC                                     CCC  ",
            "   CCC                                   CCC   ",
            "   CCC                                   CCC   ",
            "   CCC                                   CCC   ",
            "    CCC                                 CCC    ",
            "    CCC                                 CCC    ",
            "     CCC                               CCC     ",
            "     CCC                               CCC     ",
            "      CCC                             CCC      ",
            "       CCC                           CCC       ",
            "        CCC                         CCC        ",
            "         CCC                       CCC         ",
            "          CCCC                   CCCC          ",
            "           CCCCC               CCCCC           ",
            "            CCCCCCC FCBBBCF CCCCCCC            ",
            "              CCCCCCC#####CCCCCCC              ",
            "                CCCCC#####CCCCC                ",
            "                   CC#####CC                   ",
            "                    FCBBBCF                    ",
            "                                               " };

    protected static final String[] Layer2 = {
            "                    FCCCCCF                    ",
            "                   CC#####CC                   ",
            "                CCCCC#####CCCCC                ",
            "              CCCCCHHHHHHHHHCCCCC              ",
            "            CCCCHHHCC#####CCHHHCCCC            ",
            "           CCCHHCCCCC#####CCCCCHHCCC           ",
            "          ECHHCCCCC FCCCCCF CCCCCHHCE          ",
            "         CCHCCCC               CCCCHCC         ",
            "        CCHCCC                   CCCHCC        ",
            "       CCHCE                       ECHCC       ",
            "      ECHCC                         CCHCE      ",
            "     CCHCE                           ECHCC     ",
            "    CCHCC                             CCHCC    ",
            "    CCHCC                             CCHCC    ",
            "   CCHCC                               CCHCC   ",
            "   CCHCC                               CCHCC   ",
            "  CCHCC                                 CCHCC  ",
            "  CCHCC                                 CCHCC  ",
            "  CCHCC                                 CCHCC  ",
            " CCHCC                                   CCHCC ",
            "FCCHCCF                                 FCCHCCF",
            "C##H##C                                 C##H##C",
            "C##H##C                                 C##H##C",
            "C##H##C                                 C##H##C",
            "C##H##C                                 C##H##C",
            "C##H##C                                 C##H##C",
            "FCCHCCF                                 FCCHCCF",
            " CCHCC                                   CCHCC ",
            "  CCHCC                                 CCHCC  ",
            "  CCHCC                                 CCHCC  ",
            "  CCHCC                                 CCHCC  ",
            "   CCHCC                               CCHCC   ",
            "   CCHCC                               CCHCC   ",
            "    CCHCC                             CCHCC    ",
            "    CCHCC                             CCHCC    ",
            "     CCHCE                           ECHCC     ",
            "      ECHCC                         CCHCE      ",
            "       CCHCE                       ECHCC       ",
            "        CCHCCC                   CCCHCC        ",
            "         CCHCCCC               CCCCHCC         ",
            "          ECHHCCCCC FCCCCCF CCCCCHHCE          ",
            "           CCCHHCCCCC#####CCCCCHHCCC           ",
            "            CCCCHHHCC#####CCHHHCCCC            ",
            "              CCCCCHHHHHHHHHCCCCC              ",
            "                CCCCC#####CCCCC                ",
            "                   CC#####CC                   ",
            "                    FCCMCCF                    ", };

    protected static final String[] Layer3 = {
            "                    FCIBICF                    ",
            "                   CC#####CC                   ",
            "                CCCHHHHHHHHHCCC                ",
            "              CCHHHHHHHHHHHHHHHCC              ",
            "            CCHHHHHHHHHHHHHHHHHHHCC            ",
            "           CHHHHHHHCC#####CCHHHHHHHC           ",
            "          CHHHHHCCC FCIBICF CCCHHHHHC          ",
            "         CHHHHCC               CCHHHHC         ",
            "        CHHHCC                   CCHHHC        ",
            "       CHHHC                       CHHHC       ",
            "      CHHHC                         CHHHC      ",
            "     CHHHC                           CHHHC     ",
            "    CHHHC                             CHHHC    ",
            "    CHHHC                             CHHHC    ",
            "   CHHHC                               CHHHC   ",
            "   CHHHC                               CHHHC   ",
            "  CHHHC                                 CHHHC  ",
            "  CHHHC                                 CHHHC  ",
            "  CHHHC                                 CHHHC  ",
            " CHHHC                                   CHHHC ",
            "FCHHHCF                                 FCHHHCF",
            "C#HHH#C                                 C#HHH#C",
            "I#HHH#I                                 I#HHH#I",
            "B#HHH#B                                 B#HHH#B",
            "I#HHH#I                                 I#HHH#I",
            "C#HHH#C                                 C#HHH#C",
            "FCHHHCF                                 FCHHHCF",
            " CHHHC                                   CHHHC ",
            "  CHHHC                                 CHHHC  ",
            "  CHHHC                                 CHHHC  ",
            "  CHHHC                                 CHHHC  ",
            "   CHHHC                               CHHHC   ",
            "   CHHHC                               CHHHC   ",
            "    CHHHC                             CHHHC    ",
            "    CHHHC                             CHHHC    ",
            "     CHHHC                           CHHHC     ",
            "      CHHHC                         CHHHC      ",
            "       CHHHC                       CHHHC       ",
            "        CHHHCC                   CCHHHC        ",
            "         CHHHHCC               CCHHHHC         ",
            "          CHHHHHCCC FCIBICF CCCHHHHHC          ",
            "           CHHHHHHHCC#####CCHHHHHHHC           ",
            "            CCHHHHHHHHHHHHHHHHHHHCC            ",
            "              CCHHHHHHHHHHHHHHHCC              ",
            "                CCCHHHHHHHHHCCC                ",
            "                   CC#####CC                   ",
            "                    FCISICF                    ", };

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (this.recipeMapWorkable.isActive()) {
            return Textures.ACTIVE_FUSION_TEXTURE;
        } else {
            return Textures.FUSION_TEXTURE;
        }
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 198, 236);
        builder.image(4, 4, 190, 138, GuiTextures.DISPLAY);

        builder.label(9, 9, getMetaFullName(), 0xFFFFFF);
        builder.widget(new AdvancedTextWidget(9, 20, this::addDisplayText, 0xFFFFFF)
                .setMaxWidthLimit(181)
                .setClickHandler(this::handleDisplayClick));

        builder.widget(new ProgressWidget(
                () -> energyContainer.getEnergyCapacity() > 0 ?
                        1.0 * energyContainer.getEnergyStored() / energyContainer.getEnergyCapacity() : 0,
                4, 144, 94, 7,
                GuiTextures.PROGRESS_BAR_FUSION_ENERGY, ProgressWidget.MoveType.HORIZONTAL)
                        .setHoverTextConsumer(this::addEnergyBarHoverText));

        builder.widget(new ProgressWidget(
                () -> energyContainer.getEnergyCapacity() > 0 ? 1.0 * heat / energyContainer.getEnergyCapacity() : 0,
                100, 144, 94, 7,
                GuiTextures.PROGRESS_BAR_FUSION_HEAT, ProgressWidget.MoveType.HORIZONTAL)
                        .setHoverTextConsumer(this::addHeatBarHoverText));

        // Power Button + Detail
        builder.widget(new ImageCycleButtonWidget(173, 211, 18, 18, GuiTextures.BUTTON_POWER,
                recipeMapWorkable::isWorkingEnabled, recipeMapWorkable::setWorkingEnabled));
        builder.widget(new ImageWidget(173, 229, 18, 6, GuiTextures.BUTTON_POWER_DETAIL));

        // Voiding Mode Button
        builder.widget(new ImageCycleButtonWidget(173, 189, 18, 18, GuiTextures.BUTTON_VOID_MULTIBLOCK,
                4, this::getVoidingMode, this::setVoidingMode)
                        .setTooltipHoverString(MultiblockWithDisplayBase::getVoidingModeTooltip));

        // Distinct Buses Unavailable Image
        builder.widget(new ImageWidget(173, 171, 18, 18, GuiTextures.BUTTON_NO_DISTINCT_BUSES)
                .setTooltip("gregtech.multiblock.universal.distinct_not_supported"));

        // Flex Unavailable Image
        builder.widget(getFlexButton(173, 153, 18, 18));

        // Player Inventory
        builder.bindPlayerInventory(entityPlayer.inventory, 153);

        return builder;
    }

    private void addEnergyBarHoverText(List<ITextComponent> hoverList) {
        ITextComponent energyInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                TextFormattingUtil.formatNumbers(energyContainer.getEnergyStored()) + " / " +
                        TextFormattingUtil.formatNumbers(energyContainer.getEnergyCapacity()) + " EU");
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.energy_stored",
                energyInfo));
    }

    private void addHeatBarHoverText(List<ITextComponent> hoverList) {
        ITextComponent heatInfo = TextComponentUtil.stringWithColor(
                TextFormatting.RED,
                TextFormattingUtil.formatNumbers(heat) + " / " +
                        TextFormattingUtil.formatNumbers(energyContainer.getEnergyCapacity()));
        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.fusion_reactor.heat",
                heatInfo));
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        this.energyContainer.changeEnergy(previouslyStoredEnergy);
    }

    @Override
    public void invalidateStructure() {
        this.previouslyStoredEnergy = energyContainer.getEnergyStored();

        super.invalidateStructure();

        this.energyContainer = new EnergyContainerHandler(this, 0, 0, 0, 0, 0) {

            @NotNull
            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };

        this.inputEnergyContainers = new EnergyContainerList(Lists.newArrayList());
        this.heat = 0;
    }

    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));

        List<IEnergyContainer> energyInputs = getAbilities(MultiblockAbility.INPUT_ENERGY);
        this.inputEnergyContainers = new EnergyContainerList(energyInputs);

        long euCapacity = calculateEnergyStorageFactor(energyInputs.size());
        this.energyContainer = new EnergyContainerList(Collections.singletonList(
                new EnergyContainerHandler(this, euCapacity, GTValues.V[tier], 2L * energyInputs.size(), 0, 0) {

                    @NotNull
                    @Override
                    public String getName() {
                        return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
                    }
                }));
    }

    private long calculateEnergyStorageFactor(int energyInputAmount) {
        return (energyInputAmount * (long) Math.pow(2, tier - 6) * 10000000L) / 2;
    }

    @Override
    protected void updateFormedValid() {
        if (this.inputEnergyContainers.getEnergyStored() > 0) {
            long energyAdded = this.energyContainer.addEnergy(this.inputEnergyContainers.getEnergyStored());
            if (energyAdded > 0) this.inputEnergyContainers.removeEnergy(energyAdded);
        }

        super.updateFormedValid();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setLong("StoredEnergy", this.energyContainer.getEnergyStored());

        return super.writeToNBT(data);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        this.previouslyStoredEnergy = data.getLong("StoredEnergy");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);

        tooltip.add(tier < GTValues.UHV ?
                I18n.format("gregtech.machine.fusion_reactor.overclocking") :
                TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc"));

        if (tier == GTValues.LuV) {
            tooltip.add(
                    I18n.format("zbgt.machine.mega_fusion_1.tooltip.1"));
        } else if (tier == GTValues.ZPM) {
            tooltip.addAll(Arrays.asList(
                    I18n.format("zbgt.machine.mega_fusion_2.tooltip.1"),
                    I18n.format("zbgt.machine.mega_fusion_2.tooltip.2")));
        } else if (tier == GTValues.UV) {
            tooltip.addAll(Arrays.asList(
                    I18n.format("zbgt.machine.mega_fusion_3.tooltip.1"),
                    I18n.format("zbgt.machine.mega_fusion_3.tooltip.2"),
                    I18n.format("zbgt.machine.mega_fusion_3.tooltip.3")));
        }
    }

    private class MegaFusionRecipeLogic extends MultiblockRecipeLogic {

        public MegaFusionRecipeLogic(MetaTileEntityMegaFusionReactor tileEntity) {
            super(tileEntity);
        }

        @Override
        protected double getOverclockingDurationDivisor() {
            return tier < GTValues.UHV ? 2.0D : 4.0D;
        }

        @Override
        protected double getOverclockingVoltageMultiplier() {
            return tier < GTValues.UHV ? 2.0D : 4.0D;
        }

        @Override
        public long getMaxVoltage() {
            return Math.min(GTValues.V[tier], super.getMaxVoltage());
        }

        @Override
        public long getMaximumOverclockVoltage() {
            return energyContainer.getInputVoltage();
        }

        @Override
        public void updateWorkable() {
            super.updateWorkable();
            // Drain heat when the reactor is not active, is paused via soft mallet, or does not have enough energy and
            // has fully wiped recipe progress
            // Don't drain heat when there is not enough energy and there is still some recipe progress, as that makes
            // it doubly hard to complete the recipe
            // (Will have to recover heat and recipe progress)
            if (heat > 0) {
                if (!isActive || !workingEnabled || (hasNotEnoughEnergy && progressTime == 0)) {
                    heat = heat <= 10000 ? 0 : (heat - 10000);
                }
            }
        }

        @Override
        public boolean checkRecipe(@NotNull Recipe recipe) {
            if (!super.checkRecipe(recipe)) return false;

            long euToStart = recipe.getProperty(FusionEUToStartProperty.getInstance(), 0L);
            int fusionTier = FusionEUToStartProperty.getFusionTier(euToStart);

            setParallelLimit(64 * (tier + 1 - fusionTier));

            // if the reactor is not able to hold enough energy for it, do not run the recipe
            if (euToStart > energyContainer.getEnergyCapacity()) return false;

            long heatDiff = euToStart - heat;
            // if the stored heat is >= required energy, recipe is okay to run
            if (heatDiff <= 0) return true;

            // if the remaining energy needed is more than stored, do not run
            if (energyContainer.getEnergyStored() < heatDiff) return false;

            // remove the energy needed
            energyContainer.removeEnergy(heatDiff);
            // increase the stored heat
            heat += heatDiff;

            return true;
        }

        @Override
        protected void modifyOverclockPre(int @NotNull [] values, @NotNull IRecipePropertyStorage storage) {
            super.modifyOverclockPre(values, storage);

            // Limit the number of OCs to the difference in fusion reactor MK.
            // I.e., a MK2 reactor can overclock a MK1 recipe once, and a
            // MK3 reactor can overclock a MK2 recipe once, or a MK1 recipe twice.
            long euToStart = storage.getRecipePropertyValue(FusionEUToStartProperty.getInstance(), 0L);
            int fusionTier = FusionEUToStartProperty.getFusionTier(euToStart);
            if (fusionTier != 0) fusionTier = tier - fusionTier;
            values[2] = Math.min(fusionTier, values[2]);
        }

        @NotNull
        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = super.serializeNBT();
            tag.setLong("Heat", heat);
            return tag;
        }

        @Override
        public void deserializeNBT(@NotNull NBTTagCompound compound) {
            super.deserializeNBT(compound);
            heat = compound.getLong("Heat");
        }
    }
}
