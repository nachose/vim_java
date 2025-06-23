package com.jiss.app.unit_test.input;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;
import org.junit.jupiter.api.Test;
import com.jiss.app.input.InsertModeHandler;
import com.jiss.app.input.LoopStatus;
import com.jiss.app.input.InputContext;

import java.io.IOException;
import java.util.ArrayList;

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
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals(EditorMode.NORMAL, status.mode());
        assertEquals(pos, status.pos());
    }
    @Test
    void testFirstCharacter() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Character);
        when(key.getCharacter()).thenReturn('x');
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals(EditorMode.INSERT, status.mode());
        assertEquals("[x]", buffer.toString());
        TerminalPosition expectedPos = new TerminalPosition(1, 0);
        assertEquals(expectedPos, status.pos());
    }

    @Test
    void testBackspaceDeletesCharacter() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Backspace);
        TerminalPosition pos = new TerminalPosition(2, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals("ac", buffer.getFirst());
        assertEquals(EditorMode.INSERT, status.mode());
        assertEquals(new TerminalPosition(1, 0), status.pos());
    }

    @Test
    void testBackspaceAtColumnZeroDoesNothing() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Backspace);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals("abc", buffer.getFirst());
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
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("ab");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals("axb", buffer.getFirst());
        assertEquals(EditorMode.INSERT, status.mode());
        assertEquals(new TerminalPosition(2, 0), status.pos());
    }

    @Test
    void testArrowLeftMovesCursorLeft() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.ArrowLeft);
        TerminalPosition pos = new TerminalPosition(2, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals(new TerminalPosition(1, 0), status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testArrowLeftAtColumnZeroDoesNothing() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.ArrowLeft);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals(pos, status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testArrowRightMovesCursorRight() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.ArrowRight);
        TerminalPosition pos = new TerminalPosition(1, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals(new TerminalPosition(2, 0), status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testArrowRightAtBufferEndDoesNothing() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.ArrowRight);
        TerminalPosition pos = new TerminalPosition(3, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

        assertEquals(pos, status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testEnterInsertsNewlineAndMovesCursor() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.Enter);
        TerminalPosition pos = new TerminalPosition(2, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);


        assertEquals(2, buffer.size());
        assertEquals("ab", buffer.get(0));
        assertEquals("c", buffer.get(1));
        assertEquals("[ab, c]", buffer.toString());
        assertEquals(new TerminalPosition(0, 1), status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }

    @Test
    void testEOFStopsRunning() throws IOException {
        KeyStroke key = mock(KeyStroke.class);
        when(key.getKeyType()).thenReturn(KeyType.EOF);
        TerminalPosition pos = new TerminalPosition(0, 0);
        StringBuilder commandBuffer = new StringBuilder();
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        LoopStatus status = handler.handleTextInput(context);

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
        ArrayList<String> buffer = new ArrayList<>();
        buffer.add("abc");
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);

        InsertModeHandler handler = new InsertModeHandler();
        LoopStatus status = handler.handleTextInput(context);

        assertEquals(2, buffer.size());
        assertEquals("", buffer.get(0));
        assertEquals("abc", buffer.get(1));
        assertEquals("[, abc]", buffer.toString());
        // Cursor should move to start of next line
        assertEquals(new TerminalPosition(0, 1), status.pos());
        assertEquals(EditorMode.INSERT, status.mode());
    }
}
