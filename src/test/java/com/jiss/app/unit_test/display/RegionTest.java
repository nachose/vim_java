// File: src/test/java/com/jiss/app/display/RegionTest.java
package com.jiss.app.display;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegionTest {

    @Test
    void testConstructorAndGetters() {
        Region region = new Region("main", 1, 2, 10, 5);
        assertEquals("main", region.getName());
        assertEquals(1, region.getX());
        assertEquals(2, region.getY());
        assertEquals(10, region.getWidth());
        assertEquals(5, region.getHeight());
    }

    @Test
    void testSetBounds() {
        Region region = new Region("test", 0, 0, 1, 1);
        region.setBounds(3, 4, 20, 8);
        assertEquals(3, region.getX());
        assertEquals(4, region.getY());
        assertEquals(20, region.getWidth());
        assertEquals(8, region.getHeight());
    }

    @Test
    void testMultipleSetBounds() {
        Region region = new Region("multi", 1, 2, 3, 4);
        region.setBounds(5, 6, 7, 8);
        assertEquals(5, region.getX());
        assertEquals(6, region.getY());
        assertEquals(7, region.getWidth());
        assertEquals(8, region.getHeight());
        region.setBounds(-1, -2, -3, -4);
        assertEquals(-1, region.getX());
        assertEquals(-2, region.getY());
        assertEquals(-3, region.getWidth());
        assertEquals(-4, region.getHeight());
    }

    @Test
    void testEmptyNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Region("", 1, 1, 1, 1);
        });
    }

    @Test
    void testNullNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Region(null, 1, 1, 1, 1);
        });
    }

    @Test
    void testNegativeXThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Region("negX", -1, 0, 1, 1);
        });
    }

    @Test
    void testNegativeYThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Region("negY", 0, -1, 1, 1);
        });
    }

    @Test
    void testZeroWidthThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Region("zeroW", 0, 0, 0, 1);
        });
    }

    @Test
    void testNegativeWidthThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Region("negW", 0, 0, -1, 1);
        });
    }

    @Test
    void testZeroHeightThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Region("zeroH", 0, 0, 1, 0);
        });
    }

    @Test
    void testNegativeHeightThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Region("negH", 0, 0, 1, -1);
        });
    }


}