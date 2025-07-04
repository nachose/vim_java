package com.jiss.app.display;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TerminalPosition;
import java.util.List;

public class WindowContext implements StatusLineContext,
                                      CommandLineContext,
                                      BufferContext,
                                      PositionContext {
    private final EditorMode mode;
    private final List<String> buffer;
    private final StringBuilder commandBuffer;
    private final TerminalPosition position;
    private final Screen screen;
    private final int fps;

    public WindowContext(Screen screen,
                         EditorMode mode,
                         List<String> buffer,
                         StringBuilder commandBuffer,
                         TerminalPosition position,
                         int fps) {
        this.screen = screen;
        this.mode = mode;
        this.buffer = buffer;
        this.commandBuffer = commandBuffer;
        this.position = position;
        this.fps = fps;
    }

    @Override
    public Screen getScreen() { return screen; }
    @Override
    public EditorMode getMode() { return mode; }
    @Override
    public List<String> getBuffer() { return buffer; }
    @Override
    public StringBuilder getCommandBuffer() { return commandBuffer; }
    @Override
    public TerminalPosition getPosition() { return position; }
    @Override
    public int getFps() { return fps; }
}
