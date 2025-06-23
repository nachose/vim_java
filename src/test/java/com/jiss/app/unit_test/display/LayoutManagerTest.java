// File: src/test/java/com/jiss/app/display/LayoutManagerTest.java
package com.jiss.app.display;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LayoutManagerTest {

    @Test
    void testAddAndGetRegion() {
        LayoutManager<Object> lm = new LayoutManager<>(0, 0, 100, 50);
        Region<Object> r1 = new Region<>("left", 0, 0, 10, 10);
        Region<Object> r2 = new Region<>("right", 0, 0, 10, 10);
        lm.addRegion(r1);
        lm.addRegion(r2);

        assertEquals(r1, lm.getRegion("left"));
        assertEquals(r2, lm.getRegion("right"));
        assertNull(lm.getRegion("center"));
    }

    @Test
    void testGetRegionsUnmodifiable() {
        LayoutManager<Object> lm = new LayoutManager<>(0, 0, 100, 50);
        Region<Object> r = new Region<>("test", 0, 0, 1, 1);
        lm.addRegion(r);
        List<Region<Object>> regions = lm.getRegions();
        assertThrows(UnsupportedOperationException.class, () -> regions.add(r));
    }

    @Test
    void testLayoutVertical() {
        LayoutManager<Object> lm = new LayoutManager<>(0, 0, 100, 40);
        Region<Object> r1 = new Region<>("top", 0, 0, 0, 0);
        Region<Object> r2 = new Region<>("bottom", 0, 0, 0, 0);
        lm.addRegion(r1);
        lm.addRegion(r2);

        lm.layoutVertical();

        assertEquals(0, r1.getY());
        assertEquals(20, r2.getY());
        assertEquals(20, r1.getHeight());
        assertEquals(20, r2.getHeight());
        assertEquals(100, r1.getWidth());
        assertEquals(100, r2.getWidth());
    }

    @Test
    void testLayoutHorizontal() {
        LayoutManager<Object> lm = new LayoutManager<>(0, 0, 80, 20);
        Region<Object> r1 = new Region<>("left", 0, 0, 0, 0);
        Region<Object> r2 = new Region<>("right", 0, 0, 0, 0);
        lm.addRegion(r1);
        lm.addRegion(r2);

        lm.layoutHorizontal();

        assertEquals(0, r1.getX());
        assertEquals(40, r2.getX());
        assertEquals(40, r1.getWidth());
        assertEquals(40, r2.getWidth());
        assertEquals(20, r1.getHeight());
        assertEquals(20, r2.getHeight());
    }

    @Test
    void testDrawDelegatesToRegions() {
        LayoutManager<Object> lm = new LayoutManager<>(0, 0, 10, 10);
        final boolean[] called = {false};
        Region<Object> r = new Region<>("draw", 0, 0, 1, 1) {
            @Override
            public void draw(Object context) {
                called[0] = true;
            }
        };
        lm.addRegion(r);
        lm.draw(null);
        assertTrue(called[0]);
    }
}