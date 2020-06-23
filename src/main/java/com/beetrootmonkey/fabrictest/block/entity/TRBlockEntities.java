package com.beetrootmonkey.fabrictest.block.entity;


import com.beetrootmonkey.fabrictest.TRContent;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class TRBlockEntities {

  private static List<BlockEntityType<?>> TYPES = new ArrayList<>();


  //  public static final BlockEntityType<StorageUnitBaseBlockEntity> STORAGE_UNIT = register(StorageUnitBaseBlockEntity::new, "storage_unit", TRContent.StorageUnit.values());
//  public static final BlockEntityType<TankUnitBaseBlockEntity> TANK_UNIT = register(TankUnitBaseBlockEntity::new, "tank_unit", TRContent.TankUnit.values());
//  public static final BlockEntityType<DrainBlockEntity> DRAIN = register(DrainBlockEntity::new, "drain", TRContent.Machine.DRAIN);
//  public static final BlockEntityType<ThermalGeneratorBlockEntity> THERMAL_GEN = register(ThermalGeneratorBlockEntity::new, "thermal_generator", TRContent.Machine.THERMAL_GENERATOR);
//  public static final BlockEntityType<IndustrialCentrifugeBlockEntity> INDUSTRIAL_CENTRIFUGE = register(IndustrialCentrifugeBlockEntity::new, "industrial_centrifuge", TRContent.Machine.INDUSTRIAL_CENTRIFUGE);
//  public static final BlockEntityType<RollingMachineBlockEntity> ROLLING_MACHINE = register(RollingMachineBlockEntity::new, "rolling_machine", TRContent.Machine.ROLLING_MACHINE);
//  public static final BlockEntityType<IndustrialBlastFurnaceBlockEntity> INDUSTRIAL_BLAST_FURNACE = register(IndustrialBlastFurnaceBlockEntity::new, "industrial_blast_furnace", TRContent.Machine.INDUSTRIAL_BLAST_FURNACE);
//  public static final BlockEntityType<AlloySmelterBlockEntity> ALLOY_SMELTER = register(AlloySmelterBlockEntity::new, "alloy_smelter", TRContent.Machine.ALLOY_SMELTER);
//  public static final BlockEntityType<IndustrialGrinderBlockEntity> INDUSTRIAL_GRINDER = register(IndustrialGrinderBlockEntity::new, "industrial_grinder", TRContent.Machine.INDUSTRIAL_GRINDER);
//  public static final BlockEntityType<ImplosionCompressorBlockEntity> IMPLOSION_COMPRESSOR = register(ImplosionCompressorBlockEntity::new, "implosion_compressor", TRContent.Machine.IMPLOSION_COMPRESSOR);
//  public static final BlockEntityType<MatterFabricatorBlockEntity> MATTER_FABRICATOR = register(MatterFabricatorBlockEntity::new, "matter_fabricator", TRContent.Machine.MATTER_FABRICATOR);
//  public static final BlockEntityType<ChunkLoaderBlockEntity> CHUNK_LOADER = register(ChunkLoaderBlockEntity::new, "chunk_loader", TRContent.Machine.CHUNK_LOADER);
//  public static final BlockEntityType<ChargeOMatBlockEntity> CHARGE_O_MAT = register(ChargeOMatBlockEntity::new, "charge_o_mat", TRContent.Machine.CHARGE_O_MAT);
//  public static final BlockEntityType<PlayerDectectorBlockEntity> PLAYER_DETECTOR = register(PlayerDectectorBlockEntity::new, "player_detector", TRContent.Machine.PLAYER_DETECTOR);
//  public static final BlockEntityType<CableBlockEntity> CABLE = register(CableBlockEntity::new, "cable", TRContent.Cables.values());
//  public static final BlockEntityType<MachineCasingBlockEntity> MACHINE_CASINGS = register(MachineCasingBlockEntity::new, "machine_casing", TRContent.MachineBlocks.getCasings());
//  public static final BlockEntityType<DragonEggSyphonBlockEntity> DRAGON_EGG_SYPHON = register(DragonEggSyphonBlockEntity::new, "dragon_egg_syphon", TRContent.Machine.DRAGON_EGG_SYPHON);
//  public static final BlockEntityType<AssemblingMachineBlockEntity> ASSEMBLY_MACHINE = register(AssemblingMachineBlockEntity::new, "assembly_machine", TRContent.Machine.ASSEMBLY_MACHINE);
//  public static final BlockEntityType<DieselGeneratorBlockEntity> DIESEL_GENERATOR = register(DieselGeneratorBlockEntity::new, "diesel_generator", TRContent.Machine.DIESEL_GENERATOR);
//  public static final BlockEntityType<IndustrialElectrolyzerBlockEntity> INDUSTRIAL_ELECTROLYZER = register(IndustrialElectrolyzerBlockEntity::new, "industrial_electrolyzer", TRContent.Machine.INDUSTRIAL_ELECTROLYZER);
//  public static final BlockEntityType<SemiFluidGeneratorBlockEntity> SEMI_FLUID_GENERATOR = register(SemiFluidGeneratorBlockEntity::new, "semi_fluid_generator", TRContent.Machine.SEMI_FLUID_GENERATOR);
//  public static final BlockEntityType<GasTurbineBlockEntity> GAS_TURBINE = register(GasTurbineBlockEntity::new, "gas_turbine", TRContent.Machine.GAS_TURBINE);
//  public static final BlockEntityType<IronAlloyFurnaceBlockEntity> IRON_ALLOY_FURNACE = register(IronAlloyFurnaceBlockEntity::new, "iron_alloy_furnace", TRContent.Machine.IRON_ALLOY_FURNACE);
//  public static final BlockEntityType<ChemicalReactorBlockEntity> CHEMICAL_REACTOR = register(ChemicalReactorBlockEntity::new, "chemical_reactor", TRContent.Machine.CHEMICAL_REACTOR);
//  public static final BlockEntityType<InterdimensionalSUBlockEntity> INTERDIMENSIONAL_SU = register(InterdimensionalSUBlockEntity::new, "interdimensional_su", TRContent.Machine.INTERDIMENSIONAL_SU);
//  public static final BlockEntityType<AdjustableSUBlockEntity> ADJUSTABLE_SU = register(AdjustableSUBlockEntity::new, "adjustable_su", TRContent.Machine.ADJUSTABLE_SU);
//  public static final BlockEntityType<LapotronicSUBlockEntity> LAPOTRONIC_SU = register(LapotronicSUBlockEntity::new, "lapotronic_su", TRContent.Machine.LAPOTRONIC_SU);
//  public static final BlockEntityType<LSUStorageBlockEntity> LSU_STORAGE = register(LSUStorageBlockEntity::new, "lsu_storage", TRContent.Machine.LSU_STORAGE);
//  public static final BlockEntityType<DistillationTowerBlockEntity> DISTILLATION_TOWER = register(DistillationTowerBlockEntity::new, "distillation_tower", TRContent.Machine.DISTILLATION_TOWER);
//  public static final BlockEntityType<VacuumFreezerBlockEntity> VACUUM_FREEZER = register(VacuumFreezerBlockEntity::new, "vacuum_freezer", TRContent.Machine.VACUUM_FREEZER);
//  public static final BlockEntityType<FusionControlComputerBlockEntity> FUSION_CONTROL_COMPUTER = register(FusionControlComputerBlockEntity::new, "fusion_control_computer", TRContent.Machine.FUSION_CONTROL_COMPUTER);
//  public static final BlockEntityType<LightningRodBlockEntity> LIGHTNING_ROD = register(LightningRodBlockEntity::new, "lightning_rod", TRContent.Machine.LIGHTNING_ROD);
//  public static final BlockEntityType<IndustrialSawmillBlockEntity> INDUSTRIAL_SAWMILL = register(IndustrialSawmillBlockEntity::new, "industrial_sawmill", TRContent.Machine.INDUSTRIAL_SAWMILL);
//  public static final BlockEntityType<SolidFuelGeneratorBlockEntity> SOLID_FUEL_GENEREATOR = register(SolidFuelGeneratorBlockEntity::new, "solid_fuel_generator", TRContent.Machine.SOLID_FUEL_GENERATOR);
//  public static final BlockEntityType<ExtractorBlockEntity> EXTRACTOR = register(ExtractorBlockEntity::new, "extractor", TRContent.Machine.EXTRACTOR);
//  public static final BlockEntityType<CompressorBlockEntity> COMPRESSOR = register(CompressorBlockEntity::new, "compressor", TRContent.Machine.COMPRESSOR);
//  public static final BlockEntityType<ElectricFurnaceBlockEntity> ELECTRIC_FURNACE = register(ElectricFurnaceBlockEntity::new, "electric_furnace", TRContent.Machine.ELECTRIC_FURNACE);
//  public static final BlockEntityType<SolarPanelBlockEntity> SOLAR_PANEL = register(SolarPanelBlockEntity::new, "solar_panel", TRContent.SolarPanels.values());
//  public static final BlockEntityType<WaterMillBlockEntity> WATER_MILL = register(WaterMillBlockEntity::new, "water_mill", TRContent.Machine.WATER_MILL);
//  public static final BlockEntityType<WindMillBlockEntity> WIND_MILL = register(WindMillBlockEntity::new, "wind_mill", TRContent.Machine.WIND_MILL);
//  public static final BlockEntityType<RecyclerBlockEntity> RECYCLER = register(RecyclerBlockEntity::new, "recycler", TRContent.Machine.RECYCLER);
//  public static final BlockEntityType<LowVoltageSUBlockEntity> LOW_VOLTAGE_SU = register(LowVoltageSUBlockEntity::new, "low_voltage_su", TRContent.Machine.LOW_VOLTAGE_SU);
//  public static final BlockEntityType<MediumVoltageSUBlockEntity> MEDIUM_VOLTAGE_SU = register(MediumVoltageSUBlockEntity::new, "medium_voltage_su", TRContent.Machine.MEDIUM_VOLTAGE_SU);
//  public static final BlockEntityType<HighVoltageSUBlockEntity> HIGH_VOLTAGE_SU = register(HighVoltageSUBlockEntity::new, "high_voltage_su", TRContent.Machine.HIGH_VOLTAGE_SU);
//  public static final BlockEntityType<LVTransformerBlockEntity> LV_TRANSFORMER = register(LVTransformerBlockEntity::new, "lv_transformer", TRContent.Machine.LV_TRANSFORMER);
//  public static final BlockEntityType<MVTransformerBlockEntity> MV_TRANSFORMER = register(MVTransformerBlockEntity::new, "mv_transformer", TRContent.Machine.MV_TRANSFORMER);
//  public static final BlockEntityType<HVTransformerBlockEntity> HV_TRANSFORMER = register(HVTransformerBlockEntity::new, "hv_transformer", TRContent.Machine.HV_TRANSFORMER);
//  public static final BlockEntityType<EVTransformerBlockEntity> EV_TRANSFORMER = register(EVTransformerBlockEntity::new, "ev_transformer", TRContent.Machine.EV_TRANSFORMER);
//  public static final BlockEntityType<AutoCraftingTableBlockEntity> AUTO_CRAFTING_TABLE = register(AutoCraftingTableBlockEntity::new, "auto_crafting_table", TRContent.Machine.AUTO_CRAFTING_TABLE);
  public static final BlockEntityType<IronFurnaceBlockEntity> IRON_FURNACE = register(IronFurnaceBlockEntity::new, "iron_furnace", TRContent.Machine.IRON_FURNACE);
//  public static final BlockEntityType<ScrapboxinatorBlockEntity> SCRAPBOXINATOR = register(ScrapboxinatorBlockEntity::new, "scrapboxinator", TRContent.Machine.SCRAPBOXINATOR);
//  public static final BlockEntityType<PlasmaGeneratorBlockEntity> PLASMA_GENERATOR = register(PlasmaGeneratorBlockEntity::new, "plasma_generator", TRContent.Machine.PLASMA_GENERATOR);
//  public static final BlockEntityType<LampBlockEntity> LAMP = register(LampBlockEntity::new, "lamp", TRContent.Machine.LAMP_INCANDESCENT, TRContent.Machine.LAMP_LED);
//  public static final BlockEntityType<AlarmBlockEntity> ALARM = register(AlarmBlockEntity::new, "alarm", TRContent.Machine.ALARM);
//  public static final BlockEntityType<FluidReplicatorBlockEntity> FLUID_REPLICATOR = register(FluidReplicatorBlockEntity::new, "fluid_replicator", TRContent.Machine.FLUID_REPLICATOR);
//  public static final BlockEntityType<SoildCanningMachineBlockEntity> SOLID_CANNING_MACHINE = register(SoildCanningMachineBlockEntity::new, "solid_canning_machine", TRContent.Machine.SOLID_CANNING_MACHINE);
//  public static final BlockEntityType<WireMillBlockEntity> WIRE_MILL = register(WireMillBlockEntity::new, "wire_mill", TRContent.Machine.WIRE_MILL);
//  public static final BlockEntityType<GreenhouseControllerBlockEntity> GREENHOUSE_CONTROLLER = register(GreenhouseControllerBlockEntity::new, "greenhouse_controller", TRContent.Machine.GREENHOUSE_CONTROLLER);

  public static <T extends BlockEntity> BlockEntityType<T> register(Supplier<T> supplier, String name, ItemConvertible... items) {
    return register(supplier, name, Arrays.stream(items).map(itemConvertible -> Block.getBlockFromItem(itemConvertible.asItem())).toArray(Block[]::new));
  }

  public static <T extends BlockEntity> BlockEntityType<T> register(Supplier<T> supplier, String name, Block... blocks) {
    Validate.isTrue(blocks.length > 0, "no blocks for blockEntity entity type!");
    return register(new Identifier(MOD_ID, name).toString(), BlockEntityType.Builder.create(supplier, blocks));
  }

  public static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.Builder<T> builder) {
    BlockEntityType<T> blockEntityType = builder.build(null);
    Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(id), blockEntityType);
    TRBlockEntities.TYPES.add(blockEntityType);
    return blockEntityType;
  }

}