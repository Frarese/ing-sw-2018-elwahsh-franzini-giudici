package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColorVarietyTest {

    private final ColorVariety test = new ColorVariety();
    private Player player;


    @Test
    public void testScore()
    {
        player = new Player("Lion El' Johnson", 1);
        player.getGrid().setDie(0,0, new Die(ColorModel.RED));
        player.getGrid().setDie(1,1, new Die(ColorModel.BLUE));
        player.getGrid().setDie(2,2, new Die(ColorModel.GREEN));
        player.getGrid().setDie(3,3, new Die(ColorModel.YELLOW));
        player.getGrid().setDie(2,1, new Die(ColorModel.VIOLET));
        player.getGrid().setDie(1,2, new Die(ColorModel.RED));
        player.getGrid().setDie(1,3, new Die(ColorModel.BLUE));
        player.getGrid().setDie(3,1, new Die(ColorModel.GREEN));
        player.getGrid().setDie(1,4, new Die(ColorModel.YELLOW));
        player.getGrid().setDie(2,3, new Die(ColorModel.VIOLET));
        player.getGrid().setDie(2,4, new Die(ColorModel.RED));

        assertEquals(2*test.getMultiplier(),test.score(player));

    }

    @Test
    public void testEmptyGrid()
    {

        player = new Player("Lion El' Johnson", 1);

        assertEquals(0,test.score(player));

    }
}
