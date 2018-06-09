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

    public ReserveView(IntColorPair[] reserve) {
        this.reserve = reserve;
        this.uniqueNotify();
    }

    public IntColorPair[] getReserve() {
        return reserve;
    }

    public void setReserve(IntColorPair[] reserve) {
        this.reserve = reserve;
    }

    public synchronized void uniqueNotify() {
        setChanged();
        notifyObservers(this);
    }
}
