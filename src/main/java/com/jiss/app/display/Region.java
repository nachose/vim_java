package com.jiss.app.display;

import java.lang.IllegalArgumentException;

public class Region {
    protected int x, y, width, height;
    protected String name;

    public Region(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        validate();

    }

    public void validate() {
       if(name == null || name.isEmpty()) throw new IllegalArgumentException();
       if(x < 0) throw new IllegalArgumentException();
       if(y < 0) throw new IllegalArgumentException();
       if(width <= 0) throw  new IllegalArgumentException();
       if(height <= 0) throw new IllegalArgumentException();
    }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getXMax() {return x + width;}
    public int getY() { return y; }
    public int getYMax() { return y + height;}
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

}