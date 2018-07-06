package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static it.polimi.se2018.model.ColorModel.RED;
import static org.junit.Assert.assertEquals;

/**
 * Test for LightShades class
 */
public class LightShadesTest {
    private final LightShades test = new LightShades();
    private Player player;

    /**
     * Test for score calculation
     */
    @Test
    public void testScore()
    {
        Die d1,d2,d3;
        d1 = new Die(RED);
        d1.setFace(1);
        d2 = new Die(RED);
        d2.setFace(2);
        d3 = new Die(RED);
        d3.setFace(5);
        player = new Player("Magnus The Red", 15);

        player.getGrid().setDie(0,0,d1);
        player.getGrid().setDie(0,1,d1);
        player.getGrid().setDie(0,2,d2);
        player.getGrid().setDie(2,0,d2);
        player.getGrid().setDie(1,1,d3);

        assertEquals(2*test.getMultiplier(),test.score(player));

    }

    /**
     * Test for scoring with empty grid
     */
    @Test
    public void testEmptyGrid()
    {
        player = new Player("Magnus The Red", 15);

        assertEquals(0,test.score(player));

    }
}
