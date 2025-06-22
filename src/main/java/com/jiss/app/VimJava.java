package com.jiss.app;


//import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import java.io.IOException;
import com.jiss.app.input.KeyHandler;



public class VimJava {

    static private KeyHandler handler_ = new KeyHandler();


    public static void main(String[] args) throws Exception {
        try(Screen screen = new DefaultTerminalFactory().createScreen()) {

            screen.startScreen();
            ScreenStatus screenStatus = new ScreenStatus(screen);
            boolean running = true;
            StringBuilder buffer = new StringBuilder();
//            TerminalPosition pos =
//                    new TerminalPosition(screenStatus.getPosition().getColumn(),
//                                         screenStatus.getPosition().getRow());
            StringBuilder commandBuffer = new StringBuilder();

            while (running) {
                clearScreen(screen);

                drawBuffer(screen, buffer);

                drawStatusLine(screen);

                drawCommandLine(screen, commandBuffer);

                drawCursor(screenStatus, screen);

                refreshScreen(screen);

                running = handleTextInput(screenStatus, commandBuffer, buffer);
            }
            screen.stopScreen();
        }
    }

    private static void drawCursor(ScreenStatus screenStatus, Screen screen) {
        screen.setCursorPosition(screenStatus.getPosition());
    }

    private static void clearScreen(Screen screen) throws IOException {
        screen.clear();
    }

    private static void refreshScreen(Screen screen) throws IOException {
        screen.refresh(Screen.RefreshType.AUTOMATIC);
    }

    private static boolean handleTextInput(ScreenStatus screen,
                                           StringBuilder commandBuffer,
                                           StringBuilder buffer) throws IOException {

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

    private static void drawStatusLine(Screen screen) {
        // Draw status line
        int height = screen.getTerminalSize().getRows();
        int width = screen.getTerminalSize().getColumns();
        String status = "NORMAL"; // or INSERT, etc.
        for (int col = 0; col < width; col++) {
            char ch = col < status.length() ? status.charAt(col) : ' ';
            screen.setCharacter(col, height - 2, TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT)[0]);
        }
    }

    private static void drawBuffer(Screen screen, StringBuilder buffer) throws InterruptedException {
        // Draw buffer (avoid last two lines)
        int height = screen.getTerminalSize().getRows();
        // int width = screen.getTerminalSize().getColumns();
        int line = 0;
        int column = 0;
        for (int i = 0; i < buffer.length() && i < height - 2; i++) {
            if (buffer.charAt(i) == '\n') {
                line++;
                //drawDebugginErrorMessage(screen, "New line detected at index: " + i);
                column = 0; // Reset column for new line
                continue; // Skip newline characters
            }
            screen.setCharacter(column++, line, TextCharacter.fromCharacter(
                    buffer.charAt(i), TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
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
