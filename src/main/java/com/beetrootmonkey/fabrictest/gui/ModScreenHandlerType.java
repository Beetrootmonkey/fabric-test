package com.beetrootmonkey.fabrictest.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ModScreenHandlerType<T extends ScreenHandler> {
  public static final ScreenHandlerType<ModFurnaceScreenHandler> FURNACE = register("furnace", ModFurnaceScreenHandler::new);
  private final ModScreenHandlerType.Factory<T> factory;

  private static <T extends ScreenHandler> ScreenHandlerType<T> register(String path, ModScreenHandlerType.Factory<T> factory) {
    return ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, path), new ModScreenHandlerType<>(factory).factory);
  }


  private ModScreenHandlerType(ModScreenHandlerType.Factory<T> factory) {
    this.factory = factory;
  }

  @Environment(EnvType.CLIENT)
  public T create(int syncId, PlayerInventory playerInventory) {
    return this.factory.create(syncId, playerInventory);
  }

  interface Factory<T extends ScreenHandler> extends ScreenHandlerRegistry.SimpleClientHandlerFactory<T> {
    @Environment(EnvType.CLIENT)
    T create(int syncId, PlayerInventory playerInventory);
  }
}
