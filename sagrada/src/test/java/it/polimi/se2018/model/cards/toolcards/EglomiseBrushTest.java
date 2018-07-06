package it.polimi.se2018.model.cards.toolcards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Test for EglomiseBrush class
 * @author Alì El wahsh
 */
public class EglomiseBrushTest {

    private final EglomiseBrush test = new EglomiseBrush();

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