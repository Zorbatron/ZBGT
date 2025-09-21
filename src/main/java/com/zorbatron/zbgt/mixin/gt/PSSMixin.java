package com.zorbatron.zbgt.mixin.gt;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.zorbatron.zbgt.api.metatileentity.ZBGTMultiblockAbilities;
import com.zorbatron.zbgt.common.ZBGTConfig;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.util.TextFormattingUtil;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityPowerSubstation;

@Mixin(value = MetaTileEntityPowerSubstation.class, remap = false)
public abstract class PSSMixin extends MultiblockControllerBase {

    private PSSMixin(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Redirect(method = "createStructurePattern",
              at = @At(value = "INVOKE",
                       target = "Lgregtech/api/pattern/FactoryBlockPattern;setRepeatable(II)Lgregtech/api/pattern/FactoryBlockPattern;"))
    private FactoryBlockPattern changeRepeatAmount(FactoryBlockPattern instance, int minRepeat, int maxRepeat) {
        return instance.setRepeatable(minRepeat, ZBGTConfig.multiblockSettings.overridePSSHeight ?
                ZBGTConfig.multiblockSettings.overriddenPSSHeight : maxRepeat);
    }

    @SideOnly(Side.CLIENT)
    @WrapOperation(method = "addInformation",
                   at = @At(
                            value = "INVOKE",
                            target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                            ordinal = 2))
    private boolean addTooltip(List<Object> instance, Object e, Operation<Boolean> original) {
        if (ZBGTConfig.multiblockSettings.overridePSSHeight) {
            return instance.add(I18n.format("gregtech.machine.power_substation.tooltip3",
                    TextFormattingUtil.formatNumbers(ZBGTConfig.multiblockSettings.overriddenPSSHeight)));
        } else {
            return original.call(instance, e);
        }
    }

    @ModifyArg(method = "createStructurePattern",
               at = @At(value = "INVOKE",
                        target = "Lgregtech/common/metatileentities/multi/electric/MetaTileEntityPowerSubstation;abilities([Lgregtech/api/metatileentity/multiblock/MultiblockAbility;)Lgregtech/api/pattern/TraceabilityPredicate;",
                        ordinal = 0))
    private MultiblockAbility<?>[] addRFInputHatchesToStructure(MultiblockAbility<?>[] originalAbilities) {
        MultiblockAbility<?>[] newAbilities = new MultiblockAbility<?>[originalAbilities.length + 1];
        System.arraycopy(originalAbilities, 0, newAbilities, 0, originalAbilities.length);
        newAbilities[newAbilities.length - 1] = ZBGTMultiblockAbilities.RF_INPUT_HATCH;
        return newAbilities;
    }

    @ModifyArg(method = "createStructurePattern",
               at = @At(value = "INVOKE",
                        target = "Lgregtech/common/metatileentities/multi/electric/MetaTileEntityPowerSubstation;abilities([Lgregtech/api/metatileentity/multiblock/MultiblockAbility;)Lgregtech/api/pattern/TraceabilityPredicate;",
                        ordinal = 1))
    private MultiblockAbility<?>[] addRFOutputHatchesToStructure(MultiblockAbility<?>[] originalAbilities) {
        MultiblockAbility<?>[] newAbilities = new MultiblockAbility<?>[originalAbilities.length + 1];
        System.arraycopy(originalAbilities, 0, newAbilities, 0, originalAbilities.length);
        newAbilities[newAbilities.length - 1] = ZBGTMultiblockAbilities.RF_OUTPUT_HATCH;
        return newAbilities;
    }

    @Inject(method = "formStructure",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z",
                     ordinal = 2,
                     shift = At.Shift.AFTER))
    private void collectRFInputHatchesOnForm(PatternMatchContext context, CallbackInfo ci,
                                             @Local(ordinal = 0) List<IEnergyContainer> inputs) {
        inputs.addAll(getAbilities(ZBGTMultiblockAbilities.RF_INPUT_HATCH));
    }

    @Inject(method = "formStructure",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z",
                     ordinal = 5,
                     shift = At.Shift.AFTER))
    private void collectRFOutputHatchesOnForm(PatternMatchContext context, CallbackInfo ci,
                                              @Local(ordinal = 1) List<IEnergyContainer> outputs) {
        outputs.addAll(getAbilities(ZBGTMultiblockAbilities.RF_OUTPUT_HATCH));
    }
}
