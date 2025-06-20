package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;


public class InsertModeHandler implements KeyInputHandler {
    @Override
    public LoopStatus handleTextInput(ScreenStatus.Position pos,
                               KeyStroke key,
                               StringBuilder commandBuffer,
                               StringBuilder buffer) throws IOException {
        EditorMode mode = EditorMode.INSERT;
        boolean running = true;
        if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Backspace && pos.cursorX() > 0) {
            buffer.deleteCharAt(pos.cursorX() - 1);
            pos = new ScreenStatus.Position(pos.cursorX() -1, pos.cursorY());
        } else if (key.getKeyType() == KeyType.Character ) {
            buffer.insert(pos.cursorX(), key.getCharacter());
            pos = new ScreenStatus.Position(pos.cursorX() + 1, pos.cursorY());
        } else if (key.getKeyType() == KeyType.ArrowLeft && pos.cursorX() > 0) {
            pos = new ScreenStatus.Position(pos.cursorX() - 1, pos.cursorY());
        } else if (key.getKeyType() == KeyType.ArrowRight && pos.cursorX() < buffer.length()) {
            pos = new ScreenStatus.Position(pos.cursorX() + 1, pos.cursorY());
        } else if( key.getKeyType() == KeyType.Enter ) {
            // Handle Enter key in insert mode, e.g., add a newline
            buffer.insert(pos.cursorX(), '\n');
            pos = new ScreenStatus.Position(0, pos.cursorY() + 1); // Move cursor to next line
        } else if (key.getKeyType() == KeyType.EOF) {
            running = false;
        }
        return new LoopStatus(running, mode , pos);
    }
}
