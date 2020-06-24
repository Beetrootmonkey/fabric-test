package com.beetrootmonkey.fabrictest.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GrinderRecipeSerializer implements RecipeSerializer<GrinderRecipe> {
  // Define ExampleRecipeSerializer as a singleton by making its constructor private and exposing an instance.
  private GrinderRecipeSerializer() {
  }

  public static final GrinderRecipeSerializer INSTANCE = new GrinderRecipeSerializer();

  // This will be the "type" field in the json
  public static final String ID = GrinderRecipe.Type.ID;


  @Override
  // Turns json into Recipe
  public GrinderRecipe read(Identifier id, JsonObject json) {
    GrinderRecipeJsonFormat recipeJson = new Gson().fromJson(json, GrinderRecipeJsonFormat.class);
    // Validate all fields are there
    if (recipeJson.input == null || recipeJson.outputItem == null) {
      throw new JsonSyntaxException("A required attribute is missing!");
    }
    if (recipeJson.outputAmount == null) recipeJson.outputAmount = 1;
    if (recipeJson.bonusAmountMin == null) recipeJson.bonusAmountMin = 0;
    if (recipeJson.bonusAmountMax == null) recipeJson.bonusAmountMax = recipeJson.bonusAmountMin;
    if (recipeJson.bonusChance == null) recipeJson.bonusChance = 0f;

    Ingredient inputA = Ingredient.fromJson(recipeJson.input);
    Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem))
      // Validate the inputted item actually exists
      .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
    ItemStack output = new ItemStack(outputItem);

    return new GrinderRecipe(inputA, output, recipeJson.outputAmount, recipeJson.bonusAmountMin, recipeJson.bonusAmountMax, recipeJson.bonusChance, id);
  }

  @Override
  // Turns Recipe into PacketByteBuf
  public void write(PacketByteBuf packetData, GrinderRecipe recipe) {
    recipe.getInput().write(packetData);
    packetData.writeInt(recipe.getOutputAmount());
    packetData.writeInt(recipe.getBonusAmountMin());
    packetData.writeInt(recipe.getBonusAmountMax());
    packetData.writeFloat(recipe.getBonusChance());
    packetData.writeItemStack(recipe.getOutput());
  }

  @Override
  // Turns PacketByteBuf into Recipe
  public GrinderRecipe read(Identifier id, PacketByteBuf packetData) {
    // Make sure the read in the same order you have written!
    Ingredient input = Ingredient.fromPacket(packetData);
    int outputAmount = packetData.readInt();
    int bonusAmountMin = packetData.readInt();
    int bonusAmountMax = packetData.readInt();
    float bonusChance = packetData.readFloat();
    ItemStack output = packetData.readItemStack();
    return new GrinderRecipe(input, output, outputAmount, bonusAmountMin, bonusAmountMax, bonusChance, id);
  }
}
