package it.polimi.se2018.events.messages;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the MatchStart class
 * @author Alì El wahsh
 */
public class MatchStartTest {

    /**
     * Test for toString() method
     */
    @Test
    public void testDesc(){
        assertEquals("MatchStart",new MatchStart(false).toString());
    }
}