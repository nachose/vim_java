package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;

// In file: src/main/java/com/jiss/app/LoopStatus.java
public record LoopStatus(boolean running, EditorMode mode, ScreenStatus.Position pos) {}
