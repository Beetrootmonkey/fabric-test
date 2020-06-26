package com.beetrootmonkey.fabrictest.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ModBlocks {

  public static final BaseBlock TEST_BLOCK = new BaseBlock(FabricBlockSettings.of(Material.WOOL).sounds(BlockSoundGroup.CHAIN));
  public static final IronFurnaceBlock IRON_FURNACE = new IronFurnaceBlock();

  public static void doRegistration() {
    register(TEST_BLOCK, "test_block");
    register(IRON_FURNACE, "iron_furnace");
  }

  private static void register(Block block, String path) {
    register(block, new Identifier(MOD_ID, path));
  }

  private static void register(Block block, Identifier identifier) {
    Registry.register(Registry.BLOCK, identifier, block);
    Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().group(ItemGroup.MISC)));
  }
}
