package com.zorbatron.zbgt.common;

import static com.zorbatron.zbgt.ZBGTUtility.zbgtId;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

import com.zorbatron.zbgt.common.metatileentities.multi.MetaTileEntityYOTTank;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.*;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.megamultis.*;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.quads.MetaTileEntityQuadEBF;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.quads.MetaTileEntityQueezer;
import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.*;
import com.zorbatron.zbgt.common.metatileentities.storage.MetaTileEntityCreativeComputationProvider;

import gregtech.api.GTValues;
import gregtech.api.util.Mods;

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
    public static MetaTileEntityYOTTankMEHatch YOTTANK_ME_HATCH;

    public static MetaTileEntityMegaEBF MEGA_EBF;
    public static MetaTileEntityMegaLCR MEGA_LCR;
    public static MetaTileEntityMegaVF MEGA_VF;
    public static MetaTileEntityMegaOCU MEGA_OCU;
    public static MetaTileEntityMegaABS MEGA_ABS;
    public static MetaTileEntityPreciseAssembler PRASS;
    public static MetaTileEntityMegaFusionReactor[] MEGA_FUSION = new MetaTileEntityMegaFusionReactor[3];

    public static MetaTileEntityCoAL CoAL;

    public static MetaTileEntityQuadEBF QUAD_EBF;
    public static MetaTileEntityQueezer QUEEZER;

    public static MetaTileEntityYOTTank YOTTANK;

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
        if (Mods.AppliedEnergistics2.isModLoaded()) {
            YOTTANK_ME_HATCH = registerMetaTileEntity(18017,
                    new MetaTileEntityYOTTankMEHatch(zbgtId("yottank_me_hatch"), GTValues.IV));
        }

        // 18050-18099 (50) reserved for multiblocks
        MEGA_EBF = registerMetaTileEntity(18050,
                new MetaTileEntityMegaEBF(zbgtId("mega_ebf")));

        MEGA_LCR = registerMetaTileEntity(18051,
                new MetaTileEntityMegaLCR(zbgtId("mega_lcr")));

        MEGA_VF = registerMetaTileEntity(18052,
                new MetaTileEntityMegaVF(zbgtId("mega_vf")));

        MEGA_OCU = registerMetaTileEntity(18053,
                new MetaTileEntityMegaOCU(zbgtId("mega_ocu")));

        MEGA_ABS = registerMetaTileEntity(18054,
                new MetaTileEntityMegaABS(zbgtId("mega_abs")));

        PRASS = registerMetaTileEntity(18055,
                new MetaTileEntityPreciseAssembler(zbgtId("precise_assembler")));

        CoAL = registerMetaTileEntity(18056,
                new MetaTileEntityCoAL(zbgtId("coal")));

        QUAD_EBF = registerMetaTileEntity(18057,
                new MetaTileEntityQuadEBF(zbgtId("quad_ebf")));
        QUEEZER = registerMetaTileEntity(18058,
                new MetaTileEntityQueezer(zbgtId("queezer")));

        YOTTANK = registerMetaTileEntity(18059,
                new MetaTileEntityYOTTank(zbgtId("yottank")));

        for (int x = 1; x <= 3; x++) {
            MEGA_FUSION[x - 1] = registerMetaTileEntity(18059 + x,
                    new MetaTileEntityMegaFusionReactor(zbgtId("mega_fusion_" + x), x));
        }
    }
}
