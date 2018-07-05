package it.polimi.se2018.observable;

import it.polimi.se2018.model.IntColorPair;

import java.util.Observable;

/**
 * This class represents an observable of Reserve
 *
 * @author Mathyas Giudici
 */

public class ReserveView extends Observable {

    /**
     * Class attributes
     */
    private IntColorPair[] reserve;

    /**
     * Constructor
     * @param reserve reserve status
     */
    public ReserveView(IntColorPair[] reserve) {
        this.reserve = reserve;
        this.uniqueNotify();
    }

    /**
     * Getter for the reserve
     * @return reserve
     */
    public IntColorPair[] getReserve() {
        return reserve;
    }

    /**
     * Setter for the reserve
     * @param reserve reserve new status
     */
    public void setReserve(IntColorPair[] reserve) {
        this.reserve = reserve;
    }

    /**
     *  Notifies all observers
     */
    public void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
