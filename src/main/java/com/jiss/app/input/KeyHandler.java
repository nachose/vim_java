package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.LoopStatus;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.TreeMap;

public class KeyHandler implements KeyInputHandler {

    private EditorMode mode_;
    private static TreeMap<EditorMode, KeyInputHandler> modesMap_ = new TreeMap<>();
    static
    {
        modesMap_.put(EditorMode.INSERT, new InsertModeHandler());
        modesMap_.put(EditorMode.COMMAND, new CommandModeHandler());
        modesMap_.put(EditorMode.VISUAL, new VisualModeHandler());
        modesMap_.put(EditorMode.NORMAL, new NormalModeHandler());
    }

    @Override
    public LoopStatus handleTextInput(ScreenStatus screen, StringBuilder commandBuffer, StringBuilder buffer) throws IOException {
        boolean running = true;
        KeyStroke key = screen.getScreen().readInput();
        if (key.getKeyType() == KeyType.Escape) {
            mode_ = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Character && mode_ != EditorMode.INSERT && key.getCharacter() == 'i') {
            mode_ = EditorMode.INSERT;
        } else if (key.getKeyType() == KeyType.Character && mode_ != EditorMode.INSERT && key.getCharacter() == ':') {
            mode_ = EditorMode.COMMAND;
            commandBuffer.setLength(0); // Clear command buffer
        } else if (key.getKeyType() == KeyType.Character && mode_ == EditorMode.COMMAND) {
            commandBuffer.append(key.getCharacter());
        } else if (key.getKeyType() == KeyType.Enter && mode_ == EditorMode.COMMAND) {
            // Execute command (for now, just print it)
            System.out.println("Command: " + commandBuffer.toString());
            commandBuffer.setLength(0); // Clear command buffer
            mode_ = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Backspace && screen.getCursorX() > 0) {
            buffer.deleteCharAt(screen.getCursorX() - 1);
            screen.setCursorX(screen.getCursorX() - 1);
        } else if (key.getKeyType() == KeyType.Character && mode_ == EditorMode.INSERT) {
            buffer.insert(screen.getCursorX(), key.getCharacter());
            screen.setCursorX(screen.getCursorX() + 1);
        } else if (key.getKeyType() == KeyType.ArrowLeft && screen.getCursorX() > 0) {
            screen.setCursorX(screen.getCursorX() - 1);
        } else if (key.getKeyType() == KeyType.ArrowRight && screen.getCursorX() < buffer.length()) {
            screen.setCursorX(screen.getCursorX() + 1);
        } else if (key.getKeyType() == KeyType.EOF) {
            running = false;
        }

        return new LoopStatus(running, mode_);
    }
}
