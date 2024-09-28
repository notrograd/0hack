package org.sapporo.client.mods.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.sapporo.client.mods.Module;

@Environment(EnvType.CLIENT)
public class CreativeFly extends Module {

    private final MinecraftClient client = MinecraftClient.getInstance();
    public static boolean enabled = false;
    private boolean wasFlyingBefore;

    public CreativeFly() {
        super("CreativeFly");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private void enableFlight(PlayerEntity player) {
        player.getAbilities().allowFlying = true;
        player.getAbilities().flying = true;
        player.sendAbilitiesUpdate();
    }

    private void disableFlight(PlayerEntity player) {
        player.getAbilities().allowFlying = false;
        player.getAbilities().flying = false;
        player.sendAbilitiesUpdate();
    }

    public void onTick(MinecraftClient client) {
        if (client == null || client.player == null) {
            return;
        }

        PlayerEntity player = client.player;

        if (enabled) {
            if (!player.getAbilities().allowFlying || !player.getAbilities().flying) {
                enableFlight(player);
                wasFlyingBefore = player.getAbilities().flying;
            }

            Vec3d velocity = player.getVelocity();
            Vec3d movementInput = new Vec3d(0, 0, 0);

            if (client.options.jumpKey.isPressed()) {
                movementInput = movementInput.add(0, 0.5, 0);
            }

            if (client.options.sneakKey.isPressed()) {
                movementInput = movementInput.add(0, -0.5, 0);
            }

            if (client.player.input.movementForward != 0 || client.player.input.movementSideways != 0) {
                Vec3d forward = Vec3d.fromPolar(0, client.player.getYaw()).normalize().multiply(client.player.input.movementForward);
                Vec3d strafe = Vec3d.fromPolar(0, client.player.getYaw() + 90).normalize().multiply(client.player.input.movementSideways);
                movementInput = movementInput.add(forward).add(strafe);
            }

            player.setVelocity(movementInput);

        } else if (wasFlyingBefore) {
            disableFlight(player);
            wasFlyingBefore = false;
        }
    }
}
