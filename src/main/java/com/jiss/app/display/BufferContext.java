package com.jiss.app.display;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.screen.Screen;
import java.util.ArrayList;

public interface BufferContext {
    Screen getScreen();
    ArrayList<String> getBuffer();
    EditorMode getMode();
}
