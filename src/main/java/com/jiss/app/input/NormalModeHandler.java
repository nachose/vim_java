package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class NormalModeHandler implements KeyInputHandler {
    @Override
    public LoopStatus handleTextInput(ScreenStatus.Position pos,
                               KeyStroke key,
                               StringBuilder commandBuffer,
                               StringBuilder buffer) throws IOException {
        EditorMode mode = EditorMode.NORMAL;
        boolean running = true;
        if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'i') {
            mode = EditorMode.INSERT;
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == ':') {
            mode = EditorMode.COMMAND;
            commandBuffer.setLength(0); // Clear command buffer
        } else if (key.getKeyType() == KeyType.Backspace && pos.cursorX() > 0) {
            pos = new ScreenStatus.Position(pos.cursorX() - 1, pos.cursorY());
        } else if (key.getKeyType() == KeyType.ArrowLeft && pos.cursorX() > 0) {
            pos = new ScreenStatus.Position(pos.cursorX() - 1, pos.cursorY());
        } else if (key.getKeyType() == KeyType.ArrowRight && pos.cursorX() < buffer.length()) {
            pos = new ScreenStatus.Position(pos.cursorX() + 1, pos.cursorY());
        } else if (key.getKeyType() == KeyType.EOF) {
            running = false;
        }
        return new LoopStatus(running, mode, pos);
    }
}
