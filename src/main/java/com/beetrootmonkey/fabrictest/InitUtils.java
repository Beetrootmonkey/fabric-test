package com.beetrootmonkey.fabrictest;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import reborncore.RebornRegistry;
import team.reborn.energy.Energy;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class InitUtils {
  public static <I extends Item> I setup(I item, String name) {
    RebornRegistry.registerIdent(item, new Identifier(MOD_ID, name));
    return item;
  }

  public static <B extends Block> B setup(B block, String name) {
    RebornRegistry.registerIdent(block, new Identifier(MOD_ID, name));
    return block;
  }

  public static SoundEvent setup(String name) {
    Identifier identifier = new Identifier(MOD_ID, name);
    return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
  }

  public static void initPoweredItems(Item item, DefaultedList<ItemStack> itemList) {
    ItemStack uncharged = new ItemStack(item);
    ItemStack charged = new ItemStack(item);

    Energy.of(charged).set(Energy.of(charged).getMaxStored());

    itemList.add(uncharged);
    itemList.add(charged);
  }

  public static AbstractBlock.Settings setupRubberBlockSettings(boolean noCollision, float hardness, float resistance) {

    FabricBlockSettings settings = FabricBlockSettings.of(Material.WOOD, MaterialColor.SPRUCE);
    settings.strength(hardness, resistance);
    settings.sounds(BlockSoundGroup.WOOD);
    if (noCollision) {
      settings.noCollision();
    }
    settings.materialColor(MaterialColor.SPRUCE);
    return settings;
  }

  public static AbstractBlock.Settings setupRubberBlockSettings(float hardness, float resistance) {
    return setupRubberBlockSettings(false, hardness, resistance);
  }
}
