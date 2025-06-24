package com.jiss.app;


import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TerminalPosition;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.jiss.app.display.ScreenRegion;

import com.jiss.app.display.ScreenRegionFactory;
import com.jiss.app.display.WindowContext;
import com.jiss.app.input.KeyHandler;
import com.jiss.app.input.LoopStatus;
import com.jiss.app.util.FpsCounter;


public class VimJava {

    static private KeyHandler handler_ = new KeyHandler();
    static private List<ScreenRegion> regions = null;

    public VimJava () {

    }


    public static void main(String[] args) throws Exception {

        VimJava vj = new VimJava();
        try(Screen screen = new DefaultTerminalFactory().createScreen()) {

            screen.startScreen();
            ScreenStatus screenStatus = new ScreenStatus(screen);
            boolean running = true;
            ArrayList<String> buffer = new ArrayList<>();
            StringBuilder commandBuffer = new StringBuilder();
            FpsCounter counter = new FpsCounter();
            EditorMode mode = EditorMode.NORMAL;

            while (running) {
                clearScreen(screen);

                WindowContext context = createWindowContext(screen,
                                                            mode,
                                                            screenStatus.getPosition(),
                                                            buffer,
                                                            commandBuffer,
                                                            counter.getFps());
                drawRegions(screen, context);

                // Example usage in main loop
                counter.increment();

                drawCursor(screenStatus);

                refreshScreen(screen);

                LoopStatus status = handleTextInput(screenStatus, commandBuffer, buffer);
                running = status.mode() != EditorMode.STOPPED;
                mode = status.mode();
            }
            screen.stopScreen();
        }
    }

    private static void drawCursor(ScreenStatus screenStatus) {
        screenStatus.updatePostion();
    }

    private static void clearScreen(Screen screen) throws IOException {
        screen.clear();
    }

    private static void refreshScreen(Screen screen) throws IOException {
        screen.refresh(Screen.RefreshType.AUTOMATIC);
    }

    private static LoopStatus handleTextInput(ScreenStatus screen,
                                           StringBuilder commandBuffer,
                                           ArrayList<String> buffer) throws IOException {

        return handler_.handleTextInput(screen, commandBuffer, buffer);

    }

    private static WindowContext createWindowContext(Screen screen,
                         EditorMode mode,
                         TerminalPosition position,
                         ArrayList<String> buffer,
                         StringBuilder commandBuffer,
                         int fps) {
        return new WindowContext(screen,
                                 mode,
                                 buffer,
                                 commandBuffer,
                                 position,
                                 fps);
    }

    private static void drawRegions(Screen screen, WindowContext context) {
        if (regions == null) {
            regions = ScreenRegionFactory.createRegions(screen);
        }
        for (ScreenRegion region : regions) {
            region.draw(context);
        }
    }




    private static void drawDebuggingErrorMessage(Screen screen, String message) throws InterruptedException {
        drawMessage(screen, message);
        Thread.sleep(3000);
        drawMessage(screen, "");
    }

    // Draws a message in the command line region (bottom line)
    private static void drawMessage(Screen screen, String message) {
        int height = screen.getTerminalSize().getRows();
        int width = screen.getTerminalSize().getColumns();
        for (int col = 0; col < width; col++) {
            char ch = col < message.length() ? message.charAt(col) : ' ';
            screen.setCharacter(col, height - 1, TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.RED)[0]);
        }
    }
}
