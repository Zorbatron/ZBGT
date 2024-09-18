package com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.metatileentity.LaserCapableRecipeMapMultiblockController;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;

public class MetaTileEntityMegaFusionReactor extends LaserCapableRecipeMapMultiblockController {

    private final int tier;

    public MetaTileEntityMegaFusionReactor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
        this.recipeMapWorkable = new FusionRecipeLogic(this);
        this.tier = tier;
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
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.EXPORT_FLUIDS))
                .where('E', autoEnergyInputs(1, 32, 32)
                        .or(states(getCasingState())))
                .where('F', frames(getFrameMaterial()))
                .build();
    }

    protected IBlockState getCasingState() {
        BlockFusionCasing.CasingType casingType;
        casingType = switch (tier) {
            case (2) -> BlockFusionCasing.CasingType.FUSION_CASING_MK2;
            case (3) -> BlockFusionCasing.CasingType.FUSION_CASING_MK3;
            default -> BlockFusionCasing.CasingType.FUSION_CASING;
        };

        return MetaBlocks.FUSION_CASING.getState(casingType);
    }

    protected IBlockState getCoilState() {
        BlockFusionCasing.CasingType casingType;
        if (tier == 1) {
            casingType = BlockFusionCasing.CasingType.SUPERCONDUCTOR_COIL;
        } else {
            casingType = BlockFusionCasing.CasingType.FUSION_COIL;
        }

        return MetaBlocks.FUSION_CASING.getState(casingType);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    protected Material getFrameMaterial() {
        return switch (tier) {
            case (2) -> Materials.Duranium;
            case (3) -> Materials.Neutronium;
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
            "                   CC     CC                   ",
            "                CCCCC     CCCCC                ",
            "              CCCCCCC     CCCCCCC              ",
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
            " C   C                                   C   C ",
            " B   B                                   B   B ",
            " B   B                                   B   B ",
            " B   B                                   B   B ",
            " C   C                                   C   C ",
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
            "              CCCCCCC     CCCCCCC              ",
            "                CCCCC     CCCCC                ",
            "                   CC     CC                   ",
            "                    FCBBBCF                    ",
            "                                               " };

    protected static final String[] Layer2 = {
            "                    FCCCCCF                    ",
            "                   CC     CC                   ",
            "                CCCCC     CCCCC                ",
            "              CCCCCHHHHHHHHHCCCCC              ",
            "            CCCCHHHCC     CCHHHCCCC            ",
            "           CCCHHCCCCC     CCCCCHHCCC           ",
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
            "C  H  C                                 C  H  C",
            "C  H  C                                 C  H  C",
            "C  H  C                                 C  H  C",
            "C  H  C                                 C  H  C",
            "C  H  C                                 C  H  C",
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
            "           CCCHHCCCCC     CCCCCHHCCC           ",
            "            CCCCHHHCC     CCHHHCCCC            ",
            "              CCCCCHHHHHHHHHCCCCC              ",
            "                CCCCC     CCCCC                ",
            "                   CC     CC                   ",
            "                    FCCCCCF                    ", };

    protected static final String[] Layer3 = {
            "                    FCIBICF                    ",
            "                   CC     CC                   ",
            "                CCCHHHHHHHHHCCC                ",
            "              CCHHHHHHHHHHHHHHHCC              ",
            "            CCHHHHHHHHHHHHHHHHHHHCC            ",
            "           CHHHHHHHCC     CCHHHHHHHC           ",
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
            "C HHH C                                 C HHH C",
            "I HHH I                                 I HHH I",
            "B HHH B                                 B HHH B",
            "I HHH I                                 I HHH I",
            "C HHH C                                 C HHH C",
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
            "           CHHHHHHHCC     CCHHHHHHHC           ",
            "            CCHHHHHHHHHHHHHHHHHHHCC            ",
            "              CCHHHHHHHHHHHHHHHCC              ",
            "                CCCHHHHHHHHHCCC                ",
            "                   CC     CC                   ",
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
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    private class FusionRecipeLogic extends MultiblockRecipeLogic {

        public FusionRecipeLogic(MetaTileEntityMegaFusionReactor tileEntity) {
            super(tileEntity);
        }

        @Override
        protected double getOverclockingDurationDivisor() {
            return 2.0D;
        }

        @Override
        protected double getOverclockingVoltageMultiplier() {
            return 2.0D;
        }

        @Override
        public long getMaxVoltage() {
            return Math.min(GTValues.V[tier + 5], super.getMaxVoltage());
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
    }
}
