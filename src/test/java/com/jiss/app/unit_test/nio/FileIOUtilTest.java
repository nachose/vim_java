package com.jiss.app.nio;

import org.junit.jupiter.api.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class FileIOUtilTest {

    private Path tempFile;

    @BeforeEach
    void setUp() throws Exception {
        tempFile = Files.createTempFile("fileio", ".txt");
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testWriteAndReadFileAsync() throws Exception {
        List<String> lines = Arrays.asList("line1", "line2", "line3");
        FileIOUtil.writeFileAsync(tempFile, lines).get();

        List<String> readLines = FileIOUtil.readFileAsync(tempFile).get();
        assertEquals(lines, readLines);
    }

    @Test
    void testReadNonExistentFileThrows() {
        Path nonExistent = tempFile.resolveSibling("does_not_exist.txt");
        ExecutionException ex = assertThrows(ExecutionException.class, () ->
                FileIOUtil.readFileAsync(nonExistent).get()
        );
        assertTrue(ex.getCause() instanceof RuntimeException);
        assertTrue(ex.getCause().getCause() instanceof java.nio.file.NoSuchFileException);
    }

    @Test
    void testWriteAndReadEmptyFile() throws Exception {
        List<String> empty = Arrays.asList(""); // Use a single empty string to represent an empty file
        FileIOUtil.writeFileAsync(tempFile, empty).get();

        List<String> readLines = FileIOUtil.readFileAsync(tempFile).get();
        assertEquals(empty, readLines);
    }
}