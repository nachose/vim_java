package com.jiss.app.input;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TerminalPosition;
import java.util.ArrayList;

import java.io.IOException;

public interface KeyInputHandler<C> {

    LoopStatus handleTextInput(C Context) throws IOException;
}
