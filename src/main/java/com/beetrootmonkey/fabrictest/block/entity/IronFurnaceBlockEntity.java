package com.beetrootmonkey.fabrictest.block.entity;


import com.beetrootmonkey.fabrictest.TRContent;
import com.beetrootmonkey.fabrictest.recipe.DummyRecipe;
import com.beetrootmonkey.fabrictest.recipe.RecipeUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.client.screen.builder.ScreenHandlerBuilder;
import reborncore.common.util.RebornInventory;

import static com.beetrootmonkey.fabrictest.config.ModConfig.experiencePerGrind;

public class IronFurnaceBlockEntity extends AbstractIronMachineBlockEntity implements BuiltScreenHandlerProvider {

  int inputSlot = 0;
  int outputSlot = 1;
  public float experience;

  public IronFurnaceBlockEntity() {
    super(TRBlockEntities.IRON_FURNACE, 2, TRContent.Machine.IRON_FURNACE.block);
    this.inventory = new RebornInventory<>(3, "IronFurnaceBlockEntity", 64, this);
  }

  public void handleGuiInputFromClient(PlayerEntity playerIn) {
    if (playerIn instanceof ServerPlayerEntity) {
      ServerPlayerEntity player = (ServerPlayerEntity) playerIn;
      int totalExperience = (int) experience;
      while (totalExperience > 0) {
        int expToDrop = ExperienceOrbEntity.roundToOrbSize(totalExperience);
        totalExperience -= expToDrop;
        player.world.spawnEntity(new ExperienceOrbEntity(player.world, player.getX(), player.getY() + 0.5D, player.getZ() + 0.5D, expToDrop));
      }
    }
    experience = 0;
  }

  private ItemStack getResultFor(ItemStack stack) {
//    return new ItemStack(Items.DIAMOND);
    ItemStack result = RecipeUtils.getMatchingRecipes(world, DummyRecipe.Type.INSTANCE, stack);
    if (!result.isEmpty()) {
      return result.copy();
    }
    return ItemStack.EMPTY;
  }

  private float getExperienceFor(ItemStack stack) {
    return experiencePerGrind;
  }

  // AbstractIronMachineBlockEntity
  @Override
  protected void smelt() {
    if (!canSmelt()) {
      return;
    }
    ItemStack inputStack = inventory.getStack(inputSlot);
    ItemStack resultStack = getResultFor(inputStack);

    if (inventory.getStack(outputSlot).isEmpty()) {
      inventory.setStack(outputSlot, resultStack.copy());
    } else if (inventory.getStack(outputSlot).isItemEqualIgnoreDamage(resultStack)) {
      inventory.getStack(outputSlot).increment(resultStack.getCount());
    }
    experience += getExperienceFor(inputStack);
    if (inputStack.getCount() > 1) {
      inventory.shrinkSlot(inputSlot, 1);
    } else {
      inventory.setStack(inputSlot, ItemStack.EMPTY);
    }
  }

  @Override
  protected boolean canSmelt() {
    if (inventory.getStack(inputSlot).isEmpty()) {
      return false;
    }
    ItemStack outputStack = getResultFor(inventory.getStack(inputSlot));
    if (outputStack.isEmpty())
      return false;
    if (inventory.getStack(outputSlot).isEmpty())
      return true;
    if (!inventory.getStack(outputSlot).isItemEqualIgnoreDamage(outputStack))
      return false;
    int result = inventory.getStack(outputSlot).getCount() + outputStack.getCount();
    return result <= inventory.getStackLimit() && result <= outputStack.getMaxCount();
  }

  @Override
  public boolean isStackValid(int slotID, ItemStack stack) {
    return !getResultFor(stack).isEmpty();
  }

  @Override
  public void fromTag(BlockState blockState, CompoundTag compoundTag) {
    super.fromTag(blockState, compoundTag);
    experience = compoundTag.getFloat("Experience");
    inventory.read(compoundTag);
  }

  @Override
  public CompoundTag toTag(CompoundTag compoundTag) {
    super.toTag(compoundTag);
    compoundTag.putFloat("Experience", experience);
    inventory.write(compoundTag);
    return compoundTag;
  }

  // IContainerProvider
  public float getExperience() {
    return experience;
  }

  public void setExperience(float experience) {
    this.experience = experience;
  }

  @Override
  public int[] getInputSlots() {
    return new int[]{inputSlot};
  }

  @Override
  public BuiltScreenHandler createScreenHandler(int syncID, final PlayerEntity player) {
    return new ScreenHandlerBuilder("ironfurnace").player(player.inventory).inventory().hotbar()
      .addInventory().blockEntity(this)
      .fuelSlot(2, 56, 53).slot(0, 56, 17).outputSlot(1, 116, 35)
      .sync(this::getBurnTime, this::setBurnTime)
      .sync(this::getProgress, this::setProgress)
      .sync(this::getTotalBurnTime, this::setTotalBurnTime)
      .sync(this::getExperience, this::setExperience)
      .addInventory().create(this, syncID);
  }
}
