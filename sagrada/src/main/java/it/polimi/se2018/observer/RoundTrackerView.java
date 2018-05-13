package it.polimi.se2018.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * This class represents an observer of Round Tracker
 *
 * @author Mathyas Giudici
 */

public class RoundTrackerView {

    private boolean hasChanged;

    public int getRound() {
        throw new UnsupportedOperationException();
    }

    public Pair<Integer, ColorModel>[][] getRoundTracker() {
        throw new UnsupportedOperationException();
    }
}
