package com.jiss.app.input;

import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;

public interface KeyInputHandler {

    LoopStatus handleTextInput(TerminalPosition pos,
                            KeyStroke key,
                            StringBuilder commandBuffer,
                            StringBuilder buffer) throws IOException;
}
