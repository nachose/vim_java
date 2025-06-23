// File: src/test/java/com/jiss/app/input/VisualModeHandlerTest.java
package com.jiss.app.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;
import com.jiss.app.EditorMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VisualModeHandlerTest {
    private VisualModeHandler handler;
    private ArrayList<String> buffer;
    private StringBuilder commandBuffer;

    @BeforeEach
    void setUp() {
        handler = new VisualModeHandler();
        buffer = new ArrayList<>();
        buffer.add("Hello World");
        buffer.add("Second line");
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
}