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

public class DummyRecipe implements Recipe<Inventory> {

  private final Ingredient inputA;
  private final Ingredient inputB;
  private final ItemStack outputStack;
  private final Identifier id;

  public DummyRecipe(Ingredient inputA, Ingredient inputB, ItemStack outputStack, Identifier id) {
    this.inputA = inputA;
    this.inputB = inputB;
    this.outputStack = outputStack;
    this.id = id;
  }

  public Ingredient getInputA() {
    return inputA;
  }

  public Ingredient getInputB() {
    return inputB;
  }

  @Override
  public boolean matches(Inventory inv, World world) {
    return inputA.test(inv.getStack(0));
//    return inputA.test(inv.getStack(0)) && inputB.test(inv.getStack(1));
  }

  @Override
  public DefaultedList<Ingredient> getPreviewInputs() {
    DefaultedList<Ingredient> list = DefaultedList.copyOf(inputA, inputA);
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
    return outputStack;
  }

  @Override
  public Identifier getId() {
    return id;
  }

  public static class Type implements RecipeType<DummyRecipe> {
    // Type as a singleton by making its constructor private and exposing an instance.
    private Type() {
    }

    public static final Type INSTANCE = new Type();

    // This will be needed in step 4
    public static final String ID = "dummy";
  }

  @Override
  public RecipeType<?> getType() {
    return Type.INSTANCE;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return DummyRecipeSerializer.INSTANCE;
  }
}
