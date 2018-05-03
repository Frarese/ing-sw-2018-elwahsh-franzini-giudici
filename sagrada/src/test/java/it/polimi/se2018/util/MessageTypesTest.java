package it.polimi.se2018.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTypesTest {
    @Test
    public void testMessageTypes() {
        assertEquals("BROADCAST",MessageTypes.BROADCAST.name());
        assertEquals("PM",MessageTypes.PM.name());
        assertEquals("MATCH",MessageTypes.MATCH.name());
    }
}