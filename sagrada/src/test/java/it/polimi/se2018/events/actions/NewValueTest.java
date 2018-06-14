package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewValueTest {

    private final NewValue test = new NewValue("test",2);

    @Test
    public void testGetter()
    {
        assertEquals(2,test.getNewValue());
        assertNotEquals(1,test.getNewValue());
    }

}