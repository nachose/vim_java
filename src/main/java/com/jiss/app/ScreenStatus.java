package com.jiss.app;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TerminalPosition;


public class ScreenStatus {

    private TerminalPosition pos_;
    private final Screen screen_;

    public ScreenStatus (Screen screen) {
        screen_ = screen;
        pos_ = new TerminalPosition(0,0);
    }
    public ScreenStatus (Screen screen, TerminalPosition pos) {
        screen_ = screen;
        pos_ = pos;
    }

    public TerminalPosition getPosition () {
        return pos_;
    }

    public void setPosition(TerminalPosition pos) {
         pos_ = pos;
    }


    public Screen getScreen() {
        return screen_;
    }

    public void updatePostion() {
        screen_.setCursorPosition(pos_);
    }

}
