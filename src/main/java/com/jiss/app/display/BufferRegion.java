package com.jiss.app.display;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import java.util.List;

public class BufferRegion extends Region implements ScreenRegion<BufferContext> {

    public BufferRegion(Region region) {
        super(region.getName(), region.getX(), region.getY(), region.getWidth(), region.getHeight());
    }

    public BufferRegion(String name, int x, int y, int width, int height) {
        super(name, x, y, width, height);
    }
    @Override
    public void draw(BufferContext context) {
        List<String> buffer = context.getBuffer();
        Screen screen = context.getScreen();
        // Draw each line, avoid last two lines (status and command)
        for (int line = 0; line < buffer.size() && line < height - 2; line++) {
            String textLine = buffer.get(line);
            for (int col = 0; col < textLine.length() && col < width; col++) {
                screen.setCharacter(getX() + col, getY() + line, TextCharacter.fromCharacter(
                        textLine.charAt(col), TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
            }
        }
    }
}
