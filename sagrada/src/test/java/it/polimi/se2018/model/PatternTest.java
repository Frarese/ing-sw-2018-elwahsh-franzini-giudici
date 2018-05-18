package it.polimi.se2018.model;

import it.polimi.se2018.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatternTest {
    private Pattern test;

    @Before
    public void testInit()
    {
        Pair[][] temp = new Pair[Pattern.HEIGHT][Pattern.WIDTH];
        temp[0][0] = new Pair<>(ColorModel.RED,0);
        test = new Pattern(temp,"test",3);
    }


    @Test
    public void testGetters()
    {
        assertEquals("test",test.getName());
        assertEquals(3,test.getFavourPoints());
        assertEquals(0, test.getValue(0,0) );
        assertEquals(ColorModel.RED,test.getColor(0,0));
        assertEquals(ColorModel.WHITE,test.getColor(0,1));
        assertNotNull(test.toString());
    }
}