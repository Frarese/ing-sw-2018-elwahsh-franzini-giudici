package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Test for RunningPliers class
 * @author Alì El wahsh
 */
public class RunningPliersTest {

    private final RunningPliers test = new RunningPliers();

    /**
     * Test the usability check
     */
    @Test
    public void testIsUsable()
    {
        Player player = new Player("Commissar", 0);
        player.setPlacementRights(true,false);
        assertTrue(test.isUsable(player,true));
    }

}