package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the class UseToolCardMove
 * @author Alì El wahsh
 */
public class UseToolCardMoveTest {

    private final UseToolCardMove test = new UseToolCardMove("",13);

    /**
     * Test for the class getters
     */
    @Test
    public void testGetters()
    {
        assertEquals("",test.getPlayerName());
        assertEquals("UseCard",test.toString());
        assertEquals(13,test.getCardID());
    }

}