package com.jiss.app.nio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.stream.Stream;

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

    public static void readFileStreamingAsync(
            Path path,
            Consumer<String> onLine,
            Consumer<Throwable> onError,
            Runnable onComplete) {
        CompletableFuture.runAsync(() -> {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    onLine.accept(line);
                }
                onComplete.run();
            } catch (Throwable t) {
                onError.accept(t);
            }
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
                System.out.println("Error writing to file: " + e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    public static CompletableFuture<Void> writeFileStreamingAsync(
            Path path,
            Stream<String> lines,
            Consumer<Throwable> onError,
            Runnable onComplete) {
        return CompletableFuture.runAsync(() -> {
            try (BufferedWriter writer = Files.newBufferedWriter(
                    path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                lines.forEach(line -> {
                    try {
                        writer.write(line);
                        writer.newLine();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                onComplete.run();
            } catch (Throwable t) {
                onError.accept(t);
            }
        });
    }
}