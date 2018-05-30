package it.polimi.se2018.model.cards;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ActiveToolsTest {
    private ActiveTools test = new ActiveTools();

    @Test
    public void testGetTools()
    {
        assertNotNull(test.getTool(0));
        assertNull(test.getTool(4));
    }

}