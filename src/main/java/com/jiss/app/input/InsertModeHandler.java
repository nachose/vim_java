package com.jiss.app.input;

import com.jiss.app.EditorMode;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;


public class InsertModeHandler implements KeyInputHandler {
    @Override
    public LoopStatus handleTextInput(TerminalPosition pos,
                               KeyStroke key,
                               StringBuilder commandBuffer,
                               StringBuilder buffer) throws IOException {
        EditorMode mode = EditorMode.INSERT;
        boolean running = true;
        if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if( key.getKeyType() == KeyType.Enter ) {
            // Handle Enter key in insert mode, e.g., add a newline
            buffer.insert(pos.getColumn(), '\n');
            pos = new TerminalPosition(0, pos.getRow() + 1); // Move cursor to next line
        } else if (key.getKeyType() == KeyType.Backspace && pos.getColumn() > 0) {
            buffer.deleteCharAt(pos.getColumn() - 1);
            pos = new TerminalPosition(pos.getColumn() -1, pos.getRow());
        } else if (key.getKeyType() == KeyType.Character ) {
            buffer.insert(pos.getColumn(), key.getCharacter());
            pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowLeft && pos.getColumn() > 0) {
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowRight && pos.getColumn() < buffer.length()) {
            pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.EOF) {
            running = false;
        }
        return new LoopStatus(running, mode , pos);
    }
}
