package com.jiss.app.input;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import java.util.List;

public interface VisualContext {
    TerminalPosition getTerminalPosition();
    KeyStroke getKeyStroke();
    StringBuilder getCommandBuffer();
    List<String> getBuffer();
}
