package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

public class PatternChoiceTest {

    private final PatternChoice test = new PatternChoice("test", "Virtus");


    @Test
    public void testGetter()
    {
        assertEquals("Virtus",test.getPatterName());
        assertNotEquals("Fury",test.getPatterName());
    }

}