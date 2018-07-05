package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the DieFromGrid class
 * @author Alì El wahsh
 */
public class DieFromGridTest {

    private final DieFromGrid test = new DieFromGrid("test",0,0);

    /**
     * Test for the class getters
     */
    @Test
    public void testGetter()
    {
        assertEquals(0,test.getH());
        assertEquals(0,test.getW());
    }

}