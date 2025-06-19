package com.jiss.app;

import com.googlecode.lanterna.screen.Screen;

public class ScreenStatus {

    private int cursorX_;
    private int cursorY_;
    private final Screen screen_;

    public ScreenStatus (Screen screen) {
        screen_ = screen;
        cursorX_ = 0;
        cursorY_ = 0;
    }

    public int getCursorX() {
        return cursorX_;
    }

    public void setCursorX(int cursorX) {
        this.cursorX_ = cursorX;
    }

    public int getCursorY() {
        return cursorY_;
    }

    public void setCursorY(int cursorY) {
        this.cursorY_ = cursorY;
    }

    public Screen getScreen() {
        return screen_;
    }

}
