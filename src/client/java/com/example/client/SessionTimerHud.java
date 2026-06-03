package com.example.client;

public class SessionTimerHud {

    private long ticksInWorld = 0;

    public void tick() {
        ticksInWorld++;
    }

    public void reset() {
        ticksInWorld = 0;
    }

    public String getFormattedTime() {
        long totalSeconds = ticksInWorld / 20;
        long hours   = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("Session: %02d:%02d:%02d", hours, minutes, seconds);
    }
}
