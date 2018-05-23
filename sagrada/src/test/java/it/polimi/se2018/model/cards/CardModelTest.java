package it.polimi.se2018.model.cards;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardModelTest {
    private final int id = 0;
    private final String text = "This is a test";
    private CardTest test;

    private class CardTest extends CardModel
    {
        private CardTest(int id, String text)
        {
            this.id = id;
            this.text = text;
        }
    }

    @Before
    public void initTest()
    {
        test = new CardTest(id,text);
    }

    @Test
    public void testCardModel()
    {
        assertEquals(text,test.toString());
        assertEquals(id,test.getId());
    }

}
