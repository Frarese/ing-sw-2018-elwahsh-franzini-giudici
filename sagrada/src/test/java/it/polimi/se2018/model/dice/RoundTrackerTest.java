package it.polimi.se2018.model.dice;

import it.polimi.se2018.model.ColorModel;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.se2018.model.ColorModel.WHITE;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class RoundTrackerTest {
    private ArrayList<Die> dice;
    private RoundTracker test = RoundTracker.getInstance();

    /**
     * Initialization of an ArrayList of 5 dice, one for each color
     */
    @Before
    public void initTest()
    {
        dice = new ArrayList<>();
        Stream.of(ColorModel.values()).filter(c-> c != WHITE).forEach(c->dice.add(new Die(c)));
    }

    /**
     * Testing general properties
     */
    @Test
    public void testPublicInfo()
    {
        assertEquals(10,RoundTracker.ROUNDS); /*True for Sagrada*/
        assertEquals(null,test.getDie(-1,-1)); /*Must be always true*/
    }

    @Test
    public void testAddAndPop()
    {
        /*Filling round 1*/
        test.addAll(dice);
        assertEquals(1,test.lastFilledRound());
        assertEquals(dice.get(0),test.getDie(0,0));
        /*Filling all the rounds*/
        for(int i =0; i< RoundTracker.ROUNDS +1;i++) /*Can't break maximum size*/
        {
            test.addAll(dice);
        }

        /*Checking if lastFilledRound does not surpass ROUNDS*/
        assertEquals(RoundTracker.ROUNDS, test.lastFilledRound());

        /*Popping an extraplanar die*/
        assertEquals(null,test.popDie(-1,-1));

    }

    /**
     * Testing toString: NoNull string and log print
     */
    @Test
    public void testToString()
    {
        assertNotEquals(null,test.toString());
    }
}
