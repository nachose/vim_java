package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;

public class NormalModeHandler implements KeyInputHandler {
    @Override
    public LoopStatus handleTextInput(TerminalPosition pos,
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
        } else if (key.getKeyType() == KeyType.Backspace && pos.getColumn() > 0) {
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowLeft && pos.getColumn() > 0) {
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowRight && pos.getColumn() < buffer.length()) {
            pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
        }
        if (key.isShiftDown() && key.getCharacter() == 'v') {
            mode = EditorMode.VISUALLINE;
        }
        if ((key.isCtrlDown() && (key.getCharacter() == 'v' || key.getCharacter() == 'q'))) {
            mode = EditorMode.VISUALBLOCK;
        } else if (key.getKeyType() == KeyType.EOF) {
            mode = EditorMode.STOPPED;
        }
        return new LoopStatus( mode, pos);
    }
}
