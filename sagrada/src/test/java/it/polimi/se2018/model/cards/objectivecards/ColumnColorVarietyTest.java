package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColumnColorVarietyTest {

    private ColumnColorVariety test = new ColumnColorVariety();
    private Player player;


    @Test
    public void testScore()
    {
        player = new Player("Jaghatai Khan", 5);
        player.getGrid().setDie(0,0, new Die(ColorModel.RED));
        player.getGrid().setDie(1,0, new Die(ColorModel.YELLOW));
        player.getGrid().setDie(2,0, new Die(ColorModel.BLUE));
        player.getGrid().setDie(3,0, new Die(ColorModel.GREEN));
        player.getGrid().setDie(0,4, new Die(ColorModel.RED));
        player.getGrid().setDie(1,4, new Die(ColorModel.RED));
        player.getGrid().setDie(2,4, new Die(ColorModel.RED));
        player.getGrid().setDie(3,4, new Die(ColorModel.RED));


        assertEquals(test.getMultiplier(),test.score(player));
    }

    @Test
    public void testEmptyGrid()
    {

        player = new Player("Jaghatai Khan", 5);

        assertEquals(0,test.score(player));

    }
}
