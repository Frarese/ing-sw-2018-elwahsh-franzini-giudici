package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

public class DieFromGridTest {

    private final DieFromGrid test = new DieFromGrid("test",0,0);

    @Test
    public void testGetter()
    {
        assertEquals(0,test.getH());
        assertEquals(0,test.getW());
    }

}