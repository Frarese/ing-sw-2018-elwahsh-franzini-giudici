package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.ColorModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class DeckTest {

    private Deck<PrivateObjectiveCard> test;

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


    @Test
    public void testDraw()
    {
        assertEquals(2,test.draw(2).size());
        assertEquals(3,test.draw(5).size());
    }
}