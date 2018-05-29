package it.polimi.se2018.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * This class represents an observer of Round Tracker
 *
 * @author Mathyas Giudici
 */

public class RoundTrackerView {

    /**
     * Class attributes
     */
    private boolean hasChanged;

    private int round;
    private Pair<Integer, ColorModel>[][] roundTracker;

    public RoundTrackerView(boolean hasChanged, int round, Pair<Integer, ColorModel>[][] roundTracker) {
        this.hasChanged = hasChanged;
        this.round = round;
        this.roundTracker = roundTracker;
    }

    public boolean isHasChanged() {
        return hasChanged;
    }

    public int getRound() {
        return round;
    }

    public Pair<Integer, ColorModel>[][] getRoundTracker() {

        return roundTracker;
    }
}
