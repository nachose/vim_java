// File: src/test/java/com/jiss/app/display/RegionTest.java
package com.jiss.app.display;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegionTest {

    @Test
    void testConstructorAndGetters() {
        Region<Object> region = new Region<>("main", 1, 2, 10, 5);
        assertEquals("main", region.getName());
        assertEquals(1, region.getX());
        assertEquals(2, region.getY());
        assertEquals(10, region.getWidth());
        assertEquals(5, region.getHeight());
    }

    @Test
    void testSetBounds() {
        Region<Object> region = new Region<>("test", 0, 0, 1, 1);
        region.setBounds(3, 4, 20, 8);
        assertEquals(3, region.getX());
        assertEquals(4, region.getY());
        assertEquals(20, region.getWidth());
        assertEquals(8, region.getHeight());
    }

    @Test
    void testDrawDefaultDoesNothing() {
        Region<Object> region = new Region<>("draw", 0, 0, 1, 1);
        // Should not throw or do anything
        region.draw(null);
    }
}