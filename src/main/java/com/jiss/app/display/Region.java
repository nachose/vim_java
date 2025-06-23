package com.jiss.app.display;

public class Region<C> implements ScreenRegion<C> {
    protected int x, y, width, height;
    protected String name;

    public Region(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    @Override
    public void draw(C context) {
        // Default: do nothing. Subclasses override to render content.
    }
}