// File: src/test/java/com/jiss/app/display/LayoutManagerTest.java
package com.jiss.app.display;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import com.jiss.app.display.WindowContext;

class LayoutManagerTest {

    @Test
    void testAddAndGetRegion() {
        Region r = new Region("container", 0, 0, 100, 50);
        LayoutManager lm = new LayoutManager(r);
        Region r1 = new Region("left", 0, 0, 10, 10);
        Region r2 = new Region("right", 0, 0, 10, 10);
        lm.addRegion(r1);
        lm.addRegion(r2);

        assertEquals(r1, lm.getRegion("left"));
        assertEquals(r2, lm.getRegion("right"));
        assertNull(lm.getRegion("center"));
    }

    @Test
    void testGetRegionsUnmodifiable() {
        Region rc = new Region("container", 0, 0, 100, 50);
        LayoutManager lm = new LayoutManager(rc);
        Region r = new Region("test", 0, 0, 1, 1);
        lm.addRegion(r);
        List<Region> regions = lm.getRegions();
        assertThrows(UnsupportedOperationException.class, () -> regions.add(r));
    }

    @Test
    void testLayoutVertical() {
        Region r = new Region("container", 0, 0, 100, 40);
        LayoutManager lm = new LayoutManager(r);
        Region r1 = new Region("top", 0, 0, 10, 10);
        Region r2 = new Region("bottom", 0, 0, 10, 10);
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
        Region r = new Region("container", 0, 0, 80, 20);
        LayoutManager lm = new LayoutManager(r);
        Region r1 = new Region("left", 0, 0, 10, 10);
        Region r2 = new Region("right", 0, 0, 10, 10);
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

    //This is still not needed.
//    @Test
//    void testAddDuplicateRegionNames() {
//        LayoutManager lm = new LayoutManager(0, 0, 10, 10);
//        Region r1 = new Region("dup", 0, 0, 1, 1);
//        Region r2 = new Region("dup", 1, 1, 2, 2);
//        lm.addRegion(r1);
//        lm.addRegion(r2);
//        // Should retrieve the last added region with the same name
//        assertEquals(r2, lm.getRegion("dup"));
//    }

    @Test
    void testLayoutWithZeroOrOneRegion() {
        Region rc = new Region("container", 0, 0, 10, 10);
        LayoutManager lm = new LayoutManager(rc);
        // No regions
        lm.layoutVertical();
        lm.layoutHorizontal();
        // One region
        Region r = new Region("single", 0, 0, 10, 10);
        lm.addRegion(r);
        lm.layoutVertical();
        assertEquals(0, r.getX());
        assertEquals(0, r.getY());
        assertEquals(10, r.getWidth());
        assertEquals(10, r.getHeight());
    }

    @Test
    void testDrawWithNoRegions() {
        Region r = new Region("container", 0, 0, 10, 10);
        LayoutManager lm = new LayoutManager(r);
        // Should not throw
        lm.draw(null);
    }

    @Test
    void testSubclassingLayoutAndDraw() {
        class CustomLayoutManager extends LayoutManager {
            boolean layoutCalled = false;
            boolean drawCalled = false;
            CustomLayoutManager(Region r) { super(r); }
            @Override
            public void layoutVertical() { layoutCalled = true; }
            @Override
            public void draw(WindowContext ctx) { drawCalled = true; }
        }
        Region r = new Region("container", 0, 0, 10, 10);
        CustomLayoutManager clm = new CustomLayoutManager(r);
        clm.layoutVertical();
        clm.draw(null);
        assertTrue(clm.layoutCalled);
        assertTrue(clm.drawCalled);
    }

}