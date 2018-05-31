package it.polimi.se2018.events.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChangeLayerRequestTest {

    private final ChangeLayerRequest test = new ChangeLayerRequest(false,220,300);

    @Test
    public void testGetters()
    {
        assertFalse(test.isToRMI());
        assertEquals(220,test.getObjectPort());
        assertEquals(300,test.getRequestPort());
    }

}