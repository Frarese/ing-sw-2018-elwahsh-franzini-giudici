package it.polimi.se2018.model.cards;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PatternCardTest {

    private PatternCard test = new PatternCard("resources" + File.separator +"patterncard01.xml");

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

    @Test
    public void testException()
    {
        PatternCard notValid = new PatternCard("invalid position");
        assertEquals("Invalid",notValid.getFrontSide().getName());
    }
}