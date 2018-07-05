package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the ActiveObjective class
 * @author Alì El wahsh
 */
public class ActiveObjectiveTest {
    private final ActiveObjectives test = new ActiveObjectives();

    /**
     * Test for the public objective retrieval
     */
    @Test
    public void testGetObjective()
    {
        assertNotNull(test.getObjective(0));
        assertNull(test.getObjective(5));
    }

    /**
     * Test for the total public score calculation
     */
    @Test
    public void testTotalScore()
    {
        Player player = new Player("Cypher", 0);
        assertEquals(0,test.totalScore(player));
    }
}
