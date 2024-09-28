package org.sapporo.client.mods.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.sapporo.client.mods.Module;

@Environment(EnvType.CLIENT)
public class AutoSprint extends Module {

    public static boolean isEnabled;

    public AutoSprint() {
        super("AutoSprint");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onTick(MinecraftClient client) {
        if (isEnabled && client.player != null) {
            client.player.setSprinting(true);
        }
    }
}