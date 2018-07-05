package it.polimi.se2018.events.messages;

import it.polimi.se2018.events.actions.DiePlacementMove;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the InvalidMove class
 * @author Alì El wahsh
 */
public class InvalidMoveTest {


    /**
     * Test for the class getters
     */
    @Test
    public void testGetters()
    {
        DiePlacementMove move = new DiePlacementMove(0,0,0,"",true,true,true);
        InvalidMove test = new InvalidMove(move, "test",true);
        assertEquals(move,test.getMove());
        assertEquals("test",test.toString());
        assertTrue(test.isPlacement());
    }
}