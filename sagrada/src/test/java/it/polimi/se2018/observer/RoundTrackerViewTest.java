package it.polimi.se2018.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for ReserveView class
 *
 * @author Mathyas Giudici
 */

public class RoundTrackerViewTest {

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
    private Pair<Integer, ColorModel>[][] roundTracker;

    private RoundTrackerView roundTrackerView;

    @Before
    public void testInit() {
        round = 0;
        roundTracker = new Pair[1][1];

        roundTrackerView = new RoundTrackerView(round, roundTracker);
        ObjectObserver objectObserver = new ObjectObserver();
        roundTrackerView.addObserver(objectObserver);
    }

    @Test
    public void testGetRound() {
        assertEquals(round, roundTrackerView.getRound());

    }

    @Test
    public void testSetRound() {
        round = 10;
        roundTrackerView.setRound(round);
    }

    @Test
    public void testGetRoundTracker() {
        assertArrayEquals(roundTracker, roundTrackerView.getRoundTracker());
    }

    @Test
    public void testSetRoundTracker() {
        roundTracker = null;
        roundTrackerView.setRoundTracker(null);
    }
}