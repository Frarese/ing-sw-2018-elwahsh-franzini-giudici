package it.polimi.se2018.model.dice;

import it.polimi.se2018.model.ColorModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

import static it.polimi.se2018.model.ColorModel.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ReserveTest {

    private Reserve test = new Reserve();
    private ArrayList<Die> dice;

    /**
     * Init an ArrayList of 5 dice, one for each color
     */
    @Before
    public void initTest()
    {
        dice = new ArrayList<>();
        Stream.of(ColorModel.values()).filter(c-> c != WHITE).forEach(c->dice.add(new Die(c)));
    }

    /**
     * Checking the proper initialization of Reserve
     */
    @Test
    public void testStartingPoint()
    {
        assertEquals(null, test.get(0));
        assertEquals(0,test.size());
        /*invalid position check*/
        assertEquals(null,test.get(-1));
    }

    /**
     * Testing the mutator methods
     */
    @Test
    public void testAddAndPop()
    {
        /*Add a single die*/
        test.add(dice.get(0));
        assertEquals(1,test.size());

        /*pop it and integrity check*/
        assertEquals(dice.get(0),test.popDie(0));
        assertEquals(0,test.size());

        /*Add all and size check*/
        test.addAll(dice);
        assertEquals(dice.size(),test.size());

        /*Asking to pop from a not valid position*/
        assertEquals(null,test.popDie(test.size()));

        /*back to normal*/
        for(int i=0; i<dice.size(); i++)
        {
            test.popDie(0);
        }
    }

    /**
     * Simple toString check: NoNull string and Log print
     */
    @Test
    public void testToString()
    {
        test.addAll(dice);
        assertNotEquals(null,test.toString());

        for(int i=0; i<dice.size(); i++)
        {
            test.popDie(0);
        }
    }

    @Test
    public void testPopAll()
    {
        test.addAll(dice);
        assertEquals(dice,test.popAllDice());
        assertNotEquals(dice,test.popAllDice());
    }

    @Test
    public void testRollReserve()
    {
        test.addAll(dice);
        test.rollReserve();
        assertEquals(dice.size(),test.size());
        test.popAllDice();

    }
}
