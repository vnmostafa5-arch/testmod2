package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.ResourceLocation;

public class ExampleModClient implements ClientModInitializer {

    public static final SessionTimerHud HUD = new SessionTimerHud();

    @Override
    public void onInitializeClient() {
        // Register HUD before chat layer
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            ResourceLocation.fromNamespaceAndPath("sessiontimer", "timer_hud"),
            HUD
        );

        // Tick counter
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level != null) {
                HUD.tick();
            } else {
                HUD.reset();
            }
        });
    }
}
