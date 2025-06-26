package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

public class KeyHandler {

    private EditorMode mode_;
    private List<String> buffer_;
    private final TreeMap<EditorMode, KeyInputHandler> modesMap_ = new TreeMap<>();

    private void initKeyHandlers() {
        modesMap_.put(EditorMode.INSERT, new InsertModeHandler());
        modesMap_.put(EditorMode.COMMAND, new CommandModeHandler());
        modesMap_.put(EditorMode.VISUAL, new VisualModeHandler());
        modesMap_.put(EditorMode.NORMAL, new NormalModeHandler());
        modesMap_.put(EditorMode.VISUALLINE, new VisualLineModeHandler());
        modesMap_.put(EditorMode.VISUALBLOCK, new VisualBlockModeHandler());
    }

    //For testing purposes only.
    protected void setEditorMode(EditorMode mode) {
        this.mode_ = mode;
    }

    //For testing purposes only.
    protected void setModeInMap(EditorMode mode, KeyInputHandler handler) {
        modesMap_.put(mode, handler);
    }

    public KeyHandler(List<String> buffer) {
        mode_ = EditorMode.NORMAL;
        this.initKeyHandlers();
        this.buffer_ = buffer;
    }

    //TODO: Here, return a WindowContext, instead of a boolean.
    //Build a WindowContext everytime if needed.
    public LoopStatus handleTextInput(ScreenStatus screen, StringBuilder commandBuffer ) throws IOException {
        KeyStroke key = screen.getScreen().readInput();
        KeyInputHandler handler = modesMap_.get(mode_);

        InputContext context_ = new InputContext(screen.getPosition(),
                key,
                commandBuffer,
                buffer_);

        LoopStatus status = handler.handleTextInput(context_);

        mode_ = status.mode();
        screen.setPosition(status.pos());

        return status;

    }
}
