package com.jiss.app.input;

import com.jiss.app.EditorMode;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;

import java.io.IOException;
import java.util.List;


public class InsertModeHandler implements KeyInputHandler<InsertContext> {
    @Override
    public LoopStatus handleTextInput(InsertContext context) throws IOException {
        EditorMode mode = EditorMode.INSERT;
        KeyStroke key = context.getKeyStroke();
        TerminalPosition pos = context.getTerminalPosition();
        List<String> buffer = context.getBuffer();
        if(buffer.isEmpty()) {
            buffer.add("");
        }
        StringBuilder currentLine = new StringBuilder(buffer.get(pos.getRow()));
        if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if( key.getKeyType() == KeyType.Enter ) {
            // Handle Enter key in insert mode, e.g., add a newline
            int first_start = 0;
            int first_end   = pos.getColumn();
            int second_start = pos.getColumn();
            int second_end = currentLine.length();
            String first = currentLine.substring(first_start, first_end);
            String second = currentLine.substring(second_start, second_end);
            buffer.set(pos.getRow(), first);
            buffer.add(pos.getRow() + 1, second);
            pos = new TerminalPosition(0, pos.getRow() + 1); // Move cursor to next line
        } else if (key.getKeyType() == KeyType.Backspace && pos.getColumn() > 0) {
            currentLine.deleteCharAt(pos.getColumn() - 1);
            pos = new TerminalPosition(pos.getColumn() -1, pos.getRow());
            buffer.set(pos.getRow(), currentLine.toString());                                                 //
        } else if (key.getKeyType() == KeyType.Character ) {
            // currentLine.append(key.getCharacter());
            currentLine.insert(pos.getColumn(), key.getCharacter());
//            buffer.insert(pos.getColumn(), key.getCharacter());
            pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
            buffer.set(pos.getRow(), currentLine.toString());                                                 //
        } else if (key.getKeyType() == KeyType.ArrowLeft && pos.getColumn() > 0) {
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            //Only move right if not at the end of the buffer
            if(pos.getColumn() < buffer.get(pos.getRow()).length()) {
                pos = new TerminalPosition(pos.getColumn() + 1, pos.getRow());
            }
        } else if (key.getKeyType() == KeyType.EOF) {
            mode = EditorMode.STOPPED;
        }
        return new LoopStatus( mode , pos);
    }
}
