// File: src/test/java/com/jiss/app/input/NormalModeHandlerTest.java
package com.jiss.app.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;
import com.jiss.app.EditorMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.jiss.app.input.InputContext;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NormalModeHandlerTest {
    private NormalModeHandler handler;
    private ArrayList<String> buffer;
    private StringBuilder commandBuffer;

    @BeforeEach
    void setUp() {
        handler = new NormalModeHandler();
        buffer = new ArrayList<>();
        buffer.add("Hello World");
        buffer.add("Second line");
        buffer.add("Third line");
        buffer.add("Fourth line");
        buffer.add("Fifth line");
        commandBuffer = new StringBuilder();
    }

    @Test
    void testEscapeSwitchesToNormal() throws IOException {
        KeyStroke key = new KeyStroke(KeyType.Escape);
        TerminalPosition pos = new TerminalPosition(0, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(EditorMode.NORMAL, status.mode());
    }

    @Test
    void testColonSwitchesToCommand() throws IOException {
        KeyStroke key = new KeyStroke(':', false, false);
        TerminalPosition pos = new TerminalPosition(0, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(EditorMode.COMMAND, status.mode());
    }

    @Test
    void testArrowLeftMovesCursorLeft() throws IOException {
        KeyStroke key = new KeyStroke(KeyType.ArrowLeft);
        TerminalPosition pos = new TerminalPosition(5, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(4, status.pos().getColumn());
        assertEquals(0, status.pos().getRow());
    }

    @Test
    void testArrowRightMovesCursorRight() throws IOException {
        KeyStroke key = new KeyStroke(KeyType.ArrowRight);
        TerminalPosition pos = new TerminalPosition(0, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(1, status.pos().getColumn());
        assertEquals(0, status.pos().getRow());
    }

    @Test
    void testEOFStopsEditor() throws IOException {
        KeyStroke key = new KeyStroke(KeyType.EOF);
        TerminalPosition pos = new TerminalPosition(0, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(EditorMode.STOPPED, status.mode());
    }

    @Test
    void testKeyHMovesCursorLeft() throws IOException {
        KeyStroke key = new KeyStroke('h', false, false);
        TerminalPosition pos = new TerminalPosition(3, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(2, status.pos().getColumn());
        assertEquals(0, status.pos().getRow());
    }

    @Test
    void testKeyLMovesCursorRight() throws IOException {
        KeyStroke key = new KeyStroke('l', false, false);
        TerminalPosition pos = new TerminalPosition(0, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(1, status.pos().getColumn());
        assertEquals(0, status.pos().getRow());
    }

    @Test
    void testKeyJMovesCursorDown() throws IOException {
        KeyStroke key = new KeyStroke('j', false, false);
        TerminalPosition pos = new TerminalPosition(0, 1);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(0, status.pos().getColumn());
        assertEquals(2, status.pos().getRow());
    }

    @Test
    void testMoveSeveralDown() throws IOException {
        KeyStroke key = new KeyStroke('j', false, false);
        TerminalPosition pos = new TerminalPosition(0, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        context = new InputContext(status.pos(), key, commandBuffer, buffer);
        status = handler.handleTextInput(context);
        context = new InputContext(status.pos(), key, commandBuffer, buffer);
        status = handler.handleTextInput(context);
        context = new InputContext(status.pos(), key, commandBuffer, buffer);
        status = handler.handleTextInput(context);
        assertEquals(0, status.pos().getColumn());
        assertEquals(4, status.pos().getRow());
    }

    @Test
    void testMoveSeveralDownNotEnoughBuffer() throws IOException {
        //Have an empty buffer, then the keystrokes should not move the cursor down
        buffer = new ArrayList<>();
        KeyStroke key = new KeyStroke('j', false, false);
        TerminalPosition pos = new TerminalPosition(0, 0);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        context = new InputContext(status.pos(), key, commandBuffer, buffer);
        status = handler.handleTextInput(context);
        context = new InputContext(status.pos(), key, commandBuffer, buffer);
        status = handler.handleTextInput(context);
        context = new InputContext(status.pos(), key, commandBuffer, buffer);
        status = handler.handleTextInput(context);
        assertEquals(0, status.pos().getColumn());
        assertEquals(0, status.pos().getRow());
    }

    @Test
    void testKeyKMovesCursorUp() throws IOException {
        KeyStroke key = new KeyStroke('k', false, false);
        TerminalPosition pos = new TerminalPosition(0, 1);
        InputContext context = new InputContext(pos, key, commandBuffer, buffer);
        LoopStatus status = handler.handleTextInput(context);
        assertEquals(0, status.pos().getColumn());
        assertEquals(0, status.pos().getRow());
    }
}
