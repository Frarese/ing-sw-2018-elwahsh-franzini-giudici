package it.polimi.se2018.model;

import org.junit.Before;
import org.junit.Test;

import static it.polimi.se2018.model.ColorModel.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for ColorModel class
 *
 * @author Alì El Wahsh &amp; Mathyas Giudici
 */
public class ColorModelTest {

    private ColorModel c1, c2, c3, c4, c5, c6;

    /**
     * Initialization of all six colors, since we should test if they have
     * the right name for the visual conversion
     *
     * @see ColorModel toJavaFXColor() method
     */
    @Before
    public void testInit() {
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
    public void testToString() {
        assertEquals("RED", c1.toString());
        assertEquals("BLUE", c2.toString());
        assertEquals("GREEN", c3.toString());
        assertEquals("YELLOW", c4.toString());
        assertEquals("VIOLET", c5.toString());
        assertEquals("WHITE", c6.toString());
    }

    /**
     * Each color should have its JavaFX counterpart so we should check
     * which name gives the right result
     */

    @Test
    public void testToJavaFXColor() {
        assertEquals("#D11E22", c1.toJavaFXColor());
        assertEquals("#48B0B3", c2.toJavaFXColor());
        assertEquals("#32AA63", c3.toJavaFXColor());
        assertEquals("#E4D806", c4.toJavaFXColor());
        assertEquals("#A02894", c5.toJavaFXColor());
        assertEquals("#F0F0F0", c6.toJavaFXColor());
    }

}
