package com.jiss.app.display;

import com.jiss.app.EditorMode;

public class WindowContext implements StatusLineContext, CommandLineContext, BufferContext {
    private final EditorMode mode;
    private final StringBuilder buffer;
    private final StringBuilder commandBuffer;

    public WindowContext(EditorMode mode, StringBuilder buffer, StringBuilder commandBuffer) {
        this.mode = mode;
        this.buffer = buffer;
        this.commandBuffer = commandBuffer;
    }

    @Override
    public EditorMode getMode() { return mode; }
    @Override
    public StringBuilder getBuffer() { return buffer; }
    @Override
    public StringBuilder getCommandBuffer() { return commandBuffer; }
}