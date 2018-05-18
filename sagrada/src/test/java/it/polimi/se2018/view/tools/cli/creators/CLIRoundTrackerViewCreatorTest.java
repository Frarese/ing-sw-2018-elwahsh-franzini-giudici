package it.polimi.se2018.view.tools.cli.creators;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
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

    private Pair<Integer, ColorModel>[][] roundTracker;

    private CLIRoundTrackerViewCreator cliRoundTrackerViewCreator;

    @Before
    public void testInit() {
        round = 1;
        roundTracker = new Pair[2][2];
        roundTracker[0][0] = new Pair<>(1, ColorModel.RED);
        roundTracker[0][1] = new Pair<>(2, ColorModel.RED);
        cliRoundTrackerViewCreator = new CLIRoundTrackerViewCreator(round, roundTracker);
    }

    @Test
    public void displayTest() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Siamo al turno: 1");
        strings.add("Turno 1 : ");
        strings.add("0) 1-RED");
        strings.add("1) 2-RED");
        assertEquals(strings, cliRoundTrackerViewCreator.display());
    }

    @Test
    public void getRoundTest() {
        assertEquals(round,cliRoundTrackerViewCreator.getRound());
    }

    @Test
    public void setRoundTest() {
        this.round = 2;
        this.cliRoundTrackerViewCreator.setRound(round);
        assertEquals(round,cliRoundTrackerViewCreator.getRound());
    }


    @Test
    public void getRoundTrackerTest() {
        assertArrayEquals(roundTracker,cliRoundTrackerViewCreator.getRoundTracker());
    }

    @Test
    public void setRoundTrackerTest(){
        this.roundTracker = new Pair[1][1];
        cliRoundTrackerViewCreator.setRoundTracker(roundTracker);
        assertArrayEquals(roundTracker,cliRoundTrackerViewCreator.getRoundTracker());
    }
}
