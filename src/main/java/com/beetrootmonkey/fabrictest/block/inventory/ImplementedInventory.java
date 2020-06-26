package com.beetrootmonkey.fabrictest.block.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.Set;

public interface ImplementedInventory extends Inventory {
  /**
   * Gets the item list of this inventory.
   * Must return the same instance every time it's called.
   */
  DefaultedList<ItemStack> getItems();
  // Creation

  /**
   * Creates an inventory from the item list.
   */
  static ImplementedInventory of(DefaultedList<ItemStack> items) {
    return () -> items;
  }

  /**
   * Creates a new inventory with the size.
   */
  static ImplementedInventory ofSize(int size) {
    return of(DefaultedList.ofSize(size, ItemStack.EMPTY));
  }
  // Inventory

  /**
   * Returns the inventory size.
   */
  @Override
  default int size() {
    return getItems().size();
  }

  /**
   * @return true if this inventory has only empty stacks, false otherwise
   */
  @Override
  default boolean isEmpty() {
    for (int i = 0; i < size(); i++) {
      ItemStack stack = getStack(i);
      if (!stack.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Gets the item in the slot.
   */
  @Override
  default ItemStack getStack(int slot) {
    return getItems().get(slot);
  }

  /**
   * Takes a stack of the size from the slot.
   * <p>(default implementation) If there are less items in the slot than what are requested,
   * takes all items in that slot.
   */
  @Override
  default ItemStack removeStack(int slot, int amount) {
    ItemStack result = Inventories.splitStack(getItems(), slot, amount);
    if (!result.isEmpty()) {
      markDirty();
    }
    return result;
  }

  /**
   * Removes the current stack in the {@code slot} and returns it.
   */
  @Override
  default ItemStack removeStack(int slot) {
    return Inventories.removeStack(getItems(), slot);
  }

  /**
   * Replaces the current stack in the {@code slot} with the provided stack.
   * <p>If the stack is too big for this inventory ({@link Inventory#getMaxCountPerStack()}),
   * it gets resized to this inventory's maximum amount.
   */
  @Override
  default void setStack(int slot, ItemStack stack) {
    getItems().set(slot, stack);
    if (stack.getCount() > getMaxCountPerStack()) {
      stack.setCount(getMaxCountPerStack());
    }
  }

  /**
   * Clears {@linkplain #getItems() the item list}}.
   */
  @Override
  default void clear() {
    getItems().clear();
  }

  @Override
  default void markDirty() {
    // Override if you want behavior.
  }

  @Override
  default boolean canPlayerUse(PlayerEntity player) {
    return true;
  }

  ///////////////////

  /**
   * Returns the maximum number of items a stack can contain when placed inside this inventory.
   * No slots may have more than this number of items. It is effectively the
   * stacking limit for this inventory's slots.
   *
   * @return the max {@link ItemStack#getCount() count} of item stacks in this inventory
   */
  @Override
  default int getMaxCountPerStack() {
    return 64;
  }

  @Override
  default void onOpen(PlayerEntity player) {

  }

  @Override
  default void onClose(PlayerEntity player) {

  }

  /**
   * Returns whether the given stack is a valid for the indicated slot position.
   *
   * @param slot
   * @param stack
   */
  @Override
  default boolean isValid(int slot, ItemStack stack) {
    return false;
  }

  /**
   * Returns the number of times the specified item occurs in this inventory across all stored stacks.
   *
   * @param item
   */
  @Override
  default int count(Item item) {
    return 0;
  }

  /**
   * Determines whether this inventory contains any of the given candidate items.
   *
   * @param items
   */
  @Override
  default boolean containsAny(Set<Item> items) {
    return false;
  }
}
