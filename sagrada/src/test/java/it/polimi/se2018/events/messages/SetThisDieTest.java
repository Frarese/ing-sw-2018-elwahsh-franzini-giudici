package it.polimi.se2018.events.messages;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the class SetThisDie
 * @author Alì El wahsh
 */
public class SetThisDieTest {

    private final SetThisDie test = new SetThisDie(new IntColorPair(2, ColorModel.RED));

    /**
     * Test for the class getters
     */
    @Test
    public void testGetter()
    {
        assertEquals(new Integer(2),test.getDie().getFirst());
        assertEquals(ColorModel.RED,test.getDie().getSecond());
    }

}