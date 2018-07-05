package it.polimi.se2018.events;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for the Event class
 * @author Alì El wahsh
 */
public class EventTest {

    /**
     * Event extension since it's abstract
     */
    private class TestEvent extends Event
    {
        private TestEvent()
        {
            this.description = "test";
        }
    }

    /**
     * Test for toString() method
     */
    @Test
    public void testDescription()
    {
        TestEvent test = new TestEvent();
        assertEquals("test", test.toString() );
    }

}