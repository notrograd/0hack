package org.sapporo.client.mods.pvp;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.sapporo.client.mods.Module;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class AutoCrystal extends Module {

    private final MinecraftClient client = MinecraftClient.getInstance();
    public static boolean enabled = false;

    public AutoCrystal() {
        super("AutoCrystal");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onTick(MinecraftClient client) {
        if (client == null || client.player == null) {
            return;
        }

        PlayerEntity player = client.player;

        if (enabled) {
            ItemStack heldItem = player.getMainHandStack();
            if (heldItem.getItem() != Items.END_CRYSTAL) {
                return;
            }

            BlockPos playerPos = player.getBlockPos();
            BlockPos.Mutable blockPos = new BlockPos.Mutable();
            BlockPos closestPos = null;
            double closestDistance = Double.MAX_VALUE;

            for (int x = -5; x <= 5; x++) {
                for (int y = -5; y <= 5; y++) {
                    for (int z = -5; z <= 5; z++) {
                        blockPos.set(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
                        assert client.world != null;
                        Block block = client.world.getBlockState(blockPos).getBlock();

                        if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) {
                            double distance = playerPos.getSquaredDistance(blockPos);
                            if (distance < closestDistance) {
                                closestDistance = distance;
                                closestPos = blockPos.toImmutable();
                            }
                        }
                    }
                }
            }

            if (closestPos != null) {
                BlockHitResult blockHitResult = new BlockHitResult(
                        new Vec3d(closestPos.getX() + 0.5, closestPos.getY() + 1.0, closestPos.getZ() + 0.5),
                        Direction.UP,
                        closestPos,
                        false
                );

                Objects.requireNonNull(client.getNetworkHandler()).sendPacket(

                         new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, blockHitResult, 0)
                );
            }
        }
    }
}