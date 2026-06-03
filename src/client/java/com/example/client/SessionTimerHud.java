package com.example.client;

public class SessionTimerHud {

    private long ticksInWorld = 0;

    public void tick() {
        ticksInWorld++;
    }

    public void reset() {
        ticksInWorld = 0;
    }

    public long getTicksInWorld() {
        return ticksInWorld;
    }
}
