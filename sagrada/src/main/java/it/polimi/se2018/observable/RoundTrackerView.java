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

    /**
     * Constructor
     * @param round actual round
     * @param roundTracker round track dice
     */
    public RoundTrackerView(int round, IntColorPair[][] roundTracker) {
        this.round = round;
        this.roundTracker = roundTracker;
        this.uniqueNotify();
    }

    /**
     * Getter for the actual round
     * @return actual round
     */
    public int getRound() {
        return round;
    }

    /**
     * Setter for the actual round
     * @param round actual round's new value
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Getter for the Round track dice
     * @return round track dice
     */
    public IntColorPair[][] getRoundTracker() {
        return roundTracker;
    }

    /**
     * Setter for the round track dice
     * @param roundTracker new dice status
     */
    public void setRoundTracker(IntColorPair[][] roundTracker) {
        this.roundTracker = roundTracker;
    }

    /**
     * Notifies all observers
     */
    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
