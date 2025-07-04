package com.jiss.app.component_test.command;

// src/test/java/com/jiss/app/CommandExecutionComponentTest.java
import com.jiss.app.command.CommandFactory;
import com.jiss.app.command.EditorCommand;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CommandExecutionComponentTest {
    private List<String> buffer;
    private CommandFactory factory;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        buffer = new ArrayList<>(List.of("first line", "second line"));
        factory = new CommandFactory(buffer);
        tempFile = Files.createTempFile("vimjava_test", ".txt");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testWriteCommand() throws Exception {
        EditorCommand writeCmd = factory.create("w " + tempFile.toString());
        assertNotNull(writeCmd);
        writeCmd.executeSync();

        List<String> fileLines = Files.readAllLines(tempFile);
        assertEquals(buffer, fileLines);
    }

    @Test
    void testReadCommand() throws Exception {
        List<String> lines = List.of("alpha", "beta", "gamma");
        Files.write(tempFile, lines);

        buffer.clear();
        EditorCommand readCmd = factory.create("e " + tempFile.toString());
        assertNotNull(readCmd);
        readCmd.executeSync();

        assertEquals(lines, buffer);
    }

    @Test
    void testUnknownCommandReturnsNull() {
        EditorCommand cmd = factory.create("unknown");
        assertNull(cmd);
    }
}