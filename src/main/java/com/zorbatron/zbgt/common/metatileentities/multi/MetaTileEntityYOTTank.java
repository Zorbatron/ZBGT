package com.zorbatron.zbgt.common.metatileentities.multi;

import static gregtech.api.capability.GregtechDataCodes.UPDATE_ACTIVE;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.render.ZBGTTextures;
import com.zorbatron.zbgt.common.block.ZBGTMetaBlocks;
import com.zorbatron.zbgt.common.block.blocks.MiscCasing;
import com.zorbatron.zbgt.common.block.blocks.YOTTankCell;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;

public class MetaTileEntityYOTTank extends MultiblockWithDisplayBase implements IControllable {

    private boolean isWorkingEnabled;

    private BigInteger storage = BigInteger.ZERO;
    private BigInteger storageCurrent = BigInteger.ZERO;
    private FluidStack fluid;
    private FluidStack lockedFluid;

    private static final String YOTTANK_CELL_HEADER = "YOTTANK_CELL_";

    public MetaTileEntityYOTTank(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.isWorkingEnabled = true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityYOTTank(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {}

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);

        List<YOTTankCell.CasingType> cells = new ArrayList<>();
        for (Map.Entry<String, Object> cell : context.entrySet()) {
            if (cell.getKey().startsWith(YOTTANK_CELL_HEADER) && cell.getValue() instanceof CellMatchWrapper wrapper) {
                for (int i = 0; i < wrapper.amount; i++) {
                    cells.add(wrapper.casingType);
                }
            }
        }
    }

    @Override
    @NotNull
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.UP)
                .aisle("#####", "#OOO#", "#OOO#", "#OOO#", "#####")
                .aisle("MMSMM", "MXXXM", "MXXXM", "MXXXM", "MMMMM")
                .aisle("GGGGG", "GCCCG", "GCCCG", "GCCCG", "GGGGG").setRepeatable(1, 15)
                .aisle("XXXXX", "XIIIX", "XIIIX", "XIIIX", "XXXXX")
                .aisle("FFFFF", "F###F", "F###F", "F###F", "FFFFF")
                .where('S', selfPredicate())
                .where('X', states(getCasingState()))
                .where('M', autoAbilities(true, false)
                        .or(states(getCasingState())))
                .where('C', CELL_PREDICATE.get())
                .where('O', abilities(MultiblockAbility.EXPORT_FLUIDS).setPreviewCount(1).setMinGlobalLimited(1)
                        .or(states(getCasingState())))
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS).setPreviewCount(1).setMinGlobalLimited(1)
                        .or(states(getCasingState())))
                .where('F', frames(Materials.Steel))
                .where('G', states(getGlassState()))
                .where('#', air())
                .build();
    }

    protected IBlockState getCasingState() {
        return ZBGTMetaBlocks.MISC_CASING.getState(MiscCasing.CasingType.YOTTANK_CASING);
    }

    protected IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.TEMPERED_GLASS);
    }

    protected static final Supplier<TraceabilityPredicate> CELL_PREDICATE = () -> new TraceabilityPredicate(
            blockWorldState -> {
                IBlockState blockState = blockWorldState.getBlockState();
                if (ZBGTAPI.YOTTANK_CELLS.containsKey(blockState)) {
                    YOTTankCell.CasingType casing = ZBGTAPI.YOTTANK_CELLS.get(blockState);

                    String key = YOTTANK_CELL_HEADER + casing.getCellName();
                    CellMatchWrapper wrapper = blockWorldState.getMatchContext().get(key);
                    if (wrapper == null) wrapper = new CellMatchWrapper(casing);
                    blockWorldState.getMatchContext().set(key, wrapper.increment());

                    return true;
                }

                return false;
            }, () -> ZBGTAPI.YOTTANK_CELLS.entrySet().stream()
                    .sorted(Comparator.comparingInt(entry -> entry.getValue().ordinal()))
                    .map(entry -> new BlockInfo(entry.getKey(), null))
                    .toArray(BlockInfo[]::new))
                            .addTooltips("zbgt.multiblock.pattern.error.yottank_cells");

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return ZBGTTextures.YOTTANK_CASING;
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.DISPLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == UPDATE_ACTIVE) {
            this.isWorkingEnabled = buf.readBoolean();
        }
    }

    @Override
    public boolean isWorkingEnabled() {
        return this.isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        this.isWorkingEnabled = isWorkingAllowed;
        if (!getWorld().isRemote) {
            writeCustomData(GregtechDataCodes.UPDATE_ACTIVE, buf -> buf.writeBoolean(isWorkingAllowed));
        }
    }

    private static class CellMatchWrapper {

        private final YOTTankCell.CasingType casingType;
        private int amount;

        public CellMatchWrapper(YOTTankCell.CasingType casingType) {
            this.casingType = casingType;
        }

        public CellMatchWrapper increment() {
            this.amount++;
            return this;
        }
    }
}
