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

import com.beetrootmonkey.fabrictest.block.ModBlocks;
import com.beetrootmonkey.fabrictest.item.ModItems;
import com.beetrootmonkey.fabrictest.recipe.GrinderRecipe;
import com.beetrootmonkey.fabrictest.recipe.ModRecipes;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.*;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import reborncore.api.blockentity.IUpgradeable;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.builder.slot.GuiTab;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ReiPlugin implements REIPluginV0 {

  public static final Identifier PLUGIN = new Identifier(MOD_ID, "fabrictest_plugin");

  public static final Map<RecipeType<?>, ItemConvertible> iconMap = new HashMap<>();

  public ReiPlugin() {
    iconMap.put(ModRecipes.GRINDING, ModItems.GRINDSTONE);
  }

  @Override
  public Identifier getPluginIdentifier() {
    return PLUGIN;
  }

  @Override
  public void registerPluginCategories(RecipeHelper recipeHelper) {
    recipeHelper.registerCategory(new MachineRecipeCategory<>(ModRecipes.GRINDING));
  }

  @Override
  public void registerRecipeDisplays(RecipeHelper recipeHelper) {

    // TODO
    // Or use .getAllMatches if you want all of the matches
//    Optional<GrinderRecipe> match = world.getRecipeManager()
//      .getFirstMatch(GrinderRecipe.Type.INSTANCE, inventory, world);
//    recipeHelper.getAllSortedRecipes().stream().filter(recipe -> recipe.getType() == ModRecipes.GRINDING)

//    RecipeManager.getRecipeTypes(MOD_ID).forEach(recipeType -> registerMachineRecipe(recipeHelper, recipeType));

    registerMachineRecipe(recipeHelper, ModRecipes.GRINDING);
  }

  @Override
  public void registerOthers(RecipeHelper recipeHelper) {
    recipeHelper.registerWorkingStations(ModRecipes.getIdentifier(ModRecipes.GRINDING), EntryStack.create(ModBlocks.IRON_FURNACE));
  }

  private <R extends GrinderRecipe> void registerMachineRecipe(RecipeHelper recipeHelper, RecipeType<R> recipeType) {
    Function<R, RecipeDisplay> recipeDisplay = r -> new MachineRecipeDisplay<GrinderRecipe>(r);


    recipeHelper.registerRecipes(ModRecipes.getIdentifier(recipeType), (Predicate<Recipe>) recipe -> {
      return recipe.getType() == recipeType;
    }, recipeDisplay);
  }

  @Override
  public void registerBounds(DisplayHelper displayHelper) {
    BaseBoundsHandler baseBoundsHandler = BaseBoundsHandler.getInstance();
    baseBoundsHandler.registerExclusionZones(GuiBase.class, () -> {
      Screen currentScreen = MinecraftClient.getInstance().currentScreen;
      if (currentScreen instanceof GuiBase) {
        GuiBase<?> guiBase = (GuiBase<?>) currentScreen;
        int height = 0;
        if (guiBase.tryAddUpgrades() && guiBase.be instanceof IUpgradeable) {
          IUpgradeable upgradeable = (IUpgradeable) guiBase.be;
          if (upgradeable.canBeUpgraded()) {
            height = 80;
          }
        }
        for (GuiTab slot : guiBase.getTabs()) {
          if (slot.enabled()) {
            height += 24;
          }
        }
        if (height > 0) {
          int width = 20;
          return Collections.singletonList(new Rectangle(guiBase.getGuiLeft() - width, guiBase.getGuiTop() + 8, width, height));
        }
      }
      return Collections.emptyList();
    });
  }
}
