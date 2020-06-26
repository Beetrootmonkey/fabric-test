package com.beetrootmonkey.fabrictest.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

public class DemoBlockEntity extends BlockEntity {
  public DemoBlockEntity() {
    super(ModBlockEntities.DEMO_BLOCK_ENTITY);
  }

  @Override
  public void fromTag(BlockState blockState, CompoundTag compoundTag) {
    super.fromTag(blockState, compoundTag);
//    experience = compoundTag.getFloat("Experience");
  }

  @Override
  public CompoundTag toTag(CompoundTag compoundTag) {
    super.toTag(compoundTag);
//    compoundTag.putFloat("Experience", experience);
    return compoundTag;
  }
}
