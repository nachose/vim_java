package com.jiss.app.component_test.command;

// src/test/java/com/jiss/app/component_test/command/CommandParsingComponentTest.java

import com.jiss.app.command.CommandFactory;
import com.jiss.app.command.EditorCommand;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CommandParsingComponentTest {
    private List<String> buffer;
    private CommandFactory factory;

    @BeforeEach
    void setUp() {
        buffer = new ArrayList<>();
        factory = new CommandFactory(buffer);
    }

    @Test
    void testCreateWriteCommand() {
        EditorCommand cmd = factory.create("w test.txt");
        assertNotNull(cmd);
        assertEquals("WriteCommand", cmd.getClass().getSimpleName());
    }

    @Test
    void testCreateReadCommand() {
        EditorCommand cmd = factory.create("e test.txt");
        assertNotNull(cmd);
        assertEquals("ReadCommand", cmd.getClass().getSimpleName());
    }

    @Test
    void testUnknownCommandReturnsNull() {
        EditorCommand cmd = factory.create("foobar");
        assertNull(cmd);
    }

    @Test
    void testEmptyInputReturnsNull() {
        EditorCommand cmd = factory.create("");
        assertNull(cmd);
    }

    @Test
    void testMalformedCommandReturnsNull() {
        EditorCommand cmd = factory.create("w"); // Missing filename
        assertNull(cmd);
    }
}
