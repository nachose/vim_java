package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsertModeHandlerTest {

    private final InsertModeHandler handler = new InsertModeHandler();

    @Test
    void testEscapeSwitchesToNormalMode() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Escape);
        TerminalPosition pos = new TerminalPosition(2, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals(EditorMode.NORMAL, status.mode());
        assertEquals(pos, status.pos());
    }

    @Test
    void testBackspaceDeletesCharacter() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Backspace);
        TerminalPosition pos = new TerminalPosition(2, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals("ac", buffer.toString());
        assertEquals(EditorMode.INSERT, status.mode());
        assertEquals(new TerminalPosition(1, 0), status.pos());
    }

    @Test
    void testBackspaceAtColumnZeroDoesNothing() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Backspace);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals("abc", buffer.toString());
        assertEquals(EditorMode.INSERT, status.mode());
        assertEquals(pos, status.pos());
    }

    @Test
    void testCharacterInsertsAtCursor() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Character);
        when(key.getCharacter()).thenReturn('x');
        TerminalPosition pos = new TerminalPosition(1, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("ab");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals("axb", buffer.toString());
        assertEquals(EditorMode.INSERT, status.mode());
        assertEquals(new TerminalPosition(2, 0), status.pos());
    }

    @Test
    void testArrowLeftMovesCursorLeft() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.ArrowLeft);
        TerminalPosition pos = new TerminalPosition(2, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals(new TerminalPosition(1, 0), status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testArrowLeftAtColumnZeroDoesNothing() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.ArrowLeft);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals(pos, status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testArrowRightMovesCursorRight() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.ArrowRight);
        TerminalPosition pos = new TerminalPosition(1, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals(new TerminalPosition(2, 0), status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testArrowRightAtBufferEndDoesNothing() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.ArrowRight);
        TerminalPosition pos = new TerminalPosition(3, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals(pos, status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testEnterInsertsNewlineAndMovesCursor() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Enter);
        TerminalPosition pos = new TerminalPosition(2, 1);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);


        assertEquals("ab\nc", buffer.toString());
        assertEquals(new TerminalPosition(0, 2), status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testEOFStopsRunning() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.EOF);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder();

        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        assertEquals(EditorMode.STOPPED, status.mode());
        assertEquals(pos, status.pos());
    }

    @Test
    void testFirstEnterInsertsNewlineAndMovesCursor() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Enter);
        // Start at column 0, row 0 (beginning of buffer)
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        StringBuilder buffer = new StringBuilder("abc");

        InsertModeHandler handler = new InsertModeHandler();
        LoopStatus status = handler.handleTextInput(pos, key, commandBuffer, buffer);

        // Should insert newline at the start, buffer becomes "\nabc"
        assertEquals("\nabc", buffer.toString());
        // Cursor should move to start of next line
        assertEquals(new TerminalPosition(0, 1), status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }
}