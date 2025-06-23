package com.jiss.app.display;

import java.util.*;

public class LayoutManager<C> implements ScreenRegion<C> {
    private final List<Region<C>> regions = new ArrayList<>();
    private int x, y, width, height;

    public LayoutManager(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    public void addRegion(Region<C> region) {
        regions.add(region);
    }

    public Region<C> getRegion(String name) {
        for (Region<C> r : regions) {
            if (r.getName().equals(name)) return r;
        }
        return null;
    }

    public List<Region<C>> getRegions() {
        return Collections.unmodifiableList(regions);
    }

    public void layoutVertical() {
        int regionHeight = height / Math.max(1, regions.size());
        int currentY = y;
        for (Region<C> r : regions) {
            r.setBounds(x, currentY, width, regionHeight);
            currentY += regionHeight;
        }
    }

    public void layoutHorizontal() {
        int regionWidth = width / Math.max(1, regions.size());
        int currentX = x;
        for (Region<C> r : regions) {
            r.setBounds(currentX, y, regionWidth, height);
            currentX += regionWidth;
        }
    }

    @Override
    public void draw(C context) {
        for (Region<C> r : regions) {
            r.draw(context);
        }
    }
}