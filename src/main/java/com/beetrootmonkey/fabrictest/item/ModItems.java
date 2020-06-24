package com.beetrootmonkey.fabrictest.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ModItems {

  public static final Item ENDER_PEARL_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Item GOLD_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Item IRON_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Item LAPIS_LAZULI_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Item NETHER_QUARTZ_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Item NETHER_STAR_DUST = new GlowingItem(new Item.Settings().group(ItemGroup.MISC));
  public static final Item NETHER_STAR_NUGGET = new GlowingItem(new Item.Settings().group(ItemGroup.MISC));
  public static final Item NETHERITE_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Item NETHERITE_SCRAP_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Item PRISMARINE_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Item GRINDSTONE = new Item(new Item.Settings());

  public static void doRegistration() {
    register(ENDER_PEARL_DUST, "ender_pearl_dust");
    register(GOLD_DUST, "gold_dust");
    register(IRON_DUST, "iron_dust");
    register(LAPIS_LAZULI_DUST, "lapis_lazuli_dust");
    register(NETHER_QUARTZ_DUST, "nether_quartz_dust");
    register(NETHER_STAR_DUST, "nether_star_dust");
    register(NETHER_STAR_NUGGET, "nether_star_nugget");
    register(NETHERITE_DUST, "netherite_dust");
    register(NETHERITE_SCRAP_DUST, "netherite_scrap_dust");
    register(PRISMARINE_DUST, "prismarine_dust");
    register(GRINDSTONE, "grindstone");
  }

  private static void register(Item item, String path) {
    Registry.register(Registry.ITEM, new Identifier(MOD_ID, path), item);
  }
}
