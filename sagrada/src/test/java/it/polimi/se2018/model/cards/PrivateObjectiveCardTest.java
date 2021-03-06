package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for teh PrivateObjectiveCard class
 * @author Alì El wahsh
 */
public class PrivateObjectiveCardTest {

    private final PrivateObjectiveCard test = new PrivateObjectiveCard(ColorModel.RED);
    private final PrivateObjectiveCard test2 = new PrivateObjectiveCard(ColorModel.BLUE);
    private Player player;

    /**
     * TestInitialization
     */
    @Before
    public void initTest()
    {
        player = new Player("Abaddon", 200);
        Die d1,d2,d3;
        d1 = new Die(ColorModel.RED);
        d1.setFace(2);
        d2 = new Die(ColorModel.RED);
        d2.setFace(3);
        d3 = new Die(ColorModel.YELLOW);
        player.getGrid().setDie(0,0, d1);
        player.getGrid().setDie(1,0, d2);
        player.getGrid().setDie(2,0, d3);
        player.getGrid().setDie(0,1, d1);
        player.getGrid().setDie(0,2, d2);
        player.getGrid().setDie(0,3, d3);
    }

    /**
     * Test score calculation
     */
    @Test
    public void testScore()
    {

        assertEquals(10,test.score(player));
        assertEquals(0,test2.score(player));
    }

}
