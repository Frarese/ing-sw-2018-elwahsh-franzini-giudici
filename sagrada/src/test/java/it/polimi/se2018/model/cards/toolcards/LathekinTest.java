package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Test for the Lathekin class
 * @author Alì El wahsh
 */
public class LathekinTest {

    private final Lathekin test = new Lathekin();

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