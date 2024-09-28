package org.sapporo.client.mods.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.sapporo.client.mods.Module;

@Environment(EnvType.CLIENT)
public class ElytraControl extends Module {

    public static boolean enabled = false;

    public ElytraControl() {
        super("ElytraControl");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onTick(MinecraftClient client) {
        if (client == null || client.player == null) {
            return;
        }

        PlayerEntity player = client.player;

        if (enabled && player.isFallFlying()) {
            Vec3d velocity = player.getVelocity();

            double speedMultiplier = 1.5;
            double moveVertical = 0.0;
            Vec3d forward = Vec3d.fromPolar(0, player.getYaw()).normalize().multiply(client.player.input.movementForward * speedMultiplier);
            Vec3d strafe = Vec3d.fromPolar(0, player.getYaw() + 90).normalize().multiply(client.player.input.movementSideways * speedMultiplier);
            Vec3d movement = forward.add(strafe);
            if (client.options.jumpKey.isPressed()) {
                moveVertical = 1.0;
            } else if (client.options.sneakKey.isPressed()) {
                moveVertical = -0.5;
            }
            player.setVelocity(movement.x, moveVertical, movement.z);
        }
    }
}
