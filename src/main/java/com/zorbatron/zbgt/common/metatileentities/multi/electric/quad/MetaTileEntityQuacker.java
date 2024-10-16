package com.zorbatron.zbgt.common.metatileentities.multi.electric.quad;

import static com.zorbatron.zbgt.api.pattern.TraceabilityPredicates.autoEnergyInputs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.common.ZBGTConfig;
import com.zorbatron.zbgt.core.sound.ZBGTSoundEvents;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityCrackingUnit;

public class MetaTileEntityQuacker extends MetaTileEntityCrackingUnit {

    private int quackTimer;

    public MetaTileEntityQuacker(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.recipeMapWorkable.setParallelLimit(4);
        this.quackTimer = calculateQuackInterval();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityQuacker(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();

        if (ZBGTConfig.multiblockSettings.quackerQuacks && isActive()) {
            if (quackTimer == 0) {
                playQuack();
                // Only quack every 1 to 5 minutes.
                quackTimer = calculateQuackInterval();
            } else if (quackTimer > 0) {
                quackTimer--;
            }
        }
    }

    private int calculateQuackInterval() {
        return 20 * 60 + GTValues.RNG.nextInt(20 * 60 * 4);
    }

    @SideOnly(Side.CLIENT)
    private void playQuack() {
        int randomInt = GTValues.RNG.nextInt(5);
        ResourceLocation soundLocation = ZBGTSoundEvents.QUACKS.get(randomInt).getSoundName();
        BlockPos pos = getPos();

        // I'm not using GregTechAPI.soundManager.startTileSound because it checks if a sound is playing at the
        // position, which there will be because of the cracker's normal active sound effect.
        ISound sound = new PositionedSoundRecord(soundLocation, SoundCategory.BLOCKS, getVolume(), 1.0F,
                false, 0, ISound.AttenuationType.LINEAR, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
        Minecraft.getMinecraft().getSoundHandler().playSound(sound);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XCXCX", "XCXCX", "XCXCX", "XCXCX", "XCXCX")
                .aisle("XCXCX", "X###X", "XCTCX", "X###X", "XCXCX")
                .aisle("XCXCX", "XCTCX", "XCTCX", "XCTCX", "XCXCX")
                .aisle("XCXCX", "X###X", "XCTCX", "X###X", "XCXCX")
                .aisle("XCXCX", "XCXCX", "XCSCX", "XCXCX", "XCXCX")
                .where('S', selfPredicate())
                .where('C', heatingCoils())
                .where('X', states(getCasingState()).setMinGlobalLimited(25)
                        .or(autoAbilities(false, true, true, true, true, true, false))
                        .or(autoEnergyInputs(1, 8)))
                .where('T', states(getCasingState()))
                .where('#', air())
                .build();
    }
}
