package com.jiss.app.input;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import java.util.ArrayList;

public interface VisualContext {
    TerminalPosition getTerminalPosition();
    KeyStroke getKeyStroke();
    StringBuilder getCommandBuffer();
    ArrayList<String> getBuffer();
}