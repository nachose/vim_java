package com.jiss.app.nio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class FileIOUtil {

    public static CompletableFuture<List<String>> readFileAsync(Path path) {
        return CompletableFuture.supplyAsync(() -> {
            List<String> lines = new ArrayList<>();
            try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
                ByteBuffer buffer = ByteBuffer.allocate((int) Files.size(path));
                Future<Integer> result = channel.read(buffer, 0);
                result.get();
                buffer.flip();
                String content = StandardCharsets.UTF_8.decode(buffer).toString();
                for (String line : content.split("\r?\n")) {
                    lines.add(line);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return lines;
        });
    }

    public static CompletableFuture<Void> writeFileAsync(Path path, List<String> lines) {
        return CompletableFuture.runAsync(() -> {
            try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(
                    path, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                String content = String.join(System.lineSeparator(), lines);
                ByteBuffer buffer = StandardCharsets.UTF_8.encode(content);
                Future<Integer> result = channel.write(buffer, 0);
                result.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}