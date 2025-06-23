package com.jiss.app.display;

import com.jiss.app.EditorMode;
import java.util.ArrayList;

public interface BufferContext {
    ArrayList<String> getBuffer();
    EditorMode getMode();
}
