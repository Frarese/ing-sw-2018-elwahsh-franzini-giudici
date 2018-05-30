package it.polimi.se2018.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

import java.util.Observable;

/**
 * This class represents an observer of Reserve
 *
 * @author Mathyas Giudici
 */

public class ReserveView extends Observable {

    /**
     * Class attributes
     */
    private Pair<Integer, ColorModel>[] reserve;

    public ReserveView(Pair<Integer, ColorModel>[] reserve) {
        this.reserve = reserve;
        this.uniqueNotify();
    }

    public Pair<Integer, ColorModel>[] getReserve() {
        return reserve;
    }

    public void setReserve(Pair<Integer, ColorModel>[] reserve) {
        this.reserve = reserve;
        this.uniqueNotify();
    }

    private synchronized void uniqueNotify(){
        setChanged();
        notifyObservers(this);
    }
}
