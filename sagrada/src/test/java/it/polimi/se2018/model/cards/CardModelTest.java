package it.polimi.se2018.model.cards;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for the CardModel class
 * @author Alì El wahsh
 */
public class CardModelTest {
    private final int id = 0;
    private final String text = "This is a test";
    private CardTest test;

    /**
     * CardModel's extension, since it's abstract
     */
    private class CardTest extends CardModel
    {
        private CardTest(int id, String text)
        {
            this.id = id;
            this.text = text;
        }
    }

    /**
     * Test initialization
     */
    @Before
    public void initTest()
    {
        test = new CardTest(id,text);
    }

    /**
     * Tests CardModel methods
     */
    @Test
    public void testCardModel()
    {
        assertEquals(text,test.toString());
        assertEquals(id,test.getId());
    }

}
