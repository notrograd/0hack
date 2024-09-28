package org.sapporo.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Colors;
import org.sapporo.client.mods.Module;

@Environment(EnvType.CLIENT)
public class Hud extends Module {
    private static void hudDraw(DrawContext context, String args, int x, int y) {
        context.drawText(
                MinecraftClient.getInstance().textRenderer,
                args,
                x,
                y,
                Colors.RED,
                true
        );
    }

    public HudRenderCallback renderWaterMark() {
        DrawContext context = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEffectVertexConsumers());
        hudDraw(context, "0hack Client", 4, 4);
        return HudRenderCallback.EVENT.invoker();
    }

    public void onTick(MinecraftClient client)
    {
        renderWaterMark();
    }

    public Hud() {
        super("HUD");
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }
}