package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the DiePlacementMove class
 * @author Alì El wahsh
 */
public class DiePlacementMoveTest
{
    private final DiePlacementMove test = new DiePlacementMove(0,0,0,"",true,true,true);

    /**
     * Test for the class getters
     */
    @Test
    public void testGetters()
    {
        assertEquals(0,test.getHeight());
        assertEquals(0,test.getWidth());
        assertEquals(0,test.getDiePosition());
        assertEquals("",test.getPlayerName());
        assertNotNull(test.toString());
        assertTrue(test.isAdjacentRestriction());
        assertTrue(test.isColorRestriction());
        assertTrue(test.isValueRestriction());
    }

}