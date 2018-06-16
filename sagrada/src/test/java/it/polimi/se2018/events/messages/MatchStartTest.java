package it.polimi.se2018.events.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchStartTest {

    @Test
    public void testDesc(){
        assertEquals("MatchStart",new MatchStart(false).toString());
    }
}