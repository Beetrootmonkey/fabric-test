package com.beetrootmonkey.fabrictest.block;


import com.beetrootmonkey.fabrictest.gui.GuiType;
import com.beetrootmonkey.fabrictest.block.entity.IronFurnaceBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class IronFurnaceBlock extends GenericMachineBlock {

  public IronFurnaceBlock() {
    super(GuiType.IRON_FURNACE, IronFurnaceBlockEntity::new);
  }

  // Block
  @Environment(EnvType.CLIENT)
  public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    if (!isActive(stateIn)) {
      return;
    }

    final double x = (double) pos.getX() + 0.5D;
    final double y = (double) pos.getY() + 2.0D / 16.0D + rand.nextDouble() * 5.0D / 16.0D;
    final double z = (double) pos.getZ() + 0.5D;
    if (rand.nextDouble() < 0.1D) {
      worldIn.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
    }

    Direction facing = (Direction) stateIn.get(FACING);
    Direction.Axis facing$Axis = facing.getAxis();
    double double_5 = rand.nextDouble() * 0.6D - 0.3D;
    double deltaX = facing$Axis == Direction.Axis.X ? (double) facing.getOffsetX() * 0.52D : double_5;
    double deltaZ = facing$Axis == Direction.Axis.Z ? (double) facing.getOffsetZ() * 0.52D : double_5;
    worldIn.addParticle(ParticleTypes.SMOKE, x + deltaX, y, z + deltaZ, 0.0D, 0.0D, 0.0D);
    worldIn.addParticle(ParticleTypes.FLAME, x + deltaX, y, z + deltaZ, 0.0D, 0.0D, 0.0D);
  }
}
