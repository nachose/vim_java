package com.jiss.app.display;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class StatusLineRegion extends  Region implements ScreenRegion<StatusLineContext> {

    public StatusLineRegion(Region region) {
        super(region.getName(), region.getX(), region.getY(), region.getWidth(), region.getHeight());
    }
    public StatusLineRegion(String name, int x, int y, int width, int height) {
        super(name, x, y, width, height);
    }
    @Override
    public void draw(StatusLineContext context) {
        // Draw status line using context.mode, context.screen, etc.
        Screen screen = context.getScreen();
        String left = context.getMode().getModeStr();
        String right = "FPS: " + context.getFps();
        int space = getWidth() - left.length() - right.length();
        String status;
        if (space > 0) {
            status = left + " ".repeat(space) + right;
        } else {
            status = (left + right).substring(0, getWidth());
        }
        for (int col = 0; col < getWidth(); col++) {
            char ch = col < status.length() ? status.charAt(col) : ' ';
            screen.setCharacter(getX() + col, getY(), TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT)[0]);
        }
    }
}
