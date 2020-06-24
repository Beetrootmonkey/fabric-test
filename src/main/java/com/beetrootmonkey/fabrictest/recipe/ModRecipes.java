package com.beetrootmonkey.fabrictest.recipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ModRecipes {

  public static RecipeType<GrinderRecipe> GRINDING = GrinderRecipe.Type.INSTANCE;

  private static Map<Identifier, RecipeType<?>> recipeTypes = new HashMap<>();

  public static Identifier getIdentifier(RecipeType<?> type) {
    AtomicReference<Identifier> result = new AtomicReference<>();
    recipeTypes.forEach((identifier, recipeType) -> {
      if (Objects.equals(type, recipeType)) {
        result.set(identifier);
      }
    });
    return result.get();
  }

  public static void doRegistration() {
    Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(MOD_ID, GrinderRecipeSerializer.ID), GrinderRecipeSerializer.INSTANCE);
    registerRecipeType(GRINDING, GrinderRecipe.Type.ID);
  }

  private static void registerRecipeType(RecipeType<?> recipeType, String path) {
    Identifier identifier = new Identifier(MOD_ID, path);
    recipeTypes.put(identifier, recipeType);
    Registry.register(Registry.RECIPE_TYPE, identifier, recipeType);
  }
}
