package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Test for CopperFoilBurnisher class
 * @author Alì El wahsh
 */
public class CopperFoilBurnisherTest {

    private final CopperFoilBurnisher test = new CopperFoilBurnisher();

    /**
     * Test the usability check
     */
    @Test
    public void testIsUsable()
    {
        Player player = new Player("Commissar", 0);
        assertFalse(test.isUsable(player,true));
    }

}