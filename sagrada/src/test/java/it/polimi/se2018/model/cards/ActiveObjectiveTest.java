package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActiveObjectiveTest {
    private ActiveObjectives test = new ActiveObjectives();

    @Test
    public void testGetObjective()
    {
        assertNotNull(test.getObjective(0));
        assertNull(test.getObjective(5));
    }

    @Test
    public void testTotalScore()
    {
        Player player = new Player("Cypher", 0);
        assertEquals(0,test.totalScore(player));
    }
}
