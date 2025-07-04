package com.jiss.app.display;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.jiss.app.EditorMode;
import com.googlecode.lanterna.screen.Screen;
import com.jiss.app.event.EnterCommandModeEvent;
//import org.springframework.stereotype.Component;
import com.google.common.eventbus.Subscribe;
import com.jiss.app.event.EnterCommandModeEvent;

//@Component
public class CommandLineRegion extends Region implements ScreenRegion<CommandLineContext> {

    private final StringBuilder commandBuffer;

//    public CommandLineRegion(Region region) {
//        super(region.getName(), region.getX(), region.getY(), region.getWidth(), region.getHeight());
//        commandBuffer = null;
//    }

    public CommandLineRegion(Region region, StringBuilder commandBuffer) {
        super(region.getName(), region.getX(), region.getY(), region.getWidth(), region.getHeight());
        this.commandBuffer = commandBuffer;
    }
//
//    public CommandLineRegion(String name, int x, int y, int width, int height) {
//        super(name, x, y, width, height);
//        commandBuffer = null;
//    }
    @Override
    public void draw(CommandLineContext context) {
        // Draw command line
        Screen screen = context.getScreen();
        String commandPrompt = context.getMode() == EditorMode.COMMAND ? ":" : "";
        commandPrompt = commandPrompt + context.getCommandBuffer().toString();
        for (int col = 0; col < getWidth(); col++) {
            char ch = col < commandPrompt.length() ? commandPrompt.charAt(col) : ' ';
            screen.setCharacter(getX() + col, getY(), TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
        }
    }

    @Subscribe
    private void handleCommandLineEvent(EnterCommandModeEvent event) {
        this.commandBuffer.setLength(0); // Clear existing command
    }
}
