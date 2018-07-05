package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the class PatternChoice
 * @author Alì El wahsh
 */
public class PatternChoiceTest {

    private final PatternChoice test = new PatternChoice("test", "Virtus");

    /**
     * Test for the class getters
     */
    @Test
    public void testGetter()
    {
        assertEquals("Virtus",test.getPatterName());
        assertNotEquals("Fury",test.getPatterName());
    }

}