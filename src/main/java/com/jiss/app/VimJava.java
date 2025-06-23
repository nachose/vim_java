package com.jiss.app;


//import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TextCharacter;
import java.io.IOException;
import java.util.ArrayList;
import com.jiss.app.display.ScreenRegion;

import com.jiss.app.display.ScreenRegionFactory;
import com.jiss.app.input.KeyHandler;
import com.jiss.app.input.LoopStatus;
import com.jiss.app.util.FpsCounter;


public class VimJava {

    static private KeyHandler handler_ = new KeyHandler();
    static private ArrayList<ScreenRegion> regions = ScreenRegionFactory.getRegions();


    public static void main(String[] args) throws Exception {
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

                drawBuffer(screen, buffer);

                // Example usage in main loop
                counter.increment();
                drawStatusLine(screen, mode.getModeStr(), counter.getFps());

                drawCommandLine(screen, commandBuffer);

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

    private static void drawCommandLine(Screen screen, StringBuilder commandBuffer) {
        // Draw command line
        int height = screen.getTerminalSize().getRows();
        int width = screen.getTerminalSize().getColumns();
        String commandPrompt = ":" + commandBuffer.toString();
        for (int col = 0; col < width; col++) {
            char ch = col < commandPrompt.length() ? commandPrompt.charAt(col) : ' ';
            screen.setCharacter(col, height - 1, TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
        }
    }

    // Update drawStatusLine to accept mode and fps
    private static void drawStatusLine(Screen screen, String mode, int fps) {
        int height = screen.getTerminalSize().getRows();
        int width = screen.getTerminalSize().getColumns();
        String left = mode;
        String right = "FPS: " + fps;
        int space = width - left.length() - right.length();
        String status;
        if (space > 0) {
            status = left + " ".repeat(space) + right;
        } else {
            status = (left + right).substring(0, width);
        }
        for (int col = 0; col < width; col++) {
            char ch = col < status.length() ? status.charAt(col) : ' ';
            screen.setCharacter(col, height - 2, TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT)[0]);
        }
    }

//    private static void drawStatusLine(Screen screen) {
//
//        // Draw status line
//        int height = screen.getTerminalSize().getRows();
//        int width = screen.getTerminalSize().getColumns();
//        String status = "NORMAL"; // or INSERT, etc.
//        for (int col = 0; col < width; col++) {
//            char ch = col < status.length() ? status.charAt(col) : ' ';
//            screen.setCharacter(col, height - 2, TextCharacter.fromCharacter(
//                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT)[0]);
//        }
//    }

    private static void drawBuffer(Screen screen, ArrayList<String> buffer) {
        int height = screen.getTerminalSize().getRows();
        int width = screen.getTerminalSize().getColumns();
        // Draw each line, avoid last two lines (status and command)
        for (int line = 0; line < buffer.size() && line < height - 2; line++) {
            String textLine = buffer.get(line);
            for (int col = 0; col < textLine.length() && col < width; col++) {
                screen.setCharacter(col, line, TextCharacter.fromCharacter(
                        textLine.charAt(col), TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
            }
        }
    }

//    private static void drawBuffer(Screen screen, ArrayList<String> buffer) throws InterruptedException {
//        // Draw buffer (avoid last two lines)
//        int height = screen.getTerminalSize().getRows();
//        // int width = screen.getTerminalSize().getColumns();
//        int line = 0;
//        int column = 0;
//        for (int i = 0; i < buffer.length() && i < height - 2; i++) {
//            if (buffer.charAt(i) == '\n') {
//                line++;
//                //drawDebugginErrorMessage(screen, "New line detected at index: " + i);
//                column = 0; // Reset column for new line
//                continue; // Skip newline characters
//            }
//            screen.setCharacter(column++, line, TextCharacter.fromCharacter(
//                    buffer.charAt(i), TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
//        }
//    }

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
