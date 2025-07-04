package com.jiss.app.display;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class ScreenRegionFactory {


    public static List<ScreenRegion> createRegions(Screen screen) {

        int height = screen.getTerminalSize().getRows();
        int width = screen.getTerminalSize().getColumns();

        Region statusRegion = new Region("Status", 0, height -2, width, 1);
        Region commandRegion = new Region("Command", 0, height -1, width, 1);
        Region bufferRegion = new Region("Buffer", 0, 0, width, height - 2);

        ArrayList<ScreenRegion> thisList = new ArrayList<>();

        thisList.add(new StatusLineRegion(statusRegion));
        thisList.add(new CommandLineRegion(commandRegion, new StringBuilder()));
        thisList.add(new BufferRegion(bufferRegion));

        return thisList;

    }

    public static List<ScreenRegion> createRegions(Region region) {

        int height = region.getHeight();
        int width = region.getWidth();

        Region statusRegion = new Region("Status", region.getX(), height -2, width, 1);
        Region commandRegion = new Region("Command", region.getX(), height -1, width, 1);
        Region bufferRegion = new Region("Buffer", region.getX(), region.getY(), width, height - 2);

        ArrayList<ScreenRegion> thisList = new ArrayList<>();

        thisList.add(new StatusLineRegion(statusRegion));
        thisList.add(new CommandLineRegion(commandRegion, new StringBuilder()));
        thisList.add(new BufferRegion(bufferRegion));

        return thisList;

    }

}
