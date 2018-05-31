package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

public class UseToolCardMoveTest {

    private final UseToolCardMove test = new UseToolCardMove("",13,"");

    @Test
    public void testGetters()
    {
        assertEquals("",test.getPlayerName());
        assertEquals("",test.toString());
        assertEquals(13,test.getCardID());
    }

}