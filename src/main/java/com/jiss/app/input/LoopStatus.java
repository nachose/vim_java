package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.ScreenStatus;
import com.googlecode.lanterna.TerminalPosition;

// In file: src/main/java/com/jiss/app/LoopStatus.java
public record LoopStatus( EditorMode mode, TerminalPosition pos) {}
