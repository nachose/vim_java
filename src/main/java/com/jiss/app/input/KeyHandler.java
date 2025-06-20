package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.TreeMap;

public class KeyHandler {

    private EditorMode mode_;
    private static TreeMap<EditorMode, KeyInputHandler> modesMap_ = new TreeMap<>();
    static
    {
        modesMap_.put(EditorMode.INSERT, new InsertModeHandler());
        modesMap_.put(EditorMode.COMMAND, new CommandModeHandler());
        modesMap_.put(EditorMode.VISUAL, new VisualModeHandler());
        modesMap_.put(EditorMode.NORMAL, new NormalModeHandler());
        modesMap_.put(EditorMode.VISUALLINE, new VisualLineModeHandler());
        modesMap_.put(EditorMode.VISUALBLOCK, new VisualBlockModeHandler());
    }

    public KeyHandler() {
        mode_ = EditorMode.NORMAL;
    }

    public boolean handleTextInput(ScreenStatus screen, StringBuilder commandBuffer, StringBuilder buffer) throws IOException {
        boolean running = true;
        KeyStroke key = screen.getScreen().readInput();
        KeyInputHandler handler = modesMap_.get(mode_);


        LoopStatus status = handler.handleTextInput(screen.getPosition(), key, commandBuffer, buffer);

        mode_ = status.mode();
        screen = new ScreenStatus(screen.getScreen(), status.pos());

        return status.running();

    }
}
