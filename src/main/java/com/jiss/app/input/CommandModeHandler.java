package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;
import java.util.ArrayList;

public class CommandModeHandler implements KeyInputHandler<CommandContext> {
    @Override
    public LoopStatus handleTextInput(CommandContext context) throws IOException {

        EditorMode mode = EditorMode.COMMAND;
        KeyStroke key = context.getKeyStroke();
        TerminalPosition pos = context.getTerminalPosition();
        StringBuilder commandBuffer = context.getCommandBuffer();
        if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Character ) {
            commandBuffer.append(key.getCharacter());
        } else if (key.getKeyType() == KeyType.Enter ) {
            // Execute command (for now, just print it)
            System.out.println("Command: " + commandBuffer.toString());
            commandBuffer.setLength(0); // Clear command buffer
        } else if (key.getKeyType() == KeyType.Backspace && pos.getColumn() > 0) {
            commandBuffer.deleteCharAt(pos.getColumn() - 1);
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.EOF) {
            mode = EditorMode.STOPPED;
        }
        return new LoopStatus( mode, pos);
    }

}
