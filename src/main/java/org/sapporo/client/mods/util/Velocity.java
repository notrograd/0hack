package org.sapporo.client.mods.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.sapporo.client.mods.Module;

@Environment(EnvType.CLIENT)
public class Velocity extends Module {

    public static boolean enabled = false;

    private Vec3d lastVelocity;

    public Velocity() {
        super("Velocity");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
        lastVelocity = Vec3d.ZERO;
    }

    public void onTick(MinecraftClient client) {
        if (client == null || client.player == null) {
            return;
        }

        PlayerEntity player = client.player;

        if (enabled) {
            if (lastVelocity != Vec3d.ZERO) {
                player.setVelocity(lastVelocity.x, lastVelocity.y, lastVelocity.z);
            }
        }

        lastVelocity = player.getVelocity();
    }
}
