package com.jiss.app.command;

import com.jiss.app.nio.FileIOUtil;
import java.nio.file.Paths;
import java.util.List;

public class ReadCommand implements EditorCommand {
    private final List<String> buffer;
    private final String filename;

    public ReadCommand(List<String> buffer, String filename) {
        this.buffer = buffer;
        this.filename = filename;
    }

    @Override
    public void execute() {
        FileIOUtil.readFileAsync(Paths.get(filename)).thenAccept(lines -> {
            buffer.addAll(lines);
        }).exceptionally(ex -> {
            // Handle error (e.g., notify user)
            return null;
        });
    }
    @Override
    public void executeSync() {
        try {
            FileIOUtil.readFileAsync(Paths.get(filename)).thenAccept(lines -> {
                buffer.addAll(lines);
            }).exceptionally(ex -> {
                // Handle error (e.g., notify user)
                return null;
            }).get();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}