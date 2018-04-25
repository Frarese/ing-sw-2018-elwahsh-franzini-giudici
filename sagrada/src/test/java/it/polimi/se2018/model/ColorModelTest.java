package it.polimi.se2018.model;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static it.polimi.se2018.model.ColorModel.*;

/**
 * Tests for ColorModel class
 */
public class ColorModelTest {

    private ColorModel c1,c2,c3,c4,c5,c6;

    /**
     * Initialization of all six colors, since we should test if they have
     * the right name for the visual conversion
     * @see ColorModel toJavaFXColor() method
     */
    @Before
    public void testInit()
    {
        c1 = RED;
        c2 = BLUE;
        c3 = GREEN;
        c4 = YELLOW;
        c5 = VIOLET;
        c6 = WHITE;
    }


    /**
     * String value check, mostly for typos hunting
     */
    @Test
    public void testToString()
    {
       assertEquals("red",c1.toString());
        assertEquals("blue",c2.toString());
        assertEquals("green",c3.toString());
        assertEquals("yellow",c4.toString());
        assertEquals("violet",c5.toString());
        assertEquals("white",c6.toString());
    }

    /**
     * Each color should have its JavaFX counterpart so we should check
     * which name gives the right result
     */

    @Test
    public void testToJavaFXColor()
    {
        assertEquals(Color.RED,c1.toJavaFXColor());
        assertEquals(Color.BLUE,c2.toJavaFXColor());
        assertEquals(Color.GREEN,c3.toJavaFXColor());
        assertEquals(Color.YELLOW,c4.toJavaFXColor());
        assertEquals(Color.VIOLET,c5.toJavaFXColor());
        assertEquals(Color.WHITE,c6.toJavaFXColor());
    }

}
