package com.jiss.app.display;

import java.util.ArrayList;

public class ScreenRegionFactory {

    static private StatusLineRegion statusLine = new StatusLineRegion();
    static private CommandLineRegion commandLine = new CommandLineRegion();
    static private BufferRegion buffer = new BufferRegion();

    public static ArrayList<ScreenRegion> getRegions() {

        ArrayList<ScreenRegion> this_list = new ArrayList<>();

        this_list.add(statusLine);
        this_list.add(commandLine);
        this_list.add(buffer);

        return this_list;

    }


}
