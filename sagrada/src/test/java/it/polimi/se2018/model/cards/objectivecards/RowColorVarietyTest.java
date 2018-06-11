package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RowColorVarietyTest {

    private final RowColorVariety test = new RowColorVariety();
    private Player player;

    @Test
    public void testScore()
    {
        player = new Player("Regal Dorn", 7);
        player.getGrid().setDie(0,0, new Die(ColorModel.RED));
        player.getGrid().setDie(0,1, new Die(ColorModel.YELLOW));
        player.getGrid().setDie(0,2, new Die(ColorModel.BLUE));
        player.getGrid().setDie(0,3, new Die(ColorModel.GREEN));
        player.getGrid().setDie(0,4, new Die(ColorModel.VIOLET));

        player.getGrid().setDie(1,0, new Die(ColorModel.RED));
        player.getGrid().setDie(1,1, new Die(ColorModel.RED));
        player.getGrid().setDie(1,2, new Die(ColorModel.RED));
        player.getGrid().setDie(1,3, new Die(ColorModel.RED));
        player.getGrid().setDie(1,4, new Die(ColorModel.RED));

        assertEquals(test.getMultiplier(),test.score(player));
    }

    @Test
    public void testEmptyGrid()
    {

        player = new Player("Regal Dorn", 7);

        assertEquals(0,test.score(player));

    }
}
