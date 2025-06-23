package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.input.LoopStatus;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TerminalPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeyHandlerTest {

    private KeyHandler keyHandler;
    private ScreenStatus screenStatus;
    private StringBuilder commandBuffer;
    private ArrayList<String> buffer;
    private KeyInputHandler handler;
    private KeyStroke keyStroke;

    @BeforeEach
    void setUp() {
        keyHandler = new KeyHandler();
        screenStatus = mock(ScreenStatus.class);
        commandBuffer = new StringBuilder();
        buffer = new ArrayList();
        handler = mock(KeyInputHandler.class);
        keyStroke = mock(KeyStroke.class);

        var screen = mock(com.googlecode.lanterna.screen.Screen.class);
        when(screenStatus.getScreen()).thenReturn(screen);
        try {
            when(screen.readInput()).thenReturn(keyStroke);
        } catch (IOException e) {
            fail("IOException in mock setup");
        }

        var pos = mock(TerminalPosition.class);
        when(screenStatus.getPosition()).thenReturn(pos);
    }

    @Test
    void testHandleTextInput_NormalMode_RunningTrue() throws IOException {
        KeyHandlerTestUtils.setHandlerForMode(EditorMode.NORMAL, handler);
        LoopStatus status = new LoopStatus( EditorMode.NORMAL, screenStatus.getPosition());
        when(handler.handleTextInput(any())).thenReturn(status);

        status = keyHandler.handleTextInput(screenStatus, commandBuffer, buffer);

        assertTrue(status.mode() != EditorMode.STOPPED);
        verify(handler).handleTextInput(any());
    }

    @Test
    void testHandleTextInput_NormalMode_RunningFalse() throws IOException {
        KeyHandlerTestUtils.setHandlerForMode(EditorMode.NORMAL, handler);
        LoopStatus status = new LoopStatus( EditorMode.STOPPED, screenStatus.getPosition());
        when(handler.handleTextInput(any())).thenReturn(status);

        status = keyHandler.handleTextInput(screenStatus, commandBuffer, buffer);

        assertTrue(status.mode() == EditorMode.STOPPED);
    }

    @Test
    void testHandleTextInput_VisualLineMode() throws IOException {
        KeyHandlerTestUtils.setHandlerForMode(EditorMode.VISUALLINE, handler);
        // Simulate switching to VISUALLINE mode
        LoopStatus status = new LoopStatus( EditorMode.VISUALLINE, screenStatus.getPosition());
        when(handler.handleTextInput(any())).thenReturn(status);

        // Set KeyHandler's mode_ to VISUALLINE via reflection
        TestUtils.setMode(keyHandler, EditorMode.VISUALLINE);

        status = keyHandler.handleTextInput(screenStatus, commandBuffer, buffer);

        assertTrue(status.mode() != EditorMode.STOPPED);
        verify(handler).handleTextInput(any());
    }

    @Test
    void testHandleTextInput_VisualBlockMode() throws IOException {
        KeyHandlerTestUtils.setHandlerForMode(EditorMode.VISUALBLOCK, handler);
        LoopStatus status = new LoopStatus( EditorMode.VISUALBLOCK, screenStatus.getPosition());
        when(handler.handleTextInput(any())).thenReturn(status);

        TestUtils.setMode(keyHandler, EditorMode.VISUALBLOCK);

        status = keyHandler.handleTextInput(screenStatus, commandBuffer, buffer);

        assertTrue(status.mode() != EditorMode.STOPPED);
        verify(handler).handleTextInput(any());
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

// Utility to set private mode_ field for testing
class TestUtils {
    static void setMode(KeyHandler keyHandler, EditorMode mode) {
        try {
            var field = KeyHandler.class.getDeclaredField("mode_");
            field.setAccessible(true);
            field.set(keyHandler, mode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
