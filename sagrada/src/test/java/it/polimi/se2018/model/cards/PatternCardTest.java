package it.polimi.se2018.model.cards;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test for the PatternCard class
 * @author Alì El wahsh
 */
public class PatternCardTest {

    private final PatternCard test = new PatternCard("resources" + File.separator +"patterncard01.xml");

    /**
     * Test for the Xml parser
     */
    @Test
    public void testParser()
    {
        assertNotNull(test.getFrontSide());
        assertNotNull(test.getBackSide());
        assertEquals("Virtus",test.getFrontSide().getName());
        assertEquals(test.getFrontSide(),test.chooseSide(true));
        assertEquals(test.getBackSide(),test.chooseSide(false));

        assertNotNull(test.toString());
    }

    /**
     * Test for invalid file position
     */
    @Test
    public void testException()
    {
        PatternCard notValid = new PatternCard("invalid position");
        assertEquals("Invalid",notValid.getFrontSide().getName());
    }
}
