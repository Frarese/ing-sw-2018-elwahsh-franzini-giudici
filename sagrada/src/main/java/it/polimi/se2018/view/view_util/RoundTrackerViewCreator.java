package it.polimi.se2018.view.view_util;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * Interface to define RoundTrackerViewCreator's Object
 *
 * @param <E>
 * @author Mathyas Giudici
 */

public abstract class RoundTrackerViewCreator<E> {

    protected DieViewCreator dieViewCreator;

    protected int round;

    protected Pair<Integer, ColorModel>[][] rondTracker;

    /**
     * Class constructor
     *
     * @param round       contains the round
     * @param rondTracker contains the round tracker
     */
    public RoundTrackerViewCreator(int round, Pair<Integer, ColorModel>[][] rondTracker) {
        this.round = round;
        this.rondTracker = rondTracker;
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
    public Pair<Integer, ColorModel>[][] getRondTracker() {
        return rondTracker;
    }

    /**
     * Setter for round tracker
     *
     * @param rondTracker contains the round tracker to set
     */
    public void setRondTracker(Pair<Integer, ColorModel>[][] rondTracker) {
        this.rondTracker = rondTracker;
    }
}
