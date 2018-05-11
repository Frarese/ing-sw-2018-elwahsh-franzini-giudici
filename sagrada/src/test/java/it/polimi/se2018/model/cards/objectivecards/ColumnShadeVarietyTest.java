package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColumnShadeVarietyTest {

    private ColumnShadeVariety test = new ColumnShadeVariety();
    private Player player;


    @Test
    public void testScore()
    {
        Die d1,d2,d3,d4,d5;
        player = new Player("Leman Russ", 6);
        d1 = new Die(ColorModel.RED);
        d2 = new Die(ColorModel.RED);
        d3 = new Die(ColorModel.RED);
        d4 = new Die(ColorModel.RED);
        d5 = new Die(ColorModel.RED);
        d1.setFace(1);
        player.getGrid().setDie(0,0,d1);
        d2.setFace(2);
        player.getGrid().setDie(1,0,d2);
        d3.setFace(3);
        player.getGrid().setDie(2,0,d3);
        d4.setFace(6);
        player.getGrid().setDie(3,0,d4);
        d5.setFace(1);
        player.getGrid().setDie(0,1,d5);
        player.getGrid().setDie(1,1,d5);
        player.getGrid().setDie(2,1,d5);
        player.getGrid().setDie(3,1,d5);
        player.getGrid().setDie(1,3,d5);
        player.getGrid().setDie(1,4,d5);

        assertEquals(test.getMultiplier(),test.score(player));

    }

    @Test
    public void testEmptyGrid()
    {

        player = new Player("Leman Russ", 6);

        assertEquals(0,test.score(player));

    }
}
