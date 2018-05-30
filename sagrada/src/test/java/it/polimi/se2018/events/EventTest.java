package it.polimi.se2018.events;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EventTest {

    private class TestEvent extends Event
    {
        private TestEvent()
        {
            this.description = "test";
        }
    }

    @Test
    public void testDescription()
    {
        TestEvent test = new TestEvent();
        assertEquals("test", test.toString() );
    }

}