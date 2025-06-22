package com.jiss.app.display;

import com.jiss.app.EditorMode;

public interface BufferContext {
    StringBuilder getBuffer();
    EditorMode getMode();
}
