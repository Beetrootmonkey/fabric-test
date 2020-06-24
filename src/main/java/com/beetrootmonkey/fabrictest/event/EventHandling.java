package com.beetrootmonkey.fabrictest.event;

import com.beetrootmonkey.fabrictest.SheepShearCallback;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.function.Consumer;

import static com.beetrootmonkey.fabrictest.Main.RANDOM;

public class EventHandling {

  public static void doRegistration() {
    SheepShearCallback.EVENT.register(EventHandling::handleSheepShear);

    Registry.BIOME.forEach(EventHandling::handleBiome);
    //Listen for other biomes being registered
    RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> handleBiome(biome));
  }


  private static ActionResult handleSheepShear(PlayerEntity player, SheepEntity sheep, Hand hand) {
    sheep.setSheared(true);
    player.world.playSoundFromEntity(null, sheep, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);

    // create diamond item entity at sheep position
    ItemStack stack = new ItemStack(Items.DIAMOND);
    ItemEntity itemEntity = new ItemEntity(player.world, sheep.getX(), sheep.getY() + 0.5f, sheep.getZ(), stack);
    itemEntity.setVelocity(itemEntity.getVelocity().add((RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.1F, RANDOM.nextFloat() * 0.05F, (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.1F));
    player.world.spawnEntity(itemEntity);

    ItemStack itemStack = player.getStackInHand(hand);
    itemStack.damage(1, player, (Consumer) ((playerEntity) -> {
      ((PlayerEntity) playerEntity).sendToolBreakStatus(hand);
    }));

    return ActionResult.FAIL;
  }

  private static void handleBiome(Biome biome) {
    if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
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
