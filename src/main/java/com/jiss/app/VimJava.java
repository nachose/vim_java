package com.jiss.app;


//import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import java.io.IOException;



public class VimJava {

    static private EditorMode mode_ = EditorMode.NORMAL;


    public static void main(String[] args) throws Exception {
        try(Screen screen = new DefaultTerminalFactory().createScreen()) {

            screen.startScreen();
            ScreenStatus screenStatus = new ScreenStatus(screen);
            boolean running = true;
            StringBuilder buffer = new StringBuilder();
            TerminalPosition pos =
                    new TerminalPosition(screenStatus.getCursorX(), screenStatus.getCursorY());
            int height = screen.getTerminalSize().getRows();
            int width = screen.getTerminalSize().getColumns();
            StringBuilder commandBuffer = new StringBuilder();

            while (running) {
                screen.clear();

                drawBuffer(screen, buffer, height);

                drawStatusLine(screen, width, height);

                drawCommandLine(screen, commandBuffer, width, height);

                pos.withRow(screenStatus.getCursorY()).withColumn(screenStatus.getCursorX());
                screen.setCursorPosition(pos);
                screen.refresh();

                running = handleTextInput(screenStatus, commandBuffer, buffer);
            }
            screen.stopScreen();
        }
    }

    private static boolean handleTextInput(ScreenStatus screen,
                                           StringBuilder commandBuffer,
                                           StringBuilder buffer) throws IOException {

        boolean running = true;
        KeyStroke key = screen.getScreen().readInput();
        if (key.getKeyType() == KeyType.Escape) {
            mode_ = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Character && mode_ != EditorMode.INSERT && key.getCharacter() == 'i') {
            mode_ = EditorMode.INSERT;
        } else if (key.getKeyType() == KeyType.Character && mode_ != EditorMode.INSERT && key.getCharacter() == ':') {
            mode_ = EditorMode.COMMAND;
            commandBuffer.setLength(0); // Clear command buffer
        } else if (key.getKeyType() == KeyType.Character && mode_ == EditorMode.COMMAND) {
            commandBuffer.append(key.getCharacter());
        } else if (key.getKeyType() == KeyType.Enter && mode_ == EditorMode.COMMAND) {
            // Execute command (for now, just print it)
            System.out.println("Command: " + commandBuffer.toString());
            commandBuffer.setLength(0); // Clear command buffer
            mode_ = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Backspace && screen.getCursorX() > 0) {
            buffer.deleteCharAt(screen.getCursorX() - 1);
            screen.setCursorX(screen.getCursorX() - 1);
        } else if (key.getKeyType() == KeyType.Character && mode_ == EditorMode.INSERT) {
            buffer.insert(screen.getCursorX(), key.getCharacter());
            screen.setCursorX(screen.getCursorX() + 1);
        } else if (key.getKeyType() == KeyType.ArrowLeft && screen.getCursorX() > 0) {
            screen.setCursorX(screen.getCursorX() - 1);
        } else if (key.getKeyType() == KeyType.ArrowRight && screen.getCursorX() < buffer.length()) {
            screen.setCursorX(screen.getCursorX() + 1);
        } else if (key.getKeyType() == KeyType.EOF) {
            running = false;
        }

        return running;
    }

    private static void drawCommandLine(Screen screen, StringBuilder commandBuffer, int width, int height) {
        // Draw command line
        String commandPrompt = ":" + commandBuffer.toString();
        for (int col = 0; col < width; col++) {
            char ch = col < commandPrompt.length() ? commandPrompt.charAt(col) : ' ';
            screen.setCharacter(col, height - 1, TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
        }
    }

    private static void drawStatusLine(Screen screen, int width, int height) {
        // Draw status line
        String status = "NORMAL"; // or INSERT, etc.
        for (int col = 0; col < width; col++) {
            char ch = col < status.length() ? status.charAt(col) : ' ';
            screen.setCharacter(col, height - 2, TextCharacter.fromCharacter(
                    ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT)[0]);
        }
    }

    private static void drawBuffer(Screen screen, StringBuilder buffer, int height) {
        // Draw buffer (avoid last two lines)
        for (int i = 0; i < buffer.length() && i < height - 2; i++) {
            screen.setCharacter(i, 1, TextCharacter.fromCharacter(
                    buffer.charAt(i), TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
        }
    }
}
