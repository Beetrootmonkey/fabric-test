package com.beetrootmonkey.fabrictest;

import com.beetrootmonkey.fabrictest.block.entity.IronFurnaceBlockEntity;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import reborncore.common.network.ExtendedPacketBuffer;
import reborncore.common.network.NetworkManager;

import java.util.function.BiConsumer;

import static com.beetrootmonkey.fabrictest.Main.MOD_ID;

public class ServerboundPackets {

  public static final Identifier EXPERIENCE = new Identifier(MOD_ID, "experience");

  public static void init() {

    registerPacketHandler(EXPERIENCE, (extendedPacketBuffer, context) -> {
      BlockPos pos = extendedPacketBuffer.readBlockPos();

      context.getTaskQueue().execute(() -> {
        BlockEntity blockEntity = context.getPlayer().world.getBlockEntity(pos);
        if (blockEntity instanceof IronFurnaceBlockEntity) {
          ((IronFurnaceBlockEntity) blockEntity).handleGuiInputFromClient(context.getPlayer());
        }
      });
    });
  }

  private static void registerPacketHandler(Identifier identifier, BiConsumer<ExtendedPacketBuffer, PacketContext> consumer) {
    ServerSidePacketRegistry.INSTANCE.register(identifier, (packetContext, packetByteBuf) -> consumer.accept(new ExtendedPacketBuffer(packetByteBuf), packetContext));
  }

  public static Packet<ServerPlayPacketListener> createPacketExperience(IronFurnaceBlockEntity blockEntity) {
    return NetworkManager.createServerBoundPacket(EXPERIENCE, extendedPacketBuffer -> {
      extendedPacketBuffer.writeBlockPos(blockEntity.getPos());
    });
  }

}
