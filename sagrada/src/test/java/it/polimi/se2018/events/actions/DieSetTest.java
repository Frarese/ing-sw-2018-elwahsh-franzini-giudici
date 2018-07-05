package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the DieSet class
 * @author Alì El wahsh
 */
public class DieSetTest {

    private final DieSet test = new DieSet("test",1,1);

    /**
     * Test for the class getters
     */
    @Test
    public void testGetter()
    {
        assertEquals(1,test.getH());
        assertEquals(1,test.getW());
        assertNotEquals(2,test.getH());

    }

}