package com.beetrootmonkey.fabrictest.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import static com.beetrootmonkey.fabrictest.Main.RANDOM;

public class GrinderRecipe implements Recipe<Inventory> {

  private final Ingredient input;
  private final ItemStack outputItem;
  private final int outputAmount;
  private final float bonusChance;
  private final int outputBonusMin;
  private final int outputBonusMax;
  private final int duration;
  private final Identifier id;

  public GrinderRecipe(Ingredient input, ItemStack outputItem, int outputAmount, float bonusChance, int outputBonusMin, int outputBonusMax, int duration, Identifier id) {
    this.input = input;
    this.outputItem = outputItem;
    this.outputItem.setCount(outputAmount);
    this.outputAmount = outputAmount;
    this.outputBonusMin = outputBonusMin;
    this.outputBonusMax = Math.max(outputBonusMin, outputBonusMax);
    this.bonusChance = bonusChance;
    this.duration = duration;
    this.id = id;
  }

  public Ingredient getInput() {
    return input;
  }

  public int getOutputAmount() {
    return outputAmount;
  }

  public int getOutputBonusMin() {
    return outputBonusMin;
  }

  public int getOutputBonusMax() {
    return outputBonusMax;
  }

  public float getBonusChance() {
    return bonusChance;
  }

  public ItemStack getOutputItem() {
    return outputItem;
  }

  public int getDuration() {
    return duration;
  }

  @Override
  public boolean matches(Inventory inv, World world) {
    return input.test(inv.getStack(0)); // Slot 0 is the "input" slot, I suppose
  }

  @Override
  public DefaultedList<Ingredient> getPreviewInputs() {
    DefaultedList<Ingredient> list = DefaultedList.copyOf(input, input);
    return list;
  }

  @Override
  public ItemStack craft(Inventory inv) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean fits(int width, int height) {
    return false;
  }

  @Override
  public ItemStack getOutput() {
    ItemStack result = outputItem.copy();
    int amount = outputAmount;
    if (RANDOM.nextFloat() < bonusChance) {
      amount += outputBonusMin + RANDOM.nextInt(outputBonusMax - outputBonusMin + 1);
    }
    result.setCount(amount);
    return result;
  }

  @Override
  public Identifier getId() {
    return id;
  }

  public static class Type implements RecipeType<GrinderRecipe> {
    // Type as a singleton by making its constructor private and exposing an instance.
    private Type() {
    }

    public static final Type INSTANCE = new Type();
    public static final String ID = "grinding";
  }

  @Override
  public RecipeType<?> getType() {
    return Type.INSTANCE;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return GrinderRecipeSerializer.INSTANCE;
  }
}
