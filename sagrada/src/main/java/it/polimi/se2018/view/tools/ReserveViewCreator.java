package it.polimi.se2018.view.tools;

import it.polimi.se2018.model.IntColorPair;

/**
 * Class to define ReserveViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public abstract class ReserveViewCreator<E> {

    protected DieViewCreator dieViewCreator;

    protected IntColorPair[] reserve;

    /**
     * Basic Class constructor that initializes elements at default value
     */
    protected ReserveViewCreator() {
        this.reserve = null;
    }

    /**
     * Class constructor
     *
     * @param reserve contains the reserve
     */
    protected ReserveViewCreator(IntColorPair[] reserve) {
        this.reserve = reserve;
    }

    /**
     * Use to show the current reserve
     *
     * @return grid
     */
    public abstract E display();

    /**
     * Getter for reserve
     *
     * @return the reserve
     */
    public IntColorPair[] getReserve() {
        return reserve;
    }

    /**
     * Setter for reserve
     *
     * @param reserve contains the reserve to set
     */
    public void setReserve(IntColorPair[] reserve) {
        this.reserve = reserve;
    }
}
