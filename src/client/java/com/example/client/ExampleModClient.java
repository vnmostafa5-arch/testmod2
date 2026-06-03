package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ExampleModClient implements ClientModInitializer {

	private static long sessionTicks = 0;

	@Override
	public void onInitializeClient() {

		// عداد الوقت - بيشتغل كل tick
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.level != null) {
				sessionTicks++;
			} else {
				sessionTicks = 0;
			}
		});

		// شاشة صغيرة في الركن الأيمن العلوي
		HudElementRegistry.attachElementBefore(
			VanillaHudElements.CHAT,
			ResourceLocation.fromNamespaceAndPath("modid", "session_timer"),
			(graphics, tickCounter) -> {
				Minecraft mc = Minecraft.getInstance();
				if (mc.level == null) return;

				long total   = sessionTicks / 20;
				long hours   = total / 3600;
				long minutes = (total % 3600) / 60;
				long seconds = total % 60;
				String label = String.format("Session: %02d:%02d:%02d", hours, minutes, seconds);

				int sw   = mc.getWindow().getGuiScaledWidth();
				int tw   = mc.font.width(label);
				int pad  = 4;
				int boxW = tw + pad * 2;
				int boxH = 14;
				int x    = sw - boxW - 6;
				int y    = 6;

				// خلفية شفافة
				graphics.fill(x, y, x + boxW, y + boxH, 0xAA000000);
				// إطار
				graphics.fill(x,          y,          x + boxW, y + 1,      0xFF555555);
				graphics.fill(x,          y + boxH-1, x + boxW, y + boxH,   0xFF555555);
				graphics.fill(x,          y,          x + 1,    y + boxH,   0xFF555555);
				graphics.fill(x + boxW-1, y,          x + boxW, y + boxH,   0xFF555555);
				// نص
				graphics.drawString(mc.font, Component.literal(label), x + pad, y + 3, 0xFFFFFFFF);
			}
		);
	}
}
