package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.util.MatchIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for CLIScoreViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLIScoreViewCreatorTest {

    private ArrayList<String> result;

    private CLIScoreViewCreator scoreViewCreator;

    @Before
    public void testInit() {
        scoreViewCreator = new CLIScoreViewCreator();
        result = new ArrayList<>();
        result.add("Luca, Punti 4");
        result.add("Marco, Punti 3");
        result.add("Matteo, Punti 1");
        result.add("Enzo, Punti 0");
    }

    @Test
    public void testDisplay() {
        MatchIdentifier matchIdentifier = new MatchIdentifier("Enzo", "Luca", "Marco", "Matteo");
        assertEquals(result, scoreViewCreator.display(matchIdentifier, 0, 4, 3, 1));
    }
}