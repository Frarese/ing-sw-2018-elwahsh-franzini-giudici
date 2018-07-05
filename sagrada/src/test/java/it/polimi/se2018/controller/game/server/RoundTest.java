package it.polimi.se2018.controller.game.server;

import it.polimi.se2018.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test for the Round class
 * @author Alì El wahsh
 */
public class RoundTest {
    private Round test;

    /**
     * Test initialization
     */
    @Before
    public void initTest()
    {
        ArrayList<Player> temp = new ArrayList<>();
        temp.add(new Player("Tizio", 0));
        temp.add(new Player("Caio",1));
        temp.add(new Player("Sempronio",2));
        test = new Round(temp);
    }

    /**
     * Test for the class' getters
     */
    @Test
    public void testGetters()
    {
        assertEquals("Tizio",test.getCurrentPlayer().getName());
        assertTrue(test.getFirstTurn());
        test.nextTurn();
        test.nextTurn();
        test.nextTurn();
        test.nextTurn();
        test.nextTurn();
        test.nextTurn();
        assertNull(test.getCurrentPlayer());
        assertFalse(test.getFirstTurn());
        test.prepareNextRound();
        assertEquals(2,test.getRoundNumber());
        assertEquals("Caio", test.getCurrentPlayer().getName());
    }

}