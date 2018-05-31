package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

public class DiePlacementMoveTest
{
    private DiePlacementMove test = new DiePlacementMove(0,0,0,"","",true,true,true);

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