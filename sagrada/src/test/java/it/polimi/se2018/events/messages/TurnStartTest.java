package it.polimi.se2018.events.messages;
import org.junit.Test;

import static org.junit.Assert.*;

public class TurnStartTest {

    private final TurnStart test = new TurnStart("Marco","Carlo");

    @Test
    public void testGetter()
    {
        assertEquals("Marco",test.getOldPlayer());
        assertEquals("Carlo", test.getName());
    }

}