package com.beetrootmonkey.fabrictest.gui;

import com.beetrootmonkey.fabrictest.block.entity.IronFurnaceBlockEntity;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import reborncore.RebornCore;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.client.screen.BuiltScreenHandlerProvider;
import reborncore.client.screen.builder.BuiltScreenHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

//The meme is here the double suppliers dont cause the client side gui classes to load on the server
public final class GuiType<T extends BlockEntity> implements IMachineGuiHandler {
  private static final Map<Identifier, GuiType<?>> TYPES = new HashMap<>();

  public static final GuiType<IronFurnaceBlockEntity> IRON_FURNACE = register("iron_furnace", () -> () -> GuiIronFurnace::new);

  private static <T extends BlockEntity> GuiType<T> register(String id, Supplier<Supplier<GuiFactory<T>>> factorySupplierMeme) {
    return register(new Identifier(MOD_ID, id), factorySupplierMeme);
  }

  public static <T extends BlockEntity> GuiType<T> register(Identifier identifier, Supplier<Supplier<GuiFactory<T>>> factorySupplierMeme) {
    if (TYPES.containsKey(identifier)) {
      throw new RuntimeException("Duplicate gui type found");
    }
    GuiType<T> type = new GuiType<>(identifier, factorySupplierMeme);
    TYPES.put(identifier, type);
    return type;
  }

  private final Identifier identifier;
  private final Supplier<Supplier<GuiFactory<T>>> guiFactory;
  private final ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> screenHandlerFactory;
  private final ScreenHandlerType<BuiltScreenHandler> screenHandlerType;

  private GuiType(Identifier identifier, Supplier<Supplier<GuiFactory<T>>> factorySupplierMeme) {
    this.identifier = identifier;
    this.guiFactory = factorySupplierMeme;
    this.screenHandlerFactory = getScreenHandlerFactory();
    this.screenHandlerType = ScreenHandlerRegistry.registerExtended(identifier, screenHandlerFactory);
    RebornCore.clientOnly(() -> () -> ScreenRegistry.register(screenHandlerType, getGuiFactory()));
  }

  private ScreenHandlerRegistry.ExtendedClientHandlerFactory<BuiltScreenHandler> getScreenHandlerFactory() {
    return (syncId, playerInventory, packetByteBuf) -> {
      final BlockEntity blockEntity = playerInventory.player.world.getBlockEntity(packetByteBuf.readBlockPos());
      BuiltScreenHandler screenHandler = ((BuiltScreenHandlerProvider) blockEntity).createScreenHandler(syncId, playerInventory.player);

      //Set the screen handler type, not ideal but works lol
      screenHandler.setType(screenHandlerType);

      return screenHandler;
    };
  }

  @Environment(EnvType.CLIENT)
  private GuiFactory<T> getGuiFactory() {
    return guiFactory.get().get();
  }

  @Override
  public void open(PlayerEntity player, BlockPos pos, World world) {
    if (!world.isClient) {
      //This is awful
      player.openHandledScreen(new ExtendedScreenHandlerFactory() {
        @Override
        public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
          packetByteBuf.writeBlockPos(pos);
        }

        @Override
        public Text getDisplayName() {
          return new LiteralText("What is this for?");
        }

        @Nullable
        @Override
        public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
          PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
          buf.writeBlockPos(pos);
          return screenHandlerFactory.create(syncId, inv, buf);
        }
      });
    }
  }

  public Identifier getIdentifier() {
    return identifier;
  }

  @Environment(EnvType.CLIENT)
  public interface GuiFactory<T extends BlockEntity> extends ScreenRegistry.Factory<BuiltScreenHandler, HandledScreen<BuiltScreenHandler>> {
    HandledScreen<?> create(int syncId, PlayerEntity playerEntity, T blockEntity);

    @Override
    default HandledScreen create(BuiltScreenHandler builtScreenHandler, PlayerInventory playerInventory, Text text) {
      PlayerEntity playerEntity = playerInventory.player;
      //noinspection unchecked
      T blockEntity = (T) builtScreenHandler.getBlockEntity();
      return create(builtScreenHandler.syncId, playerEntity, blockEntity);
    }
  }

}
