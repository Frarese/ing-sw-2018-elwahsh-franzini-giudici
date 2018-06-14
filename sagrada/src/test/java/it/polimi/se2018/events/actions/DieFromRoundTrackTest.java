package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

public class DieFromRoundTrackTest {

    private final DieFromRoundTrack test = new DieFromRoundTrack("test",1,2);


    @Test
    public void testGetter()
    {

        assertEquals(1,test.getRound());
        assertEquals(2,test.getDiePosition());
        assertNotEquals(2,test.getRound());
    }
}