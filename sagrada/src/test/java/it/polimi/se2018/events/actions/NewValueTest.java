package it.polimi.se2018.events.actions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the class NewValue
 * @author Alì El wahsh
 */
public class NewValueTest {

    private final NewValue test = new NewValue("test",2);

    /**
     * Test for the class getters
     */
    @Test
    public void testGetter()
    {
        assertEquals(2,test.getNewValue());
        assertNotEquals(1,test.getNewValue());
    }

}