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
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.widgets.Label;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.entries.RecipeEntry;
import me.shedaniel.rei.gui.entries.SimpleRecipeEntry;
import me.shedaniel.rei.gui.widget.Widget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MachineRecipeCategory<R extends GrinderRecipe> implements RecipeCategory<MachineRecipeDisplay<R>> {

  private final RecipeType<R> recipeType;
  private int recipeLines;

  MachineRecipeCategory(RecipeType<R> recipeType) {
    this(recipeType, 1);
  }

  MachineRecipeCategory(RecipeType<R> recipeType, int lines) {
    this.recipeType = recipeType;
    this.recipeLines = lines;
  }

  @Override
  public Identifier getIdentifier() {
    return ModRecipes.getIdentifier(recipeType);
  }

  @Override
  public String getCategoryName() {
    return I18n.translate(getIdentifier().toString());
  }

  @Override
  public EntryStack getLogo() {
    return EntryStack.create(ReiPlugin.iconMap.getOrDefault(recipeType, () -> Items.DIAMOND_SHOVEL));
  }

  @Override
  public RecipeEntry getSimpleRenderer(MachineRecipeDisplay<R> recipe) {
    return SimpleRecipeEntry.create(Collections.singletonList(recipe.getInputEntries().get(0)), recipe.getOutputEntries());
  }

  @Override
  public List<Widget> setupDisplay(MachineRecipeDisplay<R> machineRecipe, Rectangle bounds) {
    Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() + 10);

    List<Widget> widgets = new LinkedList<>();
    widgets.add(Widgets.createRecipeBase(bounds));
//    widgets.add(Widgets.createArrow(new Point(startPoint.x + 26, startPoint.y + 1)).animationDurationTicks(200));
//    Widgets.createBurningFire(new Point(startPoint.x + 26, startPoint.y + 1));
//
//    int i = 0;
//    for (List<EntryStack> inputs : machineRecipe.getInputEntries()) {
//      widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 1 + (i++ * 20))).entries(inputs).markInput());
//    }
//
//    i = 0;
//    for (EntryStack outputs : machineRecipe.getOutputEntries()) {
//      widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 1 + (i++ * 20))).entry(outputs).markInput());
//    }
//

    double duration = machineRecipe.getDuration();
    int verticalOffset = -24;
    DecimalFormat df = new DecimalFormat("###.##");
    widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 9 + verticalOffset)));
    widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 1, startPoint.y + 20 + verticalOffset)).animationDurationMS(10000.0D));
    widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y + 8 + verticalOffset)).animationDurationTicks(duration));
    widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 1 + verticalOffset)).entries(machineRecipe.getInputEntries().get(0)).markInput());
    widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 9 + verticalOffset)).entries(machineRecipe.getOutputEntries()).disableBackground().markOutput());
    widgets.add(Widgets.createLabel(new Point(startPoint.x + 41, startPoint.y + 13), new TranslatableText("fabrictest:duration").append(": ").append(new TranslatableText("category.rei.campfire.time", df.format(duration / 20.0D)))).noShadow().color(-12566464, -4473925));

    int outputAmount = machineRecipe.getOutputAmount();
    int outputBonusMin = machineRecipe.getOutputBonusMin();
    int outputBonusMax = machineRecipe.getOutputBonusMax();
    float bonusChance = machineRecipe.getBonusChance();
    if (outputBonusMax > 0 && bonusChance > 0) {
      Text outputAmountText = new LiteralText("").append(new TranslatableText("fabrictest:bonus")).append(String.format(": %s - %s (%s%%)", outputBonusMin, outputBonusMax, Math.round(bonusChance * 100)));
      if (outputBonusMax == outputBonusMin) {
        outputAmountText = new LiteralText("").append(new TranslatableText("fabrictest:bonus")).append(String.format(": %s (%s%%)", outputBonusMin, Math.round(bonusChance * 100)));
      }

      Label outputAmountLabel;
      widgets.add(outputAmountLabel = Widgets.createLabel(new Point(startPoint.x, startPoint.y + 23), outputAmountText));
      outputAmountLabel.shadow(false);
      outputAmountLabel.leftAligned();
      outputAmountLabel.color(0xFF404040, 0xFFBBBBBB);
    }

    return widgets;
  }

  @Override
  public int getDisplayHeight() {
    return 49;
  }

}
