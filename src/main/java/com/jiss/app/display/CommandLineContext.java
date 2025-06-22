package com.jiss.app.display;

import com.jiss.app.EditorMode;

public interface CommandLineContext {
    EditorMode getMode();
    StringBuilder getCommandBuffer();
}
