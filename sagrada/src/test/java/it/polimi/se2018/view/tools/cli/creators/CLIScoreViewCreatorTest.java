package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.util.MatchIdentifier;
import it.polimi.se2018.util.ScoreEntry;
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
        scores.add(new ScoreEntry("Marco", 2, 0));
        scores.add(new ScoreEntry("Luca", 1, 0));
        result.add("Luca, Punti 2");
        result.add("Marco, Punti 1");
    }

    @Test
    public void testDisplay() {
        MatchIdentifier matchIdentifier = new MatchIdentifier("Luca", "Marco", null, null);
        assertEquals(result, scoreViewCreator.display(matchIdentifier, 2, 1, 0, 0));
    }
}