package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test for LensCutter class
 * @author Alì El wahsh
 */
public class LensCutterTest {

    private final LensCutter test = new LensCutter();

    /**
     * Test the usability check
     */
    @Test
    public void testIsUsable()
    {
        Player player = new Player("Commissar", 0);
        assertTrue(test.isUsable(player,true));
    }

}