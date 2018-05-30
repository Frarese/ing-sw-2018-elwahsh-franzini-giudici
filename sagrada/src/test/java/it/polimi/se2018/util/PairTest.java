package it.polimi.se2018.util;

import it.polimi.se2018.model.ColorModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {
    private final Pair<ColorModel, Integer> test = new Pair <>(ColorModel.RED,0);

    @Test
    public void testGetters()
    {
        assertEquals(ColorModel.RED,test.getFirst());
        assertEquals(new Integer(0),test.getSecond());
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEquals()
    {
        Pair<ColorModel, Integer> test2 = new Pair <>(ColorModel.RED,0);
        Pair<ColorModel, Integer> test3 = new Pair <>(ColorModel.RED,4);
        Integer test4 = 2;
        assertEquals(test,test);
        assertEquals(test2,test);
        assertNotEquals(test3,test);
        assertFalse(test.equals(test4));
    }

    @Test
    public void testHash()
    {
        assertEquals(test.getFirst().hashCode()^test.getSecond().hashCode(),test.hashCode());
    }

}