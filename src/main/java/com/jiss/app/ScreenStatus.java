package com.jiss.app;

import com.googlecode.lanterna.screen.Screen;


public class ScreenStatus {

    public record Position(int cursorX, int cursorY) {};
    private Position pos_;
    private final Screen screen_;

    public ScreenStatus (Screen screen) {
        screen_ = screen;
        pos_ = new Position(0,0);
    }

    public Position getPosition () {
        return pos_;
    }

    public void setPosition(Position pos) {
        pos_ = pos;
    }

    // public int getCursorX() {
    //     return pos_.cursorX();
    // }

    // public void setCursorX(int cursorX) {
    //     this.pos_ = new Position(cursorX, pos_.cursorY());
    // }

    // public int getCursorY() {
    //     return pos_.cursorY();
    // }

    // public void setCursorY(int cursorY) {
    //     this.pos_ = new Position(pos_.cursorX(), cursorY);
    // }

    public Screen getScreen() {
        return screen_;
    }

}
