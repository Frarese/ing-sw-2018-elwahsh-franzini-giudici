package it.polimi.se2018.observable;

import it.polimi.se2018.model.IntColorPair;
import org.junit.Before;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for RoundTrackerView class
 *
 * @author Mathyas Giudici
 */

public class RoundTrackerViewTest {

    /**
     * Mock class to observe RoundTrackerView
     */
    private class ObjectObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof RoundTrackerView) {
                assertEquals(round, ((RoundTrackerView) arg).getRound());
                assertArrayEquals(roundTracker, ((RoundTrackerView) arg).getRoundTracker());
            }
        }
    }

    private int round;
    private IntColorPair[][] roundTracker;

    private RoundTrackerView roundTrackerView;

    /**
     * Creates a round, RoundTracker and observes its RoundTrackerView
     */
    @Before
    public void testInit() {
        round = 0;
        roundTracker = new IntColorPair[1][1];

        roundTrackerView = new RoundTrackerView(round, roundTracker);
        ObjectObserver objectObserver = new ObjectObserver();
        roundTrackerView.addObserver(objectObserver);
    }

    /**
     * Tests getter method of round
     */
    @Test
    public void testGetRound() {
        assertEquals(round, roundTrackerView.getRound());

    }

    /**
     * Tests setter method of round with correct update
     */
    @Test
    public void testSetRound() {
        round = 10;
        roundTrackerView.setRound(round);
    }

    /**
     * Tests getter method of RoundTracker
     */
    @Test
    public void testGetRoundTracker() {
        assertArrayEquals(roundTracker, roundTrackerView.getRoundTracker());
    }


    /**
     * Tests setter method of RoundTracker with correct update
     */
    @Test
    public void testSetRoundTracker() {
        roundTracker = null;
        roundTrackerView.setRoundTracker(null);
    }
}