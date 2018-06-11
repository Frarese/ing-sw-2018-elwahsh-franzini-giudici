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

    @Before
    public void testInit() {
        round = 1;
        roundTracker = new IntColorPair[9][10];
        roundTracker[0][0] = new IntColorPair(1, ColorModel.RED);
        roundTracker[1][0] = new IntColorPair(2, ColorModel.RED);
        cliRoundTrackerViewCreator = new CLIRoundTrackerViewCreator(round, roundTracker);
    }

    @Test
    public void testDisplay() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Siamo al turno: 1");
        strings.add("Turno 1 : ");
        strings.add("0) 1-RED");
        strings.add("1) 2-RED");
        assertEquals(strings, cliRoundTrackerViewCreator.display());
    }

    @Test
    public void testGetRound() {
        assertEquals(round,cliRoundTrackerViewCreator.getRound());
    }

    @Test
    public void testSetRound() {
        this.round = 2;
        this.cliRoundTrackerViewCreator.setRound(round);
        assertEquals(round,cliRoundTrackerViewCreator.getRound());
    }


    @Test
    public void testGetRoundTracker() {
        assertArrayEquals(roundTracker,cliRoundTrackerViewCreator.getRoundTracker());
    }

    @Test
    public void testSetRoundTracker(){
        this.roundTracker = new IntColorPair[1][1];
        cliRoundTrackerViewCreator.setRoundTracker(roundTracker);
        assertArrayEquals(roundTracker,cliRoundTrackerViewCreator.getRoundTracker());
    }
}
