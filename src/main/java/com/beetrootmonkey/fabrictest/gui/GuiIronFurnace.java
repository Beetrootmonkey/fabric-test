package com.beetrootmonkey.fabrictest.gui;

import com.beetrootmonkey.fabrictest.PlayerUtils;
import com.beetrootmonkey.fabrictest.ServerboundPackets;
import com.beetrootmonkey.fabrictest.block.entity.IronFurnaceBlockEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.builder.widget.GuiButtonSimple;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class GuiIronFurnace extends GuiBase<BuiltScreenHandler> {

  IronFurnaceBlockEntity blockEntity;

  public GuiIronFurnace(int syncID, PlayerEntity player, IronFurnaceBlockEntity furnace) {
    super(player, furnace, furnace.createScreenHandler(syncID, player));
    this.blockEntity = furnace;
  }

  public void onClick() {
    NetworkManager.sendToServer(ServerboundPackets.createPacketExperience(blockEntity));
  }

  @Override
  public void init() {
    super.init();
    addButton(new GuiButtonSimple(getGuiLeft() + 116, getGuiTop() + 57, 18, 18, LiteralText.EMPTY, b -> onClick()) {

      @Override
      public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
        PlayerEntity player = playerInventory.player;
        if (player == null) {
          return;
        }
        String message = "Experience: ";

        float furnaceExp = blockEntity.experience;
        if (furnaceExp <= 0) {
          message = message + "0";
        } else {
          float expTillLevel = (1.0F - player.experienceProgress) * player.getNextLevelExperience();
          if (furnaceExp <= expTillLevel) {
            int percentage = (int) (blockEntity.experience * 100 / player.getNextLevelExperience());
            message = message + "+"
              + (percentage > 0 ? String.valueOf(percentage) : "<1")
              + "%";
          } else {
            int levels = 0;
            furnaceExp -= expTillLevel;
            while (furnaceExp > 0) {
              furnaceExp -= PlayerUtils.getLevelExperience(player.experienceLevel);
              ++levels;
            }
            message = message + "+" + String.valueOf(levels) + " Lvl";
          }
        }

        List<Text> list = new ArrayList<>();
        list.add(new LiteralText(message));
        renderTooltip(matrixStack, list, mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.color4f(1, 1, 1, 1);

      }

      @Override
      public void renderBg(MatrixStack matrixStack, MinecraftClient mc, int mouseX, int mouseY) {
        mc.getItemRenderer().renderInGuiWithOverrides(new ItemStack(Items.EXPERIENCE_BOTTLE), x, y);
      }
    });
  }

  @Override
  protected void drawBackground(MatrixStack matrixStack, float lastFrameDuration, int mouseX, int mouseY) {
    super.drawBackground(matrixStack, lastFrameDuration, mouseX, mouseY);
    final GuiBase.Layer layer = GuiBase.Layer.BACKGROUND;

    // Input slot
    drawSlot(matrixStack, 56, 17, layer);
    // Fuel slot
    drawSlot(matrixStack, 56, 53, layer);

    drawOutputSlot(matrixStack, 116, 35, layer);
  }

  @Override
  protected void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
    super.drawForeground(matrixStack, mouseX, mouseY);
    final GuiBase.Layer layer = GuiBase.Layer.FOREGROUND;

    builder.drawProgressBar(matrixStack, this, blockEntity.getProgressScaled(100), 100, 85, 36, mouseX, mouseY, GuiBuilder.ProgressDirection.RIGHT, layer);
    builder.drawBurnBar(matrixStack, this, blockEntity.getBurnTimeRemainingScaled(100), 100, 56, 36, mouseX, mouseY, layer);
  }
}

