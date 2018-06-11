package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class GrindingStoneTest {

    private final GrindingStone test = new GrindingStone();

    @Test
    public void testIsUsable()
    {
        Player player = new Player("Commissar", 0);
        assertFalse(test.isUsable(player,true));
    }

}