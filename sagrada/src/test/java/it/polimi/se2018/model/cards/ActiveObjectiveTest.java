package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ActiveObjectiveTest {
    private ActiveObjectives test = new ActiveObjectives();

    @Test
    public void testGetObjective()
    {
        assertNotEquals(null,test.getObjective(0));
        assertEquals(null,test.getObjective(5));
    }

    @Test
    public void testTotalScore()
    {
        Player player = new Player("Cypher", 0);
        assertEquals(0,test.totalScore(player));
    }
}
