// File: src/main/java/com/jiss/app/util/FpsCounter.java
package com.jiss.app.util;

public class FpsCounter {
    private int redrawCount = 0;
    private int fps = 0;
    private long lastFpsTime = System.currentTimeMillis();

    public void increment() {
        redrawCount++;
        long now = System.currentTimeMillis();
        if (now - lastFpsTime >= 1000) {
            fps = redrawCount;
            redrawCount = 0;
            lastFpsTime = now;
        }
    }

    public int getFps() {
        return fps;
    }
}