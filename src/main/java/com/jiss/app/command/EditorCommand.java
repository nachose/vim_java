package com.jiss.app.command;

public interface EditorCommand {
    void execute();
    //Execute synchronously, for tests.
    void executeSync();
}
