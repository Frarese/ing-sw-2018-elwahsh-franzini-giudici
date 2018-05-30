package it.polimi.se2018.model.dice;


import org.junit.Before;
import org.junit.Test;

import static it.polimi.se2018.model.ColorModel.BLUE;
import static it.polimi.se2018.model.ColorModel.YELLOW;
import static org.junit.Assert.*;

public class DieTest {

    private Die d1, d2;

    /**
     * Initialization of few dice
     */
    @Before
    public void testInit()
    {
        d1 = new Die(BLUE);
        d2 = new Die(YELLOW);
    }


    /**
     * Checking if the dice are properly initialized
     */
    @Test
    public void testDie()
    {
        assertNotNull(d1);
        assertNotNull(d2);
        assertEquals(BLUE, d1.getColor());
        assertEquals(YELLOW, d2.getColor());
    }

    /**
     * Checking the value changing methods to see if the die can't assume invalid values
     * while valid values pass through the checks
     */
    @Test
    public void testValueAssignment()
    {
        assertNotEquals(0,d1.getValue());
        d1.roll();
        assertNotEquals(0,d1.getValue());

        assertFalse(d1.setFace(7));
        assertNotEquals(7, d1.getValue());

        assertFalse(d1.setFace(-1));
        assertNotEquals(-1, d1.getValue());

        assertTrue(d2.setFace(6));
        assertEquals(6,d2.getValue());


    }

    /**
     * String value check to see possible null values
     */

    @Test
    public void testToString()
    {
        assertNotEquals(null,d1.toString());
        assertEquals(d1.getColor().toString()+ " " + d1.getValue(), d1.toString());
    }

}
