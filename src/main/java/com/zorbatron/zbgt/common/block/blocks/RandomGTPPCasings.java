package com.zorbatron.zbgt.common.block.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import org.jetbrains.annotations.NotNull;

import com.zorbatron.zbgt.api.ZBGTAPI;

import gregtech.api.block.VariantBlock;

public class RandomGTPPCasings extends VariantBlock<RandomGTPPCasings.CasingType> {

    public RandomGTPPCasings() {
        super(Material.IRON);
        setTranslationKey("misc_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(RandomGTPPCasings.CasingType.values()[0]));
        setCreativeTab(ZBGTAPI.TAB_ZBGT);
    }

    public enum CasingType implements IStringSerializable {

        CENTRIFUGE_CASING("centrifuge_casing"),
        STRUCTURAL_COKE_OVEN_CASING("structural_coke_oven_casing"),
        HEAT_RESISTANT_COKE_OVEN_CASING("heat_resistant_coke_oven_casing"),
        HEAT_PROOF_COKE_OVEN_CASING("heat_proof_coke_oven_casing"),
        MATERIAL_PRESS_MACHINE_CASING("material_press_machine_casing"),
        ELECTROLYZER_CASING("electrolyzer_casing"),
        WIRE_FACTORY_CASING("wire_factory_casing"),
        MACERATION_STACK_CASING("maceration_stack_casing"),
        MATTER_GENERATION_COIL("matter_generation_coil"),
        MATTER_FABRICATION_CASING("matter_fabrication_casing"),
        HASTELLOY_N_REACTOR_CASING("hastelloy_n_reactor_casing"),
        ZERON_100_REACTOR_SHIELDING("zeron_100_reactor_shielding"),
        THERMAL_PROCESSING_CASING("thermal_processing_casing"),
        HASTELLOY_N_SEALANT_BLOCK("hastelloy_n_sealant_block"),
        HASTELLOY_X_STRUCTURAL_BLOCK("hastelloy_x_structural_block"),
        INCOLOY_DS_FLUID_CONTAINMENT_BLOCK("incoloy_ds_fluid_containment_block"),
        WASH_PLANT_CASING("wash_plant_casing"),
        INDUSTRIAL_SIEVE_CASING("industrial_sieve_casing"),
        INDUSTRIAL_SIEVE_GRATE("industrial_sieve_grate"),
        CYCLOTRON_COIL("cyclotron_coil"),
        CYCLOTRON_OUTER_CASING("cyclotron_outer_casing"),
        THERMAL_CONTAINMENT_CASING("thermal_containment_casing"),
        BULK_PRODUCTION_FRAME("bulk_production_frame"),
        CUTTING_FACTORY_FRAME("cutting_factory_frame"),
        STERILE_FARM_CASING("sterile_farm_casing"),
        AQUATIC_CASING("aquatic_casing"),
        INCONEL_REINFORCED_CASING("inconel_reinforced_casing"),
        MULTI_USE_CASING("multi_use_casing"),
        SUPPLY_DEPOT_CASING("supply_depot_casing"),
        CONTAINMENT_CASING("containment_casing"),
        TEMPERED_ARC_FURNACE_CASING("tempered_arc_furnace_casing"),
        QUANTUM_FORCE_TRANSFORMER_COIL_CASINGS("quantum_force_transformer_coil_casings"),
        VACUUM_CASING("vacuum_casing"),
        TURBODYNE_CASING("turbodyne_casing"),
        ISAMILL_EXTERIOR_CASING("isamill_exterior_casing"),
        ISAMILL_PIPING("isamill_piping"),
        ISAMILL_GEARBOX("isamill_gearbox"),
        ELEMENTAL_CONFINEMENT_SHELL("elemental_confinement_shell"),
        SPARGE_TOWER_EXTERIOR_CASING("sparge_tower_exterior_casing"),
        STURDY_PRINTER_CASING("sturdy_printer_casing"),
        FORGE_CASING("forge_casing"),
        NEUTRON_PULSE_MANIPULATOR("neutron_pulse_manipulator"),
        COSMIC_FABRIC_MANIPULATOR("cosmic_fabric_manipulator"),
        INFINITY_INFUSED_MANIPULATOR("infinity_infused_manipulator"),
        SPACETIME_CONTINUUM_RIPPER("spacetime_continuum_ripper"),
        NEUTRON_SHIELDING_CORE("neutron_shielding_core"),
        COSMIC_FABRIC_SHIELDING_CORE("cosmic_fabric_shielding_core"),
        INFINITY_INFUSED_SHIELDING_CORE("infinity_infused_shielding_core"),
        SPACETIME_BENDING_CORE("spacetime_bending_core"),
        FORCE_FIELD_GLASS("force_field_glass"),
        INTEGRAL_ENCASEMENT_1("integral_encasement_1"),
        INTEGRAL_ENCASEMENT_2("integral_encasement_2"),
        INTEGRAL_ENCASEMENT_3("integral_encasement_3"),
        INTEGRAL_ENCASEMENT_4("integral_encasement_4"),
        INTEGRAL_ENCASEMENT_5("integral_encasement_5"),
        INTEGRAL_ENCASEMENT_6("integral_encasement_6"),
        INTEGRAL_ENCASEMENT_7("integral_encasement_7"),
        INTEGRAL_ENCASEMENT_8("integral_encasement_8"),
        INTEGRAL_ENCASEMENT_9("integral_encasement_9"),
        INTEGRAL_ENCASEMENT_10("integral_encasement_10"),
        TURBINE_SHAFT("turbine_shaft"),
        REINFORCED_STEAM_TURBINE_CASING("reinforced_steam_turbine_casing"),
        REINFORCED_HP_STEAM_TURBINE_CASING("reinforced_hp_steam_turbine_casing"),
        REINFORCED_SC_STEAM_TURBINE_CASING("reinforced_sc_steam_turbine_casing"),
        REINFORCED_GAS_TURBINE_CASING("reinforced_gas_turbine_casing"),
        REINFORCED_PLASMA_TURBINE_CASING("reinforced_plasma_turbine_casing"),
        STRUCTURAL_SOLAR_CASING("structural_solar_casing"),
        SALT_CONTAINMENT_CASING("salt_containment_casing"),
        THERMALLY_INSULATED_CASING("thermally_insulated_casing"),
        FLOTATION_CELL_CASINGS("flotation_cell_casings"),
        MOLECULAR_CONTAINMENT_CASING("molecular_containment_casing"),
        HIGH_VOLTAGE_CURRENT_CAPACITOR("high_voltage_current_capacitor"),
        PARTICLE_CONTAINMENT_CASING("particle_containment_casing"),
        RESONANCE_CHAMBER_1("resonance_chamber_1"),
        RESONANCE_CHAMBER_2("resonance_chamber_2"),
        RESONANCE_CHAMBER_3("resonance_chamber_3"),
        RESONANCE_CHAMBER_4("resonance_chamber_4"),
        MODULATOR_1("modulator_1"),
        MODULATOR_2("modulator_2"),
        MODULATOR_3("modulator_3"),
        MODULATOR_4("modulator_4"),
        STRONG_BRONZE_MACHINE_CASING("strong_bronze_machine_casing"),
        STURDY_ALUMINUM_MACHINE_CASING("sturdy_aluminum_machine_casing"),
        VIGOROUS_LAURENIUM_MACHINE_CASING("vigorous_laurenium_machine_casing"),
        RUGGED_BOTMIUM_MACHINE_CASING("rugged_botmium_machine_casing");

        private final String name;

        CasingType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getName() {
            return this.name;
        }
    }
}
