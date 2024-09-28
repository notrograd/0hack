package org.sapporo.client.mods.pvp;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.sapporo.client.mods.Module;

@Environment(EnvType.CLIENT)
public class AutoTotem extends Module {

    public static boolean fEnabled = false;

    public AutoTotem() {
        super("AutoTotem");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private boolean isTotemInOffhand(PlayerEntity player) {
        ItemStack offhandItem = player.getEquippedStack(EquipmentSlot.OFFHAND);
        return offhandItem.getItem() == Items.TOTEM_OF_UNDYING;
    }

    private int findTotemInInventory(PlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack itemStack = player.getInventory().getStack(i);
            if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                return i;
            }
        }
        return -1;
    }

    private void replaceTotemInOffhand(PlayerEntity player, int totemSlot) {
        ItemStack totemStack = player.getInventory().getStack(totemSlot);
        ItemStack currentOffhandItem = player.getEquippedStack(EquipmentSlot.OFFHAND);

        player.getInventory().setStack(totemSlot, currentOffhandItem);
        player.equipStack(EquipmentSlot.OFFHAND, totemStack);
    }

    public void onTick(MinecraftClient client) {
        if (client == null || client.player == null) {
            return;
        }

        PlayerEntity player = client.player;

        if (fEnabled) {
            if (!isTotemInOffhand(player)) {
                int totemSlot = findTotemInInventory(player);

                if (totemSlot != -1) {
                    replaceTotemInOffhand(player, totemSlot);
                }
            } else if (player.getOffHandStack().isEmpty()) {
                int totemSlot = findTotemInInventory(player);

                if (totemSlot != -1) {
                    replaceTotemInOffhand(player, totemSlot);
                }
            }
        }
    }
}
