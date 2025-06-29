package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TerminalPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeyHandlerTest {

    private KeyHandler keyHandler;
    private ScreenStatus screenStatus;
    private StringBuilder commandBuffer;
    private List<String> buffer;
    private KeyInputHandler handler;
    private KeyStroke keyStroke;

    @BeforeEach
    void setUp() {
        buffer = new ArrayList<String>();
        keyHandler = new KeyHandler(buffer);
        screenStatus = mock(ScreenStatus.class);
        commandBuffer = new StringBuilder();
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
        keyHandler.setModeInMap(EditorMode.NORMAL, handler);
        LoopStatus status = new LoopStatus( EditorMode.NORMAL, screenStatus.getPosition());
        when(handler.handleTextInput(any())).thenReturn(status);

        status = keyHandler.handleTextInput(screenStatus, commandBuffer);

        assertTrue(status.mode() != EditorMode.STOPPED);
        verify(handler).handleTextInput(any());
    }

    @Test
    void testHandleTextInput_NormalMode_RunningFalse() throws IOException {
        keyHandler.setModeInMap(EditorMode.NORMAL, handler);
        LoopStatus status = new LoopStatus( EditorMode.STOPPED, screenStatus.getPosition());
        when(handler.handleTextInput(any())).thenReturn(status);

        status = keyHandler.handleTextInput(screenStatus, commandBuffer);

        assertTrue(status.mode() == EditorMode.STOPPED);
    }

    @Test
    void testHandleTextInput_VisualLineMode() throws IOException {
        keyHandler.setModeInMap(EditorMode.VISUALLINE, handler);
        // Simulate switching to VISUALLINE mode
        LoopStatus status = new LoopStatus( EditorMode.VISUALLINE, screenStatus.getPosition());
        when(handler.handleTextInput(any())).thenReturn(status);

        // Set KeyHandler's mode_ to VISUALLINE via reflection
        keyHandler.setEditorMode(EditorMode.VISUALLINE);

        status = keyHandler.handleTextInput(screenStatus, commandBuffer);

        assertTrue(status.mode() != EditorMode.STOPPED);
        verify(handler).handleTextInput(any());
    }

    @Test
    void testHandleTextInput_VisualBlockMode() throws IOException {
        keyHandler.setModeInMap(EditorMode.VISUALBLOCK, handler);
        LoopStatus status = new LoopStatus( EditorMode.VISUALBLOCK, screenStatus.getPosition());
        when(handler.handleTextInput(any())).thenReturn(status);

        keyHandler.setEditorMode(EditorMode.VISUALBLOCK);

        status = keyHandler.handleTextInput(screenStatus, commandBuffer);

        assertTrue(status.mode() != EditorMode.STOPPED);
        verify(handler).handleTextInput(any());
    }
}

