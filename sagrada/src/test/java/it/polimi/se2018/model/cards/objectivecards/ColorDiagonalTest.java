package it.polimi.se2018.model.cards.objectivecards;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.dice.Die;
import org.junit.Test;

import static it.polimi.se2018.model.ColorModel.RED;
import static org.junit.Assert.*;


import static it.polimi.se2018.model.ColorModel.BLUE;
import static it.polimi.se2018.model.ColorModel.YELLOW;

public class ColorDiagonalTest {

    private ColorDiagonal test = new ColorDiagonal();
    private Player player;


    @Test
    public void testScore()
    {
        player = new Player("Alpahrius", 20);
        player.getGrid().setDie(2,0, new Die(BLUE));
        player.getGrid().setDie(1,1, new Die(BLUE));
        player.getGrid().setDie(2,2, new Die(BLUE));
        player.getGrid().setDie(3,3, new Die(BLUE));
        player.getGrid().setDie(1,4, new Die(YELLOW));
        player.getGrid().setDie(3,2, new Die(YELLOW));
        player.getGrid().setDie(2,3, new Die(YELLOW));
        player.getGrid().setDie(0,0, new Die(RED));


        assertEquals(7,test.score(player));
    }


    @Test
    public void testEmptyGrid()
    {

        player = new Player("Omegon", 20);

        assertEquals(0,test.score(player));

    }
}