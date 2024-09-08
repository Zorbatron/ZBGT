package com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.block.IPreciseTier;
import com.zorbatron.zbgt.api.capability.ZBGTDataCodes;
import com.zorbatron.zbgt.api.metatileentity.LaserCapableMultiShapeGCYMMultiblockController;
import com.zorbatron.zbgt.api.pattern.TraceabilityPredicates;
import com.zorbatron.zbgt.api.recipes.ITier;
import com.zorbatron.zbgt.api.render.ZBGTTextures;

import gregicality.multiblocks.api.capability.impl.GCYMMultiblockRecipeLogic;
import gregtech.api.capability.IDataAccessHatch;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;

public class MetaTileEntityPreciseAssembler extends LaserCapableMultiShapeGCYMMultiblockController implements ITier {

    private int tier;

    public MetaTileEntityPreciseAssembler(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new gregtech.api.recipes.RecipeMap[] { RecipeMaps.ASSEMBLER_RECIPES,
                RecipeMaps.ASSEMBLY_LINE_RECIPES,
                RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES });
        this.recipeMapWorkable = new GCYMMultiblockRecipeLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPreciseAssembler(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern getStructurePattern(int index) {
        return FactoryBlockPattern.start()
                .aisle("XXXXXXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "X#######X", "X#######X", "X#######X", "XXXXXXXXX")
                .aisle("XXXXXXXXX", "XGGGGGGGX", "XGGGGGGGX", "XGGGGGGGX", "XXXXXXXXX")
                .aisle("XXXXSXXXX", "F#######F", "F#######F", "F#######F", "XXXXXXXXX")
                .where('S', selfPredicate())
                .where('X', getCasingState()
                        .or(autoAbilities())
                        .or(dataHatchPredicate(index == 1)))
                // .where('H', metaTileEntities(MetaTileEntities.HULL))
                .where('F', frames(Materials.TungstenSteel))
                .where('G', states(getGlassState()))
                .where('#', air())
                .build();
    }

    protected TraceabilityPredicate getCasingState() {
        return TraceabilityPredicates.preciseCasings();
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    protected TraceabilityPredicate dataHatchPredicate(boolean isAssemblyLine) {
        // If set to assembly line, require a data hatch
        if (isAssemblyLine) {
            return abilities(MultiblockAbility.DATA_ACCESS_HATCH, MultiblockAbility.OPTICAL_DATA_RECEPTION)
                    .setExactLimit(1);
        }

        return getCasingState();
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        if (getRecipeMapIndex() == 1) {
            return isRecipeAvailable(getAbilities(MultiblockAbility.DATA_ACCESS_HATCH), recipe) ||
                    isRecipeAvailable(getAbilities(MultiblockAbility.OPTICAL_DATA_RECEPTION), recipe);
        } else {
            return true;
        }
    }

    private static boolean isRecipeAvailable(@NotNull Iterable<? extends IDataAccessHatch> hatches,
                                             @NotNull Recipe recipe) {
        for (IDataAccessHatch hatch : hatches) {
            // creative hatches do not need to check, they always have the recipe
            if (hatch.isCreative()) return true;

            // hatches need to have the recipe available
            if (hatch.isRecipeAvailable(recipe)) return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISPLAY;
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return switch (getTier()) {
            case (2) -> ZBGTTextures.PRECISE_CASING_2;
            case (3) -> ZBGTTextures.PRECISE_CASING_3;
            default -> ZBGTTextures.PRECISE_CASING_1;
        };
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object type = context.get("PreciseTier");
        if (type instanceof IPreciseTier preciseTier) {
            this.tier = preciseTier.getTier() + 1;
        } else {
            this.tier = 0;
        }

        writeCustomData(ZBGTDataCodes.MULTIBLOCK_TIER_CHANGE, packetBuffer -> packetBuffer.writeInt(this.tier));
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.tier = 0;
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == ZBGTDataCodes.MULTIBLOCK_TIER_CHANGE) {
            this.tier = buf.readInt();
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.tier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.tier = buf.readInt();
    }
}
