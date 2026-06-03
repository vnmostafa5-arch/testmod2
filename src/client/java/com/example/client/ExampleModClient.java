package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ExampleModClient implements ClientModInitializer {

    public static final SessionTimerHud TIMER = new SessionTimerHud();

    @Override
    public void onInitializeClient() {

        // Register HUD as lambda - no interface needed
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            ResourceLocation.fromNamespaceAndPath("sessiontimer", "timer_hud"),
            (graphics, tickCounter) -> {
                Minecraft mc = Minecraft.getInstance();
                if (mc.level == null) return;

                long totalSeconds = TIMER.getTicksInWorld() / 20;
                long hours   = totalSeconds / 3600;
                long minutes = (totalSeconds % 3600) / 60;
                long seconds = totalSeconds % 60;

                String label = String.format("Session: %02d:%02d:%02d", hours, minutes, seconds);

                int screenWidth = mc.getWindow().getGuiScaledWidth();
                int textWidth   = mc.font.width(label);
                int pad         = 4;
                int boxW        = textWidth + pad * 2;
                int boxH        = 14;
                int x           = screenWidth - boxW - 6;
                int y           = 6;

                // Background
                graphics.fill(x, y, x + boxW, y + boxH, 0xAA000000);

                // Border
                graphics.fill(x,          y,          x + boxW, y + 1,      0xFF555555);
                graphics.fill(x,          y + boxH-1, x + boxW, y + boxH,   0xFF555555);
                graphics.fill(x,          y,          x + 1,    y + boxH,   0xFF555555);
                graphics.fill(x + boxW-1, y,          x + boxW, y + boxH,   0xFF555555);

                // Text
                graphics.drawString(mc.font, label, x + pad, y + 3, 0xFFFFFFFF);
            }
        );

        // Tick counter
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level != null) {
                TIMER.tick();
            } else {
                TIMER.reset();
            }
        });
    }
}
