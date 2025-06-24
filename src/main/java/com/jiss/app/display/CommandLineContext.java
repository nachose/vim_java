package com.jiss.app.display;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.screen.Screen;

public interface CommandLineContext {
    Screen getScreen();
    EditorMode getMode();
    StringBuilder getCommandBuffer();
}
