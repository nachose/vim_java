package com.jiss.app.input;

import com.jiss.app.ScreenStatus;
import com.jiss.app.LoopStatus;

import java.io.IOException;

public interface KeyInputHandler {

    LoopStatus handleTextInput(ScreenStatus screen,
                            StringBuilder commandBuffer,
                            StringBuilder buffer) throws IOException;
}
