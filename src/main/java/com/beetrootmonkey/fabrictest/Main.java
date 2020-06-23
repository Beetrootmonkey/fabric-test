package com.beetrootmonkey.fabrictest;

import com.beetrootmonkey.fabrictest.block.BaseBlock;
import com.beetrootmonkey.fabrictest.recipe.DummyRecipe;
import com.beetrootmonkey.fabrictest.recipe.DummyRecipeSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Random;
import java.util.function.Consumer;

public class Main implements ModInitializer {

  public static final String MOD_ID = "fabrictest";
  public static Random random;
  public static final Item COPPER_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
  public static final Block TEST_BLOCK = new BaseBlock(FabricBlockSettings.of(Material.WOOL).dropsNothing().sounds(BlockSoundGroup.CHAIN));

  @Override
  public void onInitialize() {
    random = new Random();
    Registry.register(Registry.ITEM, new Identifier(MOD_ID, "copper_ingot"), COPPER_INGOT);
    Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "test_block"), TEST_BLOCK);
    Registry.register(Registry.ITEM, new Identifier(MOD_ID, "test_block"), new BlockItem(TEST_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
    Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "iron_furnace"), TRContent.Machine.IRON_FURNACE.block);
    Registry.register(Registry.ITEM, new Identifier(MOD_ID, "iron_furnace"), new BlockItem(TRContent.Machine.IRON_FURNACE.block, new Item.Settings().group(ItemGroup.MISC)));
    Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(MOD_ID, DummyRecipeSerializer.ID), DummyRecipeSerializer.INSTANCE);
    Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, DummyRecipe.Type.ID), DummyRecipe.Type.INSTANCE);


    SheepShearCallback.EVENT.register(this::handleSheepShear);

    Registry.BIOME.forEach(this::handleBiome);
    //Listen for other biomes being registered
    RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> handleBiome(biome));

  }

  private ActionResult handleSheepShear(PlayerEntity player, SheepEntity sheep, Hand hand) {
    sheep.setSheared(true);
    player.world.playSoundFromEntity((PlayerEntity) null, sheep, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);

    // create diamond item entity at sheep position
    ItemStack stack = new ItemStack(Items.DIAMOND);
    ItemEntity itemEntity = new ItemEntity(player.world, sheep.getX(), sheep.getY() + 0.5f, sheep.getZ(), stack);
    itemEntity.setVelocity(itemEntity.getVelocity().add((double) ((random.nextFloat() - random.nextFloat()) * 0.1F), (double) (random.nextFloat() * 0.05F), (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
    player.world.spawnEntity(itemEntity);

    ItemStack itemStack = player.getStackInHand(hand);
    itemStack.damage(1, (LivingEntity) player, (Consumer) ((playerEntity) -> {
      ((PlayerEntity) playerEntity).sendToolBreakStatus(hand);
    }));

    return ActionResult.FAIL;
  }

  private void handleBiome(Biome biome) {
    if(biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
      biome.addFeature(
        GenerationStep.Feature.UNDERGROUND_ORES,
        Feature.ORE.configure(
          new OreFeatureConfig(
            OreFeatureConfig.Target.NATURAL_STONE,
            Blocks.NETHER_QUARTZ_ORE.getDefaultState(),
            8 //Ore vein size
          )).createDecoratedFeature(
          Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(
            8, //Number of veins per chunk
            0, //Bottom Offset
            0, //Min y level
            64 //Max y level
          ))));
    }
  }
}
