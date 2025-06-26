package com.jiss.app.input;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import java.util.List;

public interface InsertContext {
    TerminalPosition getTerminalPosition();
    KeyStroke getKeyStroke();
    List<String> getBuffer();
}
