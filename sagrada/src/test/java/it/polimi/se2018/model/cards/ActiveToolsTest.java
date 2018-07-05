package it.polimi.se2018.model.cards;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test for the ActiveToolCard class
 * @author Alì El wahsh
 */
public class ActiveToolsTest {
    private final ActiveTools test = new ActiveTools();

    /**
     * Test for tool cards retrieval
     */
    @Test
    public void testGetTools()
    {
        assertNotNull(test.getTool(0));
        assertNull(test.getTool(4));
    }

}