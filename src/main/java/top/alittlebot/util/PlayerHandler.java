package top.alittlebot.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class PlayerHandler {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(player -> {
                if (player.isTouchingWater()) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1));
                }
            });
        });
    }
}
