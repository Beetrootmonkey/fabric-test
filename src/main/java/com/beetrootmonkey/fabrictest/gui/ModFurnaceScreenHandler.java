package com.beetrootmonkey.fabrictest.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;

public class ModFurnaceScreenHandler extends AbstractFurnaceScreenHandler {
   public ModFurnaceScreenHandler(int syncId, PlayerInventory playerInventory) {
      super(ScreenHandlerType.FURNACE, RecipeType.SMELTING, syncId, playerInventory);
   }

   public ModFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
      super(ScreenHandlerType.FURNACE, RecipeType.SMELTING, syncId, playerInventory, inventory, propertyDelegate);
   }
}