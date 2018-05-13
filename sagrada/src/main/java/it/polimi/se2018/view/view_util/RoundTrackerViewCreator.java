package it.polimi.se2018.view.view_util;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * Interface to define RoundTrackerViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public abstract class RoundTrackerViewCreator<E> {

    protected DieViewCreator dieViewCreator;

    protected int round;

    protected Pair<Integer, ColorModel>[][] roundTracker;

    /**
     * Class constructor
     * @param round       contains the round
     * @param roundTracker contains the round tracker
     */
    public RoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] roundTracker) {
        this.round = round;
        this.roundTracker = roundTracker;
    }

    /**
     * Use to show the current round tracker
     *
     * @return round tracker
     */
    public abstract E display();

    /**
     * Getter for round
     *
     * @return the current round
     */
    public int getRound() {
        return round;
    }

    /**
     * Setter for round
     *
     * @param round contains the round to set
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Getter for round tracker
     *
     * @return the round tracker
     */
    public Pair<Integer, ColorModel>[][] getRoundTracker() {
        return roundTracker;
    }

    /**
     * Setter for round tracker
     *
     * @param roundTracker contains the round tracker to set
     */
    public void setRoundTracker(Pair<Integer, ColorModel>[][] roundTracker) {
        this.roundTracker = roundTracker;
    }
}
