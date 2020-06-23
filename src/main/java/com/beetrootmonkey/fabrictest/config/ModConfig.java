package com.beetrootmonkey.fabrictest.config;


import reborncore.common.config.Config;

//All moved into one class as its a lot easier to find the annotations when you know where they all are
public class ModConfig {

  @Config(config = "generators", category = "generator", key = "experiencePerGrind", comment = "Amount of experience each grinding operation gives")
  public static int experiencePerGrind = 1;

  // Items
  @Config(config = "items", category = "general", key = "enableGemTools", comment = "Enable Gem armor and tools")
  public static boolean enableGemArmorAndTools = true;
}
