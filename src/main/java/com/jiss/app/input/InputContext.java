package com.jiss.app.input;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.List;

public class InputContext implements InsertContext,
                                     NormalContext,
                                     CommandContext,
                                     VisualContext
{

    private TerminalPosition pos;
    private KeyStroke key;
    private StringBuilder commandBuffer;
    private List<String> buffer;

    public InputContext(TerminalPosition pos,
                        KeyStroke key,
                        StringBuilder commandBuffer,
                        List<String> buffer) {
        this.pos = pos;
        this.key = key;
        this.commandBuffer = commandBuffer;
        this.buffer = buffer;
    }

    @Override
    public TerminalPosition getTerminalPosition() {
      return pos;
    }

    @Override
    public KeyStroke getKeyStroke() {
       return key;
    }

    @Override
    public StringBuilder getCommandBuffer() {
        return commandBuffer;
    }

    @Override
    public List<String> getBuffer() {
        return buffer;
    }

}
