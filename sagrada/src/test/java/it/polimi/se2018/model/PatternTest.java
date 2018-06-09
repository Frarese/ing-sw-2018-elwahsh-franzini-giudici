package it.polimi.se2018.model;

import it.polimi.se2018.model.IntColorPair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PatternTest {
    private Pattern test;

    @Before
    public void testInit()
    {
        IntColorPair[][] temp = new IntColorPair[Pattern.HEIGHT][Pattern.WIDTH];
        temp[0][0] = new IntColorPair(0,ColorModel.RED);
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