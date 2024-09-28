package org.sapporo.client.mods.pvp;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.sapporo.client.mods.Module;

@Environment(EnvType.CLIENT)
public class CrystalAura extends Module {

    private final MinecraftClient client = MinecraftClient.getInstance();
    public static boolean enabled = false;

    public CrystalAura() {
        super("CrystalAura");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onTick(MinecraftClient client) {
        if (client == null || client.player == null) {
            return;
        }

        PlayerEntity player = client.player;

        if (enabled) {
            Entity nearestCrystal = null;

            assert client.world != null;
            for (Entity entity : client.world.getEntities()) {
                if (entity.getType() == EntityType.END_CRYSTAL) {
                    Vec3d playerPos = player.getPos();
                    Vec3d crystalPos = entity.getPos();

                    double horizontalDistance = Math.sqrt(
                            Math.pow(playerPos.x - crystalPos.x, 2) + Math.pow(playerPos.z - crystalPos.z, 2)
                    );
                    double verticalDistance = Math.abs(playerPos.y - crystalPos.y);

                    if (horizontalDistance <= 3.0 && verticalDistance <= 3.0) {
                        nearestCrystal = entity;
                        break;
                    }
                }
            }

            if (nearestCrystal != null) {
                assert client.interactionManager != null;
                client.interactionManager.attackEntity(player, nearestCrystal);
            }
        }
    }
}
