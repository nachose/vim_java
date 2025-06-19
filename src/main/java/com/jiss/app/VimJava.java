package com.jiss.app;


//import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;

public class VimJava {
    public static void main(String[] args) throws Exception {
        try(Screen screen = new DefaultTerminalFactory().createScreen()) {

            screen.startScreen();
            boolean running = true;
            int cursorX = 0, cursorY = 0;
            StringBuilder buffer = new StringBuilder();
            boolean insertMode = false;
            TerminalPosition pos = new TerminalPosition(cursorX, cursorY);
            int height = screen.getTerminalSize().getRows();
            int width = screen.getTerminalSize().getColumns();
            StringBuilder commandBuffer = new StringBuilder();

            while (running) {
                screen.clear();

                // Draw buffer (avoid last two lines)
                for (int i = 0; i < buffer.length() && i < height - 2; i++) {
                    screen.setCharacter(i, 1, TextCharacter.fromCharacter(
                            buffer.charAt(i), TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
                }

                // Draw status line
                String status = "NORMAL"; // or INSERT, etc.
                for (int col = 0; col < width; col++) {
                    char ch = col < status.length() ? status.charAt(col) : ' ';
                    screen.setCharacter(col, height - 2, TextCharacter.fromCharacter(
                            ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK_BRIGHT)[0]);
                }

                // Draw command line
                String commandPrompt = ":" + commandBuffer.toString();
                for (int col = 0; col < width; col++) {
                    char ch = col < commandPrompt.length() ? commandPrompt.charAt(col) : ' ';
                    screen.setCharacter(col, height - 1, TextCharacter.fromCharacter(
                            ch, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)[0]);
                }

                pos.withRow(cursorY + 1).withColumn(cursorX);
                screen.setCursorPosition(pos);
                screen.refresh();

                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.Escape) {
                    insertMode = false;
                } else if (key.getKeyType() == KeyType.Character && !insertMode && key.getCharacter() == 'i') {
                    insertMode = true;
                } else if (key.getKeyType() == KeyType.Character && insertMode) {
                    buffer.insert(cursorX, key.getCharacter());
                    cursorX++;
                } else if (key.getKeyType() == KeyType.ArrowLeft && cursorX > 0) {
                    cursorX--;
                } else if (key.getKeyType() == KeyType.ArrowRight && cursorX < buffer.length()) {
                    cursorX++;
                } else if (key.getKeyType() == KeyType.EOF) {
                    running = false;
                }
            }
            screen.stopScreen();
        }
    }
}
