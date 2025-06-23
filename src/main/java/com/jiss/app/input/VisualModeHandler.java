package com.jiss.app.input;

import com.googlecode.lanterna.input.KeyType;
import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;
import java.util.ArrayList;

public class VisualModeHandler implements KeyInputHandler<VisualContext> {
    @Override
    public LoopStatus handleTextInput(VisualContext context) throws IOException {
        EditorMode mode = EditorMode.VISUAL;
        KeyStroke key = context.getKeyStroke();
        TerminalPosition pos = context.getTerminalPosition();
        ArrayList<String> buffer = context.getBuffer();
        StringBuilder commandBuffer = context.getCommandBuffer();
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
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            //Only move right if not at the end of the buffer
            if(pos.getColumn() < buffer.get(pos.getRow()).length()) {
                pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
            }
        } else if (key.getKeyType() == KeyType.EOF) {
            mode = EditorMode.STOPPED;
        }
        return new LoopStatus( mode, pos);
    }
}
