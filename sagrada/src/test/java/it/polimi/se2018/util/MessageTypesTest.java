package it.polimi.se2018.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tester class for the MessageType
 * @author Francesco Franzini
 */
public class MessageTypesTest {

    /**
     * Tests that the message types have the right names
     */
    @Test
    public void testMessageTypes() {
        assertEquals("BROADCAST",MessageTypes.BROADCAST.name());
        assertEquals("PM",MessageTypes.PM.name());
        assertEquals("MATCH",MessageTypes.MATCH.name());
    }
}