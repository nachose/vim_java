package com.jiss.app.display;

import java.util.*;

public class LayoutManager{
    private final List<Region> regions = new ArrayList<>();
    private Region region_;

    public LayoutManager(Region r) {
        this.region_ = r;
    }

    public void addRegion(Region region) {
        regions.add(region);
    }

    public void addRegionList(List<Region> regionsList) {
        for(Region r: regionsList) {
            addRegion(r);
        }
    }

    public Region getRegion(String name) {
        for (Region r : regions) {
            if (r.getName().equals(name)) return r;
        }
        return null;
    }

    public List<Region> getRegions() {
        return Collections.unmodifiableList(regions);
    }

    public void layoutVertical() {
        int regionHeight = region_.getHeight() / Math.max(1, regions.size());
        int currentY = region_.getY();
        for (Region r : regions) {
            r.setBounds(region_.getX(), currentY, region_.getWidth(), regionHeight);
            currentY += regionHeight;
        }
    }

    public void layoutHorizontal() {
        int regionWidth = region_.getWidth() / Math.max(1, regions.size());
        int currentX = region_.getX();
        for (Region r : regions) {
            r.setBounds(currentX, region_.getY(), regionWidth, region_.getHeight());
            currentX += regionWidth;
        }
    }


    public void draw(WindowContext context) {
       for(Region r :regions) {
         if(r instanceof ScreenRegion) {
             ((ScreenRegion)r).draw(context);
         }
       }
    }

}