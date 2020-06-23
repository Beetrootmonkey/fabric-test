package com.beetrootmonkey.fabrictest;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface SheepShearCallback {

  Event<SheepShearCallback> EVENT = EventFactory.createArrayBacked(SheepShearCallback.class,
    (listeners) -> (player, sheep, hand) -> {
      for (SheepShearCallback listener : listeners) {
        ActionResult result = listener.interact(player, sheep, hand);

        if (result != ActionResult.PASS) {
          return result;
        }
      }

      return ActionResult.PASS;
    });

  ActionResult interact(PlayerEntity player, SheepEntity sheep, Hand hand);
}
