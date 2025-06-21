
// File: src/test/java/com/jiss/app/input/KeyHandlerTest.java
package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.input.LoopStatus;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TerminalPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeyHandlerTest {

    private KeyHandler keyHandler;
    private ScreenStatus screenStatus;
    private StringBuilder commandBuffer;
    private StringBuilder buffer;
    private KeyInputHandler handler;
    private KeyStroke keyStroke;

    @BeforeEach
    void setUp() {
        keyHandler = new KeyHandler();
        screenStatus = mock(ScreenStatus.class);
        commandBuffer = new StringBuilder();
        buffer = new StringBuilder();
        handler = mock(KeyInputHandler.class);
        keyStroke = mock(KeyStroke.class);

        // Mock getScreen().readInput()
        var screen = mock(com.googlecode.lanterna.screen.Screen.class);
        when(screenStatus.getScreen()).thenReturn(screen);
        try {
            when(screen.readInput()).thenReturn(keyStroke);
        } catch (IOException e) {
            fail("IOException in mock setup");
        }

        // Mock getPosition()
        var pos = mock(TerminalPosition.class);
        when(screenStatus.getPosition()).thenReturn(pos);

        // Replace the handler in the static map for NORMAL mode
        KeyHandlerTestUtils.setHandlerForMode(EditorMode.NORMAL, handler);
    }

    @Test
    void testHandleTextInput_RunningTrue() throws IOException {
        LoopStatus status = new LoopStatus(true, EditorMode.NORMAL, screenStatus.getPosition());
        when(handler.handleTextInput(any(), any(), any(), any())).thenReturn(status);

        boolean running = keyHandler.handleTextInput(screenStatus, commandBuffer, buffer);

        assertTrue(running);
        verify(handler).handleTextInput(any(), any(), any(), any());
    }

    @Test
    void testHandleTextInput_RunningFalse() throws IOException {
        LoopStatus status = new LoopStatus(false, EditorMode.NORMAL, screenStatus.getPosition());
        when(handler.handleTextInput(any(), any(), any(), any())).thenReturn(status);

        boolean running = keyHandler.handleTextInput(screenStatus, commandBuffer, buffer);

        assertFalse(running);
    }
}

// Utility class to set handler in the static map (reflection hack for testing)
class KeyHandlerTestUtils {
    static void setHandlerForMode(EditorMode mode, KeyInputHandler handler) {
        try {
            var field = KeyHandler.class.getDeclaredField("modesMap_");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            java.util.Map<EditorMode, KeyInputHandler> map =
                    (java.util.Map<EditorMode, KeyInputHandler>) field.get(null);
            map.put(mode, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
