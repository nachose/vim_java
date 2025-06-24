package com.jiss.app.display;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.googlecode.lanterna.screen.Screen;
import java.util.List;
import com.jiss.app.display.ScreenRegion;

class ScreenRegionFactoryTest {

    @Test
    void testCreateScreenRegion() throws Exception {
        try(Screen screen = new DefaultTerminalFactory().createScreen()) {
            List<ScreenRegion> ret = ScreenRegionFactory.createRegions(screen);
            assertEquals(ret.size(), 3);
        }
    }

    @Test
    void testCreateFromnRegion() throws Exception {
        Region reg = new Region("hola", 3, 4, 20, 30);
        List<ScreenRegion> ret = ScreenRegionFactory.createRegions(reg);
        assertEquals(ret.size(), 3);
    }

}
