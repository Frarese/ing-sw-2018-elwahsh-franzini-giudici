package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class FluxBrushTest {

    private final FluxBrush test = new FluxBrush();

    @Test
    public void testIsUsable()
    {
        Player player = new Player("Commissar", 0);
        assertTrue(test.isUsable(player,true));
    }

}