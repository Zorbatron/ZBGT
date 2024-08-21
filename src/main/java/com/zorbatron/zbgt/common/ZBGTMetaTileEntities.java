package com.zorbatron.zbgt.common;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

import com.zorbatron.zbgt.ZBUtility;
import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.MetaTileEntityAirIntakeHatch;
import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.MetaTileEntityCreativeEnergyHatch;
import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.MetaTileEntityCreativeReservoirHatch;
import com.zorbatron.zbgt.common.metatileentities.multi.multiblockpart.MetaTileEntitySingleItemInputBus;
import com.zorbatron.zbgt.common.metatileentities.storage.MetaTileEntityCreativeComputationProvider;

import gregtech.api.GTValues;

public class ZBGTMetaTileEntities {

    public static MetaTileEntityCreativeEnergyHatch CREATIVE_ENERGY_SOURCE;
    public static MetaTileEntityCreativeEnergyHatch CREATIVE_ENERGY_SINK;
    public static MetaTileEntityCreativeReservoirHatch CREATIVE_RESERVOIR_HATCH;
    public static MetaTileEntityCreativeComputationProvider CREATIVE_COMPUTATION_PROVIDER;
    public static MetaTileEntityAirIntakeHatch AIR_INTAKE_HATCH;
    public static MetaTileEntityAirIntakeHatch EXTREME_AIR_INTAKE_HATCH;
    public static MetaTileEntitySingleItemInputBus SINGLE_ITEM_INPUT_BUS;

    public static void init() {
        CREATIVE_ENERGY_SOURCE = registerMetaTileEntity(18000,
                new MetaTileEntityCreativeEnergyHatch(ZBUtility.zbgtId("creative_energy_source"), false));
        CREATIVE_ENERGY_SINK = registerMetaTileEntity(18001,
                new MetaTileEntityCreativeEnergyHatch(ZBUtility.zbgtId("creative_energy_sink"), true));

        CREATIVE_RESERVOIR_HATCH = registerMetaTileEntity(18002,
                new MetaTileEntityCreativeReservoirHatch(ZBUtility.zbgtId("creative_reservoir_hatch")));

        CREATIVE_COMPUTATION_PROVIDER = registerMetaTileEntity(18003,
                new MetaTileEntityCreativeComputationProvider(ZBUtility.zbgtId("creative_computation_provider")));

        AIR_INTAKE_HATCH = registerMetaTileEntity(18004,
                new MetaTileEntityAirIntakeHatch(ZBUtility.zbgtId("air_intake_hatch"), GTValues.IV, 128_000, 1000));
        EXTREME_AIR_INTAKE_HATCH = registerMetaTileEntity(18005,
                new MetaTileEntityAirIntakeHatch(ZBUtility.zbgtId("extreme_air_intake_hatch"), GTValues.LuV, 256_000,
                        8000));

        SINGLE_ITEM_INPUT_BUS = registerMetaTileEntity(18006,
                new MetaTileEntitySingleItemInputBus(ZBUtility.zbgtId("single_item_input_bus")));
    }
}
