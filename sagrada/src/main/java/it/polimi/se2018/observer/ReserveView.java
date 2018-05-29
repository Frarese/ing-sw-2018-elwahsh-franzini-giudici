package it.polimi.se2018.observer;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * This class represents an observer of Reserve
 *
 * @author Mathyas Giudici
 */

public class ReserveView {

    /**
     * Class attributes
     */
    private boolean hasChanged;

    private Pair<Integer, ColorModel>[] reserve;

    public ReserveView(boolean hasChanged, Pair<Integer, ColorModel>[] reserve) {
        this.hasChanged = hasChanged;
        this.reserve = reserve;
    }

    public boolean isHasChanged() {
        return hasChanged;
    }

    public Pair<Integer, ColorModel>[] getReserve() {
        return reserve;
    }
}
