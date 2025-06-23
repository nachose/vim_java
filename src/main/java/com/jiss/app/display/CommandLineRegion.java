package com.jiss.app.display;

public class CommandLineRegion extends Region implements ScreenRegion<CommandLineContext> {

    public CommandLineRegion(Region region) {
        super(region.getName(), region.getX(), region.getY(), region.getWidth(), region.getHeight());
    }

    public CommandLineRegion(String name, int x, int y, int width, int height) {
        super(name, x, y, width, height);
    }
    @Override
    public void draw(CommandLineContext context) {
        // Draw command line if context.mode == COMMAND, etc.
    }
}
