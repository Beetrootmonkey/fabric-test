/*
 * This file is part of TechReborn, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2020 TechReborn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.beetrootmonkey.fabrictest.compat.rei;

import com.beetrootmonkey.fabrictest.recipe.GrinderRecipe;
import com.beetrootmonkey.fabrictest.recipe.ModRecipes;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import me.shedaniel.rei.utils.CollectionUtils;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MachineRecipeDisplay<R extends GrinderRecipe> implements RecipeDisplay {

  private final R recipe;
  private List<List<EntryStack>> inputs;
  private List<EntryStack> outputs;
  private int outputAmount;
  private float bonusChance;
  private int outputBonusMin;
  private int outputBonusMax;
  private int duration;

  public MachineRecipeDisplay(R recipe) {
    this.recipe = recipe;
    this.inputs = new ArrayList<>();
    this.inputs.add(CollectionUtils.map(recipe.getInput().getMatchingStacksClient(), EntryStack::create));
    this.outputs = new ArrayList<>();
    this.outputs.add(EntryStack.create(recipe.getOutputItem()));
    this.outputAmount = recipe.getOutputAmount();
    this.outputBonusMin = recipe.getOutputBonusMin();
    this.outputBonusMax = recipe.getOutputBonusMax();
    this.bonusChance = recipe.getBonusChance();
    this.duration = recipe.getDuration();
  }

  public int getOutputAmount() {
    return outputAmount;
  }

  public float getBonusChance() {
    return bonusChance;
  }

  public int getOutputBonusMin() {
    return outputBonusMin;
  }

  public int getOutputBonusMax() {
    return outputBonusMax;
  }

  public int getDuration() {
    return duration;
  }

  @Override
  public Optional<Identifier> getRecipeLocation() {
    return Optional.ofNullable(recipe).map(GrinderRecipe::getId);
  }

  @Override
  public List<List<EntryStack>> getInputEntries() {
    return inputs;
  }

  @Override
  public List<List<EntryStack>> getRequiredEntries() {
    return inputs;
  }

  @Override
  public List<EntryStack> getOutputEntries() {
    return outputs;
  }

  @Override
  public Identifier getRecipeCategory() {
    return ModRecipes.getIdentifier(ModRecipes.GRINDING);
  }
}
