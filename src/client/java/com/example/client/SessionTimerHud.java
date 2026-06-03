package com.example.client;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class SessionTimerHud implements HudElement {

    private long ticksInWorld = 0;

    public void tick() {
        ticksInWorld++;
    }

    public void reset() {
        ticksInWorld = 0;
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) return;

        long totalSeconds = ticksInWorld / 20;
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

        // Background semi-transparent
        graphics.fill(x, y, x + boxW, y + boxH, 0xAA000000);

        // Border
        graphics.fill(x,          y,          x + boxW, y + 1,      0xFF555555);
        graphics.fill(x,          y + boxH-1, x + boxW, y + boxH,   0xFF555555);
        graphics.fill(x,          y,          x + 1,    y + boxH,   0xFF555555);
        graphics.fill(x + boxW-1, y,          x + boxW, y + boxH,   0xFF555555);

        // Text with shadow
        graphics.drawTextWithShadow(mc.font, label, x + pad, y + 3, 0xFFFFFFFF);
    }
}
