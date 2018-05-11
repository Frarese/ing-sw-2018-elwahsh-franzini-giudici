package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShadeVarietyTest {

    private ShadeVariety test = new ShadeVariety();
    private Player player;


    @Test
    public void testScore()
    {
        Die d1,d2,d3,d4,d5,d6;
        d1 = new Die(ColorModel.RED);
        d1.setFace(1);
        d2 = new Die(ColorModel.RED);
        d2.setFace(2);
        d3 = new Die(ColorModel.RED);
        d3.setFace(3);
        d4 = new Die(ColorModel.RED);
        d4.setFace(4);
        d5 = new Die(ColorModel.RED);
        d5.setFace(5);
        d6 = new Die(ColorModel.RED);
        d6.setFace(6);

        player = new Player("Roboute Guilliman", 13);
        player.getGrid().setDie(0,0, d1);
        player.getGrid().setDie(1,1, d2);
        player.getGrid().setDie(2,2, d3);
        player.getGrid().setDie(3,3, d4);
        player.getGrid().setDie(2,1, d5);
        player.getGrid().setDie(1,2, d6);
        player.getGrid().setDie(1,3, d3);
        player.getGrid().setDie(3,1, d3);
        player.getGrid().setDie(1,4, d2);
        player.getGrid().setDie(2,3, d1);
        player.getGrid().setDie(2,4, new Die(ColorModel.RED));

        assertEquals(test.getMultiplier(),test.score(player));

    }

    @Test
    public void testEmptyGrid()
    {

        player = new Player("Roboute Guilliman", 13);

        assertEquals(0,test.score(player));

    }
}
