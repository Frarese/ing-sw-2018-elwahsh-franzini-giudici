package it.polimi.se2018.observable;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

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
    private Pair<Integer, ColorModel>[][] roundTracker;

    public RoundTrackerView(int round, Pair<Integer, ColorModel>[][] roundTracker) {
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

    public Pair<Integer, ColorModel>[][] getRoundTracker() {
        return roundTracker;
    }

    public void setRoundTracker(Pair<Integer, ColorModel>[][] roundTracker) {
        this.roundTracker = roundTracker;
    }

    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
