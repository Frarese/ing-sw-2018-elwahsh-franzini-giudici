package it.polimi.se2018.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board test = new Board();

    @Test
    public void testGetters()
    {
        assertNotNull(test.getObjective(0));
        assertNotNull(test.getTool(0));
        assertNotNull(test.getReserve());
        assertNotNull(test.getRoundTrack());
    }

    @Test
    public void testActions()
    {
        test.rollDiceOnReserve(4);
        assertEquals(9,test.getReserve().size());
        test.reRollReserve();
        assertEquals(9,test.getReserve().size());
        for(int i =0; i<9;i++)
            test.addDieToBag(test.getReserve().popDie(0));

    }

}