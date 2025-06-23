// File: src/test/java/com/jiss/app/util/FpsCounterTest.java
package com.jiss.app.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FpsCounterTest {

    @Test
    void testInitialFpsIsZero() {
        FpsCounter counter = new FpsCounter();
        assertEquals(0, counter.getFps());
    }

    @Test
    void testFpsIncrementsAfterOneSecond() throws InterruptedException {
        FpsCounter counter = new FpsCounter();
        for (int i = 0; i < 5; i++) {
            counter.increment();
        }
        // Wait just over 1 second to trigger FPS update
        Thread.sleep(1050);
        counter.increment();
        assertEquals(6, counter.getFps());
    }

    @Test
    void testFpsResetsAfterEachSecond() throws InterruptedException {
        FpsCounter counter = new FpsCounter();
        for (int i = 0; i < 3; i++) {
            counter.increment();
        }
        Thread.sleep(1010);
        counter.increment();
        assertEquals(4, counter.getFps());

        for (int i = 0; i < 2; i++) {
            counter.increment();
        }
        Thread.sleep(1010);
        counter.increment();
        assertEquals(3, counter.getFps());
    }
}