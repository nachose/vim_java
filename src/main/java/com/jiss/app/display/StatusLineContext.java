package com.jiss.app.display;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.screen.Screen;

public interface StatusLineContext {
    Screen getScreen();
    EditorMode getMode();
    int getFps();
}
