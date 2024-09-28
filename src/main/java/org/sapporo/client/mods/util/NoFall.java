package org.sapporo.client.mods.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.sapporo.client.mods.Module;

@Environment(EnvType.CLIENT)
public class NoFall extends Module {

    private final MinecraftClient client = MinecraftClient.getInstance();
    public static boolean enabled = false;

    public NoFall() {
        super("NoFall");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onTick(MinecraftClient client) {
        if (client == null || client.player == null) {
            return;
        }

        PlayerEntity player = client.player;

        if (enabled && player.fallDistance > 2.0F) {
           player.fallDistance = 0;
        }
    }
}
