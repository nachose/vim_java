package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;
import com.jiss.app.command.CommandFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jiss.app.input.InputContext;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandModeHandlerTest {

    private CommandModeHandler handler;
    @BeforeEach
    void setUp() {
        if(handler == null) {
            handler = new CommandModeHandler(new CommandFactory(new ArrayList<String>()));
        }
    }

    @Test
    void testEscapeSwitchesToNormalMode() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Escape);
        TerminalPosition pos = new TerminalPosition(2, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList();
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals(EditorMode.NORMAL, status.mode());
        assertEquals(pos, status.pos());
    }

    @Test
    void testCharacterAppendsToCommandBuffer() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Character);
        when(key.getCharacter()).thenReturn('x');
        TerminalPosition pos = new TerminalPosition(1, 0);
        StringBuilder commandBuffer = new StringBuilder("a");
        ArrayList<String> buffer = new ArrayList();

        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals("ax", commandBuffer.toString());
        assertEquals(EditorMode.COMMAND, status.mode());
        assertEquals(pos, status.pos());
    }

    @Test
    void testEnterClearsCommandBuffer() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Enter);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder("cmd");
        ArrayList<String> buffer = new ArrayList();

        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals("", commandBuffer.toString());
        assertEquals(EditorMode.NORMAL, status.mode());
        assertEquals(pos, status.pos());
    }

    @Test
    void testBackspaceDeletesCharacter() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Backspace);
        TerminalPosition pos = new TerminalPosition(2, 0);
        StringBuilder commandBuffer = new StringBuilder("abc");
        ArrayList<String> buffer = new ArrayList();

        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals("ac", commandBuffer.toString());
        assertEquals(EditorMode.COMMAND, status.mode());
        assertEquals(new TerminalPosition(1, 0), status.pos());
    }

    @Test
    void testBackspaceAtColumnZeroDoesNothing() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Backspace);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder("abc");
        ArrayList<String> buffer = new ArrayList();

        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals("abc", commandBuffer.toString());
        assertEquals(EditorMode.COMMAND, status.mode());
        assertEquals(pos, status.pos());
    }

    @Test
    void testEOFStopsRunning() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.EOF);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList();

        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals(EditorMode.STOPPED, status.mode());
        assertEquals(pos, status.pos());
    }
}
