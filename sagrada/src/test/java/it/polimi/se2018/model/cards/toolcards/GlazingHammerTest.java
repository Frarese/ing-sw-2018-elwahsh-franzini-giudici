package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Test for GlazingHammer class
 * @author Alì El wahsh
 */
public class GlazingHammerTest {

    private final GlazingHammer test = new GlazingHammer();

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