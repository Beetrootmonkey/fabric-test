package com.beetrootmonkey.fabrictest.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

class GrinderRecipeJsonFormat {
  JsonObject input;
  String outputItem;
  Integer outputAmount;
  Integer bonusAmountMin;
  Integer bonusAmountMax;
  Float bonusChance;
}
