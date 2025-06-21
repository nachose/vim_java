package com.jiss.app.input;

import com.googlecode.lanterna.input.KeyType;
import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;

public class VisualBlockModeHandler implements KeyInputHandler {
    @Override
    public LoopStatus handleTextInput(TerminalPosition pos,
                               KeyStroke key,
                               StringBuilder commandBuffer,
                               StringBuilder buffer) throws IOException {
        EditorMode mode = EditorMode.VISUAL;
        boolean running = true;
        if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == ':') {
            mode = EditorMode.COMMAND;
            commandBuffer.setLength(0); // Clear command buffer
        } else if (key.getKeyType() == KeyType.Backspace && pos.getColumn() > 0) {
            //selectionBuffer.deleteCharAt(pos.getColumn() - 1);
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowLeft && pos.getColumn() > 0) {
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowRight && pos.getColumn() < buffer.length()) {
            pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.EOF) {
          running = false;
        }
        return new LoopStatus(running, mode, pos);
    }
}
