package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;
import java.util.List;

import java.io.IOException;

public class NormalModeHandler implements KeyInputHandler<NormalContext> {
    @Override
    public LoopStatus handleTextInput(NormalContext context) throws IOException {
        EditorMode mode = EditorMode.NORMAL;
        KeyStroke key = context.getKeyStroke();
        TerminalPosition pos = context.getTerminalPosition();
        List<String> buffer = context.getBuffer();
        StringBuilder commandBuffer = context.getCommandBuffer();
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'i') {
            mode = EditorMode.INSERT;
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == ':') {
            mode = EditorMode.COMMAND;
            commandBuffer.setLength(0); // Clear command buffer
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'j') {
            // Move down in the buffer
            if (pos.getRow() < buffer.size() - 1) {
                pos = new TerminalPosition(pos.getColumn(), pos.getRow() + 1);
            }
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'k') {
            // Move up in the buffer
            if (pos.getRow() > 0) {
                pos = new TerminalPosition(pos.getColumn(), pos.getRow() - 1);
            }
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'h') {
            // Move left in the buffer
            if (pos.getColumn() > 0) {
                pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
            }
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'l') {
            // Move right in the buffer
            if (pos.getColumn() < buffer.get(pos.getRow()).length()) {
                pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
            }
        } else if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Backspace && pos.getColumn() > 0) {
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowLeft && pos.getColumn() > 0) {
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            //Only move right if not at the end of the buffer
            if(pos.getColumn() < buffer.get(pos.getRow()).length()) {
                pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
            }
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
