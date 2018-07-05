package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the DieFromReserve class
 * @author Alì El wahsh
 */
public class DieFromReserveTest {

    private final DieFromReserve test = new DieFromReserve("test",1);

    /**
     * Test for the class getters
     */
    @Test
    public void testGetter()
    {
        assertEquals(1,test.getIndex());
        assertNotEquals(2,test.getIndex());
    }

}