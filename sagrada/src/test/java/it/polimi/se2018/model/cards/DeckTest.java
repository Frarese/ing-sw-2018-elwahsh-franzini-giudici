package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.ColorModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test for the Deck class
 * @author Alì El wahsh
 */
public class DeckTest {

    private Deck<PrivateObjectiveCard> test;

    /**
     * Test initialization
     */
    @Before
    public void initTest()
    {
        ArrayList<PrivateObjectiveCard> temp = new ArrayList<>();
        temp.add(new PrivateObjectiveCard(ColorModel.RED));
        temp.add(new PrivateObjectiveCard(ColorModel.BLUE));
        temp.add(new PrivateObjectiveCard(ColorModel.YELLOW));
        temp.add(new PrivateObjectiveCard(ColorModel.GREEN));
        temp.add(new PrivateObjectiveCard(ColorModel.VIOLET));

        test = new Deck<>(temp);
        test.shuffle();
    }

    /**
     * Tests draw
     */
    @Test
    public void testDraw()
    {
        assertEquals(2,test.draw(2).size());
        assertEquals(3,test.draw(5).size());
    }
}