package com.beetrootmonkey.fabrictest;

import com.beetrootmonkey.fabrictest.block.ModBlocks;
import com.beetrootmonkey.fabrictest.config.ModConfig;
import com.beetrootmonkey.fabrictest.event.EventHandling;
import com.beetrootmonkey.fabrictest.item.ModItems;
import com.beetrootmonkey.fabrictest.recipe.ModRecipes;
import net.fabricmc.api.ModInitializer;

import java.util.Random;

public class Main implements ModInitializer {

  public static final String MOD_ID = "fabrictest";
  public static final Random RANDOM = new Random();

  @Override
  public void onInitialize() {
    ModConfig.doRegistration();
    ModRecipes.doRegistration();
    ModItems.doRegistration();
    ModBlocks.doRegistration();
    EventHandling.doRegistration();


  }
}
