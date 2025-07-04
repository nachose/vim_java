package com.jiss.app.input;

import com.jiss.app.EditorMode;
import com.jiss.app.command.CommandFactory;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TerminalPosition;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class CommandModeHandler implements KeyInputHandler<CommandContext> {

    private final CommandFactory cfactory_;

    @Autowired
    CommandModeHandler(CommandFactory factory) {
        cfactory_ = factory;
    }

    @Override
    public LoopStatus handleTextInput(CommandContext context) throws IOException {

        EditorMode mode = EditorMode.COMMAND;
        KeyStroke key = context.getKeyStroke();
        TerminalPosition pos = context.getTerminalPosition();
        StringBuilder commandBuffer = context.getCommandBuffer();
        if (key.getKeyType() == KeyType.Escape) {
            mode = EditorMode.NORMAL;
        } else if (key.getKeyType() == KeyType.Character ) {
            commandBuffer.append(key.getCharacter());
        } else if (key.getKeyType() == KeyType.Enter ) {
            processCommand(commandBuffer.toString());
            commandBuffer.setLength(0); // Clear command buffer
            //After executing the command, we return to normal mode.
            mode = EditorMode.NORMAL;
            //Set at 0,0 position for now.
            pos = new TerminalPosition(0, 0);
        } else if (key.getKeyType() == KeyType.Backspace && pos.getColumn() > 0) {
            commandBuffer.deleteCharAt(pos.getColumn() - 1);
            pos = new TerminalPosition(pos.getColumn() - 1, pos.getRow());
        } else if (key.getKeyType() == KeyType.EOF) {
            mode = EditorMode.STOPPED;
        }
        return new LoopStatus( mode, pos);
    }

    private void processCommand(String command) {
        // Here you would implement the logic to process the command.
        // For now, we just print it to the console.
        System.out.println("Processing command: " + command);

        if (command.equals("q")) {
            // Handle quit command
            System.out.println("Quitting the editor.");
            // You might want to set a flag or throw an exception to stop the editor loop.
        } else if (command.startsWith("w ")) {
            // Handle write command
            System.out.println("Writing changes to file.");
            cfactory_.create(command).execute();
            // Implement file writing logic here.
        } else if (command.startsWith("e ")) {
            // Handle write command
            System.out.println("Writing changes to file.");
            cfactory_.create(command).execute();
            // Implement file reading logic here.
        } else {
            System.out.println("Unknown command: " + command);
        }


    }

}
