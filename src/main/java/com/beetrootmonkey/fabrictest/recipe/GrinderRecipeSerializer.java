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
        if (recipeJson.inputA == null || recipeJson.inputB == null || recipeJson.outputItem == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        // We'll allow to not specify the output, and default it to 1.
        if (recipeJson.outputAmount == 0) recipeJson.outputAmount = 1;

        Ingredient inputA = Ingredient.fromJson(recipeJson.inputA);
        Ingredient inputB = Ingredient.fromJson(recipeJson.inputB);
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputItem))
          // Validate the inputted item actually exists
          .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.outputItem));
        ItemStack output = new ItemStack(outputItem, recipeJson.outputAmount);

        return new GrinderRecipe(inputA, inputB, output, id);
    }
    @Override
    // Turns Recipe into PacketByteBuf
    public void write(PacketByteBuf packetData, GrinderRecipe recipe) {
        recipe.getInputA().write(packetData);
        recipe.getInputB().write(packetData);
        packetData.writeItemStack(recipe.getOutput());
    }

    @Override
    // Turns PacketByteBuf into Recipe
    public GrinderRecipe read(Identifier id, PacketByteBuf packetData) {
        // Make sure the read in the same order you have written!
        Ingredient inputA = Ingredient.fromPacket(packetData);
        Ingredient inputB = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        return new GrinderRecipe(inputA, inputB, output, id);
    }
}
