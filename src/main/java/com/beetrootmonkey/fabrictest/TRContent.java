package com.beetrootmonkey.fabrictest;

import com.beetrootmonkey.fabrictest.block.IronFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Locale;

public class TRContent {

  // Misc Blocks
  public static Block COMPUTER_CUBE;
  public static Block NUKE;
  public static Block REFINED_IRON_FENCE;
  public static Block REINFORCED_GLASS;
  public static Block RUBBER_LEAVES;
  public static Block RUBBER_LOG;
  public static Block RUBBER_PLANK_SLAB;
  public static Block RUBBER_PLANK_STAIR;
  public static Block RUBBER_PLANKS;
  public static Block RUBBER_SAPLING;
  public static Block RUBBER_FENCE;
  public static Block RUBBER_FENCE_GATE;
  public static Block RUBBER_TRAPDOOR;
  public static Block RUBBER_BUTTON;
  public static Block RUBBER_PRESSURE_PLATE;
  public static Block RUBBER_DOOR;
  public static Block RUBBER_LOG_STRIPPED;
  public static Block RUBBER_WOOD;
  public static Block STRIPPED_RUBBER_WOOD;

  // Armor
  public static Item CLOAKING_DEVICE;
  public static Item LAPOTRONIC_ORBPACK;
  public static Item LITHIUM_ION_BATPACK;

  // Battery
  public static Item ENERGY_CRYSTAL;
  public static Item LAPOTRON_CRYSTAL;
  public static Item LAPOTRONIC_ORB;
  public static Item LITHIUM_ION_BATTERY;
  public static Item RED_CELL_BATTERY;

  // Tools
  public static Item TREE_TAP;
  public static Item WRENCH;
  public static Item PAINTING_TOOL;

  public static Item BASIC_CHAINSAW;
  public static Item BASIC_DRILL;
  public static Item BASIC_JACKHAMMER;
  public static Item ELECTRIC_TREE_TAP;

  public static Item ADVANCED_CHAINSAW;
  public static Item ADVANCED_DRILL;
  public static Item ADVANCED_JACKHAMMER;
  public static Item ROCK_CUTTER;

  public static Item INDUSTRIAL_CHAINSAW;
  public static Item INDUSTRIAL_DRILL;
  public static Item INDUSTRIAL_JACKHAMMER;
  public static Item NANOSABER;
  public static Item OMNI_TOOL;

  public static Item DEBUG_TOOL;

  // Other
  public static Item FREQUENCY_TRANSMITTER;
  public static Item SCRAP_BOX;
  public static Item MANUAL;

  // Gem armor & tools
  @Nullable
  public static Item BRONZE_SWORD;
  @Nullable
  public static Item BRONZE_PICKAXE;
  @Nullable
  public static Item BRONZE_SPADE;
  @Nullable
  public static Item BRONZE_AXE;
  @Nullable
  public static Item BRONZE_HOE;
  @Nullable
  public static Item BRONZE_HELMET;
  @Nullable
  public static Item BRONZE_CHESTPLATE;
  @Nullable
  public static Item BRONZE_LEGGINGS;
  @Nullable
  public static Item BRONZE_BOOTS;
  @Nullable
  public static Item RUBY_SWORD;
  @Nullable
  public static Item RUBY_PICKAXE;
  @Nullable
  public static Item RUBY_SPADE;
  @Nullable
  public static Item RUBY_AXE;
  @Nullable
  public static Item RUBY_HOE;
  @Nullable
  public static Item RUBY_HELMET;
  @Nullable
  public static Item RUBY_CHESTPLATE;
  @Nullable
  public static Item RUBY_LEGGINGS;
  @Nullable
  public static Item RUBY_BOOTS;
  @Nullable
  public static Item SAPPHIRE_SWORD;
  @Nullable
  public static Item SAPPHIRE_PICKAXE;
  @Nullable
  public static Item SAPPHIRE_SPADE;
  @Nullable
  public static Item SAPPHIRE_AXE;
  @Nullable
  public static Item SAPPHIRE_HOE;
  @Nullable
  public static Item SAPPHIRE_HELMET;
  @Nullable
  public static Item SAPPHIRE_CHESTPLATE;
  @Nullable
  public static Item SAPPHIRE_LEGGINGS;
  @Nullable
  public static Item SAPPHIRE_BOOTS;
  @Nullable
  public static Item PERIDOT_SWORD;
  @Nullable
  public static Item PERIDOT_PICKAXE;
  @Nullable
  public static Item PERIDOT_SPADE;
  @Nullable
  public static Item PERIDOT_AXE;
  @Nullable
  public static Item PERIDOT_HOE;
  @Nullable
  public static Item PERIDOT_HELMET;
  @Nullable
  public static Item PERIDOT_CHESTPLATE;
  @Nullable
  public static Item PERIDOT_LEGGINGS;
  @Nullable
  public static Item PERIDOT_BOOTS;

  public enum Machine implements ItemConvertible {

    IRON_FURNACE(new IronFurnaceBlock());

    public final String name;
    public final Block block;

    <B extends Block> Machine(B block) {
      this.name = this.toString().toLowerCase(Locale.ROOT);
      this.block = block;
      InitUtils.setup(block, name);
    }

    public ItemStack getStack() {
      return new ItemStack(block);
    }

    @Override
    public Item asItem() {
      return block.asItem();
    }
  }
}
