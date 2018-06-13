package it.polimi.se2018.view.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.model.IntColorPair;
import it.polimi.se2018.observable.RoundTrackerView;
import it.polimi.se2018.view.app.App;
import it.polimi.se2018.view.app.CLIApp;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for RoundTrackerViewObserver class
 *
 * @author Mathyas Giudici
 */

public class RoundTrackerViewObserverTest {

    @Test
    public void update() {
        App app = new CLIApp(null, null, null);

        IntColorPair[][] roundTracker = new IntColorPair[9][10];
        roundTracker[0][0] = new IntColorPair(1, ColorModel.RED);
        roundTracker[0][1] = new IntColorPair(6, ColorModel.GREEN);

        RoundTrackerView roundTrackerView = new RoundTrackerView(5, roundTracker);

        RoundTrackerViewObserver roundTrackerViewObserver = new RoundTrackerViewObserver(app);
        roundTrackerViewObserver.update(roundTrackerView, null);

        assertEquals(5, app.getRoundTrackerViewCreator().getRound());
        assertArrayEquals(roundTracker, app.getRoundTrackerViewCreator().getRoundTracker());
    }
}