package com.beetrootmonkey.fabrictest.block.entity;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ModBlockEntityType<T extends BlockEntity> extends BlockEntityType<T> {
  private static final Logger LOGGER = LogManager.getLogger();

  public static final BlockEntityType<ModFurnaceBlockEntity> FURNACE;

  static {
    FURNACE = create("furnace2", ModBlockEntityType.Builder.create(ModFurnaceBlockEntity::new, Blocks.FURNACE));
  }

  public ModBlockEntityType(Supplier<? extends T> supplier, Set<Block> blocks, Type<?> type) {
    super(supplier, blocks, type);
  }

  @Nullable
  @Override
  public T instantiate() {
    return super.instantiate();
  }

  @Override
  public boolean supports(Block block) {
    return true;
  }

  @Nullable
  @Override
  public T get(BlockView world, BlockPos pos) {
    return super.get(world, pos);
  }

  private static <T extends BlockEntity> BlockEntityType<T> create(String path, ModBlockEntityType.Builder<T> builder) {
    if (builder.blocks.isEmpty()) {
      LOGGER.warn("Block entity type {} requires at least one valid block to be defined!", path);
    }

    return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, path), builder.build(null));
  }

  public static final class Builder<T extends BlockEntity> {
    private final Supplier<? extends T> supplier;
    private final Set<Block> blocks;

    private Builder(Supplier<? extends T> supplier, Set<Block> blocks) {
      this.supplier = supplier;
      this.blocks = blocks;
    }

    public static <T extends BlockEntity> ModBlockEntityType.Builder<T> create(Supplier<? extends T> supplier, Block... blocks) {
      return new ModBlockEntityType.Builder(supplier, ImmutableSet.copyOf(blocks));
    }

    public BlockEntityType<T> build(Type<?> type) {
      return new BlockEntityType(this.supplier, this.blocks, type);
    }
  }
}
