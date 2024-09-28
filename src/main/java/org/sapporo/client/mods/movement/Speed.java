package org.sapporo.client.mods.movement;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.sapporo.client.mods.Module;

public class Speed extends Module {

    public static boolean speed_enabled = false;
    public Speed() {
        super("Speed");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onTick(MinecraftClient client) {
        if (client == null || client.player == null) {
            return;
        }

        PlayerEntity player = client.player;
        if (speed_enabled) {
            Vec3d velocity = player.getVelocity();
            Vec3d lookVec = player.getRotationVec(1.0F);

            double speedMultiplier = 1.3;

            Vec3d newVelocity = new Vec3d(
                    lookVec.x * speedMultiplier,
                    velocity.y,
                    lookVec.z * speedMultiplier
            );

            player.setVelocity(newVelocity);
        }
    }

}