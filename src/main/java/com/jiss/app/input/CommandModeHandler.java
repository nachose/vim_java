package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

public class CommandModeHandler implements KeyInputHandler {
    @Override
    public LoopStatus handleTextInput(ScreenStatus.Position pos,
                               KeyStroke key,
                               StringBuilder commandBuffer,
                               StringBuilder buffer) throws IOException {

        EditorMode mode = EditorMode.COMMAND;
        boolean running = true;
        if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Character ) {
            commandBuffer.append(key.getCharacter());
        } else if (key.getKeyType() == KeyType.Enter ) {
            // Execute command (for now, just print it)
            System.out.println("Command: " + commandBuffer.toString());
            commandBuffer.setLength(0); // Clear command buffer
        } else if (key.getKeyType() == KeyType.Backspace && pos.cursorX() > 0) {
            commandBuffer.deleteCharAt(pos.cursorX() - 1);
            pos = new ScreenStatus.Position(pos.cursorX() - 1, pos.cursorY());
        } else if (key.getKeyType() == KeyType.EOF) {
          running = false;
        }
        return new LoopStatus(running, mode, pos);
    }

}
