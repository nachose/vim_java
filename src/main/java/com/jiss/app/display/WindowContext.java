package com.jiss.app.display;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.TerminalPosition;
import java.util.ArrayList;

public class WindowContext implements StatusLineContext,
                                      CommandLineContext,
                                      BufferContext,
                                      PositionContext {
    private final EditorMode mode;
    private final ArrayList<String> buffer;
    private final StringBuilder commandBuffer;
    private final TerminalPosition position;

    public WindowContext(EditorMode mode,
                         ArrayList<String> buffer,
                         StringBuilder commandBuffer,
                         TerminalPosition position) {
        this.mode = mode;
        this.buffer = buffer;
        this.commandBuffer = commandBuffer;
        this.position = position;
    }

    @Override
    public EditorMode getMode() { return mode; }
    @Override
    public ArrayList<String> getBuffer() { return buffer; }
    @Override
    public StringBuilder getCommandBuffer() { return commandBuffer; }
    @Override
    public TerminalPosition getPosition() { return position; }
}
