package com.beetrootmonkey.fabrictest.block;

import com.beetrootmonkey.fabrictest.recipe.GrinderRecipe;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class TestBlock extends BaseBlock {
  public TestBlock(AbstractBlock.Settings settings) {
    super(settings);
  }

  @Override
  public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    super.onBreak(world, pos, state, player);
    // Something that gives the player items should always go through the server.
    // If you need to notify the client in some way, check in the server and then send a packet to the client.
    if (!world.isClient()) {
      // For the sake of simplicity we draw the items off of the player's hands and create an inventory from that.
      // Usually you use an inventory of yours instead.
      Inventory inventory = new Inventory() {
        private ItemStack mainHand;
        private ItemStack offHand;

        {
          mainHand = player.getMainHandStack();
          offHand = player.getOffHandStack();
        }

        @Override
        public int size() {
          return 2;
        }

        @Override
        public boolean isEmpty() {
          return false;
        }

        @Override
        public ItemStack getStack(int slot) {
          switch (slot) {
            case 0:
              return mainHand;
            case 1:
              return offHand;
            default:
              return null;
          }
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
          return null;
        }

        @Override
        public ItemStack removeStack(int slot) {
          return null;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {

        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
          return false;
        }

        @Override
        public void clear() {

        }
      };

      // Or use .getAllMatches if you want all of the matches
      Optional<GrinderRecipe> match = world.getRecipeManager()
        .getFirstMatch(GrinderRecipe.Type.INSTANCE, inventory, world);

      if (match.isPresent()) {
        // Give the player the item and remove from what he has. Make sure to copy the ItemStack to not ruin it!
        player.inventory.offerOrDrop(world, match.get().getOutput().copy());
        player.getMainHandStack().decrement(1);
        player.getOffHandStack().decrement(1);
      } else {
        // If it doesn't match we tell the player
        player.sendMessage(new LiteralText("No match!"), true);
      }
    }
  }
}
