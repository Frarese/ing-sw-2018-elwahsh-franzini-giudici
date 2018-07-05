package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for CLIRoundTrackerViewCreator class
 *
 * @author Mathyas Giudici
 */

public class CLIRoundTrackerViewCreatorTest {

    private int round;

    private IntColorPair[][] roundTracker;

    private CLIRoundTrackerViewCreator cliRoundTrackerViewCreator;

    /**
     * Creates RoundTracker and new CLIRoundTrackerViewCreator
     */
    @Before
    public void testInit() {
        round = 1;
        roundTracker = new IntColorPair[9][10];
        roundTracker[0][0] = new IntColorPair(1, ColorModel.RED);
        roundTracker[1][0] = new IntColorPair(2, ColorModel.RED);
        cliRoundTrackerViewCreator = new CLIRoundTrackerViewCreator(round, roundTracker);
    }

    /**
     * Checks correct CLI' RoundTracker object creation
     */
    @Test
    public void testDisplay() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Siamo al turno: 1");
        strings.add("Turno 1 : ");
        strings.add("0) 1-RED");
        strings.add("1) 2-RED");
        assertEquals(strings, cliRoundTrackerViewCreator.display());
    }

    /**
     * Tests getter method of the round
     */
    @Test
    public void testGetRound() {
        assertEquals(round, cliRoundTrackerViewCreator.getRound());
    }

    /**
     * Tests setter method of the round
     */
    @Test
    public void testSetRound() {
        this.round = 2;
        this.cliRoundTrackerViewCreator.setRound(round);
        assertEquals(round, cliRoundTrackerViewCreator.getRound());
    }

    /**
     * Tests getter method of the RoundTracker
     */
    @Test
    public void testGetRoundTracker() {
        assertArrayEquals(roundTracker, cliRoundTrackerViewCreator.getRoundTracker());
    }

    /**
     * Tests setter method of the RoundTracker
     */
    @Test
    public void testSetRoundTracker() {
        this.roundTracker = new IntColorPair[1][1];
        cliRoundTrackerViewCreator.setRoundTracker(roundTracker);
        assertArrayEquals(roundTracker, cliRoundTrackerViewCreator.getRoundTracker());
    }
}
