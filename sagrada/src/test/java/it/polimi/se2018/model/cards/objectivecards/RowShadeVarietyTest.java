package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RowShadeVarietyTest {
    private final RowShadeVariety test = new RowShadeVariety();
    private Player player;


    @Test
    public void testScore()
    {
        Die d1,d2,d3,d4,d5;
        player = new Player("Fulgrim", 3);
        d1 = new Die(ColorModel.RED);
        d2 = new Die(ColorModel.RED);
        d3 = new Die(ColorModel.RED);
        d4 = new Die(ColorModel.RED);
        d5 = new Die(ColorModel.RED);
        d1.setFace(1);
        player.getGrid().setDie(0,0,d1);
        d2.setFace(2);
        player.getGrid().setDie(0,1,d2);
        d3.setFace(3);
        player.getGrid().setDie(0,2,d3);
        d4.setFace(6);
        player.getGrid().setDie(0,3,d4);
        d5.setFace(4);
        player.getGrid().setDie(0,4,d5);

        player.getGrid().setDie(1,0,d5);
        player.getGrid().setDie(1,1,d5);
        player.getGrid().setDie(1,2,d5);
        player.getGrid().setDie(1,3,d5);
        player.getGrid().setDie(1,4,d5);

        assertEquals(test.getMultiplier(),test.score(player));

    }

    @Test
    public void testEmptyGrid()
    {

        player = new Player("Fulgrim", 3);

        assertEquals(0,test.score(player));

    }
}
