package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.util.ScoreEntry;
import it.polimi.se2018.view.tools.cli.creators.CLIScoreViewCreator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CLIScoreViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLIScoreViewCreatorTest {

    private ArrayList<String> result;

    private List scores;

    private CLIScoreViewCreator scoreViewCreator;

    @Before
    public void testInit() {
        scoreViewCreator = new CLIScoreViewCreator();
        result = new ArrayList<>();
        scores = new ArrayList();
        scores.add(new ScoreEntry("Marco",2,0));
        scores.add(new ScoreEntry("Luca",1,0));
        result.add("Marco, Punti 2");
        result.add("Luca, Punti 1");
    }

    @Test
    public void displayTest() {
        assertEquals(result,scoreViewCreator.display(scores));
    }
}