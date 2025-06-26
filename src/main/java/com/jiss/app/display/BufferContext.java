package com.jiss.app.display;

import com.jiss.app.EditorMode;
import com.googlecode.lanterna.screen.Screen;
import java.util.List;

public interface BufferContext {
    Screen getScreen();
    List<String> getBuffer();
    EditorMode getMode();
}
