package com.beetrootmonkey.fabrictest.recipe;


import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeUtils {
  /**
   * Used to get the matching output of a recipe type that only has 1 input
   */
  public static <T extends Recipe<?>> ItemStack getMatchingRecipes(World world, RecipeType<T> type, ItemStack input) {
    return getRecipes(world, type).stream()
      .filter(recipe -> {
        DefaultedList<Ingredient> list = recipe.getPreviewInputs();
        int size = list != null ? list.size() : 0;
        if (size >= 1) {
          Ingredient ingredient = list.get(0);
          return ingredient.test(input);
        }
        return false;
      })
      .map(Recipe::getOutput)
      .findFirst()
      .orElse(ItemStack.EMPTY);
  }

  public static <T extends Recipe<?>> List<Recipe<?>> getRecipes(World world, RecipeType<T> type) {
    return world.getRecipeManager().values().stream().filter(iRecipe -> iRecipe.getType() == type).collect(Collectors.toList());
  }
}
