package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.LoopStatus;
import com.jiss.app.ScreenStatus;

import java.io.IOException;

public class InsertModeHandler implements KeyInputHandler {
    @Override
    public LoopStatus handleTextInput(ScreenStatus screen, StringBuilder commandBuffer, StringBuilder buffer) throws IOException {
        return new LoopStatus(true, EditorMode.INSERT);
    }
}
