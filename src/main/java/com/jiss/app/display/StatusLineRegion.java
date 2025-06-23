package com.jiss.app.display;

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
    }
}