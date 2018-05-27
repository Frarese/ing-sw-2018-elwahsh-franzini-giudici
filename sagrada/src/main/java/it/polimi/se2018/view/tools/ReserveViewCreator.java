package it.polimi.se2018.view.tools;

import it.polimi.se2018.model.ColorModel;
import it.polimi.se2018.util.Pair;

/**
 * Class to define ReserveViewCreator's Object
 *
 * @author Mathyas Giudici
 */

public abstract class ReserveViewCreator<V,E> {

    protected DieViewCreator dieViewCreator;

    protected Pair<Integer, ColorModel>[] reserve;

    /**
     * Class constructor
     *
     * @param reserve contains the reserve
     */
    public ReserveViewCreator(Pair<Integer, ColorModel>[] reserve) {
        this.reserve = reserve;
    }

    /**
     * Use to show the current reserve
     *
     * @return grid
     */
    public abstract V display();

    /**
     * Use to pick a die from the grid
     *
     * @param index contains the index position in the reserve
     * @return die
     */
    public abstract E pickDie(int index);

    /**
     * Getter for reserve
     *
     * @return the reserve
     */
    public Pair<Integer, ColorModel>[] getReserve() {
        return reserve;
    }

    /**
     * Setter for reserve
     *
     * @param reserve contains the reserve to set
     */
    public void setReserve(Pair<Integer, ColorModel>[] reserve) {
        this.reserve = reserve;
    }
}
