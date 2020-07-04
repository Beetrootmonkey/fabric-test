package com.beetrootmonkey.fabrictest.gui;

import com.beetrootmonkey.fabrictest.PlayerUtils;
import com.beetrootmonkey.fabrictest.ServerboundPackets;
import com.beetrootmonkey.fabrictest.block.entity.IronFurnaceBlockEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import reborncore.client.gui.builder.GuiBase;
import reborncore.client.gui.builder.slot.GuiTab;
import reborncore.client.gui.builder.widget.GuiButtonSimple;
import reborncore.client.gui.guibuilder.GuiBuilder;
import reborncore.client.screen.builder.BuiltScreenHandler;
import reborncore.common.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class GuiIronFurnace extends GuiBase<BuiltScreenHandler> {

  List<GuiTab> tabs = new ArrayList<>();
  boolean showFurnace = true;
  GuiButtonSimple buttonShowFurnace;
  GuiButtonSimple buttonShowCraftinTable;
  IronFurnaceBlockEntity blockEntity;

  public GuiIronFurnace(int syncID, PlayerEntity player, IronFurnaceBlockEntity furnace) {
    super(player, furnace, furnace.createScreenHandler(syncID, player));
    this.blockEntity = furnace;
  }

  @Override
  public boolean isConfigEnabled() {
    return false;
  }

  public boolean getShowFurnace() {
    return showFurnace;
  }

  public void setShowFurnace(boolean showFurnace) {
    this.showFurnace = showFurnace;
  }

  public void onClick() {
    NetworkManager.sendToServer(ServerboundPackets.createPacketExperience(blockEntity));
  }

//  @Override
//  public Text getTitle() {
//    return getShowFurnace() ? new TranslatableText("fabrictest:oven") : new TranslatableText("fabrictest:preparation");
//  }

  

  @Override
  public void init() {
    this.x = (this.width - this.backgroundWidth) / 2;
    this.y = (this.height - this.backgroundHeight) / 2;
    buttonShowFurnace = addButton(new GuiButtonSimple(getGuiLeft() - 0, getGuiTop() - 28, 28, 29, LiteralText.EMPTY, b -> setShowFurnace(true)) {

      @Override
      public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
        PlayerEntity player = playerInventory.player;
        if (player == null) {
          return;
        }
        String message = "Switch to Furnace";

        List<Text> list = new ArrayList<>();
        list.add(new LiteralText(message));
        renderTooltip(matrixStack, list, mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.color4f(1, 1, 1, 1);

      }

      @Override
      public void renderBg(MatrixStack matrixStack, MinecraftClient mc, int mouseX, int mouseY) {
        mc.getItemRenderer().renderInGuiWithOverrides(new ItemStack(Blocks.FURNACE), x + 6, y + 10);
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        minecraftClient.getTextureManager().bindTexture(new Identifier("textures/gui/container/creative_inventory/tabs.png"));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.x, this.y, 0, getShowFurnace() ? 32 : 0, 28, getShowFurnace() ? 32 : 28);
        if (!getShowFurnace()) {
          this.drawTexture(matrices, this.x, this.y + 28, 0, 31, 2, 1);
          this.drawTexture(matrices, this.x, this.y + 29, 0, 31, 1, 1);
        }
//        this.drawTexture(matrices, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height + 2);
        this.renderBg(matrices, minecraftClient, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;
        this.drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
      }
    });

    buttonShowCraftinTable = addButton(new GuiButtonSimple(getGuiLeft() + 29, getGuiTop() - 28, 28, 29, LiteralText.EMPTY, b -> setShowFurnace(false)) {

      @Override
      public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
        PlayerEntity player = playerInventory.player;
        if (player == null) {
          return;
        }
        String message = "Switch to Crafting Table";

        List<Text> list = new ArrayList<>();
        list.add(new LiteralText(message));
        renderTooltip(matrixStack, list, mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.color4f(1, 1, 1, 1);

      }

      @Override
      public void renderBg(MatrixStack matrixStack, MinecraftClient mc, int mouseX, int mouseY) {
        mc.getItemRenderer().renderInGuiWithOverrides(new ItemStack(Blocks.CRAFTING_TABLE), x + 6, y + 10);
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        minecraftClient.getTextureManager().bindTexture(new Identifier("textures/gui/container/creative_inventory/tabs.png"));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.x, this.y, 28, !getShowFurnace() ? 32 : 0, 28, !getShowFurnace() ? 32 : 28);
//        this.drawTexture(matrices, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height + 2);
        this.renderBg(matrices, minecraftClient, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;
        this.drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
      }
    });

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

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        minecraftClient.getTextureManager().bindTexture(WIDGETS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height + 2);
        this.drawTexture(matrices, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height + 2);
        this.renderBg(matrices, minecraftClient, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;
        this.drawCenteredText(matrices, textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
      }
    });

    getTabs().clear();
  }


  @Override
  protected void drawBackground(MatrixStack matrixStack, float lastFrameDuration, int mouseX, int mouseY) {
    super.drawBackground(matrixStack, lastFrameDuration, mouseX, mouseY);
    final GuiBase.Layer layer = GuiBase.Layer.BACKGROUND;

    if (showFurnace) {
      // Input slot
      drawSlot(matrixStack, 56, 17, layer);
      // Fuel slot
      drawSlot(matrixStack, 56, 53, layer);

      drawOutputSlot(matrixStack, 116, 35, layer);
    } else {
      // Furnace input slot
      matrixStack.peek();
      // Furnace fuel slot
      matrixStack.peek();
      drawOutputSlot(matrixStack, 116, 35, layer);

      drawSlot(matrixStack, 20, 17, layer);
      drawSlot(matrixStack, 38, 17, layer);
      drawSlot(matrixStack, 56, 17, layer);

      drawSlot(matrixStack, 20, 35, layer);
      drawSlot(matrixStack, 38, 35, layer);
      drawSlot(matrixStack, 56, 35, layer);

      drawSlot(matrixStack, 20, 53, layer);
      drawSlot(matrixStack, 38, 53, layer);
      drawSlot(matrixStack, 56, 53, layer);
    }
  }

  @Override
  protected void drawForeground(MatrixStack matrixStack, int mouseX, int mouseY) {
    super.drawForeground(matrixStack, mouseX, mouseY);
    final GuiBase.Layer layer = GuiBase.Layer.FOREGROUND;

    if (showFurnace) {
      builder.drawBurnBar(matrixStack, this, blockEntity.getBurnTimeRemainingScaled(100), 100, 56, 36, mouseX, mouseY, layer);
    }
    builder.drawProgressBar(matrixStack, this, blockEntity.getProgressScaled(100), 100, 85, 36, mouseX, mouseY, GuiBuilder.ProgressDirection.RIGHT, layer);

  }
}

