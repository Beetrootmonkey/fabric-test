package com.beetrootmonkey.fabrictest.recipe;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ModRecipes {

  public static void doRegistration() {
    Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(MOD_ID, DummyRecipeSerializer.ID), DummyRecipeSerializer.INSTANCE);
    Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, DummyRecipe.Type.ID), DummyRecipe.Type.INSTANCE);
  }
}
