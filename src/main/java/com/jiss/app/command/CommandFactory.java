package com.jiss.app.command;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {
    private final List<String> buffer;

    public CommandFactory(List<String> buffer) {
        this.buffer = buffer;
    }

    public EditorCommand create(String input) {
        if (input.startsWith("w ")) {
            String filename = input.substring(2).trim();
            return new WriteCommand(buffer, filename);
        }
        else if(input.startsWith("e ")) {
            String filename = input.substring(2).trim();
            return new ReadCommand(buffer, filename);
        }
        // Add more commands as needed
        return null;
    }
}