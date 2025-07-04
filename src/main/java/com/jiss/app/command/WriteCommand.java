package com.jiss.app.command;

import com.jiss.app.nio.FileIOUtil;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class WriteCommand implements EditorCommand {
    private final List<String> buffer;
    private final String filename;

    public WriteCommand(List<String> buffer, String filename) {
        this.buffer = buffer;
        this.filename = filename;
    }

    @Override
    public void execute() {
        // Use buffer to get content and write to file
        FileIOUtil.writeFileAsync(Paths.get(filename), buffer);
    }

    @Override
    public void executeSync() {
        try {
            FileIOUtil.writeFileAsync(Paths.get(filename), buffer).get();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
        }
    }
}