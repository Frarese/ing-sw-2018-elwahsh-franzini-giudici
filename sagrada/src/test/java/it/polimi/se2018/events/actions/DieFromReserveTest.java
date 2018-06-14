package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

public class DieFromReserveTest {

    private final DieFromReserve test = new DieFromReserve("test",1);

    @Test
    public void testGetter()
    {
        assertEquals(1,test.getIndex());
        assertNotEquals(2,test.getIndex());
    }

}