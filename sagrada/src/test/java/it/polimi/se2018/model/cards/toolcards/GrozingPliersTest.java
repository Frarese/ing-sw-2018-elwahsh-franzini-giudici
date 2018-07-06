package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Test for GrozingPliers class
 * @author Alì El wahsh
 */
public class GrozingPliersTest {

    private final GrozingPliers test = new GrozingPliers();

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