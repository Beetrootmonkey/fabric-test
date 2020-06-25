package com.beetrootmonkey.fabrictest.block.entity;

import com.beetrootmonkey.fabrictest.gui.ModFurnaceScreenHandler;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ModFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
  public ModFurnaceBlockEntity() {
    super(ModBlockEntityType.FURNACE, RecipeType.SMELTING);
  }

  @Override
  protected Text getContainerName() {
    return new TranslatableText("fabrictest.container.furnace");
  }

  @Override
  protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
    return new ModFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
  }


}
