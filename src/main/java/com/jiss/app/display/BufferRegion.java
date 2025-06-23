package com.jiss.app.display;

public class BufferRegion extends Region implements ScreenRegion<BufferContext> {

    public BufferRegion(Region region) {
        super(region.getName(), region.getX(), region.getY(), region.getWidth(), region.getHeight());
    }

    public BufferRegion(String name, int x, int y, int width, int height) {
        super(name, x, y, width, height);
    }
    @Override
    public void draw(BufferContext context) {
        // Draw buffer using context.buffer, etc.
    }
}
