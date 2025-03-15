package com.zorbatron.zbgt.common.metatileentities;

import static com.zorbatron.zbgt.api.util.ZBGTUtility.zbgtId;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

import com.zorbatron.zbgt.api.ZBGTAPI;
import com.zorbatron.zbgt.api.metatileentity.ZBGTMultiblockAbilities;
import com.zorbatron.zbgt.common.metatileentities.multi.MTEYOTTank;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.MTECircuitAssemblyLine;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.MTECoAL;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.MTECryogenicFreezer;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.MTEVolcanus;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.large.*;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.mega.*;
import com.zorbatron.zbgt.common.metatileentities.multi.electric.quad.*;
import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.*;
import com.zorbatron.zbgt.common.metatileentities.multi.primitive.MTEIndustrialPBF;
import com.zorbatron.zbgt.common.metatileentities.storage.MTECreativeComputationProvider;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.machines.MachineItemBlock;

public class ZBGTMetaTileEntities {

    public static MTECreativeEnergyHatch CREATIVE_ENERGY_SOURCE;
    public static MTECreativeEnergyHatch CREATIVE_ENERGY_SINK;
    public static MTECreativeFluidHatch CREATIVE_RESERVOIR_HATCH;
    public static MTECreativeComputationProvider CREATIVE_COMPUTATION_PROVIDER;
    public static MTEAirIntakeHatch AIR_INTAKE_HATCH;
    public static MTEAirIntakeHatch EXTREME_AIR_INTAKE_HATCH;
    public static MTESingleItemInputBus SINGLE_ITEM_INPUT_BUS;
    public static MTECreativeItemBus CREATIVE_ITEM_BUS;
    public static MTESuperInputBus SUPER_INPUT_BUS;
    public static MTELargeParallelHatch[] ZBGT_PARALLEL_HATCHES = new MTELargeParallelHatch[7];
    public static MTEYOTTankMEHatch YOTTANK_ME_HATCH;
    public static MTESterileCleaningHatch STERILE_CLEANING_HATCH;
    public static MTEFilteredHatch PYROTHEUM_HEATING_HATCH;
    public static MTEFilteredHatch CRYOTHEUM_COOLING_HATCH;
    public static MetaTileEntityRFEnergyHatch[] RF_ENERGY_HATCH_INPUT = new MetaTileEntityRFEnergyHatch[GTValues.V.length];
    public static MetaTileEntityRFEnergyHatch[] RF_ENERGY_HATCH_OUTPUT = new MetaTileEntityRFEnergyHatch[GTValues.V.length];

    public static MTEMegaEBF MEGA_EBF;
    public static MTEMegaLCR MEGA_LCR;
    public static MTEMegaVF MEGA_VF;
    public static MTEMegaOCU MEGA_OCU;
    public static MTEMegaABS MEGA_ABS;
    public static MTEPreciseAssembler PRASS;
    public static MTEMegaFusionReactor[] MEGA_FUSION = new MTEMegaFusionReactor[3];

    public static MTECoAL CoAL;
    public static MTECircuitAssemblyLine CAL;

    public static MTEQueebf QUAD_EBF;
    public static MTEQueezer QUEEZER;
    public static MTEQuacker QUACKER;

    public static MTEYOTTank YOTTANK;

    public static MTELargeRockBreaker LARGE_ROCK_BREAKER;
    public static MTELargeAirCollector LARGE_AIR_COLLECTOR;
    public static MTELargeAlloySmelter LARGE_ALLOY_SMELTER;

    public static MTEVolcanus VOLCANUS;
    public static MTECryogenicFreezer CRYOGENIC_FREEZER;

    public static MTEIndustrialPBF IPBF;

    public static void init() {
        MachineItemBlock.addCreativeTab(ZBGTAPI.TAB_ZBGT);

        // 18000-18049 (50) reserved for multiblock parts
        CREATIVE_ENERGY_SOURCE = registerMetaTileEntity(18000,
                new MTECreativeEnergyHatch(zbgtId("creative_energy_source"), false));
        CREATIVE_ENERGY_SINK = registerMetaTileEntity(18001,
                new MTECreativeEnergyHatch(zbgtId("creative_energy_sink"), true));

        CREATIVE_RESERVOIR_HATCH = registerMetaTileEntity(18002,
                new MTECreativeFluidHatch(zbgtId("creative_reservoir_hatch"), false));

        CREATIVE_COMPUTATION_PROVIDER = registerMetaTileEntity(18003,
                new MTECreativeComputationProvider(zbgtId("creative_computation_provider")));

        AIR_INTAKE_HATCH = registerMetaTileEntity(18004,
                new MTEAirIntakeHatch(zbgtId("air_intake_hatch"), GTValues.IV, 128_000, 1000));
        EXTREME_AIR_INTAKE_HATCH = registerMetaTileEntity(18005,
                new MTEAirIntakeHatch(zbgtId("extreme_air_intake_hatch"), GTValues.LuV, 256_000,
                        8000));

        SINGLE_ITEM_INPUT_BUS = registerMetaTileEntity(18006,
                new MTESingleItemInputBus(zbgtId("single_item_input_bus")));

        CREATIVE_ITEM_BUS = registerMetaTileEntity(18008,
                new MTECreativeItemBus(zbgtId("creative_item_bus")));

        SUPER_INPUT_BUS = registerMetaTileEntity(18009,
                new MTESuperInputBus(zbgtId("super_input_bus")));

        ZBGT_PARALLEL_HATCHES[0] = registerMetaTileEntity(18010, new MTELargeParallelHatch(
                zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.UEV])), GTValues.UHV, 1024));
        ZBGT_PARALLEL_HATCHES[1] = registerMetaTileEntity(18011, new MTELargeParallelHatch(
                zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.UIV])), GTValues.UEV, 4096));
        ZBGT_PARALLEL_HATCHES[2] = registerMetaTileEntity(18012, new MTELargeParallelHatch(
                zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.UXV])), GTValues.UIV, 16_384));
        ZBGT_PARALLEL_HATCHES[3] = registerMetaTileEntity(18013, new MTELargeParallelHatch(
                zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.OpV])), GTValues.UXV, 65_536));
        ZBGT_PARALLEL_HATCHES[4] = registerMetaTileEntity(18014, new MTELargeParallelHatch(
                zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.MAX])), GTValues.OpV, 262_144));
        ZBGT_PARALLEL_HATCHES[5] = registerMetaTileEntity(18015, new MTELargeParallelHatch(
                zbgtId(String.format("parallel_hatch.%s", GTValues.VN[GTValues.MAX]) + ".1"), GTValues.MAX,
                1_048_576));
        ZBGT_PARALLEL_HATCHES[6] = registerMetaTileEntity(18016, new MTELargeParallelHatch(
                zbgtId("parallel_hatch.final"), GTValues.MAX, Integer.MAX_VALUE));

        YOTTANK_ME_HATCH = registerMetaTileEntity(18017,
                new MTEYOTTankMEHatch(zbgtId("yottank_me_hatch"), GTValues.IV));

        STERILE_CLEANING_HATCH = registerMetaTileEntity(18018,
                new MTESterileCleaningHatch(zbgtId("sterile_cleaning_hatch")));

        PYROTHEUM_HEATING_HATCH = registerMetaTileEntity(18019,
                new MTEFilteredHatch(zbgtId("pyrotheum_heating_hatch"), GTValues.IV,
                        ZBGTMultiblockAbilities.PYROTHEUM_HATCH, () -> ZBGTAPI.pyrotheum, 128_000));
        CRYOTHEUM_COOLING_HATCH = registerMetaTileEntity(18020,
                new MTEFilteredHatch(zbgtId("cryotheum_cooling_hatch"), GTValues.IV,
                        ZBGTMultiblockAbilities.CRYOTHEUM_HATCH, () -> ZBGTAPI.cryotheum, 128_000));

        for (int i = 0; i <= (GregTechAPI.isHighTier() ? GTValues.OpV : GTValues.UHV); i++) {
            RF_ENERGY_HATCH_INPUT[i] = registerMetaTileEntity(18019 + 2 + i, new MetaTileEntityRFEnergyHatch(
                    zbgtId(String.format("rf_input_hatch_%s", GTValues.VN[i].toLowerCase())), i, false));
        }

        for (int i = 0; i <= (GregTechAPI.isHighTier() ? GTValues.OpV : GTValues.UHV); i++) {
            RF_ENERGY_HATCH_OUTPUT[i] = registerMetaTileEntity(18033 + 2 + i, new MetaTileEntityRFEnergyHatch(
                    zbgtId(String.format("rf_output_hatch_%s", GTValues.VN[i].toLowerCase())), i, true));
        }

        // 18050-18099 (50) reserved for multiblocks
        MEGA_EBF = registerMetaTileEntity(18050,
                new MTEMegaEBF(zbgtId("mega_ebf")));

        MEGA_LCR = registerMetaTileEntity(18051,
                new MTEMegaLCR(zbgtId("mega_lcr")));

        MEGA_VF = registerMetaTileEntity(18052,
                new MTEMegaVF(zbgtId("mega_vf")));

        MEGA_OCU = registerMetaTileEntity(18053,
                new MTEMegaOCU(zbgtId("mega_ocu")));

        MEGA_ABS = registerMetaTileEntity(18054,
                new MTEMegaABS(zbgtId("mega_abs")));

        PRASS = registerMetaTileEntity(18055,
                new MTEPreciseAssembler(zbgtId("precise_assembler")));

        CoAL = registerMetaTileEntity(18056,
                new MTECoAL(zbgtId("coal")));

        QUAD_EBF = registerMetaTileEntity(18057,
                new MTEQueebf(zbgtId("quad_ebf")));
        QUEEZER = registerMetaTileEntity(18058,
                new MTEQueezer(zbgtId("queezer")));

        YOTTANK = registerMetaTileEntity(18059,
                new MTEYOTTank(zbgtId("yottank")));

        MEGA_FUSION[0] = registerMetaTileEntity(18060,
                new MTEMegaFusionReactor(zbgtId("mega_fusion_1"), GTValues.LuV));
        MEGA_FUSION[1] = registerMetaTileEntity(18061,
                new MTEMegaFusionReactor(zbgtId("mega_fusion_2"), GTValues.ZPM));
        MEGA_FUSION[2] = registerMetaTileEntity(18062,
                new MTEMegaFusionReactor(zbgtId("mega_fusion_3"), GTValues.UV));
        // Reserve 18063 and 18064 for the mega fusion 4 and 5 if I ever do them

        LARGE_ROCK_BREAKER = registerMetaTileEntity(18065,
                new MTELargeRockBreaker(zbgtId("large_rock_breaker")));
        LARGE_AIR_COLLECTOR = registerMetaTileEntity(18066,
                new MTELargeAirCollector(zbgtId("large_air_collector")));
        LARGE_ALLOY_SMELTER = registerMetaTileEntity(18068,
                new MTELargeAlloySmelter(zbgtId("large_alloy_smelter")));

        QUACKER = registerMetaTileEntity(18069,
                new MTEQuacker(zbgtId("quacker")));

        CAL = registerMetaTileEntity(18070,
                new MTECircuitAssemblyLine(zbgtId("cal")));

        VOLCANUS = registerMetaTileEntity(18071,
                new MTEVolcanus(zbgtId("volcanus")));
        CRYOGENIC_FREEZER = registerMetaTileEntity(18072,
                new MTECryogenicFreezer(zbgtId("cryogenic_freezer")));

        IPBF = registerMetaTileEntity(18073,
                new MTEIndustrialPBF(zbgtId("industrial_pbf")));
    }
}
