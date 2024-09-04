package com.zorbatron.zbgt.common;

import static com.zorbatron.zbgt.ZBGTUtility.zbgtId;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

import com.zorbatron.zbgt.api.util.ZBGTMods;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis.MetaTileEntityMegaEBF;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis.MetaTileEntityMegaLCR;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis.MetaTileEntityMegaVF;
import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.*;
import com.zorbatron.zbgt.common.metatileentities.storage.MetaTileEntityCreativeComputationProvider;

import gregtech.api.GTValues;

public class ZBGTMetaTileEntities {

    public static MetaTileEntityCreativeEnergyHatch CREATIVE_ENERGY_SOURCE;
    public static MetaTileEntityCreativeEnergyHatch CREATIVE_ENERGY_SINK;
    public static MetaTileEntityCreativeFluidHatch CREATIVE_RESERVOIR_HATCH;
    public static MetaTileEntityCreativeComputationProvider CREATIVE_COMPUTATION_PROVIDER;
    public static MetaTileEntityAirIntakeHatch AIR_INTAKE_HATCH;
    public static MetaTileEntityAirIntakeHatch EXTREME_AIR_INTAKE_HATCH;
    public static MetaTileEntitySingleItemInputBus SINGLE_ITEM_INPUT_BUS;
    public static MetaTileEntityCreativeItemBus CREATIVE_ITEM_BUS;
    public static MetaTileEntitySuperInputBus SUPER_INPUT_BUS;
    public static MetaTileEntityLargeParallelHatch[] ZBGT_PARALLEL_HATCHES = new MetaTileEntityLargeParallelHatch[7];

    public static MetaTileEntityMegaEBF MEGA_EBF;
    public static MetaTileEntityMegaLCR MEGA_LCR;
    public static MetaTileEntityMegaVF MEGA_VF;

    public static void init() {
        // 18000-18049 (50) reserved for multiblock parts
        CREATIVE_ENERGY_SOURCE = registerMetaTileEntity(18000,
                new MetaTileEntityCreativeEnergyHatch(zbgtId("creative_energy_source"), false));
        CREATIVE_ENERGY_SINK = registerMetaTileEntity(18001,
                new MetaTileEntityCreativeEnergyHatch(zbgtId("creative_energy_sink"), true));

        CREATIVE_RESERVOIR_HATCH = registerMetaTileEntity(18002,
                new MetaTileEntityCreativeFluidHatch(zbgtId("creative_reservoir_hatch"), false));

        CREATIVE_COMPUTATION_PROVIDER = registerMetaTileEntity(18003,
                new MetaTileEntityCreativeComputationProvider(zbgtId("creative_computation_provider")));

        AIR_INTAKE_HATCH = registerMetaTileEntity(18004,
                new MetaTileEntityAirIntakeHatch(zbgtId("air_intake_hatch"), GTValues.IV, 128_000, 1000));
        EXTREME_AIR_INTAKE_HATCH = registerMetaTileEntity(18005,
                new MetaTileEntityAirIntakeHatch(zbgtId("extreme_air_intake_hatch"), GTValues.LuV, 256_000,
                        8000));

        SINGLE_ITEM_INPUT_BUS = registerMetaTileEntity(18006,
                new MetaTileEntitySingleItemInputBus(zbgtId("single_item_input_bus")));

        CREATIVE_ITEM_BUS = registerMetaTileEntity(18008,
                new MetaTileEntityCreativeItemBus(zbgtId("creative_item_bus")));

        SUPER_INPUT_BUS = registerMetaTileEntity(18009,
                new MetaTileEntitySuperInputBus(zbgtId("super_input_bus")));

        if (ZBGTMods.GCYM.isModLoaded()) {
            ZBGT_PARALLEL_HATCHES[0] = registerMetaTileEntity(18010, new MetaTileEntityLargeParallelHatch(
                    zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.UEV])), GTValues.UHV, 1024));
            ZBGT_PARALLEL_HATCHES[1] = registerMetaTileEntity(18011, new MetaTileEntityLargeParallelHatch(
                    zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.UIV])), GTValues.UEV, 4096));
            ZBGT_PARALLEL_HATCHES[2] = registerMetaTileEntity(18012, new MetaTileEntityLargeParallelHatch(
                    zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.UXV])), GTValues.UIV, 16_384));
            ZBGT_PARALLEL_HATCHES[3] = registerMetaTileEntity(18013, new MetaTileEntityLargeParallelHatch(
                    zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.OpV])), GTValues.UXV, 65_536));
            ZBGT_PARALLEL_HATCHES[4] = registerMetaTileEntity(18014, new MetaTileEntityLargeParallelHatch(
                    zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.MAX])), GTValues.OpV, 262_144));
            ZBGT_PARALLEL_HATCHES[5] = registerMetaTileEntity(18015, new MetaTileEntityLargeParallelHatch(
                    zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.MAX]) + ".1"), GTValues.MAX,
                    1_048_576));
            ZBGT_PARALLEL_HATCHES[6] = registerMetaTileEntity(18016, new MetaTileEntityLargeParallelHatch(
                    zbgtId("parallel_hatch.final"), GTValues.MAX, Integer.MAX_VALUE));
        }

        // 18050-18099 (50) reserved for multiblocks
        if (ZBGTMods.GCYM.isModLoaded()) {
            MEGA_EBF = registerMetaTileEntity(18050,
                    new MetaTileEntityMegaEBF(zbgtId("mega_ebf")));

            MEGA_LCR = registerMetaTileEntity(18051,
                    new MetaTileEntityMegaLCR(zbgtId("mega_lcr")));

            MEGA_VF = registerMetaTileEntity(18052,
                    new MetaTileEntityMegaVF(zbgtId("mega_vf")));
        }
    }
}
