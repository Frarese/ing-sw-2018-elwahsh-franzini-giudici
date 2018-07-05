package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for DieFromRoundTrack class
 * @author Alì El wahsh
 */
public class DieFromRoundTrackTest {

    private final DieFromRoundTrack test = new DieFromRoundTrack("test",1,2);

    /**
     * Test for the class getters
     */
    @Test
    public void testGetter()
    {

        assertEquals(1,test.getRound());
        assertEquals(2,test.getDiePosition());
        assertNotEquals(2,test.getRound());
    }
}