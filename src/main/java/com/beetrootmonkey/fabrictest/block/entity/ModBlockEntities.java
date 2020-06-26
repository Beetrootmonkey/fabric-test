package com.beetrootmonkey.fabrictest.block.entity;

import com.beetrootmonkey.fabrictest.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ModBlockEntities {
  public static BlockEntityType<DemoBlockEntity> DEMO_BLOCK_ENTITY;

  public void doRegistration() {
    DEMO_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "demo"), BlockEntityType.Builder.create(DemoBlockEntity::new, ModBlocks.TEST_BLOCK).build(null));
  }
}
