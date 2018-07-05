package it.polimi.se2018.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test for the model class Board
 * @author Alì El wahsh
 */
public class BoardTest {

    private final Board test = new Board();

    /**
     * Test for the class getters
     */
    @Test
    public void testGetters()
    {
        assertNotNull(test.getObjective(0));
        assertNotNull(test.getTool(0));
        assertNotNull(test.getReserve());
        assertNotNull(test.getRoundTrack());
        assertNotNull(test.getObjectives());
        assertNotNull(test.getTools());
        assertNotNull(test.getBag());
    }

    /**
     * Tests all the legal action inside the board
     */
    @Test
    public void testActions()
    {
        test.rollDiceOnReserve(4);
        assertEquals(9,test.getReserve().size());
        test.reRollReserve();
        assertEquals(9,test.getReserve().size());
        for(int i =0; i<9;i++)
            test.addDieToBag(test.getReserve().popDie(0));
        test.reRollReserve();
        test.putReserveOnRoundTrack();
        assertEquals(1,test.getRoundTrack().lastFilledRound());
    }

    /**
     * Tests final public score calculation
     */
    @Test
    public void testTotalScore()
    {
        assertEquals(0, test.totalScore(new Player("Carlo", 0)));
    }

}