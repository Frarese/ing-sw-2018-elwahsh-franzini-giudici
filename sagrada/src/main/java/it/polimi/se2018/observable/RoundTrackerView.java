package it.polimi.se2018.observable;

import it.polimi.se2018.model.IntColorPair;

import java.util.Observable;

/**
 * This class represents an observable of Round Tracker
 *
 * @author Mathyas Giudici
 */

public class RoundTrackerView extends Observable {

    /**
     * Class attributes
     */
    private int round;
    private IntColorPair[][] roundTracker;

    public RoundTrackerView(int round, IntColorPair[][] roundTracker) {
        this.round = round;
        this.roundTracker = roundTracker;
        this.uniqueNotify();
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public IntColorPair[][] getRoundTracker() {
        return roundTracker;
    }

    public void setRoundTracker(IntColorPair[][] roundTracker) {
        this.roundTracker = roundTracker;
    }

    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
