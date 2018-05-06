package it.polimi.se2018.view.view_util;

/**
 * Interface to define ReserveViewCreator's Object
 *
 * @param <E>
 * @author Mathyas Giudici
 */

public interface ReserveViewCreator<E> {

    /**
     * Use to show the current reserve
     *
     * @return grid
     */
    E display();

    /**
     * Use to pick a die from the grid
     *
     * @param index contains the index position in the reserve
     * @return die
     */
    E pickDie(int index);
}
