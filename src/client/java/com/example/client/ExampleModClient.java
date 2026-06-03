package com.example.client;

import com.example.client.SessionTimerHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.resources.ResourceLocation;

public class ExampleModClient implements ClientModInitializer {

    public static final SessionTimerHud HUD = new SessionTimerHud();

    @Override
    public void onInitializeClient() {
        // Register HUD overlay
        HudLayerRegistrationCallback.EVENT.register(layeredDraw ->
            layeredDraw.addLayerBefore(
                IdentifiedLayer.CHAT,
                new ResourceLocation("sessiontimer", "timer_hud"),
                HUD
            )
        );

        // Tick every client tick to update the timer
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level != null) {
                HUD.tick();
            } else {
                HUD.reset();
            }
        });
    }
}
