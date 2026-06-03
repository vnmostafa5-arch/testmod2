package com.example.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDraw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;

public class SessionTimerHud implements LayeredDraw.Layer {

    private long ticksInWorld = 0;
    private boolean inWorld = false;

    public void tick() {
        ticksInWorld++;
        inWorld = true;
    }

    public void reset() {
        ticksInWorld = 0;
        inWorld = false;
    }

    @Override
    public void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();

        // Only show when in a world
        if (mc.level == null) return;

        long totalSeconds = ticksInWorld / 20;
        long hours   = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        String timeText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        String label    = "Session: " + timeText;

        int screenWidth  = mc.getWindow().getGuiScaledWidth();

        int textWidth  = mc.font.width(label);
        int boxPadding = 4;
        int boxWidth   = textWidth + boxPadding * 2;
        int boxHeight  = 14;

        // Position: top-right corner with small margin
        int x = screenWidth - boxWidth - 6;
        int y = 6;

        // Draw semi-transparent background
        graphics.fill(x, y, x + boxWidth, y + boxHeight, 0xAA000000);

        // Draw border
        graphics.fill(x,                  y,               x + boxWidth, y + 1,           0xFF555555);
        graphics.fill(x,                  y + boxHeight-1, x + boxWidth, y + boxHeight,   0xFF555555);
        graphics.fill(x,                  y,               x + 1,        y + boxHeight,   0xFF555555);
        graphics.fill(x + boxWidth - 1,   y,               x + boxWidth, y + boxHeight,   0xFF555555);

        // Draw text (white with shadow)
        graphics.drawString(mc.font, label, x + boxPadding, y + 3, 0xFFFFFFFF, true);
    }
}
