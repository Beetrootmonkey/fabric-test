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
    if (recipeJson.input == null) {
      throw new JsonSyntaxException("Attribute 'input' is missing!");
    } else if (recipeJson.outputItem == null) {
      throw new JsonSyntaxException("Attribute 'outputItem' is missing!");
    } else if (recipeJson.outputAmount == null) {
      throw new JsonSyntaxException("Attribute 'outputAmount' is missing!");
    } else if (recipeJson.outputAmount < 1) {
      throw new JsonSyntaxException("Attribute 'outputAmount' must be greater than 0!");
    } else if (recipeJson.duration == null) {
      throw new JsonSyntaxException("Attribute 'duration' is missing!");
    } else if (recipeJson.duration < 1) {
      throw new JsonSyntaxException("Attribute 'duration' must be greater than 0!");
    }

    if (recipeJson.outputBonusMin == null) recipeJson.outputBonusMin = 0;
    if (recipeJson.outputBonusMax == null) recipeJson.outputBonusMax = recipeJson.outputBonusMin;
    if (recipeJson.bonusChance == null) recipeJson.bonusChance = 0f;

    Ingredient inputA = Ingredient.fromJson(recipeJson.input);
    Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem))
      // Validate the inputted item actually exists
      .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
    ItemStack output = new ItemStack(outputItem);

    return new GrinderRecipe(inputA, output, recipeJson.outputAmount, recipeJson.bonusChance, recipeJson.outputBonusMin, recipeJson.outputBonusMax, recipeJson.duration, id);
  }

  @Override
  // Turns Recipe into PacketByteBuf
  public void write(PacketByteBuf packetData, GrinderRecipe recipe) {
    recipe.getInput().write(packetData);
    packetData.writeInt(recipe.getOutputAmount());
    packetData.writeFloat(recipe.getBonusChance());
    packetData.writeInt(recipe.getOutputBonusMin());
    packetData.writeInt(recipe.getOutputBonusMax());
    packetData.writeInt(recipe.getDuration());
    packetData.writeItemStack(recipe.getOutput());
  }

  @Override
  // Turns PacketByteBuf into Recipe
  public GrinderRecipe read(Identifier id, PacketByteBuf packetData) {
    // Make sure the read in the same order you have written!
    Ingredient input = Ingredient.fromPacket(packetData);
    int outputAmount = packetData.readInt();
    float bonusChance = packetData.readFloat();
    int outputBonusMin = packetData.readInt();
    int outputBonusMax = packetData.readInt();
    int duration = packetData.readInt();
    ItemStack output = packetData.readItemStack();
    return new GrinderRecipe(input, output, outputAmount, bonusChance, outputBonusMin, outputBonusMax, duration, id);
  }
}
