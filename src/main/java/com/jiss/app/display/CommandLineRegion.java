package com.jiss.app.display;

public class CommandLineRegion implements ScreenRegion<CommandLineContext> {
    @Override
    public void draw(CommandLineContext context) {
        // Draw command line if context.mode == COMMAND, etc.
    }
}
